package com.dl.core.jxls.validation.processor;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dl.core.jxls.model.CellDataModel;
import com.dl.core.jxls.model.RowDataModel;
import com.dl.core.jxls.model.UploadDataModel;
import com.dl.core.jxls.util.StringHelper;
import com.dl.core.jxls.validation.util.ValidateUtils;

/**
 * 在SelectOneProcessor的基础上对数据的长度进行判断，只有指定长度的数值才被接受
 * @author dylan
 * @date 2013-5-22 下午2:40:33
 */
@Component("lenSelectOnePro")
@Scope("prototype")
public class LengthSelectOneProcessor extends SelectOneProcessor {

	protected String PARAM_NAME_LEN="l";
	
	@Override
	protected void doProcess(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		String param = getParameter(PARAM_NAME_SOURCE, null);
		int len = Integer.parseInt(getParameter(PARAM_NAME_LEN, "0"));
		if (StringHelper.isNotEmpty(param)) {
			for (String c : ValidateUtils.extractPositions(param)) {
				Object val = findColumnValue(c, row);
				if (val != null && len == val.toString().trim().length()) {
					cell.setValue(val);
					break;
				}
			}
		}
	}
}
