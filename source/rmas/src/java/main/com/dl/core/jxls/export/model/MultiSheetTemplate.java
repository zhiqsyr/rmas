package com.dl.core.jxls.export.model;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 非常无奈，添加多模板页
 * 
 * @author Lucas
 * @date 2014-12-12 上午10:17:47
 */
public class MultiSheetTemplate {

	private InputStream template;
	private List<Sheet> sheets = new ArrayList<Sheet>();

	public InputStream getTemplate() {
		return template;
	}

	public void setTemplate(InputStream template) {
		this.template = template;
	}

	public List<Sheet> getSheets() {
		return sheets;
	}

	public void addSheets(List<Sheet> s) {
		sheets.addAll(s);
	}
	
	public void addSheet(Sheet s) {
		sheets.add(s);
	}
	
	public void addSheet(int index, Sheet s) {
		sheets.add(index, s);
	}

}
