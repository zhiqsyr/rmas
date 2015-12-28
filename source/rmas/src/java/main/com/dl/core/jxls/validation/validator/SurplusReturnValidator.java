package com.dl.core.jxls.validation.validator;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dl.core.jxls.model.CellDataModel;
import com.dl.core.jxls.model.RowDataModel;
import com.dl.core.jxls.model.UploadDataModel;
import com.dl.core.jxls.validation.util.ValidateUtils;

/**
 * 只有当盈余返还总额大于0才可以选择'是'
 * @author Lucas
 * @date 2014-4-4 上午9:30:26
 */
@Component("surplusVal")
@Scope("prototype")
public class SurplusReturnValidator extends AbstractColumnValidator {

	protected String PARAM_NAME_COL = "c";//最多一列或行
	protected String YES_VALUE = "是";
	private String errMsg = null;
	
	@Override
	protected boolean doValidate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		String col = getParameter(PARAM_NAME_COL, null);
		
		if (StringUtils.isNotBlank(col)) {
			String val = row.getRowData().getCellContent(Integer.parseInt(col) - 1);
			
			if (StringUtils.equals(YES_VALUE, value)) {
				if (ValidateUtils.isNumeric(val)
						&& BigDecimal.ZERO.compareTo(new BigDecimal(val)) >= 0) {
					errMsg = "只有当行" + col + "大于0此项才可选'是'.";
					return false;
				} else if (!ValidateUtils.isNumeric(val)){
					errMsg = "行" + col + "请填写数字.";
					return false;
				}
			} 
		}
		
		return true;
	}

	@Override
	protected String getDefaultErrMsg() {
		return errMsg;
	}
	
	@Override
	protected boolean ignoreBlankValue() {
		return false;
	}
}
