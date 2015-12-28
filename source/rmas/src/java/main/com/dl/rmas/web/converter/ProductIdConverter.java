package com.dl.rmas.web.converter;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.spring.SpringUtil;

import com.dl.rmas.entity.Product;
import com.dl.rmas.service.ProductService;

@SuppressWarnings("rawtypes")
public class ProductIdConverter implements Converter {

	@Override
	public Object coerceToBean(Object obj, Component comp, BindContext ctx) {
		return obj;
	}

	@Override
	public Object coerceToUi(Object obj, Component comp, BindContext ctx) {
		if (obj == null) {
		    return "";
		}
		
		ProductService productService = (ProductService) SpringUtil.getBean("productService");
		Product vo = productService.queryById(Product.class, Integer.parseInt(obj.toString()));
		return vo == null ? "" : vo.getPn();
	}
	
}