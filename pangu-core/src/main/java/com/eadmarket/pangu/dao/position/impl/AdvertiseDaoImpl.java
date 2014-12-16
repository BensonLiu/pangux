package com.eadmarket.pangu.dao.position.impl;

import com.google.common.collect.Maps;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.dao.BaseDao;
import com.eadmarket.pangu.dao.position.AdvertiseDao;
import com.eadmarket.pangu.domain.AdvertiseContractDO;
import com.eadmarket.pangu.domain.AdvertiseDO;

import java.util.List;
import java.util.Map;

/**
 * 广告位存储层接口默认实现类
 *
 * @author liuyongpo@gmail.com
 */
class AdvertiseDaoImpl extends BaseDao implements AdvertiseDao {

  @Override
  public AdvertiseDO getById(Long id) throws DaoException {
    return selectOne("AdvertiseDao.getById", id);
  }

  @Override
  public List<AdvertiseDO> getByProjectId(Long projectId) throws DaoException {
    return selectList("AdvertiseDao.getByProjectId", projectId);
  }

  @Override
  public Long addAdvertise(AdvertiseDO advertiseDO) throws DaoException {
    insert("AdvertiseDao.addAdvertise", advertiseDO);
    return advertiseDO.getId();
  }

  @Override
  public int updateAdvertiseById(AdvertiseDO advertise) throws DaoException {
    return update("AdvertiseDao.updateAdvertiseById", advertise);
  }

  @Override
  public void updateProfitById(Long id, Long addProfit) throws DaoException {
    Map<String, Object> param = Maps.newHashMap();
    param.put("id", id);
    param.put("addProfit", addProfit);

    update("AdvertiseDao.updateProfitById", param);
  }

  @Override
  public AdvertiseContractDO getActiveContractByAdvertiseId(Long advertiseId) throws DaoException {
    return selectOne("AdvertiseDao.getActiveContractByAdvertiseId", advertiseId);
  }

  @Override
  public void updateContractStatusByAdvertiseId(Long advertiseId, Integer status)
      throws DaoException {
    Map<String, Object> param = Maps.newHashMap();
    param.put("status", status);
    param.put("advertiseId", advertiseId);
    update("AdvertiseDao.updateContractStatusByAdvertiseId", param);
  }

  @Override
  public List<AdvertiseDO> queryPositionsForTimer(Long minId, AdvertiseDO.AdvertiseStatus status)
      throws DaoException {
    Map<String, Object> param = Maps.newHashMap();
    param.put("minId", minId);
    param.put("status", status);
    return selectList("AdvertiseDao.queryPositionsForTimer", param);
  }

}
