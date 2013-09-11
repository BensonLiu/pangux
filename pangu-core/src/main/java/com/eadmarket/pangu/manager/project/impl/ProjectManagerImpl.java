package com.eadmarket.pangu.manager.project.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.ExceptionCode;
import com.eadmarket.pangu.ManagerException;
import com.eadmarket.pangu.common.Query;
import com.eadmarket.pangu.dao.position.PositionDao;
import com.eadmarket.pangu.dao.project.ProjectDao;
import com.eadmarket.pangu.domain.PositionDO;
import com.eadmarket.pangu.domain.ProjectDO;
import com.eadmarket.pangu.domain.ProjectDO.ProjectStatus;
import com.eadmarket.pangu.dto.CreateProjectContext;
import com.eadmarket.pangu.manager.project.ProjectManager;
import com.eadmarket.pangu.query.ProjectQuery;


/**
 * Project业务接口默认实现
 * 
 * @author liuyongpo@gmail.com
 */
class ProjectManagerImpl implements ProjectManager {

	@Resource private ProjectDao projectDao;
	@Resource private PositionDao positionDao;
	
	@Override
	public ProjectDO getById(Long id) throws ManagerException {
		try {
			return projectDao.getById(id);
		} catch (DaoException ex) {
			throw new ManagerException(ExceptionCode.SYSTEM_ERROR, "id:" + id, ex);
		}
	}

	@Override
	public ProjectDO getProjectWithPositionById(Long id)
			throws ManagerException {
		ProjectDO project = getById(id);
		try {
			List<PositionDO> positions = positionDao.getByProjectId(id);
			project.setPositions(positions);
		} catch (DaoException ex) {
			throw new ManagerException(ExceptionCode.SYSTEM_ERROR, "id:" + id, ex);
		}
		return project;
	}

	@Override
	public Long createProject(CreateProjectContext projectContext)
			throws ManagerException {
		ProjectDO project = new ProjectDO();
		project.setCategoryId(projectContext.getCategoryId());
		project.setDescription(projectContext.getDescription());
		project.setLogoUrl(projectContext.getLogoUrl());
		project.setOwnerId(projectContext.getOwnerId());
		project.setTitle(projectContext.getTitle());
		project.setType(projectContext.getType());
		project.setStatus(ProjectStatus.NORMAL);
		//TODO:补充alex
		try {
			return projectDao.create(project);
		} catch (DaoException ex) {
			throw new ManagerException(ExceptionCode.SYSTEM_ERROR, "project:" + project, ex);
		}
	}

	@Override
	public void deleteProjectById(Long id) throws ManagerException {
		ProjectDO project = new ProjectDO();
		project.setId(id);
		project.setStatus(ProjectStatus.DELETED);
		updateById(project);
	}

	@Override
	public void updateById(ProjectDO projectDO) throws ManagerException {
		try {
			projectDao.updateById(projectDO);
		} catch (DaoException ex) {
			throw new ManagerException(ExceptionCode.SYSTEM_ERROR, "project:" + projectDO, ex);
		}
	}

	@Override
	public List<ProjectDO> query(Query<ProjectQuery> query)
			throws ManagerException {
		try {
			int count = projectDao.count(query);
			query.setTotalItem(count);
			if (count <= 0) {
				return Collections.emptyList();
			}
			return projectDao.query(query);
		} catch (DaoException ex) {
			throw new ManagerException(ExceptionCode.SYSTEM_ERROR, "query:" + query, ex);
		}
	}

}
