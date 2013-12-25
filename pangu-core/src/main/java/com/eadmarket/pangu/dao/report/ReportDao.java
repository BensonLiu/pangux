package com.eadmarket.pangu.dao.report;

import java.util.List;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.common.Query;
import com.eadmarket.pangu.domain.ReportDO;
import com.eadmarket.pangu.query.ReportQuery;

/**
 * 报表存储层接口
 * 
 * @author liuyongpo@gmail.com
 *
 * @throws DaoException
 */
public interface ReportDao {

	List<ReportDO> query(Query<ReportQuery> query) throws DaoException;
	
	int count(Query<ReportQuery> query) throws DaoException;
	
	Long insert(ReportDO report) throws DaoException;

    /**
     * 根据广告位和来源ip来查询报表
     *
     * @param reportQuery 报表查询对象，参数至少包括ip和positionId
     * @return 对应的报表对象，如果存在的话
     */
    ReportDO getReportDOByIpAndPosition(ReportQuery reportQuery) throws DaoException;

    /**
     * 更新报表的展示次数和点击次数
     *
     * @param reportDO 更新参数
     */
    void updateReportImpAndClick(ReportDO reportDO) throws DaoException;
}
