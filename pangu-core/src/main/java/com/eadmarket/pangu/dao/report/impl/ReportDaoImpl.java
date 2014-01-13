package com.eadmarket.pangu.dao.report.impl;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.common.Query;
import com.eadmarket.pangu.dao.BaseDao;
import com.eadmarket.pangu.dao.report.ReportDao;
import com.eadmarket.pangu.domain.ReportDO;
import com.eadmarket.pangu.query.ReportQuery;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * ReportDao的默认实现
 *
 * liuyongpo@gmail.com
 */
class ReportDaoImpl extends BaseDao implements ReportDao {

    @Override
    public List<ReportDO> query(Query<ReportQuery> query) throws DaoException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public int count(Query<ReportQuery> query) throws DaoException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public Long insert(ReportDO report) throws DaoException {
        insert("ReportDao.insert", report);
        return report.getId();
    }

    @Override
    public ReportDO getReportDOByIpAndPosition(ReportQuery reportQuery) throws DaoException {
        return selectOne("ReportDao.getReportDOByIpAndPosition", reportQuery);
    }

    @Override
    public void updateReportImpAndClick(ReportDO reportDO) throws DaoException {
        if (reportDO.getId() != null && (reportDO.getClick() != null || reportDO.getImpression() != null)) {
            update("ReportDao.updateReportImpAndClick", reportDO);
        }
    }

    @Override
    public List<ReportDO> getReportDOsByMinId(Long minId, Integer pageSize) throws DaoException {
        Map<String, Object> param = Maps.newHashMap();
        param.put("minId", minId);
        param.put("pageSize", pageSize);
        return selectList("ReportDao.getReportDOsByMinId", param);
    }
}
