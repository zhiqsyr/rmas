package com.dl.core.jxls.model;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

import com.dl.core.jxls.common.model.ParameterSet;
import com.dl.core.jxls.entity.ReportConfig;
import com.dl.core.jxls.parse.model.TableModel;
import com.dl.core.jxls.validation.ValidateResult;

/**
 * Excel数据上报的模型类，它将解析到的Excel数据与{@link ReportConfig}关联起来,并可以用于在数据校验时存储数据的结果
 * 
 * @author dylan
 * @date 2013-1-22 下午2:06:52
 */
public class UploadDataModel implements Serializable, ReportConfigSupport {
	private static final long serialVersionUID = 6372567937312069663L;
	private TableModel sheet;

	private ReportConfig reportConfig;
	/**
	 * 数据行模型集合
	 */
	private Set<RowDataModel> rowDatas = new TreeSet<RowDataModel>();
	/**
	 * 数据校验的校验结果
	 */
	private ValidateResult validateResult;
	/**
	 * 是否全局性的错误，如数据文件不正确或文件找不到等较为严重的错误
	 */
	private boolean isGlobalError = false;

	/**
	 * 
	 */
	private ParameterSet properties;

	public void addRow(RowDataModel row) {
		rowDatas.add(row);
	}

	public TableModel getSheet() {
		return sheet;
	}

	public void setSheet(TableModel sheet) {
		this.sheet = sheet;
	}

	public ReportConfig getReportConfig() {
		return reportConfig;
	}

	public void setReportConfig(ReportConfig reportConfig) {
		this.reportConfig = reportConfig;
	}

	public Set<RowDataModel> getRowDatas() {
		return rowDatas;
	}

	public void setRowDatas(Set<RowDataModel> rowDatas) {
		this.rowDatas = rowDatas;
	}

	public ValidateResult getValidateResult() {
		return validateResult;
	}

	public void setValidateResult(ValidateResult validateResult) {
		this.validateResult = validateResult;
	}

	public boolean isGlobalError() {
		return isGlobalError;
	}

	public void setGlobalError(boolean isGlobalError) {
		this.isGlobalError = isGlobalError;
	}

	/**
	 * 设置参数信息
	 * 
	 * @param properties
	 */
	public void setProperties(ParameterSet properties) {
		if (this.properties == null) {
			this.properties = new ParameterSet();
		}
		this.properties.addParameters(properties);
	}

	/**
	 * 设置参数信息
	 * 
	 * @param name
	 * @param value
	 */
	public void setProperty(String name, String value) {
		if (this.properties == null) {
			this.properties = new ParameterSet();
		}
		this.properties.setParameter(name, value);
	}

	/**
	 * 获取参数信息
	 */
	public ParameterSet properties() {
		if (this.properties == null) {
			this.properties = new ParameterSet();
		}
		return this.properties;
	}
}
