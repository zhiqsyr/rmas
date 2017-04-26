package com.dl.core.jxls.model;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.dl.core.jxls.entity.ColumnConfig;
import com.dl.core.jxls.parse.model.CellModel;
import com.dl.core.jxls.validation.ValidateResult;

/**
 * 单元格数据模型，可以将Excel中解析到的单元格数据与{@link ColumnConfig}关联起来。用于数据的校验和数据处理之后的结果存储.
 * 
 * @author dylan
 * @date 2013-1-22 下午2:13:35
 */
public class CellDataModel implements Comparable<CellDataModel>, Serializable {
	private static final long serialVersionUID = -1950415780812448398L;
	// 列配置信息
	private ColumnConfig columnConfig;
	// 单元格中的值的字符串类型表示
	private String strValue;
	// 数据校验的结果
	private ValidateResult validateResult = new ValidateResult();
	// 单元格名称
	private String cellName;
	// 列别名
	private String aliasName;
	// 列名
	private String columnName;
	// 对应的excel单元格数据
	private CellModel cell;
	// 从单元格中解析到的值
	private Object value;
	// 列配置中的顺序
	private int order;

	public CellDataModel() {

	}

	public CellDataModel(ColumnConfig columnConfig) {
		setColumnConfig(columnConfig);
	}

	public ColumnConfig getColumnConfig() {
		return columnConfig;
	}

	public void setColumnConfig(ColumnConfig columnConfig) {
		this.columnConfig = columnConfig;
		this.aliasName = StringUtils.isBlank(columnConfig.getAliasName()) ? columnConfig
				.getColumnName() : columnConfig.getAliasName();
		this.order = columnConfig.getOrdinal() == null ? Integer.MAX_VALUE
				: columnConfig.getOrdinal();
		this.columnName = columnConfig.getColumnName();
	}

	public void setCell(CellModel cell) {
		this.cell = cell;
		if (cell != null) {
			this.strValue = StringUtils.isBlank(cell.getContent()) ? null
					: cell.getContent().trim();
		}
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getStrValue() {
		return strValue;
	}

	public void setStrValue(String value) {
		this.strValue = value;
	}

	public ValidateResult getValidateResult() {
		return validateResult;
	}

	public void setValidateResult(ValidateResult validateResult) {
		this.validateResult = validateResult;
	}

	public String getCellName() {
		return cellName;
	}

	public void setCellName(String cellName) {
		this.cellName = cellName;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public CellModel getCell() {
		return cell;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	@Override
	public int compareTo(CellDataModel other) {
		if (this.order < other.order) {
			return -1;
		}
		if (this.order > other.order) {
			return 1;
		}
		return 0;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(columnName);
		if (this.validateResult.isSuccess()) {
			sb.append(":").append(value);
		} else {
			sb.append(":错误,").append(validateResult.getMsg());
		}
		return sb.toString();
	}

}
