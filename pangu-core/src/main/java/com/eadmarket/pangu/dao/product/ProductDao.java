package com.eadmarket.pangu.dao.product;

import java.util.List;

import com.eadmarket.pangu.DaoException;
import com.eadmarket.pangu.domain.ProductDO;

/**
 * ��Ʒ���ݴ洢��ӿ�
 * 
 * @author liuyongpo@gmail.com
 */
public interface ProductDao {
	ProductDO getById(Long id) throws DaoException;
	
	Long insert(ProductDO product) throws DaoException;
	
	void updateProduct(ProductDO product) throws DaoException;
	
	List<ProductDO> getProductsByUserId(Long userId) throws DaoException;
}
