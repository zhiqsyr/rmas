package com.dl.rmas.web.converter;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.spring.SpringUtil;

import com.dl.rmas.entity.Order;
import com.dl.rmas.service.OrderService;

@SuppressWarnings("rawtypes")
public class OrderIdRmaConverter implements Converter {

	@Override
	public Object coerceToBean(Object obj, Component comp, BindContext ctx) {
		return obj;
	}

	@Override
	public Object coerceToUi(Object obj, Component comp, BindContext ctx) {
		if (obj == null) {
		    return "";
		}
		
		OrderService orderService = (OrderService) SpringUtil.getBean("orderService");
		return orderService.queryById(Order.class, Integer.parseInt(obj.toString())).getRma();
	}
	
}
