package com.dl.core.jxls.validation.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dl.core.jxls.model.CellDataModel;
import com.dl.core.jxls.model.RowDataModel;
import com.dl.core.jxls.model.UploadDataModel;

/**
 * 只用于验证合作社产业结构是否为空,要求:所属行业与服务内容至少选择一项
 * bullshit!!
 * @author Lucas
 * @date 2014-3-21 下午2:19:10
 */
@Component("nonNullStructureVal")
@Scope("prototype")
public class NonNullForStructureValidator extends AbstractColumnValidator {

	private String[] column = {"busi_type_1st", "service_type"}; //所属行业、服务内容
	
	@Override
	protected boolean doValidate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		for (String _col : column) {
			Object _val = row.getCell(_col).getValue();
			if (_val != null &&
					StringUtils.isNotBlank(_val.toString())) return true;
		}
		return false;
	}

	@Override
	protected String getDefaultErrMsg() {
		return "产业结构（一）（二）中至少选择一项.";
	}
	
	@Override
	protected String getDefaultErrMsgKey() {
		return "non.null.structure";
	}

	@Override
	protected boolean ignoreBlankValue() {
		return false;
	}
}
