package com.eadmarket.pangu.dao.user.impl;

import java.util.Map;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.dao.BaseDao;
import com.eadmarket.pangu.dao.user.UserDao;
import com.eadmarket.pangu.domain.UserDO;
import com.eadmarket.pangu.domain.UserDO.UserStatus;
import com.google.common.collect.Maps;

/**
 * UserDao瀹炵幇绫� * 
 * @author liuyongpo@gmail.com
 */
class UserDaoImpl extends BaseDao implements UserDao {

	public UserDO getById(Long userId) throws DaoException {
		return selectOne("UserDao.getById", userId);
	}

	@Override
	public void updateStatusById(Long userId, UserStatus status) throws DaoException {
		Map<String, Object> param = Maps.newHashMap();
		param.put("userId", userId);
		param.put("status", status);
		update("UserDao.updateStatusById", param);
	}

	@Override
	public Long insert(UserDO user) throws DaoException {
		insert("UserDao.insert", user);
		return user.getId();
	}

	@Override
	public void addCashTo(Long userId, Long cash) throws DaoException {
		Map<String, Object> param = Maps.newHashMap();
		param.put("userId", userId);
		param.put("cash", cash);
		update("UserDao.addCashTo", param);
	}

	@Override
	public boolean reduceCachFrom(Long userId, Long cash) throws DaoException {
		Map<String, Object> param = Maps.newHashMap();
		param.put("userId", userId);
		param.put("cash", cash);
		return update("UserDao.reduceCachFrom", param) == 1;
	}

	@Override
	public UserDO getByEmail(String email) throws DaoException {
		return selectOne("UserDao.getByEmail", email);
	}

	@Override
	public void updatePassword(Long userId, String password)
			throws DaoException {
		Map<String, Object> param = Maps.newHashMap();
		param.put("userId", userId);
		param.put("password", password);
		
		update("UserDao.updatePassword", param);
	}

	@Override
	public void updateUser(UserDO userDO) throws DaoException {
		update("UserDao.updateUser", userDO);
	}

}
