package com.eadmarket.pangu.timer;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lombok.Setter;

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
import com.eadmarket.pangu.dao.product.ProductDao;
import com.eadmarket.pangu.dao.trade.TradeDao;
import com.eadmarket.pangu.dao.user.UserDao;
import com.eadmarket.pangu.domain.FinanceDO;
import com.eadmarket.pangu.domain.PositionDO;
import com.eadmarket.pangu.domain.ProductDO;
import com.eadmarket.pangu.domain.TradeDO;
import com.eadmarket.pangu.domain.PositionDO.PositionStatus;
import com.eadmarket.pangu.domain.TradeDO.TradeStatus;
import com.eadmarket.pangu.domain.UserDO;
import com.eadmarket.pangu.query.TradeQuery;
import com.eadmarket.pangu.util.email.EmailService;
import com.google.common.collect.Maps;

/**
 * 交易相关的时间程序
 * 
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
	
	@Resource
	private ProductDao productDao;
	
	@Resource 
	private EmailService emailService;
	
	/**
	 * 网站对应的中间账户，这样就把我们自己当成第三方同等对待了。
	 */
	@Setter private Long eadmarketAccountId;

	/**
	 * 交易到期通知买卖家双方,更改交易状态为完成，恢复广告位的状态为待出售
	 */
	public void notifyBuyerAndSellerWhenTradeExpire() {
		
		LOG.warn("TradeTimer starting, begin to scan expired trades");
		
		Query<TradeQuery> query = new Query<TradeQuery>();
		TradeQuery tradeQuery = new TradeQuery();
		/*
		 * 至少延后30分钟处理，为了和划款时间程序避开冲突
		 */
		tradeQuery.setMaxEndDate(DateUtils.addMinutes(new Date(), 30));
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
					try {
						UserDO seller = userDao.getById(trade.getSellerId());
						Map<String, Object> emailParam = Maps.newHashMap();
						emailParam.put("name", seller.getNick());
						PositionDO position = positionDao.getById(trade.getPositionId());
						emailParam.put("title", position.getTitle());
						emailService.sendEmail("201410141717", seller.getEmail(), emailParam);
					} catch (Exception ex) {
						LOG.error("failed to send email to userId:" + trade.getSellerId(), ex);
					}
					
					try {
						UserDO buyer = userDao.getById(trade.getBuyerId());
						Map<String, Object> emailParam = Maps.newHashMap();
						emailParam.put("name", buyer.getNick());
						
						ProductDO product = productDao.getById(trade.getProductId());
						emailParam.put("title", product.getName());
						
						PositionDO position = positionDao.getById(trade.getPositionId());
						emailParam.put("positionTitle", position.getTitle());
						emailService.sendEmail("201410141747", buyer.getEmail(), emailParam);
					} catch (Exception ex) {
						LOG.error("failed to send email to userId:" + trade.getBuyerId(), ex);
					}
				}
			}
			
			try {
				count = tradeDao.count(query);
			} catch (DaoException ex) {
				LOG.error("count trade failed, " + query, ex);
				return;
			}
		}
		LOG.warn("TradeTimer end");
	}

	/**
	 * 划款给卖家,每天定时划款给卖家
	 */
	public void transferMoneyToSeller() {
		
		LOG.warn("Timer begin");
		
		TradeQuery tradeQuery = new TradeQuery();
		Date now = new Date();
		tradeQuery.setMinEndDate(now);
		Date yesterday = DateUtils.addDays(now, -1);
		tradeQuery.setLastTransferDate(yesterday);
		tradeQuery.setStatus(TradeStatus.IMPLEMENTING);

		Query<TradeQuery> query = Query.create(tradeQuery);

		try {
			Integer count = tradeDao.count(query);
			while (count > 0) {
				LOG.warn("found unprocessed trades,begin to process");
				List<TradeDO> tradeList = tradeDao.query(query);
				for (final TradeDO trade : tradeList) {
					adTransactionTemplate.execute(new TransferMoneyTransaction(trade, now));
					LOG.warn("processing " + trade);
				}
				count = tradeDao.count(query);
			}
			LOG.warn("Timer end");
		} catch (Exception ex) {
			LOG.error("timerTask transfer money exception", ex);
		}
	}

	private final class TransferMoneyTransaction implements TransactionCallback<Boolean> {
		private final TradeDO trade;
		private final Date transferDate;

		private TransferMoneyTransaction(TradeDO trade, Date now) {
			this.trade = trade;
			this.transferDate = now;
		}

		@Override
		public Boolean doInTransaction(TransactionStatus status) {
			try {
				Long originalPrice = trade.getOriginalPrice();
				/*
				 * 为了保证划款的正确性，此处还是算一下，如果时间程序中间挂了一天，继续跑的话还可以补齐之前的款项
				 */
				int rangeDays = DateUtils.truncatedCompareTo(transferDate, trade.getLastTransferDate(), Calendar.DATE);
				Long cash = rangeDays * originalPrice;
				/*
				 * 1.划款到卖家账户中
				 */
				userDao.addCashTo(trade.getSellerId(), cash);
				/*
				 * 2.增加广告位收益
				 */
				positionDao.updateProfitById(trade.getPositionId(), cash);
				/*
				 * 3.插入卖家财务记录
				 */
				FinanceDO financeDO = new FinanceDO();
				financeDO.setNumber(cash);
				financeDO.setUserId(trade.getSellerId());
				financeDO.setType(FinanceDO.TYPE_AD_IN);
				financeDO.setRemark("广告位收入");
				financeDao.insert(financeDO);
				
				/*
				 * 4.减少默认账户金额
				 */
				userDao.reduceCachWithoutCheck(eadmarketAccountId, cash);
				/*
				 * 5.插入默认账户的财务记录
				 */
				financeDO = new FinanceDO();
				financeDO.setNumber(cash);
				financeDO.setUserId(eadmarketAccountId);
				financeDO.setType(FinanceDO.TYPE_AD_OUT);
				financeDO.setRemark("划款");
				financeDao.insert(financeDO);
				/*
				 * 6.更新交易记录上次划款时间
				 */
				TradeDO tradeDO = new TradeDO();
				tradeDO.setId(trade.getId());
				tradeDO.setLastTransferDate(transferDate);
				tradeDao.updateTrade(tradeDO);

			} catch (DaoException ex) {
				status.setRollbackOnly();
				throw new RuntimeException(ex);
			}
			return Boolean.TRUE;
		}
	}

}
