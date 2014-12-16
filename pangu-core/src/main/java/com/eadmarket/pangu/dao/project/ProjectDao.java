package com.eadmarket.pangu.dao.project;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.common.Query;
import com.eadmarket.pangu.domain.ProjectDO;
import com.eadmarket.pangu.query.ProjectQuery;

import java.util.List;

/**
 * Project存储层接口
 *
 * @author liuyongpo@gmail.com
 */
public interface ProjectDao {

  ProjectDO getById(Long id) throws DaoException;

  List<ProjectDO> getByUserId(Long userId) throws DaoException;

  Long create(ProjectDO project) throws DaoException;

  void updateById(ProjectDO projectDO) throws DaoException;

  Integer count(Query<ProjectQuery> query) throws DaoException;

  List<ProjectDO> query(Query<ProjectQuery> query) throws DaoException;
}
