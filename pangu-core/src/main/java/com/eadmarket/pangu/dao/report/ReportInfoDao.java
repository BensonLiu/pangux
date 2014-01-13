package com.eadmarket.pangu.dao.report;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.domain.ReportInfoDO;

import java.util.List;

/**
 * 对report_info表的操作接口
 *
 * @author liuyongpo@gmail.com
 */
public interface ReportInfoDao {

    /**
     * 根据最小的id拉取指定大小页的报表数据
     *
     * @param minId 最小编号
     * @param pageSize 页大小
     * @return 满足条件的页大小
     * @throws DaoException
     */
    List<ReportInfoDO> getReportInfoDOsByMinId(Long minId, int pageSize) throws DaoException;

    void insertReportInfo(ReportInfoDO reportInfoDO) throws DaoException;
}
