package com.eadmarket.pangu.manager.position.impl;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.ExceptionCode;
import com.eadmarket.pangu.ManagerException;
import com.eadmarket.pangu.dao.position.AdvertiseDao;
import com.eadmarket.pangu.domain.AdvertiseContractDO;
import com.eadmarket.pangu.domain.AdvertiseDO;
import com.eadmarket.pangu.manager.position.AdvertiseManager;

import javax.annotation.Resource;

/**
 * 广告位的业务接口实现类
 *
 * @author liuyongpo@gmail.com
 */
class AdvertiseManagerImpl implements AdvertiseManager {

  @Resource
  private AdvertiseDao advertiseDao;

  @Override
  public AdvertiseDO getById(Long id) throws ManagerException {
    try {
      return advertiseDao.getById(id);
    } catch (DaoException ex) {
      throw new ManagerException(ExceptionCode.SYSTEM_ERROR, "id:" + id, ex);
    }
  }

  @Override
  public AdvertiseDO getAdvertiseDOWithContractById(Long id) throws ManagerException {
    try {
      AdvertiseDO advertise = advertiseDao.getById(id);
      if (advertise != null) {
        AdvertiseContractDO contractDO = advertiseDao.getActiveContractByAdvertiseId(id);
        advertise.setContractDO(contractDO);
      }
      return advertise;
    } catch (DaoException ex) {
      throw new ManagerException(ExceptionCode.SYSTEM_ERROR, "id:" + id, ex);
    }
  }

  @Override
  public void addAdvertise(AdvertiseDO advertise) throws ManagerException {
    try {
      advertiseDao.addAdvertise(advertise);
    } catch (DaoException ex) {
      throw new ManagerException(ExceptionCode.SYSTEM_ERROR, "advertise:" + advertise, ex);
    }
  }

  @Override
  public void updateAdvertiseStatus(Long advertiseId, AdvertiseDO.AdvertiseStatus targetStatus)
      throws ManagerException {
    AdvertiseDO advertiseDO = new AdvertiseDO();
    advertiseDO.setId(advertiseId);
    advertiseDO.setStatus(targetStatus);
    try {
      advertiseDao.updateAdvertiseById(advertiseDO);
    } catch (DaoException ex) {
      throw new ManagerException(ExceptionCode.SYSTEM_ERROR, "advertise:" + advertiseDO, ex);
    }
  }

}
