package com.eadmarket.pangu.manager.product;

import java.util.List;

import com.eadmarket.pangu.ManagerException;
import com.eadmarket.pangu.domain.ProductDO;

/**
 * 产品的业务接口类
 * 
 * @author liuyongpo@gmail.com
 */
public interface ProductManager {
	ProductDO getById(Long id) throws ManagerException;
	
	Long insert(ProductDO product) throws ManagerException;
	
	void deleteProduct(Long id) throws ManagerException;
	
	void updateProduct(ProductDO product) throws ManagerException;
	
	List<ProductDO> getProductsByUserId(Long userId) throws ManagerException;
}
