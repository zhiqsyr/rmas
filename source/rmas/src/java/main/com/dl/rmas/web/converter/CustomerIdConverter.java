package com.dl.rmas.web.converter;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.spring.SpringUtil;

import com.dl.rmas.entity.Customer;
import com.dl.rmas.service.CustomerService;

@SuppressWarnings("rawtypes")
public class CustomerIdConverter implements Converter {

	@Override
	public Object coerceToBean(Object obj, Component comp, BindContext ctx) {
		return obj;
	}

	@Override
	public Object coerceToUi(Object obj, Component comp, BindContext ctx) {
		if (obj == null) {
		    return "";
		}
		
		CustomerService customerService = (CustomerService) SpringUtil.getBean("customerService");
		return customerService.queryById(Customer.class, Integer.parseInt(obj.toString())).getFullName();
	}
	
}
