package com.eadmarket.pangu.manager.position;

import com.eadmarket.pangu.ManagerException;
import com.eadmarket.pangu.domain.AdvertiseDO;

/**
 * 广告位的业务操作接口
 *
 * @author liuyongpo@gmail.com
 */
public interface AdvertiseManager {

  AdvertiseDO getById(Long id) throws ManagerException;

  AdvertiseDO getAdvertiseDOWithContractById(Long id) throws ManagerException;

  void addAdvertise(AdvertiseDO advertise) throws ManagerException;

  void updateAdvertiseStatus(Long advertiseId, AdvertiseDO.AdvertiseStatus targetStatus)
      throws ManagerException;

}
