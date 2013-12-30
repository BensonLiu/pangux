package com.eadmarket.pangu.manager.trade.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.eadmarket.pangu.domain.AdvertiseDO;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.ExceptionCode;
import com.eadmarket.pangu.ManagerException;
import com.eadmarket.pangu.common.Query;
import com.eadmarket.pangu.dao.position.AdvertiseDao;
import com.eadmarket.pangu.dao.trade.TradeDao;
import com.eadmarket.pangu.dao.user.UserDao;
import com.eadmarket.pangu.domain.TradeDO;
import com.eadmarket.pangu.domain.TradeDO.TradeStatus;
import com.eadmarket.pangu.dto.CreateTradeContext;
import com.eadmarket.pangu.manager.trade.TradeManager;
import com.eadmarket.pangu.query.TradeQuery;

/**
 * @author liuyongpo@gmail.com
 */
class TradeManagerImpl implements TradeManager {
	
	private final static Logger LOG = LoggerFactory.getLogger(TradeManagerImpl.class);
	
	@Resource private TradeDao tradeDao;
	
	@Resource private UserDao userDao;
	
	@Resource private AdvertiseDao advertiseDao;
	
	@Resource private TransactionTemplate adTransactionTemplate;
	
	@Override
	public Long createTrade(final CreateTradeContext tradeContext)
			throws ManagerException {
		final AdvertiseDO advertiseDO;
		try {
			advertiseDO = advertiseDao.getById(tradeContext.getPositionId());
		} catch (DaoException ex) {
			throw new ManagerException(ExceptionCode.SYSTEM_ERROR, ex);
		}
		if (advertiseDO == null) {
			throw new ManagerException(ExceptionCode.POSITION_NOT_EXIST);
		}
		if (!advertiseDO.isOnSale()) {
			throw new ManagerException(ExceptionCode.POSITION_NOT_ON_SALE);
		}
		final TradeDO trade = generateTradeDO(tradeContext, advertiseDO);
		ExceptionCode code = adTransactionTemplate.execute(new TransactionCallback<ExceptionCode>() {

			@Override
			public ExceptionCode doInTransaction(TransactionStatus status) {
				try {
					boolean reduceCach = userDao.reduceCachFrom(advertiseDO.getOwnerId(), trade.getTotalFee());
					if (!reduceCach) {
						return ExceptionCode.ACCOUNT_HAVE_NO_ENGHOU_MONEY;
					}
					
					AdvertiseDO positionParam = new AdvertiseDO();
					positionParam.setId(advertiseDO.getId());
					positionParam.setStatus(AdvertiseDO.AdvertiseStatus.SOLD_OUT);
					int updateCount = advertiseDao.updateAdvertiseById(positionParam);
					if (updateCount <= 0) {
						status.setRollbackOnly();
						return ExceptionCode.POSITION_NOT_ON_SALE;
					}
					
					tradeDao.createTrade(trade);
				} catch (DaoException ex) {
					status.setRollbackOnly();
					LOG.error("create trade failed," + tradeContext, ex);
					return ExceptionCode.SYSTEM_ERROR;
				}
				return null;
			}
		});
		
		if (code != null) {
			//有异常发生
			throw new ManagerException(code);
		}
		
		return trade.getId();
	}
	
	private TradeDO generateTradeDO(CreateTradeContext tradeContext, AdvertiseDO advertiseDO) {
		TradeDO trade = new TradeDO();
		trade.setSellerId(advertiseDO.getOwnerId());
		trade.setBuyerId(tradeContext.getBuyerId());
		trade.setNum(tradeContext.getNum());
		trade.setPositionId(tradeContext.getPositionId());
		trade.setPrice(tradeContext.getPrice());
		trade.setStatus(TradeStatus.IMPLEMENTING);
		trade.setProductId(tradeContext.getProductId());
		Date startDate = new Date();
		trade.setStartDate(startDate);
		trade.setEndDate(DateUtils.addMonths(startDate, tradeContext.getNum()));
		return trade;
	}

	@Override
	public List<TradeDO> query(Query<TradeQuery> query) throws ManagerException {
		try {
			int count = tradeDao.count(query);
			if (count > 0) {
				query.setTotalItem(count);
				return tradeDao.query(query);
			}
			return Collections.emptyList();
		} catch (DaoException ex) {
			throw new ManagerException(ExceptionCode.SYSTEM_ERROR, ex);
		}
	}
	
}
