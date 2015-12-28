package com.dl.rmas.service.impl;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dl.rmas.common.cache.Constants;
import com.dl.rmas.common.enums.KeyinStatus;
import com.dl.rmas.common.enums.ReceiveStatus;
import com.dl.rmas.common.utils.PropertiesUtils;
import com.dl.rmas.entity.Bom;
import com.dl.rmas.entity.Customer;
import com.dl.rmas.entity.DictCode;
import com.dl.rmas.entity.DictType;
import com.dl.rmas.entity.Order;
import com.dl.rmas.entity.Product;
import com.dl.rmas.entity.User;
import com.dl.rmas.service.BomService;
import com.dl.rmas.service.CustomerService;
import com.dl.rmas.service.DictTypeCodeService;
import com.dl.rmas.service.LabelValueBeanService;
import com.dl.rmas.service.OrderService;
import com.dl.rmas.service.ProductService;
import com.dl.rmas.service.UserService;
import com.dl.rmas.web.zkmodel.LabelValueBean;

@Service("labelValueBeanService")
public class LabelValueBeanServiceImpl implements LabelValueBeanService {

	@Autowired
	private DictTypeCodeService dictTypeCodeService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;
	@Autowired
	private BomService bomService;
	@Autowired
	private ProductService productService;
	
	@Override
	public List<LabelValueBean<Integer>> buildByDictTypeIdWithSelect(
			Integer typeId) {
		List<LabelValueBean<Integer>> result = new ArrayList<LabelValueBean<Integer>>();
		
		LabelValueBean<Integer> bean = new LabelValueBean<Integer>();
		bean.setLabel(Constants.WITH_SELECT);
		result.add(bean);
		result.addAll(buildByDictTypeId(typeId));
		
		return result;
	}
	
	@Override
	public List<LabelValueBean<Integer>> buildByDictTypeId(Integer typeId) {
		DictType dictType = dictTypeCodeService.queryById(DictType.class, typeId);
		List<DictCode> dictCodes = dictTypeCodeService.queryDictCodesByDictTypeId(typeId);
		
		List<LabelValueBean<Integer>> result = new ArrayList<LabelValueBean<Integer>>();
		
		LabelValueBean<Integer> bean;
		for (DictCode dictCode : dictCodes) {
			bean = new LabelValueBean<Integer>();
			
			switch (dictType.getShowWhich()) {
			case CODE_KEY:
				bean.setLabel(dictCode.getCodeKey());
				break;
			case CODE_NAME:
				bean.setLabel(dictCode.getCodeName());
				break;
			case CODE_DESC:
				bean.setLabel(dictCode.getCodeDesc());
			}
			bean.setValue(dictCode.getCodeId());

			result.add(bean);
		}
		
		return result;
	}
	
