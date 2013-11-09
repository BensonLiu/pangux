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
import com.eadmarket.pangu.dao.position.PositionDao;
import com.eadmarket.pangu.domain.PositionDO;
import com.eadmarket.pangu.domain.PositionDO.PositionStatus;

/**
 * TestCase for {@link PositionDao}
 * 
 * @author liuyongpo@gmail.com
 */
public class PositionDaoTest extends BaseTest {
	@Resource private PositionDao positionDao;
	
	@Test public void testQueryPositionForTimer() throws DaoException {
		List<PositionDO> positions = positionDao.queryPositionsForTimer(0L, PositionStatus.ON_SALE);
		assertThat(positions, notNullValue());
		assertThat(positions.size(), greaterThan(0));
		assertThat(positions.size(), lessThanOrEqualTo(30));
	}
	
	@Test public void testUpdate() throws DaoException {
		PositionDO param = new PositionDO();
		param.setId(3L);
		param.setStatus(PositionStatus.LOCKED);
		
		positionDao.updatePositionById(param);
		
		PositionDO p = positionDao.getById(3L);
		
		assertThat(p, notNullValue());
		assertThat(p.getStatus(), equalTo(PositionStatus.LOCKED));
	}
}
