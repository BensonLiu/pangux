package com.eadmarket.pangu.dao.position.impl;

import java.util.List;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.dao.BaseDao;
import com.eadmarket.pangu.dao.position.PositionDao;
import com.eadmarket.pangu.domain.PositionDO;

/**
 * 广告位存储层接口默认实现类
 * 
 * @author liuyongpo@gmail.com
 */
class PositionDaoImpl extends BaseDao implements PositionDao {

	@Override
	public PositionDO getById(Long id) throws DaoException {
		return selectOne("PositionDao.getById", id);
	}

	@Override
	public List<PositionDO> getByProjectId(Long projectId) throws DaoException {
		return selectList("PositionDao.getByProjectId", projectId);
	}

	@Override
	public Long addPosition(PositionDO position) throws DaoException {
		insert("PositionDao.addPosition", position);
		return position.getId();
	}

	@Override
	public int updatePositionById(PositionDO position) throws DaoException {
		return update("PositionDao.updatePositionById", position);
	}

}
