package com.dl.core.jxls.export.model;

import java.io.InputStream;

import com.dl.core.jxls.parse.ExcelTemplateParser.ParseMode;

public class SheetTemplate {
	private InputStream template;
	private String sheetName;
	private int templateRow = 0;// 数据模板所在的行，以0为起始

	private ParseMode parseMode = ParseMode.rowAfterRow;

	public InputStream getTemplate() {
		return template;
	}

	public void setTemplate(InputStream template) {
		this.template = template;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public int getTemplateRow() {
		return templateRow;
	}

	/**
	 * 设置数据模板所在的行，以0为起始
	 * 
	 * @param templateRow
	 */
	public void setTemplateRow(int templateRow) {
		this.templateRow = templateRow;
	}

	public ParseMode getParseMode() {
		return parseMode;
	}

	public void setParseMode(ParseMode parseMode) {
		this.parseMode = parseMode;
	}

}
