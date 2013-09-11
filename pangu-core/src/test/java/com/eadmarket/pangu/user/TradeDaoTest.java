/**
 * 
 */
package com.eadmarket.pangu.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.eadmarket.pangu.BaseTest;
import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.common.Query;
import com.eadmarket.pangu.dao.trade.TradeDao;
import com.eadmarket.pangu.domain.TradeDO;
import com.eadmarket.pangu.query.TradeQuery;

/**
 * @author liuyongpo@gmail.com
 */
public class TradeDaoTest extends BaseTest {
	@Resource private TradeDao tradeDao;
	
	@Test public void testCount() throws DaoException {
		
		TradeQuery tradeQuery = new TradeQuery();
		tradeQuery.setBuyerId(10L);
		
		Query<TradeQuery> query = new Query<TradeQuery>();
		query.setCondition(tradeQuery);
		
		int count = tradeDao.count(query);
		
		assertThat(count, is(greaterThan(1)));
	}
	
	@Test public void testQuery() throws DaoException {
		TradeQuery tradeQuery = new TradeQuery();
		tradeQuery.setBuyerId(10L);
		
		Query<TradeQuery> query = new Query<TradeQuery>();
		query.setCondition(tradeQuery);
		query.setTotalItem(8);
		query.setCurrentPage(1);
		query.setPageSize(2);
		
		List<TradeDO> list = tradeDao.query(query);
		
		assertThat(list, is(notNullValue()));
		assertThat(list.size(), is(greaterThan(1)));
	}
}
