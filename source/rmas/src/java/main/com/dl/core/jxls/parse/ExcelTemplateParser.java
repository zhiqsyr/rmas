package com.dl.core.jxls.parse;

import java.io.InputStream;
import java.util.List;

import com.dl.core.jxls.parse.model.CellModel;
import com.dl.core.jxls.parse.model.ColumnWidthDefine;
import com.dl.core.jxls.parse.model.MergedCell;
import com.dl.core.jxls.parse.model.RowModel;
import com.dl.core.jxls.parse.model.TableModel;
import com.dl.core.jxls.util.JxlUtil;
import com.dl.core.jxls.util.StringHelper;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * 解析Excel表格模板文件
 * 
 * @author dylan
 * @date 2013-5-3 下午3:37:28
 */
public class ExcelTemplateParser {

	public static final int PARSE_CONTINUE = 0;
	public static final int SKIP_ROW = -1;
	public static final int SKIP_SHEET = -2;

	private int startRow;// 开始行号,以1做为起始
	private int endRow;// 结束行号,以1做为起始
	private int startCol;// 开始列号,以1做为起始
	private int endCol;// 结束列号,以1做为起始

	private InputStream resource;// 要解析的资源文件
	private String sheetName;// 要解析的sheet页名称

	private boolean needParseComment = true;// 是否需要解析标注信息
	private boolean needParseStyle = true;// 是否需要解析样式信息
	// 数据解析方式
	private ParseMode parseMode = ParseMode.rowAfterRow;

	private Workbook workbook;// 要解析的workbook
	private Sheet sheet;// 要解析的sheet页

	/**
	 * 数据解析的方式
	 * 
	 * @author dylan
	 * @date 2013-5-8 下午3:59:48
	 */
	public static enum ParseMode {
		/**
		 * 一行一行解析
		 */
		rowAfterRow,
		/**
		 * 一列一列解析,该解析方式下，不对行高和列宽进行解析
		 */
		colAfterCol;
	}

	/**
	 * 获取要解析的workbook
	 * 
	 * @return
	 */
	public Workbook getWorkbook() {
		if (workbook == null) {
			workbook = loadWorkbook();
		}
		return workbook;
	}

	/**
	 * 获取要解析的sheet
	 * 
	 * @return
	 */
	public Sheet getSheet() {
		if (sheet == null) {
			workbook = getWorkbook();
			if (StringHelper.isNotEmpty(sheetName)) {
				sheet = workbook.getSheet(sheetName);
			} else {
				sheet = workbook.getSheet(0);
			}
			if (sheet == null) {
				throw new RuntimeException("要解析的sheet不存在!");
			}
		}
		return sheet;
	}

	/**
	 * 解析模板文件，并把结果封装成一个TemplateTable对象
	 * 
	 * @return
	 */
	public TableModel parseTemplate() {
		try {
			sheet = getSheet();
			TableModel table = new TableModel();
			// 所有的列宽
			buildColumnDefine(sheet, table);

			// 加载文件中的内容
			extractTemplateContent(sheet, table);
			return table;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// 解析完成之后，把资源释放掉
			if (workbook != null) {
				workbook.close();
				workbook = null;
				sheet = null;
			}
		}
	}

