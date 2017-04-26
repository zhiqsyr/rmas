package com.dl.core.jxls.validation.validator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dl.core.jxls.model.CellDataModel;
import com.dl.core.jxls.model.RowDataModel;
import com.dl.core.jxls.model.UploadDataModel;
import com.dl.core.jxls.validation.util.ValidateUtils;

/**
 * @author Lucas
 * @date 2014-1-7 下午4:27:35
 */
@Component("decimalVal")
@Scope("prototype")
public class DecimalValidator extends AbstractColumnValidator {

	@Override
	protected boolean doValidate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		return ValidateUtils.isNumeric(value);
	}

	@Override
	protected String getDefaultErrMsgKey() {
		return "decimal";
	}

	@Override
	protected String getDefaultErrMsg() {
		return "值应该由数字组成!";
	}
}
