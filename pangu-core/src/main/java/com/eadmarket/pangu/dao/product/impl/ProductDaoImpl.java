package com.eadmarket.pangu.dao.product.impl;

import java.util.List;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.dao.BaseDao;
import com.eadmarket.pangu.dao.product.ProductDao;
import com.eadmarket.pangu.domain.ProductDO;

/**
 * 产品存储层默认实现
 * 
 * @author liuyongpo@gmail.com
 */
class ProductDaoImpl extends BaseDao implements ProductDao {

	@Override
	public ProductDO getById(Long id) throws DaoException {
		return selectOne("ProductDao.getById", id);
	}

	@Override
	public Long insert(ProductDO product) throws DaoException {
		insert("ProjectDao.insert", product);
		return product.getId();
	}

	@Override
	public void updateProduct(ProductDO product) throws DaoException {
		update("ProductDao.updateProduct", product);
	}

	@Override
	public List<ProductDO> getProductsByUserId(Long userId) throws DaoException {
		return selectList("ProductDao.getProductsByUserId", userId);
	}

}
