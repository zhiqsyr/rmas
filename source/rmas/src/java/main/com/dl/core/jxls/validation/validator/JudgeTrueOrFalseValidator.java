package com.dl.core.jxls.validation.validator;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dl.core.jxls.model.CellDataModel;
import com.dl.core.jxls.model.RowDataModel;
import com.dl.core.jxls.model.UploadDataModel;
import com.dl.core.jxls.validation.util.ValidateUtils;

/**
 * @author Lucas
 * @date 2014-4-3 下午3:36:51
 */
@Component("judgeVal")
@Scope("prototype")
public class JudgeTrueOrFalseValidator extends AbstractColumnValidator {

	protected String PARAM_NAME_COL = "c";//列或行号
	protected String YES_VALUE = "是";
	protected String CHECK_VALUE = "√";
	private String errMsg = null;
	
	@Override
	protected boolean doValidate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		String cols = getParameter(PARAM_NAME_COL, null);
		int _tempValue = 0;
		
		if (StringUtils.isNotBlank(cols)) {
			List<String> str = ValidateUtils.extractPositions(cols);
			
			for (String c : str) {
				String val = row.getRowData().getCellContent(Integer.parseInt(c) - 1);
				
				if (StringUtils.equals(CHECK_VALUE, val)) _tempValue++;
			}
		}
		
		if (!StringUtils.equals(YES_VALUE, value)
				&& _tempValue > 0) {
			errMsg = "只有在选择'是'的情况下,才可以勾选行" + cols + ".";
			return false;
		} else if (StringUtils.equals(YES_VALUE, value)
				&& _tempValue == 0) {
			errMsg = "选择'是'之后,请在行" + cols + "中选择一项或多项.";
			return false;
		}
		
		return true;
	}
	
	@Override
	protected boolean ignoreBlankValue() {
		return false;
	}
	
	@Override
	protected String getDefaultErrMsg() {
		return errMsg;
	}

}
