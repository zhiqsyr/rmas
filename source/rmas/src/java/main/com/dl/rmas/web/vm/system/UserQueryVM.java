package com.dl.rmas.web.vm.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import com.dl.rmas.common.cache.Constants;
import com.dl.rmas.common.enums.Validity;
import com.dl.rmas.entity.User;
import com.dl.rmas.service.LabelValueBeanService;
import com.dl.rmas.service.UserService;
import com.dl.rmas.web.zkmodel.LabelValueBean;

public class UserQueryVM extends PageVM {

	public static final String KEY_USER = "user";
	public static final String KEY_OLD_VALIDATE = "oldValidate";
	
	@WireVariable
	private LabelValueBeanService labelValueBeanService;
	@WireVariable
	private UserService userService;
	
	private List<LabelValueBean<Validity>> validityLabelValueBeans;
	
	private User queryDto;
	private List<User> result;
	private User selected;
	
	@Init
	public void init() {
		validityLabelValueBeans = labelValueBeanService.buildByEnumClassWithSelect(Validity.class);
		
		queryDto = new User();
		queryDto.setValidity(Validity.VALID);
		
		onSearch();
	}
	
	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onSearch() {
		result = userService.queryUsersByQueryDto(queryDto, pagingDto);
	}
	
	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onShowAdd() {
		showModal(UserEditVM.URL_USER_EDIT);
		
		onSearch();
	}

	@Command
	@NotifyChange("selected")
	public void onShowEdit() {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put(KEY_USER, selected);
		showModal(UserEditVM.URL_USER_EDIT, args);
	}
	
	@Command
	public void onShowAssignRoles() {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put(KEY_USER, selected);
		showModal(UserRoleAssignVM.URL_USER_ROLE_ASSIGN, args);
	}
	
	@Command
	public void onShowPasswordEdit(@BindingParam("user")User user) {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put(KEY_USER, user);
		args.put(KEY_OLD_VALIDATE, Boolean.FALSE.toString());
		showModal(UserPasswordEditVM.URL_USER_PASSWORD_EDIT, args);
	}
	
	@Command
	@NotifyChange("result")
	public void onValidUser(@BindingParam("user")User user) {
		user.setValidity(Validity.VALID);
		userService.doModifyUser(user);
		
		showInformationBox(Constants.OPERATION_COMPLETED);
	}
	
	@Command
	@NotifyChange("result")
	public void onInValidUser(@BindingParam("user")User user) {
		user.setValidity(Validity.INVALID);
		userService.doModifyUser(user);
		
		showInformationBox(Constants.OPERATION_COMPLETED);
	}
	
	public List<LabelValueBean<Validity>> getValidityLabelValueBeans() {
		return validityLabelValueBeans;
	}
	
	public User getQueryDto() {
		return queryDto;
	}

	public void setQueryDto(User queryDto) {
		this.queryDto = queryDto;
	}

	public List<User> getResult() {
		return result;
	}
	
	public User getSelected() {
		return selected;
	}

	public void setSelected(User selected) {
		this.selected = selected;
	}
	
}
