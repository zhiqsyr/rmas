package com.dl.core.jxls.validation.validator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dl.core.jxls.model.CellDataModel;
import com.dl.core.jxls.model.RowDataModel;
import com.dl.core.jxls.model.UploadDataModel;
import com.dl.core.jxls.validation.util.ValidateUtils;

/**
 * 正整数校验
 * 
 * @author dylan
 * @date 2012-8-30 下午2:49:49
 */
@Scope("prototype")
@Component("posIntegerVal")
public class PositiveIntegerValidator extends AbstractColumnValidator {

	@Override
	protected boolean doValidate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		return ValidateUtils.isPositiveInteger(value);
	}

	@Override
	protected String getDefaultErrMsg() {
		return "值应该为正整数";
	}

	@Override
	protected String getDefaultErrMsgKey() {
		return "positiveInteger";
	}

}
