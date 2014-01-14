package com.eadmarket.pangu.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.dao.DataAccessException;

import com.eadmarket.pangu.DaoException;

/**
 * 通用的dao
 * 
 * @author liuyongpo@gmail.com
 */
public abstract class BaseDao extends SqlSessionDaoSupport {
	
	protected <T> T selectOne(String statement) throws DaoException {
		try {
			return getSqlSession().selectOne(statement);
		} catch (DataAccessException ex) {
			throw new DaoException(ex);
		}
	}

	protected <T> T selectOne(String statement, Object parameter) throws DaoException {
		try {
			return getSqlSession().selectOne(statement, parameter);
		} catch (DataAccessException ex) {
			throw new DaoException(ex);
		}
	}

	protected <T> List<T> selectList(String statement) throws DaoException {
		try {
			return getSqlSession().selectList(statement);
		} catch (DataAccessException ex) {
			throw new DaoException(ex);
		}
	}

	protected <T> List<T> selectList(String statement, Object parameter) throws DaoException {
		try {
			return getSqlSession().selectList(statement, parameter);
		} catch (DataAccessException ex) {
			throw new DaoException(ex);
		}
	}

	protected int insert(String statement, Object parameter) throws DaoException {
		try {
			return getSqlSession().insert(statement, parameter);
		} catch (DataAccessException ex) {
			throw new DaoException(ex);
		}
	}

	protected int update(String statement) throws DaoException {
		try {
			return getSqlSession().update(statement);
		} catch (DataAccessException ex) {
			throw new DaoException(ex);
		}
	}

	protected int update(String statement, Object parameter) throws DaoException {
		try {
			return getSqlSession().update(statement, parameter);
		} catch (DataAccessException ex) {
			throw new DaoException(ex);
		}
	}

    protected void delete(String statement, Object parameter) throws DaoException {
        try {
            getSqlSession().delete(statement, parameter);
        } catch (DataAccessException ex) {
            throw new DaoException(ex);
        }
    }

}
