package com.dl.core.jxls.validation.validator;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dl.core.jxls.model.CellDataModel;
import com.dl.core.jxls.model.RowDataModel;
import com.dl.core.jxls.model.UploadDataModel;
import com.dl.core.jxls.validation.util.ValidateUtils;

/**
 * 数据范围校验
 * @author Lucas
 * @date 2014-7-30 下午4:00:09
 */
@Component("numericRangeVal")
@Scope("prototype")
public class NumericRangeValidator extends AbstractColumnValidator {

	private static final String PARAM_MIN = "mi";
	private static final String PARAM_MAX = "mx";
	private String errMsg;
	private Object[] params;
	
	@Override
	protected boolean doValidate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		BigDecimal currentValue = getNumeric(value);
		if (currentValue == null) return true;
		
		BigDecimal minValue = getNumeric(getParameter(PARAM_MIN, null));
		BigDecimal maxValue = getNumeric(getParameter(PARAM_MAX, null));
		if (minValue == null && maxValue == null) return true;
		
		if (minValue != null && maxValue == null) {
			if (currentValue.compareTo(minValue) >= 0) return true;
			else {
				errMsg = "值不能小于{0}.";
				params = new Object[] { minValue.doubleValue() };
				return false;
			}
		} else if (maxValue != null && minValue == null) {
			if (currentValue.compareTo(maxValue) <= 0) return true;
			else {
				errMsg = "值不能大于{0}.";
				params = new Object[] { maxValue.doubleValue() };
				return false;
			}
		} else if (minValue != null && maxValue != null){
			if (currentValue.compareTo(minValue) >= 0
					&& currentValue.compareTo(maxValue) <= 0) return true;
			else {
				errMsg = "值应该介于{0}和{1}之间";
				params = new Object[] { minValue.doubleValue(), maxValue.doubleValue()};
				return false;
			}
		}
		
		return true;
	}
	
	private BigDecimal getNumeric(String val) {
		if (StringUtils.isNotBlank(val)
				&& ValidateUtils.isNumeric(val)) return new BigDecimal(val);
		else return null;
	}
	
	@Override
	protected String getDefaultErrMsg() {
		return errMsg;
	}

	@Override
	protected Object[] getErrParams() {
		return params;
	}
}
