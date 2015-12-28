package com.dl.core.jxls.validation.processor;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dl.core.jxls.entity.ColumnConfig;
import com.dl.core.jxls.model.CellDataModel;
import com.dl.core.jxls.model.RowDataModel;
import com.dl.core.jxls.model.UploadDataModel;
import com.dl.core.jxls.util.StringHelper;
import com.dl.core.jxls.validation.util.ValidateUtils;

/**
 * 从多个列中选择不为空的一个值作为当前列的值,
 * 
 * @author dylan
 * @date 2013-5-15 下午2:00:50
 */
@Component("selectOnePro")
@Scope("prototype")
public class SelectOneProcessor extends AbstractColumnProcessor {

	protected String PARAM_NAME_SOURCE = "s";
	// TODO 是否将0当作null处理，对于1,0选择的输入框，把0当作null来处理
	private boolean zeroAsNull = true;

	@Override
	protected void doProcess(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		String param = getParameter(PARAM_NAME_SOURCE, null);
		if (StringHelper.isNotEmpty(param)) {
			for (String c : ValidateUtils.extractPositions(param)) {
				Object val = findColumnValue(c, row);
				if (val != null) {
					cell.setValue(val);
					break;
				}
			}
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

}
