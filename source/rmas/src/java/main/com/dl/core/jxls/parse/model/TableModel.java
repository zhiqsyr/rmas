package com.dl.core.jxls.parse.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 表格模型
 * 
 * @author dylan
 * @date 2013-5-6 上午9:12:56
 */
public class TableModel {
	private String tableId;// 表格ID,用以标识当前表格模型,需要用户赋值
	private String title;// 表格文本描述,用以描述当前表格模型,需要用户赋值
	private List<ColumnWidthDefine> columnWidthDefines = new ArrayList<ColumnWidthDefine>();

	private List<RowModel> rows = new ArrayList<RowModel>();

	public void addColumnWidthDefine(ColumnWidthDefine columnDefine) {
		columnWidthDefines.add(columnDefine);
	}

	public void addRow(RowModel tr) {
		this.rows.add(tr);
	}

	/**
	 * 获得指定序列的行模型,起始序号为0
	 * 
	 * @param rowSerial
	 * @return
	 */
	public RowModel getRow(int rowSerial) {
		for (RowModel row : rows) {
			if (row.getSerial() == rowSerial) {
				return row;
			}
		}
		return null;
	}

	/**
	 * 获得指定坐标的格子内容,行列的起始序号均为0
	 * 
	 * @param rowSerial
	 * @param colSerial
	 * @return
	 */
	public CellModel getCell(int rowSerial, int colSerial) {
		RowModel row = getRow(rowSerial);
		return row == null ? null : row.getCell(colSerial);
	}

	/**
	 * 获得指定坐标的格子内容,行列的起始序号均为0
	 * 
	 * @param rowSerial
	 * @param colSerial
	 * @return
	 */
	public String getCellContent(int rowSerial, int colSerial) {
		RowModel row = getRow(rowSerial);
		return row == null ? null : row.getCellContent(colSerial);
	}

	/**
	 * 获取列宽定义
	 * 
	 * @return
	 */
	public List<ColumnWidthDefine> getColumnWidthDefines() {
		Collections.sort(columnWidthDefines,
				new Comparator<ColumnWidthDefine>() {
					@Override
					public int compare(ColumnWidthDefine o1,
							ColumnWidthDefine o2) {
						if (o1.getCol() < o2.getCol()) {
							return -1;
						}
						return o1.getCol() > o2.getCol() ? 1 : 0;
					}

				});
		return columnWidthDefines;
	}

	/**
	 * 表格中的所有行
	 * 
	 * @return
	 */
	public List<RowModel> rows() {
		Collections.sort(rows, new Comparator<RowModel>() {
			@Override
			public int compare(RowModel o1, RowModel o2) {
				if (o1.getSerial() < o2.getSerial()) {
					return -1;
				}
				return o1.getSerial() > o2.getSerial() ? 1 : 0;
			}
		});
		return rows;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableName(String tableName) {
		this.tableId = tableName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
