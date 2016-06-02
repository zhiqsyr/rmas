package com.dl.core.jxls.validation.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dl.core.jxls.model.CellDataModel;
import com.dl.core.jxls.model.RowDataModel;
import com.dl.core.jxls.model.UploadDataModel;

/**
 * 告诉验证器   根据参数来判断本列是否需要填写
 * @author Lucas
 * @date 2014-7-24 下午4:45:08
 */
@Component("coopTypeSexVal")
@Scope("prototype")
public class CoopTypeControlSexValidator extends AbstractColumnValidator {

	protected String PARAM_NAME_KEY = "p";
	protected String PARAM_NAME_VAL = "v";
	protected String PARAM_NAME_MSG_OBJ = "m";
	private Object param_1 = null;
	private Object param_2 = null;
	private Object param_3 = null;
	private Object param_4 = null;
	
	@Override
	protected boolean doValidate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		String colName = getParameter(PARAM_NAME_KEY, null);
		String colVal = getParameter(PARAM_NAME_VAL, null);
		String objVal = getParameter(PARAM_NAME_MSG_OBJ, null);
		
		Object _val = row.getCell(colName).getValue();//实际值
		
		if (colVal.equals(_val)){
			if (StringUtils.isBlank(value)){
				param_1 = getColumnAlias(colName);
				param_2 = objVal;
				param_3 = getColumnConfig().getDataPosition();
				param_4 = getColumnAlias();
				return false;
			}
		}
		return true;
	}

	protected boolean ignoreBlankValue() {
		return false;
	}
	
	protected String getDefaultErrMsg() {
		return "{0}选择了{1}，必须选择第{2}行{3}";
	}
	
	protected Object[] getErrParams() {
		return new Object[] { param_1, param_2, param_3, param_4 };
	}
}
