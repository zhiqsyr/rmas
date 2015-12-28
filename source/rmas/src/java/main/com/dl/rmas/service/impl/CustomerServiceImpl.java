package com.dl.rmas.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dl.rmas.common.enums.Validity;
import com.dl.rmas.dao.CustomerDao;
import com.dl.rmas.entity.Customer;
import com.dl.rmas.service.CustomerService;
import com.dl.rmas.web.zkmodel.PagingDto;

@Service("customerService")
public class CustomerServiceImpl extends BaseServiceImpl implements CustomerService {

	@Autowired
	private CustomerDao customerDao;
	
	@Override
	public boolean isFullNameExist(String fullName) {
		if (StringUtils.isBlank(fullName)) {
			return false;
		}
		
		Customer customer = new Customer();
		customer.setFullName(fullName);
		
		List<Customer> result = queryByExample(customer);
		
		if (result.size() == 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isShortNameExist(String shortName) {
		if (StringUtils.isBlank(shortName)) {
			return false;
		}
		
		Customer customer = new Customer();
		customer.setShortName(shortName);
		
		List<Customer> result = queryByExample(customer);
		
		if (result.size() == 1) {
			return true;
		}
		return false;
	}

	@Override
	public void doCreateCustomer(Customer customer) {
		customer.setCreateUser(currentUserId());
		customer.setCreateTime(currentTime());
		customer.setValidity(Validity.VALID);
		
		doSave(customer);
	}
	
	@Override
	public void doModifyCustomer(Customer customer) {
		customer.setLastModifier(currentUserId());
		customer.setLastModifyTime(currentTime());
		
		doUpdate(customer);
	}
	
	@Override
	public void doCreateOrModifyCustomer(Customer customer) {
		if (customer.getCustomerId() == null) {
			doCreateCustomer(customer);
		} else {
			doModifyCustomer(customer);
		}
	}
	
	@Override
	public List<Customer> queryCustomersByQueryDto(Customer queryCustomer, PagingDto pagingDto){
		return customerDao.findCustomersByQueryDto(queryCustomer, pagingDto);
	}
}
