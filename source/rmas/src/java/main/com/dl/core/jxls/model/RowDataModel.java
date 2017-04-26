package com.dl.core.jxls.model;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

import com.dl.core.jxls.entity.ReportConfig;
import com.dl.core.jxls.parse.model.RowModel;
import com.dl.core.jxls.validation.ValidateResult;

/**
 * 数据上报的行模型，通过与{@link ReportConfig}和解析到的excel数据进行关联。存放数据校验结果
 * 
 * @author dylan
 * @date 2013-1-22 下午2:10:28
 */
public class RowDataModel implements Comparable<RowDataModel>, Serializable {
	private static final long serialVersionUID = 5265268210083168149L;

	private RowModel rowData;
	/**
	 * 当前行号
	 */
	private int row;
	/**
	 * 数据校验结果
	 */
	private ValidateResult validateResult = new ValidateResult();

	/**
	 * 当前行的单元格数据集合
	 */
	private Set<CellDataModel> cellDatas = new TreeSet<CellDataModel>();

	public void addCell(CellDataModel cell) {
		cellDatas.add(cell);
	}

	public RowModel getRowData() {
		return rowData;
	}

	public void setRowData(RowModel rowData) {
		this.rowData = rowData;
		this.row = rowData.getSerial();
	}

	public int getRow() {
		return row;
	}

	public ValidateResult getValidateResult() {
		return validateResult;
	}

	public void setValidateResult(ValidateResult validateResult) {
		this.validateResult = validateResult;
	}

	public Set<CellDataModel> getCellDatas() {
		return cellDatas;
	}

	public void setCellDatas(Set<CellDataModel> cellDatas) {
		this.cellDatas = cellDatas;
	}

	public CellDataModel getCell(String columnName) {
		if (cellDatas == null) {
			return null;
		}
		for (CellDataModel cell : cellDatas) {
			if (columnName.equals(cell.getColumnName())) {
				return cell;
			}
		}
		return null;
	}

	public String getStrValue(String columnName) {
		CellDataModel cell = getCell(columnName);
		return cell == null ? null : cell.getStrValue();
	}

	public Object getValue(String columnName) {
		CellDataModel cell = getCell(columnName);
		return cell == null ? null : cell.getValue();
	}

	@Override
	public int compareTo(RowDataModel other) {
		if (this.row < other.row) {
			return -1;
		}
		if (this.row > other.row) {
			return 1;
		}
		return 0;
	}

}
