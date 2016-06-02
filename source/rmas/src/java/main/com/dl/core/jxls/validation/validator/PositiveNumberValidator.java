package com.dl.core.jxls.validation.validator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dl.core.jxls.model.CellDataModel;
import com.dl.core.jxls.model.RowDataModel;
import com.dl.core.jxls.model.UploadDataModel;
import com.dl.core.jxls.validation.util.ValidateUtils;
/**
 * 
 * @author dylan
 * @date 2012-8-30 下午2:34:39
 */
@Scope("prototype")
@Component("posNumberVal")
public class PositiveNumberValidator extends AbstractColumnValidator {

	@Override
	protected boolean doValidate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		return ValidateUtils.isNumberNoZero(value);
	}
	
	@Override
	protected String getDefaultErrMsg() {
		return "值应该为正数";
	}
	
	@Override
	protected String getDefaultErrMsgKey() {
		return "postiveNumber";
	}

}
