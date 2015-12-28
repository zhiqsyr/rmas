package com.dl.rmas.service;

import java.util.List;

import com.dl.rmas.entity.Bom;
import com.dl.rmas.web.zkmodel.LabelValueBean;

public interface LabelValueBeanService {

	List<LabelValueBean<Integer>> buildByDictTypeIdWithSelect(Integer typeId);
	List<LabelValueBean<Integer>> buildByDictTypeId(Integer typeId);

	<E extends Enum<E>> List<LabelValueBean<E>> buildByEnumClassWithSelect(Class<E> enumClass);
	<E extends Enum<E>> List<LabelValueBean<E>> buildByEnumClass(Class<E> enumClass);
	
	/**
	 * 客户公司下拉列表
	 *
	 * @return
	 */
	List<LabelValueBean<Integer>> buildValidCustomersWithSelect();
	List<LabelValueBean<Integer>> buildValidCustomers();
	
	/**
	 * 依据客户公司ID，组建RMA下拉列表
	 * 
	 * @return
	 */
	List<LabelValueBean<String>> buildOpenRmasByCustomerIdWithSelect(Integer customerId);
	List<LabelValueBean<String>> buildOpenRmasByCustomerId(Integer customerId);
	
	/**
	 * 员工下拉列表
	 *
	 * @return
	 */
	List<LabelValueBean<Integer>> buildUsersWithSelect();
	List<LabelValueBean<Integer>> buildUsers();
	
	/**
	 * 物料类别下拉列表
	 * 
	 * @return
	 */
	List<LabelValueBean<Bom>> buildBomMaterialNameWithSelect(Integer productId);
	
	/**
	 * 产品pn下拉列表
	 * 
	 * @return
	 */
	List<LabelValueBean<Integer>> buildPnsWithSelect();
	
}
