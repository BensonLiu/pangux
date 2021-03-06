package com.eadmarket.pangu.dao.kv.impl;

import com.google.common.collect.Maps;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.dao.BaseDao;
import com.eadmarket.pangu.dao.kv.KVDao;

import java.util.Map;

/**
 * Created by liu on 1/10/14.
 */
class KVDaoImpl extends BaseDao implements KVDao {

  @Override
  public String getByKey(String key) throws DaoException {
    return selectOne("KVDao.getByKey", key);
  }

  @Override
  public void insertKV(String key, String value) throws DaoException {
    Map<String, Object> param = Maps.newHashMap();
    param.put("tKey", key);
    param.put("tValue", value);
    insert("KVDao.insertKV", param);
  }

  @Override
  public void updateKV(String key, String value) throws DaoException {
    Map<String, Object> param = Maps.newHashMap();
    param.put("tKey", key);
    param.put("tValue", value);
    update("KVDao.updateKV", param);
  }
}
