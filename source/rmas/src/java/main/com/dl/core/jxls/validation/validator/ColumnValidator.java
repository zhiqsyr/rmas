package com.dl.core.jxls.validation.validator;

import com.dl.core.jxls.entity.ColumnConfig;
import com.dl.core.jxls.model.CellDataModel;
import com.dl.core.jxls.model.RowDataModel;
import com.dl.core.jxls.model.UploadDataModel;
import com.dl.core.jxls.validation.ValidateResult;

/**
 * 数据列级别的数据校验
 * 
 * @author dylan
 * @date 2012-8-13 下午3:49:32
 */
public interface ColumnValidator {
	/**
	 * 对单元格数据进行校验
	 * 
	 * @param row
	 * @param data
	 * @return
	 */
	public ValidateResult validate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value);

	/**
	 * 当前列的配置信息
	 * 
	 * @return
	 */
	public ColumnConfig getColumnConfig();

}
