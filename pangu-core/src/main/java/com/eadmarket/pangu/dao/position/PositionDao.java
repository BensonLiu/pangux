package com.eadmarket.pangu.dao.position;

import java.util.List;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.domain.PositionDO;
import com.eadmarket.pangu.domain.PositionDO.PositionStatus;

/**
 * 广告位的存储层接口
 * 
 * @throws DaoException
 * 
 * @author liuyongpo@gmail.com
 */
public interface PositionDao {
	
	PositionDO getById(Long id) throws DaoException;
	
	List<PositionDO> getByProjectId(Long projectId) throws DaoException;
	
	Long addPosition(PositionDO position) throws DaoException;
	
	int updatePositionById(PositionDO position) throws DaoException;
	
	void updateProfitById(Long id, Long addProfit) throws DaoException;
	
	/**
	 * 获取广告位编号大于minId并且状态是status的广告位
	 * 
	 * @param minId 最小广告位编号
	 * @param status 广告位状态
	 * @return 满足条件的广告位集合
	 */
	List<PositionDO> queryPositionsForTimer(Long minId, PositionStatus status) throws DaoException;
}
