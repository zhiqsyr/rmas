package com.dl.rmas.web.vm.system;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Window;

import com.dl.rmas.common.cache.Constants;
import com.dl.rmas.entity.Role;
import com.dl.rmas.service.RoleService;

public class RoleEditVM extends BaseVM {

	public static final String URL_ROLE_EDIT = "/zul/system/role_edit.zul";
	
	@WireVariable
	private RoleService roleService;
	
	private Role role;
	
	@Wire
	private Window editWin;
	
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		
		role = getArgValue(Role.class, RoleQueryVM.KEY_ROLE);
		if (role == null) {
			role = new Role();
		}
	}
	
	@Command
	public void onSubmit() {
		roleService.doCreateOrModifyRole(role);
		
		showInformationBox(Constants.OPERATION_COMPLETED);
		editWin.detach();
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
}
