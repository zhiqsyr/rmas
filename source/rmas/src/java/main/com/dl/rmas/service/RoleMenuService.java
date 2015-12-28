package com.dl.rmas.service;

import java.util.List;

import com.dl.rmas.entity.Menu;
import com.dl.rmas.entity.Role;
import com.dl.rmas.entity.RoleMenu;

public interface RoleMenuService extends BaseService {

	/**
	 * 首先清空原来关联关系，然后插入新的关联
	 * 
	 * @param role
	 * @param menus
	 */
	void doCreateRoleMenus(Role role, List<Menu> menus);
	
	List<RoleMenu> queryRoleMenusByRoleId(Integer roleId);
	
}
