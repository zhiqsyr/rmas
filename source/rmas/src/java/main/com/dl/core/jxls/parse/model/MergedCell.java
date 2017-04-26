package com.dl.core.jxls.parse.model;

import jxl.Cell;
/**
 * 合并的单元格
 * @author dylan
 * @date 2013-5-6 上午9:16:46
 */
public class MergedCell {
	private int startRow;//开始行号,以0作为起始
	private int endRow;//结束行号,以0作为起始
	private int startCol;//开始列号,以0作为起始
	private int endCol;//结束列号,以0作为起始
	private Cell cell;//单元格,即topLeft单元格

	public MergedCell() {
	}

	public MergedCell(int startRow, int startCol, int endRow, int endCol) {
		super();
		this.startRow = startRow;
		this.endRow = endRow;
		this.startCol = startCol;
		this.endCol = endCol;
	}

	public MergedCell(int startRow, int startCol, int endRow, int endCol,
			Cell cell) {
		super();
		this.startRow = startRow;
		this.endRow = endRow;
		this.startCol = startCol;
		this.endCol = endCol;
		this.cell = cell;
	}

	/**
	 * 跨行数
	 * 
	 * @return
	 */
	public int getRowspan() {
		return endRow - startRow + 1;
	}

	/**
	 * 跨列数
	 * 
	 * @return
	 */
	public int getColspan() {
		return endCol - startCol + 1;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public int getStartCol() {
		return startCol;
	}

	public void setStartCol(int startCol) {
		this.startCol = startCol;
	}

	public int getEndCol() {
		return endCol;
	}

	public void setEndCol(int endCol) {
		this.endCol = endCol;
	}

	public Cell getCell() {
		return cell;
	}

	public void setCell(Cell cell) {
		this.cell = cell;
	}

}
