package com.dl.core.jxls.validation.validator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dl.core.jxls.model.CellDataModel;
import com.dl.core.jxls.model.RowDataModel;
import com.dl.core.jxls.model.UploadDataModel;
import com.dl.core.jxls.validation.util.ValidateUtils;

/**
 * 非负数字校验
 * 
 * @author dylan
 * @date 2012-8-21 下午2:38:40
 */
@Component("nonNeNumericVal")
@Scope("prototype")
public class NonNeNumericValidator extends AbstractColumnValidator {

	@Override
	protected boolean doValidate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		return ValidateUtils.isNonNeNumeric(value);
	}

	@Override
	protected String getDefaultErrMsgKey() {
		return "nonNeNumeric";
	}

	@Override
	protected String getDefaultErrMsg() {
		return "值应该为非负数字!";
	}

}
