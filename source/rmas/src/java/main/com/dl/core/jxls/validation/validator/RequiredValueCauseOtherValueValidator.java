package com.dl.core.jxls.validation.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dl.core.jxls.model.CellDataModel;
import com.dl.core.jxls.model.RowDataModel;
import com.dl.core.jxls.model.UploadDataModel;
import com.dl.core.jxls.validation.util.ValidateUtils;

/**
 * @author Lucas
 * @date 2014-3-24 下午1:19:04
 */
@Component("requiredCauseOtherVal")
@Scope("prototype")
public class RequiredValueCauseOtherValueValidator extends
		AbstractColumnValidator {

	protected String PARAM_NAME_SOURCE = "s";
	protected String PARAM_NAME_LENGTH = "l";
	protected String PARAM_NAME_VALUE = "√";
	private String errMsg = null;

	@Override
	protected boolean doValidate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		String param = getParameter(PARAM_NAME_SOURCE, null);
		String len = getParameter(PARAM_NAME_LENGTH, null);

		if (StringUtils.isNotBlank(param)) {
			for (String c : ValidateUtils.extractPositions(param)) {
				String val = row.getRowData().getCellContent(Integer.parseInt(c)-1);
				
				if (!StringUtils.equals(PARAM_NAME_VALUE, val)
						&& StringUtils.isNotBlank(value)) {
					errMsg = getErrorMessage("当填写{0}行<font color='red'>{1}</font>必须选择了{2}行."
						, String.valueOf(cell.getCell().getSerial()+1), cell.getAliasName(), c);
					return false;
				}
			}
		}
		
		if (StringUtils.isNotBlank(len)) {
			if (StringUtils.isBlank(value)) return true;//这里不对空字符串进行验证
			if (!StringUtils.equals(len, String.valueOf(value.trim().length()))) {
				errMsg = cell.getAliasName() + "长度为" + len + "位.";
				return false;
			}
		}
		return true;
	}

	@Override
	protected String getDefaultErrMsg() {
		return errMsg;
	}

	@Override
	protected boolean ignoreBlankValue() {
		return false;
	}
}
