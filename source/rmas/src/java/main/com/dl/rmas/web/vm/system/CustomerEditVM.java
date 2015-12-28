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
import com.dl.rmas.entity.Customer;
import com.dl.rmas.service.CustomerService;

public class CustomerEditVM extends BaseVM {

	public static final String URL_CUSTOMER_EDIT = "/zul/system/customer_edit.zul";
	
	@WireVariable
	private CustomerService customerService;
	
	private Customer customer;
	
	@Wire
	private Window editWin;
	
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		customer = getArgValue(Customer.class, CustomerQueryVM.KEY_CUSTOMER);
		
		if(customer == null){
			customer = new Customer();
		}
	}
	
	@Command
	public void onSubmit() {
		if (customer.getCustomerId() == null && !checkBeforeSubmit()) {
			return;
		}
		
		customerService.doCreateOrModifyCustomer(customer);
		
		showInformationBox(Constants.OPERATION_COMPLETED);
		editWin.detach();
	}

	private boolean checkBeforeSubmit() {
		if (customerService.isFullNameExist(customer.getFullName())) {
			showWarningBox(PropertiesUtils.getValueInSystem("customer.edit.validate.fullname"));
			return false;
		}
		if (customerService.isShortNameExist(customer.getShortName())) {
			showWarningBox(PropertiesUtils.getValueInSystem("customer.edit.validate.shortname"));
			return false;
		}
		
		return true;
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
}
