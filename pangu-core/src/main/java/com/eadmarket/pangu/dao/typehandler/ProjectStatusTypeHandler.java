package com.eadmarket.pangu.dao.typehandler;

import com.eadmarket.pangu.domain.ProjectDO.ProjectStatus;

/**
 * @author liuyongpo@gmail.com
 *
 */
public class ProjectStatusTypeHandler extends IEnumTypeHandler<ProjectStatus> {

	@Override
	protected ProjectStatus[] allCells() {
		return ProjectStatus.values();
	}

}
