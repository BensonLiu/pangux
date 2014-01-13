package com.eadmarket.pangu.component.report;

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
public class ReportConverter extends AbstractReportConverter<ReportDO> {

    @Resource private ReportDao reportDao;

    @Override
    protected List<ReportDO> getReportSources(Long minId) throws DaoException {
        return reportDao.getReportDOsByMinId(minId, pageSize);
    }

    @Override
    protected void mergeReportIntoReportComp(ReportDO reportDO) throws DaoException {
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
                reportCompQueryParam.setAdvertiseId(reportDO.getPositionId());
                reportCompQueryParam.setTimeType(timeType);
                reportCompQueryParam.setTimeValue(date);
                reportCompDao.insertReportComp(reportCompQueryParam);
            }
        }
    }

    @Override
    protected Long updateOffset(ReportDO report) throws DaoException {
        Long minId = report.getId();
        kvDao.updateKV(offsetKey, minId.toString());
        return minId;
    }

}
