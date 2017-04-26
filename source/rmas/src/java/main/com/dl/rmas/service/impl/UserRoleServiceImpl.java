package com.dl.rmas.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dl.rmas.entity.Role;
import com.dl.rmas.entity.User;
import com.dl.rmas.entity.UserRole;
import com.dl.rmas.service.UserRoleService;

@Service("userRoleService")
public class UserRoleServiceImpl extends BaseServiceImpl implements UserRoleService {

	@Override
	public List<UserRole> queryUserRoles(Integer userId, Integer roleId) {
		UserRole userRole = new UserRole();
		userRole.setUserId(userId);
		userRole.setRoleId(roleId);
		
		return queryByExample(userRole);
	}

	@Override
	public void doAssignUserRoles(User user, List<Role> roles) {
		List<UserRole> olds = queryUserRoles(user.getUserId(), null);
		for (UserRole userRole : olds) {
			doDelete(userRole);
		}
		
		UserRole userRole;
		for (Role role : roles) {
			userRole = new UserRole();
			userRole.setUserId(user.getUserId());
			userRole.setRoleId(role.getRoleId());
			userRole.setCreateUser(currentUserId());
			userRole.setCreateTime(currentTime());
			
			doSave(userRole);
		}
	}
	
	@Override
	public boolean isUserAdmin(Integer userId) {
		UserRole ur = new UserRole();
		ur.setUserId(userId);
		ur.setRoleId(1);
		
		List<UserRole> result = queryByExample(ur);
		if (result.size() == 1) {
			return true;
		}
		
		return false;
	}
	
}
