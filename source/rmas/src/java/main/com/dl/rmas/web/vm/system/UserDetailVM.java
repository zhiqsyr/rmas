package com.dl.rmas.web.vm.system;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;

import com.dl.rmas.entity.User;

public class UserDetailVM extends BaseVM {

	public static final String URL_USER_DETAIL = "/zul/system/user_detail.zul";
	
	private User user;
	
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		
		user = getArgValue(User.class, UserQueryVM.KEY_USER);
	}
	
	@Command
	public void onShowUserPasswordEdit() {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put(UserQueryVM.KEY_USER, user);
		
		showModal(UserPasswordEditVM.URL_USER_PASSWORD_EDIT, args);
	}
	
	@Command
	@NotifyChange("user")
	public void onShowEditWin() {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put(UserQueryVM.KEY_USER, user);
		
		showModal(UserEditVM.URL_USER_EDIT, args);
	}

	public User getUser() {
		return user;
	}
	
}
