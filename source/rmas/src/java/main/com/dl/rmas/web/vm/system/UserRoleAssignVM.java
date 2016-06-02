package com.dl.rmas.web.vm.system;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Window;

import com.dl.rmas.common.cache.Constants;
import com.dl.rmas.entity.Role;
import com.dl.rmas.entity.User;
import com.dl.rmas.entity.UserRole;
import com.dl.rmas.service.RoleService;
import com.dl.rmas.service.UserRoleService;

public class UserRoleAssignVM extends BaseVM {

	public static final String URL_USER_ROLE_ASSIGN = "/zul/system/user_role_assign.zul";
	
	@WireVariable
	private RoleService roleService;
	@WireVariable
	private UserRoleService userRoleService;
	
	private User user;
	private List<Role> roleList;

	@Wire
	private Window editWin;
	
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		
		user = getArgValue(User.class, UserQueryVM.KEY_USER);

		// 列出系统有效角色，勾选user已有角色
		List<UserRole> userRoles = userRoleService.queryUserRoles(user.getUserId(), null);
		roleList = roleService.queryAllValid(Role.class);
		for (Role role : roleList) {
			for (UserRole userRole : userRoles) {
				if (userRole.getRoleId().equals(role.getRoleId())) {
					role.setIsRelated(true);
					break;
				}
			}
		}
		view.setAttribute("roleList", roleList);
	}
	
	@Command
	public void onSubmit() {
		List<Role> checked = new ArrayList<Role>();
		Checkbox cb;
		for (Role role : roleList) {
			cb = (Checkbox) editWin.getFellow(role.getRoleId().toString());
			if (cb.isChecked()) {
				checked.add(role);
			}
		}
		
		userRoleService.doAssignUserRoles(user, checked);
		showInformationBox(Constants.OPERATION_COMPLETED);
		editWin.detach();
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Role> getRoleList() {
		return roleList;
	}
	
}
