package com.dl.rmas.web.vm.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import com.dl.rmas.common.cache.Constants;
import com.dl.rmas.common.enums.Validity;
import com.dl.rmas.entity.Customer;
import com.dl.rmas.service.CustomerService;
import com.dl.rmas.service.UserRoleService;

public class CustomerQueryVM extends PageVM {

	public static final String KEY_CUSTOMER = "customer";

	@WireVariable
	private CustomerService customerService;
	@WireVariable
	private UserRoleService userRoleService;
	
	private Customer queryCustomer = new Customer(Validity.VALID);
	private Customer selected;
	
	private List<Customer> result;
	
	@Init
	public void init() {
		onSearch();
	}
	
	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onSearch() {
		result = customerService.queryCustomersByQueryDto(queryCustomer, pagingDto);
	}

	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onShowAdd() {
		showModal(CustomerEditVM.URL_CUSTOMER_EDIT);
		onSearch();
	}
	
	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onShowEdit() {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put(KEY_CUSTOMER, selected);
		
		showModal(CustomerEditVM.URL_CUSTOMER_EDIT, args);
	}
	
	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onDelete() {
		if (!showQuestionBox(Constants.CONFIRM_TO_DELETE)) {
			return;
		}
		
		customerService.doDelete(selected);
		showInformationBox(Constants.OPERATION_COMPLETED);
		onSearch();		
	}

	public Customer getQueryCustomer() {
		return queryCustomer;
	}

	public void setQueryCustomer(Customer queryCustomer) {
		this.queryCustomer = queryCustomer;
	}

	public Customer getSelected() {
		return selected;
	}

	public void setSelected(Customer selected) {
		this.selected = selected;
	}

	public List<Customer> getResult() {
		return result;
	}
	
	public boolean getIsAdmin() {
		return userRoleService.isUserAdmin(getCurrentUser().getUserId());
	}	
	
}
