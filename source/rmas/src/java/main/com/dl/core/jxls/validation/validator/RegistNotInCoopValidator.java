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
 * 特殊处理n_regist_member_count_23
 * @author Lucas
 * @date 2014-4-2 下午3:24:04
 */
@Component("registSpecialVal")
@Scope("prototype")
public class RegistNotInCoopValidator extends AbstractColumnValidator {

	protected String PARAM_NAME_REQUIRED = "r";
	protected String MARK_EQ = ">";
	protected String CHECK_VALUE = "√";
	protected String OR_VALUE = "or";
	protected String AND_VALUE = "and";
	private String errMsg = null;
	
	@Override
	protected boolean doValidate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		String required = getParameter(PARAM_NAME_REQUIRED, null);
		
		if (StringUtils.isNotBlank(required)) {
			int _tempValue = 0;
			List<String> str = ValidateUtils.extractPositions(required);
			for (String c : str) {
				if (c.indexOf(MARK_EQ) > 0) {
					String[] $r = c.split(MARK_EQ);
					String val_1 = row.getRowData().getCellContent(Integer.parseInt($r[0]) - 1);
					String val_2 = row.getRowData().getCellContent(Integer.parseInt($r[1]) - 1);
					
					if (StringUtils.equals(CHECK_VALUE, val_1)
							&& !StringUtils.equals(CHECK_VALUE, val_2)
							&& (StringUtils.isNotBlank(value)
									&& Integer.valueOf(value) > 0)) continue;
					else _tempValue++;
				} else {
					String val = row.getRowData().getCellContent(Integer.parseInt(c) - 1);
					
					if (!StringUtils.equals(CHECK_VALUE, val)
							&& (StringUtils.isNotBlank(value)
								&& Integer.valueOf(value) > 0)) _tempValue++;
				}
			}
			
			if (_tempValue > 0
					&& _tempValue == str.size()) {//拼接error
				errMsg = "填写了行" + cell.getColumnConfig().getDataPosition() + "后";
				for (String c : str) {
					if (c.indexOf(MARK_EQ) > 0) {
						String[] $r = c.split(MARK_EQ);
						errMsg += getErrorMessage(",选择行{0}但不应该选行{1}"
								, $r[0], $r[1]);
					} else {
						errMsg += getErrorMessage(",或选择行{0}."
								, c);
					}
				}
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
