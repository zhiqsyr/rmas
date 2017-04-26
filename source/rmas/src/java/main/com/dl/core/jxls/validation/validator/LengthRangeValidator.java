package com.dl.core.jxls.validation.validator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dl.core.jxls.model.CellDataModel;
import com.dl.core.jxls.model.RowDataModel;
import com.dl.core.jxls.model.UploadDataModel;

/**
 * 长度范围校验
 * 
 * @author dylan
 * @date 2012-8-30 下午2:57:48
 */
@Component("lengthRangeVal")
@Scope("prototype")
public class LengthRangeValidator extends AbstractColumnValidator {
	private static final String PARAM_MIN_LENGTH = "mi";
	private static final String PARAM_MAX_LENGTH = "mx";
	private String errMsg;
	private String msgKey;
	private Object[] params;

	@Override
	protected boolean doValidate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		Integer minSize = getMinSize();
		Integer maxSize = getMaxSize();
		int length = value.length();
		if (minSize == null) {
			if (maxSize == null || length <= maxSize) {
				return true;
			} else {
				errMsg = "长度不能大于{0}";
				msgKey = "lengthRange.max";
				params = new Object[] { maxSize };
				return false;
			}
		} else if (maxSize == null) {
			if (length >= minSize) {
				return true;
			} else {
				errMsg = "长度不能小于{0}";
				msgKey = "lengthRange.min";
				params = new Object[] { minSize };
				return false;
			}
		} else if (length >= minSize && length <= maxSize) {
			return true;
		} else {
			errMsg = "长度应该介于{0}和{1}之间";
			msgKey = "lengthRange.range";
			params = new Object[] { minSize, maxSize };
			return false;
		}
	}

	private Integer getMinSize() {
		String strLength = getParameter(PARAM_MIN_LENGTH);
		if (strLength == null) {
			return null;
		}
		try {
			return Integer.parseInt(strLength);
		} catch (NumberFormatException e) {
			throw new RuntimeException("长度范围最小值属性配置出错，请检查!");
		}
	}

	private Integer getMaxSize() {
		String strLength = getParameter(PARAM_MAX_LENGTH);
		if (strLength == null) {
			return null;
		}
		try {
			return Integer.parseInt(strLength);
		} catch (NumberFormatException e) {
			throw new RuntimeException("长度范围最大值属性配置出错，请检查!");
		}
	}

	@Override
	protected String getDefaultErrMsg() {
		return errMsg;
	}

	@Override
	protected String getDefaultErrMsgKey() {
		return msgKey;
	}

	@Override
	protected Object[] getErrParams() {
		return params;
	}

}
