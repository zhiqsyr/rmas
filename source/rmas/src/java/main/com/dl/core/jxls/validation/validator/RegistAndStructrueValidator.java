package com.dl.core.jxls.validation.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dl.core.jxls.model.CellDataModel;
import com.dl.core.jxls.model.RowDataModel;
import com.dl.core.jxls.model.UploadDataModel;

/**
 * 处理选择了已按《登记条例》登记后的关联项
 * @author Lucas
 * @date 2014-7-23 上午10:28:25
 */
@Component("registAndStructrueVal")
@Scope("prototype")
public class RegistAndStructrueValidator extends AbstractColumnValidator {

	protected String PARAM_NAME_P = "p";
	private String errMsg = "选中了22行<font color='red'>其中：是否已按《登记条例》登记</font>,第{0}行{1}1到7必需选择一项.";
	private Object param_1 = null;
	private Object param_2 = null;
	private final String NEED_COLUMN = "is_regist";
	private final String YES_VALUE = "1";
	
	protected boolean doValidate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		String colName = getParameter(PARAM_NAME_P, null);
		
		Object _val = row.getCell(colName).getValue();
		Object _is_regist_val = row.getCell(NEED_COLUMN).getValue();
		
		if (YES_VALUE.equals(_is_regist_val)) {
			if (_val == null || StringUtils.isBlank(_val.toString())) {
				param_1 = cell.getColumnConfig().getDataPosition();
				param_2 = cell.getAliasName();
				return false;
			}
		}
		
		return true;
	}
	
	protected boolean ignoreBlankValue() {
		return false;
	}
	
	protected String getDefaultErrMsg() {
		return errMsg;
	}
	
	protected Object[] getErrParams() {
		return new Object[] { param_1, param_2 };
	}

}
