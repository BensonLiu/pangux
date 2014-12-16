package com.eadmarket.pangu.dao.report.impl;

import com.google.common.collect.Maps;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.dao.BaseDao;
import com.eadmarket.pangu.dao.report.ReportInfoDao;
import com.eadmarket.pangu.domain.ReportInfoDO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * report_info表操作实现
 *
 * @author liuyongpo@gmail.com
 */
class ReportInfoDaoImpl extends BaseDao implements ReportInfoDao {

  @Override
  public List<ReportInfoDO> getReportInfoDOsByMinId(Long minId, int pageSize) throws DaoException {
    Map<String, Object> param = Maps.newHashMap();
    param.put("minId", minId);
    param.put("pageSize", pageSize);
    return selectList("ReportInfoDao.getReportInfoDOsByMinId", param);
  }

  @Override
  public void insertReportInfo(ReportInfoDO reportInfoDO) throws DaoException {
    insert("ReportInfoDao.insertReportInfo", reportInfoDO);
  }

  @Override
  public List<ReportInfoDO> getReportInfoDOsByGmtCreate(Date minGmtCreate, int pageSize)
      throws DaoException {
    Map<String, Object> param = Maps.newHashMap();
    param.put("gmtCreate", minGmtCreate);
    param.put("pageSize", pageSize);
    return selectList("ReportInfoDao.getReportInfoDOsByGmtCreate", param);
  }

  @Override
  public void insertReportInfoHis(ReportInfoDO reportInfoDO) throws DaoException {
    insert("ReportInfoDao.insertReportInfoHis", reportInfoDO);
  }

  @Override
  public void deleteReportInfoById(Long reportId) throws DaoException {
    delete("ReportInfoDao.deleteReportInfoById", reportId);
  }
}