	/**
	 * 加载文件中的内容,填充到table中
	 * 
	 * @param sheet
	 * @param table
	 */
	private void extractTemplateContent(Sheet sheet, TableModel table) {
		// 合并的单元格
		List<MergedCell> ranges = JxlUtil.getMergedCells(sheet);
		int outerStart;
		int outerEnd;
		if (parseMode == ParseMode.colAfterCol) {
			// 按列解析
			outerStart = startCol <= 1 ? 0 : startCol - 1;
			outerEnd = endCol < 1 ? getSheet().getColumns() : endCol;
		} else {// 按行解析
			outerStart = startRow <= 1 ? 0 : startRow - 1;
			outerEnd = endRow < 1 ? getSheet().getRows() : endRow;
		}
		//添加对导入模板tableId的验证
		table.setTableName(getTableNoFromSheet());
		
		for (; outerStart < outerEnd; outerStart++) {
			// 解析数据行
			RowModel tr = new RowModel();
			tr.setSerial(outerStart);
			if (needParseStyle && parseMode != ParseMode.colAfterCol) {
				tr.setHeight(JxlUtil.getRowHeight(outerStart, sheet));
			}
			// 当前行(或列)的合并单元格
			List<MergedCell> rowRanges = parseMode == ParseMode.colAfterCol ? JxlUtil
					.extractColMergedCells(outerStart, ranges) : JxlUtil
					.extractRowMergedCells(outerStart, ranges);
			for (MergedCell range : rowRanges) {
				CellModel td = new CellModel();
				if (parseMode == ParseMode.colAfterCol) {
					td.setSerial(range.getStartRow());
					td.setRowspan(range.getColspan());
					td.setColspan(range.getRowspan());
				} else {
					td.setSerial(range.getStartCol());
					td.setColspan(range.getColspan());
					td.setRowspan(range.getRowspan());
				}
				buildTDTemplate(td, range.getCell());
				tr.addCell(td);
			}
			int innerStart;
			int innerEnd;
			if (parseMode == ParseMode.colAfterCol) {
				// 按列解析
				innerStart = startRow <= 1 ? 0 : startRow - 1;
				innerEnd = endRow < 1 ? getSheet().getRows() : endRow;
			} else {
				innerStart = startCol <= 1 ? 0 : startCol - 1;
				innerEnd = endCol < 1 ? getSheet().getColumns() : endCol;
			}
			// 解析当前行的单元格
			for (; innerStart < innerEnd; innerStart++) {
				// 如果是按列解析，则outerStart代表列，否则代表行
				Cell cell = parseMode == ParseMode.colAfterCol ? sheet.getCell(
						outerStart, innerStart) : sheet.getCell(innerStart,
						outerStart);
				if (!JxlUtil.isMergedCell(cell, ranges)) {
					CellModel td = new CellModel();
					td.setSerial(innerStart);
					buildTDTemplate(td, cell);
					tr.addCell(td);
				}
			}
			int listenerResult = beforeAddRow(tr);
			if (listenerResult == PARSE_CONTINUE) {
				table.addRow(tr);
			} else if (listenerResult == SKIP_ROW) {
				continue;
			} else if (listenerResult == SKIP_SHEET) {
				break;
			}
		}
	}

	/**
	 * 添加新行之前的事件监听
	 * 
	 * @param tr
	 * @return
	 */
	protected int beforeAddRow(RowModel tr) {
		return PARSE_CONTINUE;
	}

	private void buildTDTemplate(CellModel td, Cell cell) {
		td.setContent(JxlUtil.getCellContent(cell));
		if (needParseComment) {// 是否解析标注信息
			td.setComment(JxlUtil.getCellComment(cell));
			JxlUtil.buildTDStyle(cell, td);
		}
	}

	/**
	 * 设置要解析的区域,行号和列号都是以1做为开始
	 * 
	 * @param startRow
	 * @param startCol
	 * @param endRow
	 * @param endCol
	 */
	public void setParseRange(int startRow, int startCol, int endRow, int endCol) {
		setStartRow(startRow);
		setStartCol(startCol);
		setEndRow(endRow);
		setEndCol(endCol);
	}
	
	public String getTableNoFromSheet(){
		return getSheet().getCell(0, 0).getContents();
	}
	
	// 需要解析的列的列宽信息
	private void buildColumnDefine(Sheet sheet, TableModel table) {
		if (!needParseStyle || parseMode != ParseMode.rowAfterRow) {// 不对样式进行解析
			return;
		}
		int c = startCol <= 1 ? 0 : startCol - 1;
		int end = endCol < 1 ? getSheet().getColumns() : endCol;
		for (; c < end; c++) {
			int width = JxlUtil.getColumnWidth(c, sheet);
			table.addColumnWidthDefine(new ColumnWidthDefine(c, width));
		}
	}

	// 加载workbook
	private Workbook loadWorkbook() {
		Workbook workbook = null;
		try {
			workbook = Workbook.getWorkbook(resource);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return workbook;
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

	public InputStream getResource() {
		return resource;
	}

	public void setResource(InputStream resource) {
		this.resource = resource;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public boolean isNeedParseComment() {
		return needParseComment;
	}

	public void setNeedParseComment(boolean needParseComment) {
		this.needParseComment = needParseComment;
	}

	public boolean isNeedParseStyle() {
		return needParseStyle;
	}

	public void setNeedParseStyle(boolean needParseStyle) {
		this.needParseStyle = needParseStyle;
	}

	public ParseMode getParseMode() {
		return parseMode;
	}

	public void setParseMode(ParseMode parseMode) {
		this.parseMode = parseMode;
	}

}
