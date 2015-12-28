package com.dl.core.jxls.validation.validator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dl.core.jxls.model.CellDataModel;
import com.dl.core.jxls.model.RowDataModel;
import com.dl.core.jxls.model.UploadDataModel;
import com.dl.core.jxls.validation.util.ValidateUtils;

@Scope("prototype")
@Component("numberSequenceVal")
public class NumberSequenceValidator extends AbstractColumnValidator {

	@Override
	protected boolean doValidate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		return ValidateUtils.isNumberSequence(value);
	}

	@Override
	protected String getDefaultErrMsgKey() {
		return "numberSequence";
	}

	@Override
	protected String getDefaultErrMsg() {
		return "值应该由数字组成.";
	}

}
