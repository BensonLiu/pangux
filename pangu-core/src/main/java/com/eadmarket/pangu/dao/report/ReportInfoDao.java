package com.eadmarket.pangu.dao.report;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.domain.ReportInfoDO;

import javax.annotation.concurrent.ThreadSafe;
import java.util.Date;
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

    /**
     * 插入新的report记录
     *
     * @param reportInfoDO reportInfo记录
     * @throws DaoException
     */
    void insertReportInfo(ReportInfoDO reportInfoDO) throws DaoException;

    /**
     * 根据最小创建时间分页拉取数据
     *
     * @param minGmtCreate 最小创建时间
     * @param pageSize 页大小
     * @return 满足条件的report记录
     * @throws DaoException
     */
    List<ReportInfoDO> getReportInfoDOsByGmtCreate(Date minGmtCreate, int pageSize) throws DaoException;

    /**
     * 插入Report记录到历史表中,有幂等效果
     *
     * @param reportInfoDO
     * @throws DaoException
     */
    void insertReportInfoHis(ReportInfoDO reportInfoDO) throws DaoException;

    /**
     * 根据report记录编号删除记录
     *
     * @param reportId report记录编号
     * @throws DaoException
     */
    void deleteReportInfoById(Long reportId) throws DaoException;
}
