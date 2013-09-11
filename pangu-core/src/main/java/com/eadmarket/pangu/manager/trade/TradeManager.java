package com.eadmarket.pangu.manager.trade;

import java.util.List;

import com.eadmarket.pangu.ManagerException;
import com.eadmarket.pangu.common.Query;
import com.eadmarket.pangu.domain.TradeDO;
import com.eadmarket.pangu.dto.CreateTradeContext;
import com.eadmarket.pangu.query.TradeQuery;

/**
 * ���׵�ҵ������ӿڲ�
 * 
 * @author liuyongpo@gmail.com
 */
public interface TradeManager {
	Long createTrade(CreateTradeContext tradeContext) throws ManagerException;
	
	List<TradeDO> query(Query<TradeQuery> query) throws ManagerException;
}
