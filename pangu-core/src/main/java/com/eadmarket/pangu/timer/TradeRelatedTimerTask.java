package com.eadmarket.pangu.timer;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.common.Query;
import com.eadmarket.pangu.dao.finance.FinanceDao;
import com.eadmarket.pangu.dao.position.PositionDao;
import com.eadmarket.pangu.dao.trade.TradeDao;
import com.eadmarket.pangu.dao.user.UserDao;
import com.eadmarket.pangu.domain.FinanceDO;
import com.eadmarket.pangu.domain.PositionDO;
import com.eadmarket.pangu.domain.TradeDO;
import com.eadmarket.pangu.domain.PositionDO.PositionStatus;
import com.eadmarket.pangu.domain.TradeDO.TradeStatus;
import com.eadmarket.pangu.query.TradeQuery;

/**
 * @author liuyongpo@gmail.com
 */
public class TradeRelatedTimerTask {

	private final static Logger LOG = LoggerFactory.getLogger(TradeRelatedTimerTask.class);

	@Resource
	private TradeDao tradeDao;

	@Resource
	private UserDao userDao;

	@Resource
	private FinanceDao financeDao;

	@Resource
	private TransactionTemplate adTransactionTemplate;
	
	@Resource
	private PositionDao positionDao;

	/**
	 * 交易到期通知买卖家双方,更改交易状态为完成，恢复广告位的状态为待出售
	 */
	public void notifyBuyerAndSellerWhenTradeExpire() {
		
		LOG.warn("TradeTimer starting, begin to scan expired trades");
		
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
				Boolean success = adTransactionTemplate.execute(new TransactionCallback<Boolean>() {
					@Override
					public Boolean doInTransaction(TransactionStatus status) {
						Boolean result = Boolean.TRUE;
						try {
							int updateCount = tradeDao.updateStatus(trade.getId(), trade.getStatus(), TradeStatus.COMPLETED);
							if (updateCount > 0) {
								PositionDO position = new PositionDO();
								position.setId(trade.getPositionId());
								position.setStatus(PositionStatus.ON_SALE);
								positionDao.updatePositionById(position);
								LOG.warn("Trade {} expired, update position {} to ON_SALE", trade.getId(), trade.getPositionId());
							} else {
								result = Boolean.FALSE;
							}
						} catch (DaoException ex) {
							LOG.error("transaction failed, " + trade, ex);
							status.setRollbackOnly();
							result = Boolean.FALSE;
						}
						return result;
					}
				});
				
				if (success) {
					//send email to buyer and seller 
				}
			}
			
			try {
				count = tradeDao.count(query);
			} catch (DaoException ex) {
				LOG.error("count trade failed, " + query, ex);
				return;
			}
		}
		LOG.warn("TradeTimer end, begin to scan expired trades");
	}

	/**
	 * 划款给卖家,每天定时划款给卖家
	 */
	public void transferMoneyToSeller() {
		TradeQuery tradeQuery = new TradeQuery();
		// tradeQuery.setMinEndDate(now);
		Date now = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
		tradeQuery.setLastTransferDate(now);
		tradeQuery.setStatus(TradeStatus.IMPLEMENTING);

		Query<TradeQuery> query = Query.create(tradeQuery);

		try {
			Integer count = tradeDao.count(query);
			while (count > 0) {
				List<TradeDO> tradeList = tradeDao.query(query);
				for (final TradeDO trade : tradeList) {
					adTransactionTemplate.execute(new TransferMoneyTransaction(trade, now));
				}
				count = tradeDao.count(query);
			}
		} catch (Exception ex) {
			LOG.error("timerTask transfer money exception", ex);
		}
	}

	private final class TransferMoneyTransaction implements TransactionCallback<Boolean> {
		private final TradeDO trade;
		private final Date now;

		private TransferMoneyTransaction(TradeDO trade, Date now) {
			this.trade = trade;
			this.now = now;
		}

		@Override
		public Boolean doInTransaction(TransactionStatus status) {
			try {
				/*
				 * 1.划款到卖家账户中
				 */
				userDao.addCashTo(trade.getSellerId(), 0L);
				/*
				 * 2.插入财务记录
				 */
				FinanceDO financeDO = new FinanceDO();
				financeDO.setBalance(0L);
				financeDO.setUserId(trade.getSellerId());
				financeDO.setType(0);
				financeDao.insert(financeDO);
				/*
				 * 3.更新交易记录上次划款时间
				 */
				TradeDO tradeDO = new TradeDO();
				tradeDO.setId(trade.getId());
				tradeDO.setLastTransferDate(now);
				tradeDao.updateTrade(tradeDO);

			} catch (DaoException ex) {
				status.setRollbackOnly();
				throw new RuntimeException(ex);
			}
			return Boolean.TRUE;
		}
	}

}
