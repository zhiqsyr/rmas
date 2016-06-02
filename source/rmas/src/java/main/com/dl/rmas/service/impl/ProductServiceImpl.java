package com.dl.rmas.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dl.rmas.common.enums.Validity;
import com.dl.rmas.dao.ProductDao;
import com.dl.rmas.entity.Product;
import com.dl.rmas.service.ProductService;
import com.dl.rmas.web.zkmodel.PagingDto;

@Service("productService")
public class ProductServiceImpl extends BaseServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	
	@Override
	public Product queryProductByPn(String pn) {
		Product product = new Product();
		product.setPn(pn);
		
		List<Product> products = queryByExample(product);
		if (products.size() == 1 && products.get(0).getPn().equals(pn)) {
			return products.get(0);
		}
		
		return null;
	}
	
	@Override
	public List<Product> queryAllSortedProducts() {
		DetachedCriteria dc = DetachedCriteria.forClass(Product.class);
		dc.addOrder(Order.asc("pn"));
		return (List<Product>) productDao.getHibernateTemplate().findByCriteria(dc);
	}
	
	@Override
	public List<Product> queryProductsByQueryDto(Product product, PagingDto pagingDto) {
		return productDao.findProductsByQueryDto(product, pagingDto);
	}
	
	@Override
	public void doCreateOrModifyProduct(Product product) {
		if (product.getProductId() == null) {
			product.setCreateUser(currentUserId());
			product.setCreateTime(currentTime());
			product.setValidity(Validity.VALID);
		} else {
			product.setLastModifier(currentUserId());
			product.setLastModifyTime(currentTime());
		}
		
		doSaveOrUpdate(product);
	}
	
	@Override
	public void doRemove(Product product) {
		product.setValidity(Validity.INVALID);
		
		doCreateOrModifyProduct(product);
	}
	
}
