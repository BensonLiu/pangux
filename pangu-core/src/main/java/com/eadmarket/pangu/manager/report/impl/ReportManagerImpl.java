package com.eadmarket.pangu.manager.report.impl;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.ExceptionCode;
import com.eadmarket.pangu.ManagerException;
import com.eadmarket.pangu.dao.report.ReportDao;
import com.eadmarket.pangu.dao.trade.TradeDao;
import com.eadmarket.pangu.domain.AdvertiseDO;
import com.eadmarket.pangu.domain.ReportDO;
import com.eadmarket.pangu.domain.TradeDO;
import com.eadmarket.pangu.manager.report.ReportManager;
import com.eadmarket.pangu.query.ReportQuery;

import javax.annotation.Resource;

/**
 * 报表业务接口实现类
 * 
 * @author liuyongpo@gmail.com
 */
class ReportManagerImpl implements ReportManager {

    @Resource private ReportDao reportDao;

    @Resource private TradeDao tradeDao;

    @Override
    public void responseForDisplay(AdvertiseDO advertiseDO, String ip) throws ManagerException {
        responseForEvent(advertiseDO, ip, 1L, 0L);
    }

    private void responseForEvent(AdvertiseDO advertiseDO, String ip, Long impression, Long click) throws ManagerException {
        ReportQuery reportQuery = new ReportQuery();
        Long id = advertiseDO.getId();
        reportQuery.setPositionId(id);
        reportQuery.setIp(ip);
        try {
            ReportDO report = reportDao.getReportDOByIpAndPosition(reportQuery);
            if (report == null) {
                newReport(advertiseDO, ip, impression, click);
            } else {
                ReportDO reportDO = new ReportDO();
                reportDO.setId(report.getId());
                reportDO.setImpression(impression);
                reportDO.setClick(click);
                reportDao.updateReportImpAndClick(reportDO);
            }
        } catch (DaoException ex) {
            throw new ManagerException(ExceptionCode.SYSTEM_ERROR, "" + advertiseDO, ex);
        }
    }

    private void newReport(AdvertiseDO advertiseDO, String ip, Long initImpression, Long initClick) throws DaoException {
        ReportDO newReport = new ReportDO();

        newReport.setPositionId(advertiseDO.getId());
        newReport.setIp(ip);
        newReport.setClick(initClick);
        newReport.setImpression(initImpression);

        Long tradeId = advertiseDO.getContractDO().getTradeId();
        filledWithTradeInfo(newReport, tradeId);
        reportDao.insert(newReport);
    }

    private void filledWithTradeInfo(ReportDO reportDO, Long tradeId) throws DaoException {
        TradeDO trade = tradeDao.getById(tradeId);

        reportDO.setTradeId(tradeId);
        reportDO.setProductId(trade.getProductId());
        reportDO.setBuyerId(trade.getBuyerId());
        reportDO.setSellerId(trade.getSellerId());
    }

    @Override
    public void responseForClick(AdvertiseDO advertiseDO, String ip) throws ManagerException {
        responseForEvent(advertiseDO, ip, 0L, 1L);
    }

}
