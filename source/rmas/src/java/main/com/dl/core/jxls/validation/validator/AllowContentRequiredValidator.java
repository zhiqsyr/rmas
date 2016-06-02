package com.dl.core.jxls.validation.validator;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dl.core.jxls.model.CellDataModel;
import com.dl.core.jxls.model.RowDataModel;
import com.dl.core.jxls.model.UploadDataModel;
import com.dl.core.jxls.validation.util.ValidateUtils;

/**
 * 当选择项后，相关项目必须填写
 * 
 * @author Lucas
 * @date 2014-4-2 下午1:25:59
 */
@Component("allowConReqVal")
@Scope("prototype")
public class AllowContentRequiredValidator extends AbstractColumnValidator {

	protected String PARAM_NAME_REQUIRED = "r";// 多个值之间关系取决于参数m
	protected String PARAM_NAME_MARK = "m";
	protected String CHECK_VALUE = "√";
	protected String BLANK_VALUE = "　";
	protected String ZERO_VALUE = "0";
	protected String OR_VALUE = "or";
	protected String AND_VALUE = "and";
	private Object param_1 = null;
	private Object param_2 = null;

	@Override
	protected boolean doValidate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		String required = getParameter(PARAM_NAME_REQUIRED, null);
		String mark = getParameter(PARAM_NAME_MARK, null);
		
		int _boolTemp = 0;
		
		if (StringUtils.isNotBlank(required)
				&& StringUtils.isNotBlank(value)){
			List<String> str = ValidateUtils.extractPositions(required);
			
			if(!StringUtils.equals(BLANK_VALUE, value)
					&& !ValidateUtils.isNumeric(value)) {
				for (String c : str) {
					String val = row.getRowData().getCellContent(Integer.parseInt(c) - 1);
					if (StringUtils.isNotBlank(val)
							&& !StringUtils.equals(BLANK_VALUE, val)) continue;
					else _boolTemp++;
				}
			} else if (ValidateUtils.isNumeric(value)) {
				for (String c : str) {
					String val = row.getRowData().getCellContent(Integer.parseInt(c) - 1);
					if (StringUtils.isNotBlank(val)
							&& !StringUtils.equals(BLANK_VALUE, val)) continue;
					else _boolTemp++;
				}
			}
			
			if (StringUtils.isBlank(mark)
					|| StringUtils.equals(mark, OR_VALUE)) {
				if (_boolTemp == str.size()) {
					param_1 = cell.getColumnConfig().getDataPosition();
					param_2 = required.replaceAll(",", "或");
					return false;
				}
			} else if (StringUtils.equals(mark, AND_VALUE)
					&& _boolTemp > 0) {
					param_1 = cell.getColumnConfig().getDataPosition();
					param_2 = required;
					return false;
			}
		}
		return true;
	}

	@Override
	protected boolean ignoreBlankValue() {
		return false;
	}

	@Override
	protected String getDefaultErrMsg() {
		return "勾选或填写过行{0}后,必须勾选或填写行{1}.";
	}

	@Override
	protected Object[] getErrParams() {
		return new Object[] { param_1, param_2 };
	}

}
