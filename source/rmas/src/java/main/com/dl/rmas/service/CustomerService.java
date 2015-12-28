package com.dl.rmas.service;

import java.util.List;

import com.dl.rmas.entity.Customer;
import com.dl.rmas.web.zkmodel.PagingDto;

public interface CustomerService extends BaseService {

	boolean isFullNameExist(String fullName);
	boolean isShortNameExist(String shortName);
	
	void doCreateCustomer(Customer customer);
	
	void doModifyCustomer(Customer customer);

	void doCreateOrModifyCustomer(Customer customer);
	
	List<Customer> queryCustomersByQueryDto(Customer queryCustomer, PagingDto pagingDto);
}
