package com.eadmarket.pangu.dao.report;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.query.ReportCompQuery;

/**
 * Created by liu on 1/10/14.
 */
public interface ReportCompDao {

    Long getIdByReportCompQuery(ReportCompQuery reportCompQuery) throws DaoException;

    void updateReportCompById(ReportCompQuery reportCompQuery) throws DaoException;

    void insertReportComp(ReportCompQuery reportCompQuery) throws DaoException;

}
