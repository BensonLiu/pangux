package com.eadmarket.pangu.manager.position;

import com.eadmarket.pangu.ManagerException;
import com.eadmarket.pangu.domain.PositionDO;

/**
 * ���λ��ҵ������ӿ�
 * 
 * @author liuyongpo@gmail.com
 */
public interface PositionManager {
	PositionDO getById(Long id) throws ManagerException;
	
	void addPosition(PositionDO position) throws ManagerException;
}
