package com.eadmarket.pangu.dao.finance;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.domain.FinanceDO;

import java.util.List;

/**
 * @author liuyongpo@gmail.com
 */
public interface FinanceDao {

  void insert(FinanceDO finance) throws DaoException;

  List<FinanceDO> getByUserId(Long userId) throws DaoException;
}
