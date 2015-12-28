package com.dl.rmas.common.cache;

import com.dl.rmas.common.utils.PropertiesUtils;

public class Constants {

	public static final String WEBAPP_NAME = PropertiesUtils.getValueInSystem("webapp.name");

	/** TAT等级 */
	public static final Integer TYPE_ID_TAT_LEVEL = 300;
	/** 维修代码 */
	public static final Integer TYPE_ID_MAINTAIN_CODE = 100;
	/** CID类型 */
	public static final Integer TYPE_ID_CID_TYPE = 400;
	/** OUTLET */
	public static final Integer TYPE_ID_OUTLET = 500;
	/** 客户故障描述 */
	public static final Integer TYPE_ID_CUSTOMER_FAULT_DESC = 200;
	/** 产品类型 */
	public static final Integer TYPE_ID_PRODUCT_TYPE = 600;
	
	public static final Integer MENU_ID_SYSTEM_MANAGEMENT = 400;
	
	public static final String WITH_SELECT = PropertiesUtils.getValueInSystem("with.select");
	
	/** 操作完成 */
	public static final String OPERATION_COMPLETED = PropertiesUtils.getValueInSystem("operation.completed");
	/** 确认删除？ */
	public static final String CONFIRM_TO_DELETE = PropertiesUtils.getValueInSystem("confirm.to.delete");
	
	
}
