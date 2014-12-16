package com.eadmarket.pangu.dao.finance.impl;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.dao.BaseDao;
import com.eadmarket.pangu.dao.finance.FinanceDao;
import com.eadmarket.pangu.domain.FinanceDO;

import java.util.List;

/**
 * 财务存储层实现
 *
 * @author liuyongpo@gmail.com
 */
class FinanceDaoImpl extends BaseDao implements FinanceDao {

  @Override
  public void insert(FinanceDO finance) throws DaoException {
    insert("FinanceDao.insert", finance);
  }

  @Override
  public List<FinanceDO> getByUserId(Long userId) throws DaoException {
    return null;
  }

}
