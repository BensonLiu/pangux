package com.eadmarket.pangu.manager.report.impl;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.ExceptionCode;
import com.eadmarket.pangu.ManagerException;
import com.eadmarket.pangu.dao.report.ReportInfoDao;
import com.eadmarket.pangu.domain.AdvertiseDO;
import com.eadmarket.pangu.domain.ReportInfoDO;
import com.eadmarket.pangu.manager.report.ReportInfoManager;

import javax.annotation.Resource;

/**
 * 报表业务接口实现类
 *
 * @author liuyongpo@gmail.com
 */
class ReportInfoManagerImpl implements ReportInfoManager {

  @Resource
  private ReportInfoDao reportInfoDao;

  @Override
  public void responseForOperation(AdvertiseDO advertiseDO, String ip, int operationType)
      throws ManagerException {
    ReportInfoDO reportInfoDO = new ReportInfoDO();
    reportInfoDO.setAdvertiseId(advertiseDO.getId());
    reportInfoDO.setOperationType(operationType);
    reportInfoDO.setIp(ip);
    reportInfoDO.setTradeId(advertiseDO.getContractDO().getTradeId());
    try {
      reportInfoDao.insertReportInfo(reportInfoDO);
    } catch (DaoException ex) {
      throw new ManagerException(ExceptionCode.SYSTEM_ERROR, "" + advertiseDO, ex);
    }
  }

}
