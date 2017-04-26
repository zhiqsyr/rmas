package com.dl.core.jxls.validation.validator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dl.core.jxls.model.CellDataModel;
import com.dl.core.jxls.model.RowDataModel;
import com.dl.core.jxls.model.UploadDataModel;
import com.dl.core.jxls.validation.util.ValidateUtils;
/**
 * 自然数校验
 * @author dylan
 * @date 2013-6-8 下午2:25:41
 */
@Scope("prototype")
@Component("nonNeIntVal")
public class NonNeIntegerValidator extends AbstractColumnValidator {

	@Override
	protected boolean doValidate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		return ValidateUtils.isNonNeInteger(value);
	}
	
	@Override
	protected String getDefaultErrMsg() {
		return "值应该为自然数";
	}
	
	@Override
	protected String getDefaultErrMsgKey() {
		return "nonNeInteger";
	}

}
