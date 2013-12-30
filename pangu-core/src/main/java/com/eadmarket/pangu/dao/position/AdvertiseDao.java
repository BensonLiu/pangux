package com.eadmarket.pangu.dao.position;

import java.util.List;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.domain.AdvertiseContractDO;
import com.eadmarket.pangu.domain.AdvertiseDO;

/**
 * 广告位的存储层接口
 * 
 * @throws DaoException
 * 
 * @author liuyongpo@gmail.com
 */
public interface AdvertiseDao {
	
	AdvertiseDO getById(Long id) throws DaoException;
	
	List<AdvertiseDO> getByProjectId(Long projectId) throws DaoException;
	
	Long addAdvertise(AdvertiseDO position) throws DaoException;
	
	int updateAdvertiseById(AdvertiseDO position) throws DaoException;
	
	void updateProfitById(Long id, Long addProfit) throws DaoException;

    /**
     * 根据广告位编号获取活跃的广告位契约
     *
     * @param advertiseId 广告位编号
     * @return 关联的活跃广告契约
     */
    AdvertiseContractDO getActiveContractByAdvertiseId(Long advertiseId) throws DaoException;

	/**
	 * 获取广告位编号大于minId并且状态是status的广告位
	 * 
	 * @param minId 最小广告位编号
	 * @param status 广告位状态
	 * @return 满足条件的广告位集合
	 */
	List<AdvertiseDO> queryPositionsForTimer(Long minId, AdvertiseDO.AdvertiseStatus status) throws DaoException;
}
