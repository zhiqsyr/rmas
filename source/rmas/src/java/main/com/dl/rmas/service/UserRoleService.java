package com.dl.rmas.service;

import java.util.List;

import com.dl.rmas.entity.Role;
import com.dl.rmas.entity.User;
import com.dl.rmas.entity.UserRole;

public interface UserRoleService extends BaseService {

	/**
	 * 依据userId及roleId获得用户角色关联情况，userId/roleId不可同时为null
	 *
	 * @param userId
	 * @param roleId
	 * @return
	 */
	List<UserRole> queryUserRoles(Integer userId, Integer roleId);
	
	/**
	 * 首先清空user原来的角色；然后为user分配roles角色
	 * 
	 * @param user
	 * @param roles
	 */
	void doAssignUserRoles(User user, List<Role> roles);
	
	/**
	 * <b>Function: <b>userId 是否是管理员
	 *
	 * @param userId
	 * @return
	 */
	boolean isUserAdmin(Integer userId);
	
}
