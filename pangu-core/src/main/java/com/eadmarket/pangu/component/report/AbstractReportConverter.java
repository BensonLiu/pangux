package com.eadmarket.pangu.component.report;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.dao.kv.KVDao;
import com.eadmarket.pangu.dao.report.ReportCompDao;
import com.eadmarket.pangu.dao.report.ReportDao;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 其他表数据迁移merge到report_comp
 *
 * @author liuyongpo@gmail.com
 */
public abstract class AbstractReportConverter<T> implements BeanNameAware {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractReportConverter.class);

    private static final int DEFAULT_PAGE_SIZE = 100;

    protected static final int[] TIME_TYPES = new int[]{2, 3, 4};

    @Resource protected KVDao kvDao;

    @Resource protected ReportCompDao reportCompDao;

    @Setter private volatile boolean interrupt = false;

    @Setter protected String offsetKey;

    @Setter protected int pageSize = DEFAULT_PAGE_SIZE;

    @Setter private String beanName;

    /**
     * merge操作是否正在执行，防止多个线程同时执行
     */
    private AtomicBoolean executing = new AtomicBoolean(false);

    public void convert() {

        if (!executing.compareAndSet(false, true)) {
            LOG.warn("{} converter is executing, return now", beanName);
            return;
        }

        try {
            LOG.warn("{} begin to convert", beanName);

            Long minId = initOffset();
            while (!interrupt) {
                List<T> reportDOs = getReportSources(minId);
                if (CollectionUtils.isEmpty(reportDOs)) {
                    LOG.warn("{} report convert completed", beanName);
                    return;
                }
                for (T report : reportDOs) {
                    /*
                     * 此处没考虑事务，因为报表偶尔错个数据也不影响大局
                     */
                    mergeReportIntoReportComp(report);
                    minId = updateOffset(report);
                }
                holdSometime();
            }
        } catch (DaoException ex) {
            LOG.error("AbstractReportConverter", ex);
        } finally {
            executing.set(false);
        }

    }

    protected void holdSometime() {
        try {
            TimeUnit.MILLISECONDS.sleep(10L);
        } catch (InterruptedException ex) {
            LOG.error("report transfer sleep interrupted", ex);
        }
    }

    private Long initOffset() throws DaoException {
        String minIdStr = kvDao.getByKey(offsetKey);

        Long minId = 0L;

        if (StringUtils.isNotBlank(minIdStr)) {
            minId = Long.valueOf(minIdStr);
        } else {
            kvDao.insertKV(offsetKey, minId.toString());
        }
        return minId;
    }

    protected abstract void mergeReportIntoReportComp(T report) throws DaoException;

    protected abstract Long updateOffset(T report) throws DaoException;

    protected abstract List<T> getReportSources(Long minId) throws DaoException;
}
