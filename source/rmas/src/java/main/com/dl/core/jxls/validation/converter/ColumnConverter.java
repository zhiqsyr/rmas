package com.dl.core.jxls.validation.converter;

import com.dl.core.jxls.entity.ColumnConfig;

/**
 * 列校验转换器，将字符串类型的数据转换为需要的数据类型
 * 
 * @author dylan
 * @date 2012-10-9 下午2:10:42
 */
public interface ColumnConverter {
	/**
	 * 将单元格数据转换为需要的数据类型
	 * 
	 * @param model
	 * @param row
	 * @param cell
	 *            当前单元格的模型
	 * @param columnConfig
	 *            当前列的列配置
	 */
	public Object convert(ColumnConfig columnConfig, String strValue)
			throws DataConvertException;
}
