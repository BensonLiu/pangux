package com.eadmarket.pangu.dao.project.impl;

import java.util.List;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.common.Query;
import com.eadmarket.pangu.dao.BaseDao;
import com.eadmarket.pangu.dao.project.ProjectDao;
import com.eadmarket.pangu.domain.ProjectDO;
import com.eadmarket.pangu.query.ProjectQuery;

/**
 * @author liuyongpo@gmail.com
 */
class ProjectDaoImpl extends BaseDao implements ProjectDao {

	@Override
	public ProjectDO getById(Long id) throws DaoException {
		return selectOne("ProjectDao.getById", id);
	}

	@Override
	public Long create(ProjectDO project) throws DaoException {
		insert("ProjectDao.insert", project);
		return project.getId();
	}

	@Override
	public void updateById(ProjectDO projectDO) throws DaoException {
		update("ProjectDao.updateById", projectDO);
	}

	@Override
	public List<ProjectDO> getByUserId(Long userId) throws DaoException {
		return selectList("ProjectDao.getByUserId", userId);
	}

	@Override
	public Integer count(Query<ProjectQuery> query) throws DaoException {
		return selectOne("ProjectDao.count", query);
	}

	@Override
	public List<ProjectDO> query(Query<ProjectQuery> query) throws DaoException {
		return selectList("ProjectDao.query", query);
	}
	
}
