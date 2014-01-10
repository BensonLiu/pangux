package com.eadmarket.pangu.temp;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.dao.kv.KVDao;
import com.eadmarket.pangu.dao.report.ReportCompDao;
import com.eadmarket.pangu.dao.report.ReportDao;
import com.eadmarket.pangu.domain.ReportDO;
import com.eadmarket.pangu.query.ReportCompQuery;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * report表数据迁移merge到report_comp
 *
 * @author liuyongpo@gmail.com
 */
public class ReportTransfer {

    private static final Logger LOG = LoggerFactory.getLogger(ReportTransfer.class);

    private static final String REPORT_TRANSFER_MIN_ID = "report_transfer_min_id";

    private static final int PAGE_SIZE = 100;

    private static final int[] TIME_TYPES = new int[]{2, 3, 4};

    @Resource private KVDao kvDao;

    @Resource private ReportCompDao reportCompDao;

    @Resource private ReportDao reportDao;

    @Setter private volatile boolean interrupt = false;

    public void convert() throws DaoException {

        String minIdStr = kvDao.getByKey(REPORT_TRANSFER_MIN_ID);

        Long minId = 0L;

        if (StringUtils.isNotBlank(minIdStr)) {
            minId = Long.valueOf(minIdStr);
        }

        while (!interrupt) {
            List<ReportDO> reportDOs = reportDao.getReportDOsByMinId(minId, PAGE_SIZE);
            if (CollectionUtils.isEmpty(reportDOs)) {
                LOG.warn("report transfer completed");
                return;
            }

            for (ReportDO reportDO : reportDOs) {
                mergeReportIntoReportComp(reportDO);
                minId = reportDO.getId();
                kvDao.updateKV(REPORT_TRANSFER_MIN_ID, minId.toString());
            }

            try {
                TimeUnit.MILLISECONDS.sleep(10L);
            } catch (InterruptedException ex) {
                LOG.error("report transfer sleep interrupted", ex);
            }
        }

    }

    private void mergeReportIntoReportComp(ReportDO reportDO) throws DaoException {
        Long tradeId = reportDO.getTradeId();

        Long click = reportDO.getClick();

        Long impression = reportDO.getImpression();

        Date gmtCreate = reportDO.getGmtCreate();

        ReportCompQuery reportCompQuery = new ReportCompQuery();
        reportCompQuery.setTradeId(tradeId);

        for (int timeType : TIME_TYPES) {
            Date date;
            if (timeType == 2) {
                date = DateUtils.truncate(gmtCreate, Calendar.MONTH);
            } else if (timeType == 3) {
                date = DateUtils.truncate(gmtCreate, Calendar.DATE);
            } else {
                date = DateUtils.truncate(gmtCreate, Calendar.HOUR_OF_DAY);
            }
            reportCompQuery.setTimeType(timeType);
            reportCompQuery.setTimeValue(date);

            Long id = reportCompDao.getIdByReportCompQuery(reportCompQuery);
            if (id != null) {
                ReportCompQuery reportCompQueryParam = new ReportCompQuery();
                reportCompQueryParam.setId(id);
                reportCompQueryParam.setClickNum(click);
                reportCompQueryParam.setDisplayNum(impression);
                reportCompDao.updateReportCompById(reportCompQueryParam);
            } else {
                ReportCompQuery reportCompQueryParam = new ReportCompQuery();
                reportCompQueryParam.setClickNum(click);
                reportCompQueryParam.setDisplayNum(impression);
                reportCompQueryParam.setTradeId(reportDO.getTradeId());
                reportCompQueryParam.setProductId(reportDO.getProductId());
                reportCompDao.insertReportComp(reportCompQueryParam);
            }
        }
    }

}
