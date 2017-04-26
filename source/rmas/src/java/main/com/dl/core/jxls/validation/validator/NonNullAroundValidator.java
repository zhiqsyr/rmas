package com.dl.core.jxls.validation.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dl.core.jxls.model.CellDataModel;
import com.dl.core.jxls.model.RowDataModel;
import com.dl.core.jxls.model.UploadDataModel;
import com.dl.core.jxls.validation.util.ValidateUtils;

/**
 * 单选范围必选验证器,负责人身份、成员分布范围等
 * p.s. fuck validator
 * @author Lucas
 * @date 2014-3-21 上午10:53:11
 */
@Component("nonNullAroundVal")
@Scope("prototype")
public class NonNullAroundValidator extends AbstractColumnValidator {

	protected String PARAM_NAME_SOURCE = "s";
	protected String PARAM_NAME_RESOURCE = "n";
	private String rowName;
	
	@Override
	protected boolean doValidate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		int num = 0;
		String param = getParameter(PARAM_NAME_SOURCE, null);
		if(StringUtils.isNotBlank(param)){
			for (String c : ValidateUtils.extractPositions(param)) {
				String val = row.getRowData().getCellContent(Integer.parseInt(c)-1);
				if (StringUtils.isBlank(val)
						|| StringUtils.equals("　", val)) {
					num = num + 1;
				} else return true;
			}
		}
		if (num > 0) {
			rowName = getParameter(PARAM_NAME_RESOURCE);
			return false;
		}
		return true;
	}
	
	@Override
	protected String getDefaultErrMsg() {
		return "<font color='red'>{0}</font>必选一项.";
	}
	
	@Override
	protected String getDefaultErrMsgKey() {
		return "non.null.around";
	}

	@Override
	protected Object[] getErrParams() {
		return new Object[] { rowName };
	}

	@Override
	protected boolean ignoreBlankValue() {
		return false;
	}
	
}
