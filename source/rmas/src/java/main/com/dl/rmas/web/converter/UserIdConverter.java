package com.dl.rmas.web.converter;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.spring.SpringUtil;

import com.dl.rmas.entity.User;
import com.dl.rmas.service.UserService;

@SuppressWarnings("rawtypes")
public class UserIdConverter implements Converter {
	
	@Override
	public Object coerceToBean(Object obj, Component comp, BindContext ctx) {
		return obj;
	}

	@Override
	public Object coerceToUi(Object obj, Component comp, BindContext ctx) {
		if (obj == null) {
		    return "";
		}
		
		UserService userService = (UserService) SpringUtil.getBean("userService");
		return userService.queryById(User.class, Integer.parseInt(obj.toString())).getUserName();
	}
	
}
