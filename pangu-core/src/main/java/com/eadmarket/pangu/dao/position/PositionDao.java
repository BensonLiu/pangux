package com.eadmarket.pangu.dao.position;

import java.util.List;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.domain.PositionDO;

/**
 * ���λ�Ĵ洢��ӿ�
 * 
 * @author liuyongpo@gmail.com
 */
public interface PositionDao {
	
	PositionDO getById(Long id) throws DaoException;
	
	List<PositionDO> getByProjectId(Long projectId) throws DaoException;
	
	Long addPosition(PositionDO position) throws DaoException;
	
	int updatePositionById(PositionDO position) throws DaoException;
}
