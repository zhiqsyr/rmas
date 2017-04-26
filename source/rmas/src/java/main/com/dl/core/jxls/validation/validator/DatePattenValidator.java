package com.dl.core.jxls.validation.validator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dl.core.jxls.model.CellDataModel;
import com.dl.core.jxls.model.RowDataModel;
import com.dl.core.jxls.model.UploadDataModel;
import com.dl.core.jxls.validation.util.ValidateUtils;

/**
 * @author Lucas
 * @date 2014-3-20 上午9:47:35
 */
@Component("datePattenVal")
@Scope("prototype")
public class DatePattenValidator extends AbstractColumnValidator {

	@Override
	protected boolean doValidate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		return ValidateUtils.isDate(value);
	}

	@Override
	protected String getDefaultErrMsgKey() {
		return "date.patten";
	}

	@Override
	protected String getDefaultErrMsg() {
		return "日期格式不正确.";
	}
}
