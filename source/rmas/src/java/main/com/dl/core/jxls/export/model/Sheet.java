package com.dl.core.jxls.export.model;

import java.util.List;

import com.dl.core.jxls.parse.ExcelTemplateParser.ParseMode;

/**
 * @author Lucas
 * @date 2014-12-12 上午10:28:24
 */
@SuppressWarnings("all")
public class Sheet {

	private String sheetName;
	private int templateRow = 0;// 数据模板所在的行，以0为起始
	private ListDataContext context;
	private ExtraCellSupport support;
	
	private ParseMode parseMode = ParseMode.rowAfterRow;

	public ListDataContext getContext() {
		return context;
	}
	
	public void setContext(List context) {
		this.context = new ListDataContext(context);
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

	public void setSupport(ExtraCellSupport support) {
		this.support = support;
	}

	public ExtraCellSupport getSupport() {
		return support;
	}

}
