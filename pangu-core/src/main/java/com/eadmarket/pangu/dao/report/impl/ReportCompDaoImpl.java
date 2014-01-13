package com.eadmarket.pangu.dao.report.impl;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.dao.BaseDao;
import com.eadmarket.pangu.dao.report.ReportCompDao;
import com.eadmarket.pangu.query.ReportCompQuery;

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
}
