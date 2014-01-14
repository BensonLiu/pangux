package com.eadmarket.pangu.dao.report.impl;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.dao.BaseDao;
import com.eadmarket.pangu.dao.report.ReportCompDao;
import com.eadmarket.pangu.query.ReportCompQuery;
import com.google.common.collect.Maps;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对report的汇总表操作实现
 *
 * @author liuyongpo@gmail.com
 */
class ReportCompDaoImpl extends BaseDao implements ReportCompDao {

    @Override
    public Long getIdByReportCompQuery(ReportCompQuery reportCompQuery) throws DaoException {
        return selectOne("ReportCompDao.getIdByReportCompQuery", reportCompQuery);
    }

    @Override
    public void updateReportCompById(ReportCompQuery reportCompQuery) throws DaoException {
        update("ReportCompDao.updateReportCompById", reportCompQuery);
    }

    @Override
    public void insertReportComp(ReportCompQuery reportCompQuery) throws DaoException {
        insert("ReportCompDao.insertReportComp", reportCompQuery);
    }

    @Override
    public List<Long> getRemovableIds(int timeType, Date timeValue, int pageSize) throws DaoException {
        Map<String,Object> parameter = Maps.newHashMap();
        parameter.put("timeType", timeType);
        parameter.put("timeValue", timeValue);
        parameter.put("pageSize", pageSize);
        return selectList("ReportCompDao.getRemovableIds", parameter);
    }

    @Override
    public void deleteReportCompById(Long reportId) throws DaoException {
        delete("ReportCompDao.deleteReportCompById", reportId);
    }
}
