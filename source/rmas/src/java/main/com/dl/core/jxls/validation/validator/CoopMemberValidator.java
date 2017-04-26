package com.dl.core.jxls.validation.validator;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dl.core.jxls.model.CellDataModel;
import com.dl.core.jxls.model.RowDataModel;
import com.dl.core.jxls.model.UploadDataModel;
import com.dl.core.jxls.validation.util.ValidateUtils;

/**
 * 只用于校验成员总数
 * @author Lucas
 * @date 2014-3-31 上午9:43:45
 */
@Component("coopMemberVal")
@Scope("prototype")
public class CoopMemberValidator extends AbstractColumnValidator {

	protected String PARAM_NAME_SOURCE = "s";
	private String errMsg;
	private int serial_11 = 31;
	private int serial_23 = 43;
	private int serial_28 = 48;
	
	@Override
	protected boolean doValidate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		String param = getParameter(PARAM_NAME_SOURCE, null);
		Map<Integer, Integer> requiredMap = new HashMap<Integer, Integer>();

		if (StringUtils.isNotBlank(param)) {
			for (String c : ValidateUtils.extractPositions(param)) {
				String content = row.getRowData().getCellContent(Integer.parseInt(c)-1);
				if (StringUtils.isBlank(content)) continue;
				Integer _val = Integer.valueOf(content);
				requiredMap.put(Integer.parseInt(c), _val);
			}
			
			if (requiredMap.size() == 1) {
				Integer _val_10 = StringUtils.isBlank(value) ? 0 : Integer.valueOf(value);
				for (Map.Entry<Integer,Integer> entry : requiredMap.entrySet()) {
					if (entry.getKey() == serial_11
							|| entry.getKey() == serial_23) {
						if (entry.getValue().equals(_val_10)
								&& _val_10 >= 5) return true;
						errMsg = "选行号31或43,则需行号30=行号31或者行号30=行号43,且行号30>=数值5.";
						return false;
					} else if (entry.getKey() == serial_28) {
						if (entry.getValue() > 0
								&& _val_10 == 0) return true;
						errMsg = "选行号48,则需行号48>数值0,且行号30=数值0.";
						return false;
					}
				}
			} else {
				errMsg = "在行号31、43、48中必选填一种.";
				return false;
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
