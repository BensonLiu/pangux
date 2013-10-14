package com.eadmarket.pangu.manager.trade.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.ExceptionCode;
import com.eadmarket.pangu.ManagerException;
import com.eadmarket.pangu.common.Query;
import com.eadmarket.pangu.dao.position.PositionDao;
import com.eadmarket.pangu.dao.trade.TradeDao;
import com.eadmarket.pangu.dao.user.UserDao;
import com.eadmarket.pangu.domain.PositionDO;
import com.eadmarket.pangu.domain.PositionDO.PositionStatus;
import com.eadmarket.pangu.domain.TradeDO;
import com.eadmarket.pangu.domain.TradeDO.TradeStatus;
import com.eadmarket.pangu.dto.CreateTradeContext;
import com.eadmarket.pangu.manager.trade.TradeManager;
import com.eadmarket.pangu.query.TradeQuery;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * @author liuyongpo@gmail.com
 */
class TradeManagerImpl implements TradeManager, InitializingBean {
	
	private final static Logger LOG = LoggerFactory.getLogger(TradeManagerImpl.class);
	
	@Resource private TradeDao tradeDao;
	
	@Resource private UserDao userDao;
	
	@Resource private PositionDao positionDao;
	
	@Resource private TransactionTemplate adTransactionTemplate;
	
	@Override
	public Long createTrade(final CreateTradeContext tradeContext)
			throws ManagerException {
		final PositionDO positionDO;
		try {
			positionDO = positionDao.getById(tradeContext.getPositionId());
		} catch (DaoException ex) {
			throw new ManagerException(ExceptionCode.SYSTEM_ERROR, ex);
		}
		if (positionDO == null) {
			throw new ManagerException(ExceptionCode.POSITION_NOT_EXIST);
		}
		if (!positionDO.isOnSale()) {
			throw new ManagerException(ExceptionCode.POSITION_NOT_ON_SALE);
		}
		final TradeDO trade = generateTradeDO(tradeContext, positionDO);
		ExceptionCode code = adTransactionTemplate.execute(new TransactionCallback<ExceptionCode>() {

			@Override
			public ExceptionCode doInTransaction(TransactionStatus status) {
				try {
					boolean reduceCach = userDao.reduceCachFrom(positionDO.getOwnerId(), trade.getTotalFee());
					if (!reduceCach) {
						return ExceptionCode.ACCOUNT_HAVE_NO_ENGHOU_MONEY;
					}
					
					PositionDO positionParam = new PositionDO();
					positionParam.setId(positionDO.getId());
					positionParam.setStatus(PositionStatus.SOLD_OUT);
					int updateCount = positionDao.updatePositionById(positionParam);
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
	
	private TradeDO generateTradeDO(CreateTradeContext tradeContext, PositionDO positionDO) {
		TradeDO trade = new TradeDO();
		trade.setSellerId(positionDO.getOwnerId());
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
	
	private final static ScheduledExecutorService SCHEDULED_SERVICE 
		= Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setNameFormat("Trade-Status-Scaner-%d").build());
	
	private void init() {
		/*
		 * 启动线程扫描已经过期的交易，修改交易状态为完成并且修改广告位的状态为待出售
		 */
		SCHEDULED_SERVICE.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				Query<TradeQuery> query = new Query<TradeQuery>();
				TradeQuery tradeQuery = new TradeQuery();
				tradeQuery.setMaxEndDate(new Date());
				tradeQuery.setStatus(TradeStatus.IMPLEMENTING);
				query.setCondition(tradeQuery);
				int count;
				try {
					count = tradeDao.count(query);
				} catch (DaoException ex) {
					LOG.error("count trade failed, " + query, ex);
					return;
				}
				
				while (count > 0) {
					List<TradeDO> timeouttedTrades;
					try {
						timeouttedTrades = tradeDao.query(query);
					} catch (DaoException ex) {
						LOG.error("query trade failed, " + query, ex);
						return;
					}
					if (CollectionUtils.isEmpty(timeouttedTrades)) {
						return;
					}
					
					for (final TradeDO trade : timeouttedTrades) {
						adTransactionTemplate.execute(new TransactionCallback<Void>() {
							@Override
							public Void doInTransaction(TransactionStatus status) {
								try {
									int updateCount = tradeDao.updateStatus(trade.getId(), trade.getStatus(), TradeStatus.COMPLETED);
									if (updateCount > 0) {
										PositionDO position = new PositionDO();
										position.setId(trade.getPositionId());
										position.setStatus(PositionStatus.ON_SALE);
										positionDao.updatePositionById(position);
									}
								} catch (DaoException ex) {
									LOG.error("transaction failed, " + trade, ex);
									status.setRollbackOnly();
								}
								return null;
							}
						});
					}
					
					try {
						count = tradeDao.count(query);
					} catch (DaoException ex) {
						LOG.error("count trade failed, " + query, ex);
						return;
					}
				}
				
			}
		}, 1, 1, TimeUnit.HOURS);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//init();
	}
}
