package com.dl.core.jxls.validation.validator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dl.core.jxls.model.CellDataModel;
import com.dl.core.jxls.model.RowDataModel;
import com.dl.core.jxls.model.UploadDataModel;

/**
 * 保证当前列的长度为指定值, 可接受参数l做为指定的长度
 * 
 * @author dylan
 * @date 2012-8-30 下午1:40:24
 */
@Scope("prototype")
@Component("lengthVal")
public class LengthValidator extends AbstractColumnValidator {
	private static final String PARAM_LENGTH = "l";
	private int length;

	@Override
	protected boolean doValidate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		String strLen = getParameter(PARAM_LENGTH);
		length = Integer.parseInt(strLen.trim());
		return length == value.length();
	}

	@Override
	protected String getDefaultErrMsg() {
		return "长度应该为{0}位";
	}

	@Override
	protected String getDefaultErrMsgKey() {
		return "lengthVal";
	}

	@Override
	protected Object[] getErrParams() {
		return new Object[] { length };
	}

}
