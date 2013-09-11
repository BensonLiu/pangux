package com.eadmarket.pangu.manager.project;

import java.util.List;

import com.eadmarket.pangu.ManagerException;
import com.eadmarket.pangu.common.Query;
import com.eadmarket.pangu.domain.ProjectDO;
import com.eadmarket.pangu.dto.CreateProjectContext;
import com.eadmarket.pangu.query.ProjectQuery;

/**
 * 网站业务操作接口
 * 
 * @author liuyongpo@gmail.com
 */
public interface ProjectManager {
	ProjectDO getById(Long id) throws ManagerException;
	
	ProjectDO getProjectWithPositionById(Long id) throws ManagerException;
	
	Long createProject(CreateProjectContext projectContext) throws ManagerException;
	
	void deleteProjectById(Long id) throws ManagerException;
	
	void updateById(ProjectDO projectDO) throws ManagerException;
	
	List<ProjectDO> query(Query<ProjectQuery> query) throws ManagerException;
}
