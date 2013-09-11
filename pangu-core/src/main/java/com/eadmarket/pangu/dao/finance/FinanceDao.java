package com.eadmarket.pangu.dao.finance;

import java.util.List;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.domain.FinanceDO;

/**
 * @author liuyongpo@gmail.com
 */
public interface FinanceDao {
	void insert(FinanceDO finance) throws DaoException;
	
	List<FinanceDO> getByUserId(Long userId) throws DaoException;
}
