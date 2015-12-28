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
import com.dl.rmas.common.utils.PropertiesUtils;
import com.dl.rmas.entity.User;
import com.dl.rmas.service.UserService;

public class UserEditVM extends BaseVM {

	public static final String URL_USER_EDIT = "/zul/system/user_edit.zul";
	
	@WireVariable
	private UserService userService;
	
	private User user;

	@Wire
	private Window editWin;
	
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		
		user = getArgValue(User.class, UserQueryVM.KEY_USER);
		if (user == null) {
			user = new User();
		}
	}
	
	@Command
	public void onSubmit() {
		if (user.getUserId() == null && userService.isUserNoExist(user.getUserNo())) {
			showWarningBox(PropertiesUtils.getValueInSystem("user.edit.validate"));
			return;
		}
		
		if (user.getUserId() == null) {
			userService.doCreateUserWithMD5(user);
		} else {
			userService.doModifyUser(user);
		}
		
		showInformationBox(Constants.OPERATION_COMPLETED);
		editWin.detach();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
