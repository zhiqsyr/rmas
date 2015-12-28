package com.dl.rmas.service;

import com.dl.rmas.entity.Role;

public interface RoleService extends BaseService {
	
	void doCreateOrModifyRole(Role role);
	
	/**
	 * 删除角色，并删除角色关联用户及角色关联菜单
	 *
	 * @param role
	 */
	void doDeleteRole(Role role);
	
}
