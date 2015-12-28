package com.dl.core.jxls.validation.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dl.core.jxls.model.CellDataModel;
import com.dl.core.jxls.model.RowDataModel;
import com.dl.core.jxls.model.UploadDataModel;

/**
 * 非空校验器,null,空白字符串都会被认为是空的情况
 * 
 * @author dylan
 * @date 2012-8-21 下午2:28:54
 */
@Component("nonNullVal")
@Scope("prototype")
public class NonNullValidator extends AbstractColumnValidator {

	@Override
	public boolean doValidate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		return StringUtils.isNotBlank(value);
	}
	
	@Override
	protected String getDefaultErrMsgKey() {
		return "nonNull";
	}
	
	@Override
	protected String getDefaultErrMsg() {
		return "值不能为空!";
	}
	
	@Override
	protected boolean ignoreBlankValue() {
		return false;
	}

}
