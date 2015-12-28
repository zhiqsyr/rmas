package com.dl.core.jxls.validation.validator;

import com.dl.core.jxls.entity.ColumnConfig;
import com.dl.core.jxls.model.CellDataModel;
import com.dl.core.jxls.model.RowDataModel;
import com.dl.core.jxls.model.UploadDataModel;
import com.dl.core.jxls.util.StringHelper;
import com.dl.core.jxls.validation.util.ValidateUtils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
/**
 * 
 * @author JIANGHUIJI
 * 2013-7-3 下午4:01:28
 */
@Component("noNullOneVal")
@Scope("prototype")
public class ParentSelectOrNotValidator extends AbstractColumnValidator {

	protected String PARAM_NAME_SOURCE = "s";
	// TODO 是否将0当作null处理，对于1,0选择的输入框，把0当作null来处理
	private boolean zeroAsNull = true;
	
	@Override
	protected boolean doValidate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		int num=0;
		
		String param = getParameter(PARAM_NAME_SOURCE, null);
		if (StringHelper.isNotEmpty(param)) {
			for (String c : ValidateUtils.extractPositions(param)) {
				Object val = findColumnValue(c, row);
				if (val != null) {
					num = num + 1;
				}
			}
		}
		
		if(num==1){
			return true;
		}else{
			return false;
		}
	}
	
	
	protected Object findColumnValue(String c, RowDataModel row) {
		Object val = null;
		for (ColumnConfig columnConfig : reportConfig.getColumnConfigs()) {
			if (c.equalsIgnoreCase(columnConfig.getDataPosition())) {
				val = row.getValue(columnConfig.getColumnName());
				break;
			}
		}
		if (val != null && ((val instanceof String
				&& StringHelper.isEmpty((String) val)) || (zeroAsNull && "0".equals(val.toString().trim())))) {
			// 确保当前格式不是空白的字符串
			return null;
		}
		return val;
	}
	
	
	@Override
	protected String getDefaultErrMsg() {
		String param = getParameter(PARAM_NAME_SOURCE, null);
		StringBuffer sb = new StringBuffer();
		if (StringHelper.isNotEmpty(param)) {
			for (String c : ValidateUtils.extractPositions(param)) {
				sb.append(c).append(",");
			}
		}
		return sb+"行必选且只能选一个";
	}
	
	@Override
	protected String getDefaultErrMsgKey() {
		return "nonNullOnlyOne";
	}

	@Override
	protected boolean ignoreBlankValue() {
		return false;
	}
	


}