	@Override
	public <E extends Enum<E>> List<LabelValueBean<E>> buildByEnumClassWithSelect(
			Class<E> enumClass) {
		List<LabelValueBean<E>> result = new ArrayList<LabelValueBean<E>>();
		
		LabelValueBean<E> bean = new LabelValueBean<E>();
		bean.setLabel(Constants.WITH_SELECT);
		result.add(bean);
		result.addAll(buildByEnumClass(enumClass));
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <E extends Enum<E>> List<LabelValueBean<E>> buildByEnumClass(
			Class<E> enumClass) {
		List<LabelValueBean<E>> result = new ArrayList<LabelValueBean<E>>();
		
		if (enumClass == null) {
			return result;
		}
        
		EnumSet<E> enumSet = EnumSet.allOf(enumClass);
        if(enumSet == null || enumSet.isEmpty()) {
        	return result;
        }
            
        LabelValueBean<E> bean;
        for (Enum<E> e : enumSet) {
        	bean = new LabelValueBean<E>();
			bean.setLabel(PropertiesUtils.getValueInEnums(enumClass.getName() + "." + e.name()));
        	bean.setValue((E) e);
        	
        	result.add(bean);
		}
        
		return result;
	}
	
	@Override
	public List<LabelValueBean<Integer>> buildValidCustomersWithSelect() {
		List<LabelValueBean<Integer>> result = new ArrayList<LabelValueBean<Integer>>();
		
		LabelValueBean<Integer> bean = new LabelValueBean<Integer>();
		bean.setLabel(Constants.WITH_SELECT);
		result.add(bean);
		result.addAll(buildValidCustomers());
		
		return result;
	}
	
	@Override
	public List<LabelValueBean<Integer>> buildValidCustomers() {
		List<Customer> validCostomers = customerService.queryAllValid(Customer.class);

		List<LabelValueBean<Integer>> result = new ArrayList<LabelValueBean<Integer>>(validCostomers.size());
		LabelValueBean<Integer> bean;
		for (Customer customer : validCostomers) {
			bean = new LabelValueBean<Integer>(customer.getShortName() + ": " + customer.getFullName(), 
					customer.getCustomerId());
			result.add(bean);
		}
		
		return result;
	}
	
	@Override
	public List<LabelValueBean<String>> buildOpenRmasByCustomerIdWithSelect(
			Integer customerId) {
		List<LabelValueBean<String>> result = new ArrayList<LabelValueBean<String>>();
		
		LabelValueBean<String> bean = new LabelValueBean<String>();
		bean.setLabel(Constants.WITH_SELECT);
		result.add(bean);
		result.addAll(buildOpenRmasByCustomerId(customerId));
		
		return result;
	}
	
	@Override
	public List<LabelValueBean<String>> buildOpenRmasByCustomerId(Integer customerId) {
		Order query = new Order();
		query.setCustomerId(customerId);
		query.setKeyinStatus(KeyinStatus.OPEN);
		query.setReceiveStatus(ReceiveStatus.RECEIVED);
		List<Order> orders = orderService.queryOrdersByQueryDto(query, null);

		List<LabelValueBean<String>> result = new ArrayList<LabelValueBean<String>>(orders.size());
		LabelValueBean<String> bean;
		for (Order order : orders) {
			bean = new LabelValueBean<String>(order.getRma(), order.getRma());
			
			result.add(bean);
		}
		
		return result;
	}
	
	@Override
	public List<LabelValueBean<Integer>> buildUsersWithSelect() {
		List<LabelValueBean<Integer>> result = new ArrayList<LabelValueBean<Integer>>();
		
		LabelValueBean<Integer> bean = new LabelValueBean<Integer>();
		bean.setLabel(Constants.WITH_SELECT);
		result.add(bean);
		result.addAll(buildUsers());
		
		return result;
	}
	
	@Override
	public List<LabelValueBean<Integer>> buildUsers() {
		List<User> users = userService.queryAllValid(User.class);
		
		List<LabelValueBean<Integer>> result = new ArrayList<LabelValueBean<Integer>>(users.size());
		LabelValueBean<Integer> bean;
		for (User user : users) {
			bean = new LabelValueBean<Integer>(user.getUserNo() + ": " + user.getUserName(), user.getUserId());
			
			result.add(bean);
		}
		
		return result;
	}
	
	@Override
	public List<LabelValueBean<Bom>> buildBomMaterialNameWithSelect(Integer productId) {
		List<Bom> bomList = bomService.queryByProductId(productId);
		
		List<String> materialNames = new ArrayList<String>();
		Map<String, Bom> maps = new HashMap<String, Bom>();
		for (Bom bom : bomList) {
			if (maps.get(bom.getMaterialName()) == null) {
				maps.put(bom.getMaterialName(), bom);
			}
		}
		
		List<LabelValueBean<Bom>> result = new ArrayList<LabelValueBean<Bom>>(materialNames.size() + 1);
		result.add(new LabelValueBean<Bom>(Constants.WITH_SELECT, null));
		for (String materialName : maps.keySet()) {
			result.add(new LabelValueBean<Bom>(materialName, maps.get(materialName)));
		}
		return result;
	}
	
	@Override
	public List<LabelValueBean<Integer>> buildPnsWithSelect() {
		List<Product> products = productService.queryAll(Product.class);
		
		List<LabelValueBean<Integer>> result = new ArrayList<LabelValueBean<Integer>>();
		result.add(new LabelValueBean<Integer>(Constants.WITH_SELECT, null));
		for (Product product : products) {
			result.add(new LabelValueBean<Integer>(product.getPn(), product.getProductId()));
		}
		
		return result;
	}

}
