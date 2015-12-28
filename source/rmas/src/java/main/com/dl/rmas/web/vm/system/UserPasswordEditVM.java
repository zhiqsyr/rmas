package com.dl.rmas.web.vm.system;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.DigestUtils;
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

public class UserPasswordEditVM extends BaseVM {

	public static final String URL_USER_PASSWORD_EDIT = "/zul/system/user_password_edit.zul";
	
	@WireVariable
	private UserService userService;
	
	private User user;
	private String oldValidate;
	private String oldPassword;
	private String newPassword;
	private String confirmNewPassword;
	
	@Wire
	private Window editPasswordWin;
	
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		
		user = getArgValue(User.class, UserQueryVM.KEY_USER);
		oldValidate = getArgValue(String.class, UserQueryVM.KEY_OLD_VALIDATE);
		if (!Boolean.FALSE.toString().equals(oldValidate)) {
			oldValidate = Boolean.TRUE.toString();
		}
	}

	@Command
	public void onSubmit() {
		if (oldValidate.equals(Boolean.TRUE.toString())) {
			if (StringUtils.isBlank(oldPassword)) {
				showWarningBox(PropertiesUtils.getValueInSystem("user.password.edit.validate.old"));
				return;
			} 
			
			if (!DigestUtils.md5DigestAsHex(oldPassword.getBytes()).equals(user.getPassword())) {
				showWarningBox(PropertiesUtils.getValueInSystem("user.password.edit.validate.old.wrong"));
				return;
			}
		}
		if (!newPassword.equals(confirmNewPassword)) {
			showWarningBox(PropertiesUtils.getValueInSystem("user.password.edit.validate.new"));
			return;
		}
		
		userService.doModifyUserPassword(user, newPassword);
		
		showInformationBox(Constants.OPERATION_COMPLETED);
		editPasswordWin.detach();
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getOldValidate() {
		return oldValidate;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}
	
}
