/**
 * 
 */
package com.eadmarket.pangu.dao;

import com.eadmarket.pangu.BaseTest;
import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.dao.position.AdvertiseDao;
import com.eadmarket.pangu.domain.AdvertiseContractDO;
import com.eadmarket.pangu.domain.AdvertiseDO;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * TestCase for {@link com.eadmarket.pangu.dao.position.AdvertiseDao}
 * 
 * @author liuyongpo@gmail.com
 */
public class AdvertiseDaoTest extends BaseTest {

	@Resource private AdvertiseDao advertiseDao;
	
	@Test public void testQueryPositionForTimer() throws DaoException {
		List<AdvertiseDO> positions = advertiseDao.queryPositionsForTimer(0L, AdvertiseDO.AdvertiseStatus.ON_SALE);
		assertThat(positions, notNullValue());
		assertThat(positions.size(), greaterThan(0));
		assertThat(positions.size(), lessThanOrEqualTo(30));
	}
	
	@Test public void testUpdate() throws DaoException {
		AdvertiseDO param = new AdvertiseDO();
		param.setId(3L);
		param.setStatus(AdvertiseDO.AdvertiseStatus.CAN_RESERVE);
		
		advertiseDao.updateAdvertiseById(param);
		
		AdvertiseDO p = advertiseDao.getById(3L);
		
		assertThat(p, notNullValue());
		assertThat(p.getStatus(), equalTo(AdvertiseDO.AdvertiseStatus.CAN_RESERVE));
	}

    @Test public void testGetActiveContractByAdvertiseId_with_notExistedId() throws DaoException {
        Long notExistedAdvertiseId = -1L;

        AdvertiseContractDO nullAdvertise = advertiseDao.getActiveContractByAdvertiseId(notExistedAdvertiseId);

        assertThat(nullAdvertise, is(nullValue()));
    }
}
