package com.eadmarket.pangu.dao.recharge.impl;

import java.util.List;
import java.util.Map;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.dao.BaseDao;
import com.eadmarket.pangu.dao.recharge.RechargeDao;
import com.eadmarket.pangu.domain.RechargeDO;
import com.eadmarket.pangu.domain.RechargeDO.RechargeStatus;
import com.google.common.collect.Maps;

/**
 * 充值功能存储层默认实现
 * 
 * @author liuyongpo@gmail.com
 */
public class RechargeDaoImpl extends BaseDao implements RechargeDao {

	@Override
	public RechargeDO getById(Long id) throws DaoException {
		return selectOne("RechargeDao.getById", id);
	}

	@Override
	public Long createRecharge(RechargeDO rechargeDO) throws DaoException {
		insert("RechargeDao.insert", rechargeDO);
		return rechargeDO.getId();
	}

	@Override
	public int updateStatus(Long id, RechargeStatus status, RechargeStatus currentStatus) throws DaoException {
		Map<String, Object> param = Maps.newHashMap();
		param.put("id", id);
		param.put("status", status);
		param.put("expectedStatus", currentStatus);
		return update("RechargeDao.updateStatus", param);
	}
	
	public void updateOutOrderId(Long id, String outOrderId) throws DaoException {
		Map<String, Object> param = Maps.newHashMap();
		param.put("id", id);
		param.put("outOrderId", outOrderId);
		update("RechargeDao.updateOutOrderId", param);
	}
	
	@Override
	public List<RechargeDO> query() throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int count() throws DaoException {
		// TODO Auto-generated method stub
		return 0;
	}

}
