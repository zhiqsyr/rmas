package com.dl.core.jxls.validation.processor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dl.core.jxls.common.model.ParameterSet;
import com.dl.core.jxls.common.model.Parameterizable;
import com.dl.core.jxls.entity.ColumnConfig;
import com.dl.core.jxls.entity.ReportConfig;
import com.dl.core.jxls.model.CellDataModel;
import com.dl.core.jxls.model.RowDataModel;
import com.dl.core.jxls.model.UploadDataModel;

/**
 * 数据列的处理器
 * @author dylan
 * @date 2012-9-28 下午1:33:57
 */
public abstract class AbstractColumnProcessor implements ColumnProcessor, Parameterizable {
	protected final Log logger = LogFactory.getLog(getClass());
	/**
	 * 配置的参数信息
	 */
	protected ParameterSet parameters;
	/**
	 * 当前列的列配置信息
	 */
	protected ColumnConfig columnConfig;
	/**
	 * 当前补贴项目的配置信息
	 */
	protected ReportConfig reportConfig;
	@Override
	public ParameterSet parameters() {
		return parameters;
	}

	@Override
	public void setParameters(ParameterSet parameters) {
		this.parameters = parameters;
	}
	
	/**
	 * 获取参数值,如果结果为null,使用默认结果
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getParameter(String key, String defaultValue) {
		String value = null;
		if (parameters == null
				|| (value = parameters.getParameter(key)) == null) {
			value = defaultValue;
		}
		return value;
	}

	@Override
	public void process(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		this.reportConfig = model.getReportConfig();
		this.columnConfig = cell.getColumnConfig();
		doProcess(model, row, cell, value);
	}
	
	protected abstract void doProcess(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value);

}
