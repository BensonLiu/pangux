package com.eadmarket.pangu.dao.kv;

import com.eadmarket.pangu.DaoException;

/**
 * Created by liu on 1/10/14.
 */
public interface KVDao {

    String getByKey(String key) throws DaoException;

    void insertKV(String key, String value) throws DaoException;

    void updateKV(String key, String value) throws DaoException;
}
