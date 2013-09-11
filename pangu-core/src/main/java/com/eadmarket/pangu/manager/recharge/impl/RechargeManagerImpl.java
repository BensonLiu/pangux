package com.eadmarket.pangu.manager.recharge.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.ExceptionCode;
import com.eadmarket.pangu.ManagerException;
import com.eadmarket.pangu.dao.recharge.RechargeDao;
import com.eadmarket.pangu.dao.user.UserDao;
import com.eadmarket.pangu.domain.RechargeDO;
import com.eadmarket.pangu.domain.RechargeDO.RechargeStatus;
import com.eadmarket.pangu.dto.RechargeDTO;
import com.eadmarket.pangu.manager.recharge.RechargeManager;

/**
 * 充值业务操作
 * 
 * @author liuyongpo@gmail.com
 */
class RechargeManagerImpl implements RechargeManager {

	private final static Logger LOG = LoggerFactory.getLogger(RechargeManagerImpl.class);
	
	@Resource private UserDao userDao;
	
	@Resource private RechargeDao rechargeDao;
	
	@Resource private TransactionTemplate adTransactionTemplate;
	
	@Override
	public boolean finish(final Long rechargeId, final String outOrderId, final Long cash)
			throws ManagerException {
		final RechargeDO rechargeDO;
		try {
			rechargeDO = rechargeDao.getById(rechargeId);
		} catch (DaoException ex) {
			throw new ManagerException(ExceptionCode.SYSTEM_ERROR, "", ex);
		}
		if (rechargeDO == null) {
			throw new ManagerException(ExceptionCode.RECHARGE_NOT_EXIST);
		}
		if (rechargeDO.isCompleted()) {
			/*
			 * 此处为了支持幂等
			 */
			return true;
		}
		boolean result = adTransactionTemplate.execute(new TransactionCallback<Boolean>() {

			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				boolean result = false;
				try {
					int updateCount = rechargeDao.updateStatus(rechargeId, RechargeStatus.COMPELETED, rechargeDO.getStatus());
					if (updateCount > 0) {
						userDao.addCashTo(rechargeDO.getUserId(), cash);
						rechargeDao.updateOutOrderId(rechargeId, outOrderId);
						result = true;
					}
				} catch (DaoException ex) {
					status.setRollbackOnly();
					LOG.error("failed to finish recharge, id:" + rechargeId + ",outOrderId:" + outOrderId, ex);
				}
				return result;
			}
		});
		
		return result;
	}

	@Override
	public Long create(RechargeDTO rechargeDTO) throws ManagerException {
		RechargeDO recharge = new RechargeDO();
		recharge.setUserId(rechargeDTO.getUserId());
		recharge.setChannelType(rechargeDTO.getChannelType());
		recharge.setCash(rechargeDTO.getCash());
		recharge.setStatus(RechargeStatus.NEW);
		try {
			return rechargeDao.createRecharge(recharge);
		} catch (DaoException ex) {
			throw new ManagerException(ExceptionCode.SYSTEM_ERROR, "failed to creat recharge" + rechargeDTO, ex);
		}
	}

}
