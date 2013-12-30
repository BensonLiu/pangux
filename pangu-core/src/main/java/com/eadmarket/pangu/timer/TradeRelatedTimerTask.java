package com.eadmarket.pangu.timer;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.eadmarket.pangu.domain.*;
import lombok.Setter;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.common.Query;
import com.eadmarket.pangu.dao.finance.FinanceDao;
import com.eadmarket.pangu.dao.position.AdvertiseDao;
import com.eadmarket.pangu.dao.product.ProductDao;
import com.eadmarket.pangu.dao.trade.TradeDao;
import com.eadmarket.pangu.dao.user.UserDao;
import com.eadmarket.pangu.domain.AdvertiseDO;
import com.eadmarket.pangu.domain.TradeDO.TradeStatus;
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
	private AdvertiseDao advertiseDao;
	
	@Resource
	private ProductDao productDao;
	
	@Resource 
	private EmailService emailService;
	
	/**
	 * 网站对应的中间账户，这样就把我们自己当成第三方同等对待了。
	 */
	@Setter private Long eadmarketAccountId;

	/**
	 * 交易过期，修改交易的状态为完成，并且发邮件通知交易双方
	 * 
	 * @param trade 要过期的交易对象
	 */
	private void applyExpireTrade(final TradeDO trade) {
		
		LOG.warn("Trade({}) expired", trade.getId());
		
		Boolean success = adTransactionTemplate.execute(new TransactionCallback<Boolean>() {
			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				Boolean result = Boolean.TRUE;
				try {
					int updateCount = tradeDao.updateStatus(trade.getId(), trade.getStatus(), TradeStatus.COMPLETED);
					if (updateCount > 0) {
						AdvertiseDO position = new AdvertiseDO();
						position.setId(trade.getPositionId());
						position.setStatus(AdvertiseDO.AdvertiseStatus.ON_SALE);
						advertiseDao.updateAdvertiseById(position);
						LOG.warn("Trade {} expired, update position {} to ON_SALE", trade.getId(), trade.getPositionId());
					} else {
						result = Boolean.FALSE;
					}
					LOG.warn("update {} {}", trade, updateCount);
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
				AdvertiseDO position = advertiseDao.getById(trade.getPositionId());
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
				
				AdvertiseDO position = advertiseDao.getById(trade.getPositionId());
				emailParam.put("positionTitle", position.getTitle());
				emailService.sendEmail("201410141747", buyer.getEmail(), emailParam);
			} catch (Exception ex) {
				LOG.error("failed to send email to userId:" + trade.getBuyerId(), ex);
			}
		}
	}

	/**
	 * 划款给卖家,每天定时划款给卖家
	 */
	public void execute() {
		
		TradeQuery tradeQuery = new TradeQuery();
		Date now = new Date();
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
					LOG.warn("processing " + trade);
					Boolean success = adTransactionTemplate.execute(new TransferMoneyTransaction(trade, now));
					/*
					 * 划款成功并且交易确实过期了，执行交易的过期逻辑
					 */
					if (success && trade.getEndDate().before(now)) {
						applyExpireTrade(trade);
					}
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
				/*
				 * 还是上述的情况，万一时间程序在开发人员不知道的情况下crash两天，而且交易本来应该在一天前结束的，那么要算出交易之后经历的天数
				 */
				int invalidDays = DateUtils.truncatedCompareTo(transferDate, trade.getEndDate(), Calendar.DATE);
				if (invalidDays > 0) {
					rangeDays = rangeDays - invalidDays;
				}
				
				Long cash = rangeDays * originalPrice;
				
				if (cash < 0) {
					return Boolean.TRUE;
				}
				
				/*
				 * 1.划款到卖家账户中
				 */
				userDao.addCashTo(trade.getSellerId(), cash);
				/*
				 * 2.增加广告位收益
				 */
				advertiseDao.updateProfitById(trade.getPositionId(), cash);
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
				//在此通过上次打款时间校正下最后划款时间
				tradeDO.setLastTransferDate(DateUtils.addDays(trade.getLastTransferDate(), rangeDays));
				tradeDao.updateTrade(tradeDO);
				
			} catch (DaoException ex) {
				status.setRollbackOnly();
				throw new RuntimeException(ex);
			}
			return Boolean.TRUE;
		}
	}

}
