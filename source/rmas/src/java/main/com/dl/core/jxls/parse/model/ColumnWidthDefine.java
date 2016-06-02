package com.dl.core.jxls.parse.model;

/**
 * 表格的列宽定义
 * 
 * @author dylan
 * @date 2013-5-8 下午2:15:17
 */
public class ColumnWidthDefine {
	private int col;
	private int width;

	/**
	 * 
	 */
	public ColumnWidthDefine() {
	}

	/**
	 * @param col
	 */
	public ColumnWidthDefine(int col) {
		this.col = col;
	}

	/**
	 * @param col
	 * @param width
	 */
	public ColumnWidthDefine(int col, int width) {
		this.col = col;
		this.width = width;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

}
