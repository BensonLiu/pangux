package com.eadmarket.pangu.manager.product.impl;

import java.util.List;

import javax.annotation.Resource;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.ExceptionCode;
import com.eadmarket.pangu.ManagerException;
import com.eadmarket.pangu.dao.product.ProductDao;
import com.eadmarket.pangu.domain.ProductDO;
import com.eadmarket.pangu.manager.product.ProductManager;

/**
 * 产品业务操作接口默认实现
 * 
 * @author liuyongpo@gmail.com
 */
class ProductManagerImpl implements ProductManager {

	@Resource private ProductDao productDao;
	
	@Override
	public ProductDO getById(Long id) throws ManagerException {
		try {
			return productDao.getById(id);
		} catch (DaoException ex) {
			throw new ManagerException(ExceptionCode.SYSTEM_ERROR, "id:" + id, ex);
		}
	}

	@Override
	public Long insert(ProductDO product) throws ManagerException {
		try {
			return productDao.insert(product);
		} catch (DaoException ex) {
			throw new ManagerException(ExceptionCode.SYSTEM_ERROR, "product:" + product, ex);
		}
	}

	@Override
	public void deleteProduct(Long id) throws ManagerException {
		ProductDO product = new ProductDO();
		product.setId(id);
		product.setStatus(ProductDO.DELETE_STATUS);
		updateProduct(product);
	}

	@Override
	public void updateProduct(ProductDO product) throws ManagerException {
		try {
			productDao.updateProduct(product);
		} catch (DaoException ex) {
			throw new ManagerException(ExceptionCode.SYSTEM_ERROR, "product:" + product, ex);
		}
	}

	@Override
	public List<ProductDO> getProductsByUserId(Long userId)
			throws ManagerException {
		try {
			return productDao.getProductsByUserId(userId);
		} catch (DaoException ex) {
			throw new ManagerException(ExceptionCode.SYSTEM_ERROR, "userId:" + userId, ex);
		}
	}

}
