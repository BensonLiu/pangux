package com.eadmarket.pangu.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import javax.annotation.Resource;

import org.junit.Test;

import com.eadmarket.pangu.BaseTest;
import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.dao.user.UserDao;
import com.eadmarket.pangu.domain.UserDO;
import com.eadmarket.pangu.domain.UserDO.UserStatus;

/**
 * UserDao娴嬭瘯绫� * 
 * @author liuyongpo@gmail.com
 */
public class UserDaoTest extends BaseTest {
	
	@Resource private UserDao userDao;
	
	@Test public void testInsertUser() throws DaoException {
		UserDO userDO = createTestUserDO();
		Long id = null;
		try {
			id = userDao.insert(userDO);
			
			assertThat(id, is(greaterThan(0L)));
		} finally {
			if (id != null) {
				adJdbcTemplate.execute("delete from user where id = " + id);
			}
		}
	}

	private UserDO createTestUserDO() {
		UserDO userDO = new UserDO();
		
		userDO.setNick("TestCaseNick");
		userDO.setEmail("594974221@qq.com");
		userDO.setHeaderUrl("1000.jpg");
		userDO.setStatus(UserStatus.REGISTED_UNACTIVE);
		userDO.setType(1);
		userDO.setPassword("TestCasePwd");
		userDO.setBalance(1000L);
		userDO.setAccount("DefaultAccount");
		userDO.setScore(0L);
		return userDO;
	}
	
	@Test public void testUpdateStatus() throws DaoException {
		UserDO userDO = createTestUserDO();
		
		Long id = null;
		try {
			id = userDao.insert(userDO);
			
			assertThat(id, is(greaterThan(0L)));
			
			userDao.updateStatusById(id, UserStatus.ACTIVE);
			
			UserDO userInDb = userDao.getById(id);
			
			assertThat(userInDb, is(notNullValue()));
			assertThat(userInDb.getStatus(), is(equalTo(UserStatus.ACTIVE)));
			
		} finally {
			if (id != null) {
				adJdbcTemplate.execute("delete from user where id = " + id);
			}
		}
	}
	
	@Test public void testAddCashTo() throws DaoException {
		UserDO userDO = createTestUserDO();
		
		Long id = null;
		try {
			id = userDao.insert(userDO);
			
			assertThat(id, is(greaterThan(0L)));
			
			userDao.addCashTo(id, 1000L);
			
			UserDO userInDb = userDao.getById(id);
			
			assertThat(userInDb, is(notNullValue()));
			
			assertThat(userInDb.getBalance(), is(equalTo(2000L)));
		} finally {
			if (id != null) {
				adJdbcTemplate.execute("delete from user where id = " + id);
			}
		}
	}
	
	@Test public void testReduceCashFromWhenNoEnoughCach() throws DaoException {
		UserDO userDO = createTestUserDO();
		
		Long id = null;
		try {
			id = userDao.insert(userDO);
			
			assertThat(id, is(greaterThan(0L)));
			
			boolean result = userDao.reduceCachFrom(id, 1001L);
			
			assertThat(result, is(equalTo(false)));
			
		} finally {
			if (id != null) {
				adJdbcTemplate.execute("delete from user where id = " + id);
			}
		}
	}
	
	@Test public void testReduceCashFromWhenHaveEnoughCach() throws DaoException {
		UserDO userDO = createTestUserDO();
		
		Long id = null;
		try {
			id = userDao.insert(userDO);
			
			assertThat(id, is(greaterThan(0L)));
			
			boolean result = userDao.reduceCachFrom(id, 100L);
			
			assertThat(result, is(equalTo(true)));
			
			UserDO userInDb = userDao.getById(id);
			
			assertThat(userInDb, is(notNullValue()));
			
			assertThat(userInDb.getBalance(), is(equalTo(900L)));

		} finally {
			if (id != null) {
				adJdbcTemplate.execute("delete from user where id = " + id);
			}
		}

	}
}
