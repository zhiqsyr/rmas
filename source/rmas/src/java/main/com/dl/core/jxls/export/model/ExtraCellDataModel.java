package com.dl.core.jxls.export.model;


/**
 * @author Lucas
 * @date 2014-12-12 下午1:55:58
 */
public class ExtraCellDataModel {

	private int row;
	private int col;
	private String content;
	
	private jxl.write.Label label = null;

	public ExtraCellDataModel(int col, int row, String con) {
		this.row = row;
		this.col = col;
		this.content = con;
		this.label = new jxl.write.Label(col, row, con);
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public jxl.write.Label getLabel() {
		return label;
	}

}
