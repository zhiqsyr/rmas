package com.dl.rmas.web.vm.system;

import org.apache.commons.lang.StringUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;

import com.dl.rmas.entity.User;

public class UserLoginVM extends BaseVM {

	public static final String URL_USER_LOGIN = "/zul/system/user_login.zul";
	
	private User user;
	private String failMessage;
	
	@Init
	public void init() {
		user =  new User();
		
		user.setUserNo(getRequestAttribute(String.class, "userName"));
		user.setPassword(getRequestAttribute(String.class, "password"));
		failMessage = getRequestAttribute(String.class, "failMessage");
	}
	
	@Command
	public void onCreate() {
		if (StringUtils.isNotBlank(failMessage)) {
			showErrorBox(failMessage);
		}
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
