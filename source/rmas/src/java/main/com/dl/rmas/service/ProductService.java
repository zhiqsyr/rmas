package com.dl.rmas.service;

import java.util.List;

import com.dl.rmas.entity.Product;
import com.dl.rmas.web.zkmodel.PagingDto;

public interface ProductService extends BaseService {

	Product queryProductByPn(String pn);
	
	/**
	 * <b>Function: <b>返回 pn 升序排列产品列表
	 *
	 * @return
	 */
	List<Product> queryAllSortedProducts();
	List<Product> queryProductsByQueryDto(Product product, PagingDto pagingDto);
	
	void doCreateOrModifyProduct(Product product);
	
	void doRemove(Product product);
	
}
