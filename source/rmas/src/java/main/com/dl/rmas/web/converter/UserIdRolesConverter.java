package com.dl.rmas.web.converter;

import java.util.List;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.spring.SpringUtil;

import com.dl.rmas.entity.Role;
import com.dl.rmas.entity.UserRole;
import com.dl.rmas.service.RoleService;
import com.dl.rmas.service.UserRoleService;

@SuppressWarnings("rawtypes")
public class UserIdRolesConverter implements Converter {
	
	@Override
	public Object coerceToBean(Object obj, Component comp, BindContext ctx) {
		return obj;
	}

	@Override
	public Object coerceToUi(Object obj, Component comp, BindContext ctx) {
		if (obj == null) {
		    return "";
		}
		
		UserRoleService userRoleService = (UserRoleService) SpringUtil.getBean("userRoleService");
		List<UserRole> userRoles = userRoleService.queryUserRoles(Integer.parseInt(obj.toString()), null);
		
		if (userRoles.size() == 0) {
			return "";
		}
		
		
		StringBuilder sb = new StringBuilder();
		RoleService roleService = (RoleService) SpringUtil.getBean("roleService");
		for (UserRole userRole : userRoles) {
			sb.append(roleService.queryById(Role.class, userRole.getRoleId()).getRoleName() + "，");
		}
		return sb.substring(0, sb.length() - 1);
	}
	
}
