package com.dl.core.jxls.validation.validator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dl.core.jxls.model.CellDataModel;
import com.dl.core.jxls.model.RowDataModel;
import com.dl.core.jxls.model.UploadDataModel;
import com.dl.core.jxls.validation.util.ValidateUtils;

/**
 * @author Lucas
 * @date 2014-3-20 上午10:01:38
 */
@Component("ymPattenVal")
@Scope("prototype")
public class FarmDateYMvalidator extends AbstractColumnValidator {

	@Override
	protected boolean doValidate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		return ValidateUtils.isYMDate(value);
	}

	@Override
	protected String getDefaultErrMsgKey() {
		return "birth.patten";
	}

	@Override
	protected String getDefaultErrMsg() {
		return "年月格式不正确.";
	}
}
