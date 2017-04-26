package com.dl.rmas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dl.rmas.common.enums.Validity;
import com.dl.rmas.entity.Role;
import com.dl.rmas.entity.RoleMenu;
import com.dl.rmas.entity.UserRole;
import com.dl.rmas.service.RoleMenuService;
import com.dl.rmas.service.RoleService;
import com.dl.rmas.service.UserRoleService;

@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl implements RoleService {

	@Autowired
	private RoleMenuService roleMenuService;
	@Autowired
	private UserRoleService userRoleService;
	
	@Override
	public void doCreateOrModifyRole(Role role) {
		if (role.getRoleId() == null) {
			role.setCreateUser(currentUserId());
			role.setCreateTime(currentTime());
			role.setValidity(Validity.VALID);
		
			doSave(role);
		} else {
			role.setLastModifier(currentUserId());
			role.setLastModifyTime(currentTime());
			
			doUpdate(role);
		}
	}
	
	@Override
	public void doDeleteRole(Role role) {
		// 删除角色关联菜单
		List<RoleMenu> roleMenus = roleMenuService.queryRoleMenusByRoleId(role.getRoleId());
		for (RoleMenu roleMenu : roleMenus) {
			roleMenuService.doDelete(roleMenu);
		}
		
		// 删除角色关联用户
		List<UserRole> userRoles = userRoleService.queryUserRoles(null, role.getRoleId());
		for (UserRole userRole : userRoles) {
			userRoleService.doDelete(userRole);
		}
		
		doDelete(role);
	}
	
}
