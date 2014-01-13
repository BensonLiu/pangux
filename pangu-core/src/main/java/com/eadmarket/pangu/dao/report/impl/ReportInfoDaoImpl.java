package com.eadmarket.pangu.dao.report.impl;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.dao.BaseDao;
import com.eadmarket.pangu.dao.report.ReportInfoDao;
import com.eadmarket.pangu.domain.ReportInfoDO;
import com.google.common.collect.Maps;

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
}
