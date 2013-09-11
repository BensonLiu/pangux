package com.eadmarket.pangu.manager.position.impl;

import javax.annotation.Resource;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.ExceptionCode;
import com.eadmarket.pangu.ManagerException;
import com.eadmarket.pangu.dao.position.PositionDao;
import com.eadmarket.pangu.domain.PositionDO;
import com.eadmarket.pangu.manager.position.PositionManager;

/**
 * 广告位的业务接口实现类
 * 
 * @author liuyongpo@gmail.com
 */
class PositionManagerImpl implements PositionManager {

	@Resource private PositionDao positionDao;
	
	@Override
	public PositionDO getById(Long id) throws ManagerException {
		try {
			return positionDao.getById(id);
		} catch (DaoException ex) {
			throw new ManagerException(ExceptionCode.SYSTEM_ERROR, "id:" + id, ex);
		}
	}

	@Override
	public void addPosition(PositionDO position) throws ManagerException {
		try {
			positionDao.addPosition(position);
		} catch (DaoException ex) {
			throw new ManagerException(ExceptionCode.SYSTEM_ERROR, "position:" + position, ex);
		}
	}

}
