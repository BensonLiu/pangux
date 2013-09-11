package com.eadmarket.pangu.dao.recharge;

import java.util.List;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.domain.RechargeDO;
import com.eadmarket.pangu.domain.RechargeDO.RechargeStatus;

/**
 * ��ֵ��¼�洢��ӿ�
 * 
 * @author liuyongpo@gmail.com
 */
public interface RechargeDao {
	
	RechargeDO getById(Long id) throws DaoException;
	
	Long createRecharge(RechargeDO rechargeDO) throws DaoException;
	
	int updateStatus(Long id, RechargeStatus status, RechargeStatus currentStatus) throws DaoException;
	
	void updateOutOrderId(Long id, String outOrderId) throws DaoException;
	
	List<RechargeDO> query() throws DaoException;
	
	int count() throws DaoException;
	
}
