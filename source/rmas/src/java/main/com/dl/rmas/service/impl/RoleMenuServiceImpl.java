package com.dl.rmas.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dl.rmas.entity.Menu;
import com.dl.rmas.entity.Role;
import com.dl.rmas.entity.RoleMenu;
import com.dl.rmas.service.RoleMenuService;

@Service("roleMenuService")
public class RoleMenuServiceImpl extends BaseServiceImpl implements RoleMenuService {

	@Override
	public void doCreateRoleMenus(Role role, List<Menu> menus) {
		List<RoleMenu> olds = queryRoleMenusByRoleId(role.getRoleId());
		for (RoleMenu old : olds) {
			doDelete(old);
		}
		
		RoleMenu roleMenu;
		for (Menu menu : menus) {
			roleMenu = new RoleMenu();
			roleMenu.setRoleId(role.getRoleId());
			roleMenu.setMenuId(menu.getMenuId());
			roleMenu.setCreateUser(currentUserId());
			roleMenu.setCreateTime(currentTime());
			
			doSave(roleMenu);
		}
	}

	@Override
	public List<RoleMenu> queryRoleMenusByRoleId(Integer roleId) {
		RoleMenu roleMenu = new RoleMenu();
		roleMenu.setRoleId(roleId);
		
		return queryByExample(roleMenu);
	}
	
}
