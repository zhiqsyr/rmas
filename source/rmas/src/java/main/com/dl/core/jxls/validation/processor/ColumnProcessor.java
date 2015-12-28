package com.dl.core.jxls.validation.processor;

import com.dl.core.jxls.model.CellDataModel;
import com.dl.core.jxls.model.RowDataModel;
import com.dl.core.jxls.model.UploadDataModel;

/**
 * 数据列处理器
 * @author dylan
 * @date 2012-9-28 下午1:32:43
 */
public interface ColumnProcessor {
	/**
	 * 对单元格数据进行处理
	 * @param model 
	 * @param row
	 * @param cell 当前单元格的模型
	 * @param value 单元格目前的值
	 */
	public void process(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value);
}
