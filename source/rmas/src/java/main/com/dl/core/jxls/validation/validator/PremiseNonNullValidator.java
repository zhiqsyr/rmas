package com.dl.core.jxls.validation.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dl.core.jxls.model.CellDataModel;
import com.dl.core.jxls.model.RowDataModel;
import com.dl.core.jxls.model.UploadDataModel;

/**
 * 对其前置关联列进行校验，保证当前列不为空时，前置列也不会为空
 * 
 * @author dylan
 * @date 2012-8-30 上午11:01:11
 */
@Scope("prototype")
@Component("premiseNonNullVal")
public class PremiseNonNullValidator extends AbstractColumnValidator {
	private static final String PARAM_PREMISE = "p";
	private String columnAlias;
	private String preColumnAlias;

	@Override
	protected boolean doValidate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		String pColumn = getParameter(PARAM_PREMISE);
		if (StringUtils.isNotBlank(value) && StringUtils.isNotBlank(pColumn)) {
			String premiseData = row.getStrValue(pColumn.trim());
			// 当前列不为空时，保证其前置列也不为空
			if (StringUtils.isBlank(premiseData)) {
				columnAlias = getColumnAlias();
				preColumnAlias = getColumnAlias(pColumn.trim());
				return false;
			}
		}
		return true;
	}

	@Override
	protected String getDefaultErrMsg() {
		return "列\"{0}\"不为空时，\"{1}\"也不能为空";
	}

	@Override
	protected String getDefaultErrMsgKey() {
		return "premiseNonNull";
	}

	@Override
	protected Object[] getErrParams() {
		return new String[] { columnAlias, preColumnAlias };
	}

}
