package com.eadmarket.pangu.component.report;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.dao.report.ReportInfoDao;
import com.eadmarket.pangu.dao.trade.TradeDao;
import com.eadmarket.pangu.domain.ReportInfoDO;
import com.eadmarket.pangu.domain.TradeDO;
import com.eadmarket.pangu.query.ReportCompQuery;
import org.apache.commons.lang3.time.DateUtils;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * merge report_info表数据到report_comp表
 *
 * @author liuyongpo@gmail.com
 */
class ReportInfoConverter extends AbstractReportConverter<ReportInfoDO> {

    @Resource private ReportInfoDao reportInfoDao;

    @Resource private TradeDao tradeDao;

    @Override
    protected void mergeReportIntoReportComp(ReportInfoDO report) throws DaoException {
        Long tradeId = report.getTradeId();

        int operationType = report.getOperationType();

        Date gmtCreate = report.getGmtCreate();

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
                if (operationType == ReportInfoDO.CLICK_OPT_TYPE) {
                    reportCompQueryParam.setClickNum(1L);
                } else {
                    reportCompQueryParam.setDisplayNum(1L);
                }
                reportCompDao.updateReportCompById(reportCompQueryParam);
            } else {
                ReportCompQuery reportCompQueryParam = new ReportCompQuery();
                if (operationType == ReportInfoDO.CLICK_OPT_TYPE) {
                    reportCompQueryParam.setClickNum(1L);
                } else {
                    reportCompQueryParam.setDisplayNum(1L);
                }
                reportCompQueryParam.setTradeId(tradeId);
                reportCompQueryParam.setProductId(getProductId(tradeId));
                reportCompQueryParam.setAdvertiseId(report.getAdvertiseId());
                reportCompQueryParam.setTimeType(timeType);
                reportCompQueryParam.setTimeValue(date);
                reportCompDao.insertReportComp(reportCompQueryParam);
            }
        }
    }

    @Override
    protected Long updateOffset(ReportInfoDO report) throws DaoException {
        Long minId = report.getId();
        kvDao.updateKV(offsetKey, minId.toString());
        return minId;
    }

    @Override
    protected List<ReportInfoDO> getReportSources(Long minId) throws DaoException {
        return reportInfoDao.getReportInfoDOsByMinId(minId, pageSize);
    }

    private Long getProductId(Long tradeId) throws DaoException {
        TradeDO trade = tradeDao.getById(tradeId);
        return trade.getProductId();
    }
}
