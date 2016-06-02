package com.dl.core.jxls.parse.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 行模型,如果解析顺序是一列一列解析，这里的结果为一列的数据
 * 
 * @author dylan
 * @date 2013-5-3 下午3:25:55
 */
public class RowModel {
	private int serial;// 行号(或列号)，以0作为开始行号(或列号)
	private int height;// 行高

	private List<CellModel> cells = new ArrayList<CellModel>();

	public void addCell(CellModel cell) {
		cells.add(cell);
	}

	/**
	 * 获得指定序列的单元格,起始序号为0
	 * 
	 * @param cellSerial
	 * @return
	 */
	public CellModel getCell(int cellSerial) {
		for (CellModel cell : cells) {
			if (cell.getSerial() == cellSerial) {
				return cell;
			}
		}
		return null;
	}

	/**
	 * 获得指定序号的单元格内容,起始序号为0
	 * 
	 * @param cellSerial
	 * @return
	 */
	public String getCellContent(int cellSerial) {
		CellModel td = getCell(cellSerial);
		return td == null ? null : td.getContent();
	}

	/**
	 * 当前行(或列)的所有单元格,结果按单元格的序号(serial)排序
	 * 
	 * @return
	 */
	public List<CellModel> cells() {
		Collections.sort(cells, new Comparator<CellModel>() {
			@Override
			public int compare(CellModel o1, CellModel o2) {
				if (o1.getSerial() < o2.getSerial()) {
					return -1;
				}
				return o1.getSerial() > o2.getSerial() ? 1 : 0;
			}
		});
		return cells;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getSerial() {
		return serial;
	}

	public void setSerial(int serial) {
		this.serial = serial;
	}
}
