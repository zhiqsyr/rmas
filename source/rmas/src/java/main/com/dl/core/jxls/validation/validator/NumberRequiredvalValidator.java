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
 * 主要验证填写数字后，是否有相关项未做选择，
 * 如：填写已按《登记条例》登记的专业合作社成员数 ，但未选择其中:是否已按《登记条例》登记
 * @author Lucas
 * @date 2014-4-1 上午11:01:40
 */
@Component("numberRequiredValValid")
@Scope("prototype")
public class NumberRequiredvalValidator extends AbstractColumnValidator {

	protected String PARAM_NAME_REQUIRED = "r";//多个必填项之间关系取决于rmark
	protected String PARAM_NAME_REQUIRED_MARK = "rmark";
	protected String PARAM_NAME_NON_REQUIRED = "n";//限定只有1项
	protected String PARAM_NAME_RN_MARK = "rnmark";
	protected String CHECK_VALUE = "√";
	protected String OR_VALUE = "or";
	protected String AND_VALUE = "and";
	private String errMsg = null;
	
	@Override
	protected boolean doValidate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		String required = getParameter(PARAM_NAME_REQUIRED, null);
		String non = getParameter(PARAM_NAME_NON_REQUIRED, null);
		String rmark = getParameter(PARAM_NAME_REQUIRED_MARK, null);
		String rnmark = getParameter(PARAM_NAME_RN_MARK, null);
		boolean requiredValue = false;
		boolean nonValue = true;

		if (StringUtils.isNotBlank(required)
				&& StringUtils.isBlank(non)) {
			requiredValue = validRequiredParam(row, cell, value, required, rmark);
		} else if (StringUtils.isNotBlank(non)) {
			String val = row.getRowData().getCellContent(Integer.parseInt(non)-1);
			
			if (StringUtils.equals(CHECK_VALUE, val)
					&& (StringUtils.isNotBlank(value)
						&& Integer.valueOf(value) > 0)) {
				nonValue = false;
			}
		}
		
		if (StringUtils.isBlank(rnmark)
				|| StringUtils.equals(rnmark, OR_VALUE)) {
			if (StringUtils.isBlank(non)) {
				return requiredValue;
			} else {
				if (requiredValue || nonValue) return true;
				errMsg = getErrorMessage("填写了行{0}后,必须选择行{1},或不选行{2}."
						, cell.getColumnConfig().getDataPosition()
						, required.replaceAll(",", "或")
						, non);
				return false;
			}
		} else if (StringUtils.equals(rnmark, AND_VALUE)) {
			if (requiredValue && nonValue) return true;
			return false;
		}
		
		return true;
	}
	
	private boolean validRequiredParam(RowDataModel row,
			CellDataModel cell, String value, String required, String rmark) {
		List<String> str = ValidateUtils.extractPositions(required);
		int _tempValue = 0; //用于记录错误条数
		
		if (str.size() == 1) {
			String val = row.getRowData().getCellContent(Integer.parseInt(str.get(0)) - 1);
			if (!StringUtils.equals(CHECK_VALUE, val)
					&& (StringUtils.isNotBlank(value)
						&& Integer.valueOf(value) > 0)) {
				errMsg = getErrorMessage("填写了行{0}后,必须选择行{1}."
						, cell.getColumnConfig().getDataPosition()
						, str.get(0));
				return false;
			}
		} else if (str.size() > 1) {
			for (String c : str) {
				String val = row.getRowData().getCellContent(Integer.parseInt(c)-1);
				
				if (StringUtils.equals(OR_VALUE, rmark)) {
					if (!StringUtils.equals(CHECK_VALUE, val)
							&& (StringUtils.isNotBlank(value)
								&& Integer.valueOf(value) > 0)) {
						_tempValue++;
					}
				} else if (StringUtils.equals(AND_VALUE, rmark)) {
					if (!StringUtils.equals(CHECK_VALUE, val)
							&& (StringUtils.isNotBlank(value)
								&& Integer.valueOf(value) > 0)) {
						errMsg = getErrorMessage("填写了行{0}后,必须选择行{1}."
								, cell.getColumnConfig().getDataPosition()
								, required);
						return false;
					}
				}
			}
			
			if (_tempValue > 0
					&& _tempValue == str.size()) {//用于OR的情况
				errMsg = getErrorMessage("填写了行{0}后,必须选择行{1}."
						, cell.getColumnConfig().getDataPosition()
						, required.replaceAll(",", "或"));
				return false;
			}
		}
		return true;
	}
	
	@Override
	protected String getDefaultErrMsg() {
		return errMsg;
	}

}
