package com.eadmarket.pangu.dao.trade;

import java.util.List;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.common.Query;
import com.eadmarket.pangu.domain.TradeDO;
import com.eadmarket.pangu.domain.TradeDO.TradeStatus;
import com.eadmarket.pangu.query.TradeQuery;

/**
 * 交易的存储接口
 * 
 * @author liuyongpo@gmail.com
 */
public interface TradeDao {
	
	TradeDO getById(Long id) throws DaoException;
	
	Long createTrade(TradeDO trade) throws DaoException;
	
	List<TradeDO> query(Query<TradeQuery> query) throws DaoException;
	
	Integer count(Query<TradeQuery> query) throws DaoException;
	
	int updateStatus(Long id, TradeStatus originalStatus, TradeStatus targetStatus) throws DaoException;

	void updateTrade(TradeDO trade) throws DaoException;
}
