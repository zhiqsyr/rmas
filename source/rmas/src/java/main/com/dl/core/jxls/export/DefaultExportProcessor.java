package com.dl.core.jxls.export;

import java.io.IOException;
import java.util.List;

import jxl.write.Blank;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.lang.StringUtils;

import com.dl.core.jxls.export.model.DataContext;
import com.dl.core.jxls.export.model.ListDataContext;
import com.dl.core.jxls.export.model.SheetTemplate;
import com.dl.core.jxls.parse.ExcelTemplateParser.ParseMode;
import com.dl.core.jxls.parse.model.MergedCell;
import com.dl.core.jxls.util.JxlUtil;

@SuppressWarnings("rawtypes")
public class DefaultExportProcessor extends AbstractExportProcessor {

	public void process(SheetTemplate template, DataContext context,
			ResultHolder result) {
		if (!(context instanceof ListDataContext)) {
			throw new RuntimeException("Only ListDataContext is supported!");
		}
		interalProcess(template, (ListDataContext) context, result);
	}
	
	public void process(SheetTemplate template, DataContext context, String key) {
		if (!(context instanceof ListDataContext)) {
			throw new RuntimeException("Only ListDataContext is supported!");
		}
		interalProcess(template, (ListDataContext) context, key);
	}
	
	public void process(SheetTemplate template, DataContext context,
			String key, int limit) {
		if (!(context instanceof ListDataContext)) {
			throw new RuntimeException("Only ListDataContext is supported!");
		}
		interalProcess(template, (ListDataContext) context, key, limit);
	}
	
	protected void interalProcess(SheetTemplate template,
			ListDataContext context, ResultHolder result) {
		try {
			WritableWorkbook workbook = createWorkBook(template, context,
					getOutputStream(context));
			WritableSheet sheet = null;
			if (StringUtils.isNotEmpty(template.getSheetName())) {
				sheet = workbook.getSheet(template.getSheetName().trim());
			} else if (workbook.getNumberOfSheets() > 0) {
				sheet = workbook.getSheet(0);
			}
			if (sheet != null) {
				int row = template.getTemplateRow();
				if (template.getParseMode() == ParseMode.colAfterCol) {
					// 按列进行解析
					fillColAfterCol(sheet, row, context, result);
				} else {
					// 按行进行解析
					fillRowAfterRow(sheet, row, context, result);
				}
			}
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				getOutputStream(context).close();
			} catch (IOException e1) {
			}
		}
	}
	
	protected void interalProcess(SheetTemplate template,
			ListDataContext context, String key) {
		try {
			WritableWorkbook workbook = createWorkBook(template, context,
					getOutputStream(context, key));
			WritableSheet sheet = null;
			if (StringUtils.isNotEmpty(template.getSheetName())) {
				sheet = workbook.getSheet(template.getSheetName().trim());
			} else if (workbook.getNumberOfSheets() > 0) {
				sheet = workbook.getSheet(0);
			}
			if (sheet != null) {
				int row = template.getTemplateRow();
				if (template.getParseMode() == ParseMode.colAfterCol) {
					// 按列进行解析
					fillColAfterCol(sheet, row, context, null);
				} else {
					// 按行进行解析
					fillRowAfterRow(sheet, row, context, null);
				}
			}
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				getOutputStream(context, key).close();
			} catch (IOException e1) {
			}
		}
	}
	
	protected void interalProcess(SheetTemplate template,
			ListDataContext context, String key, int limit) {
		try {
			WritableWorkbook workbook = createWorkBook(template, context,
					getOutputStream(context, key));
			WritableSheet sheet = null;
			String sheetName = template.getSheetName().trim();
			int row = template.getTemplateRow();
			
			if (StringUtils.isNotEmpty(sheetName)) {
				sheet = workbook.getSheet(sheetName);
			} else if (workbook.getNumberOfSheets() > 0) {
				sheet = workbook.getSheet(0);
			}
			if (sheet != null) {
				if (template.getParseMode() == ParseMode.colAfterCol) {
					// 按列进行解析
					int j = 1;
					for (int i = 0; i < context.datas().size(); i += limit, j++) {
						
						workbook.createSheet(sheetName + j, j);
						
						fillColContentFromInit(sheet, workbook.getSheet(j));			
						context.position(i);
						
						fillColAfterCol(workbook.getSheet(j), row, context, limit);
					}
					workbook.removeSheet(0);
				} else {
					// 按行进行解析
					// TODO 未实现
					fillRowAfterRow(sheet, row, context, null);
				}
			}
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				getOutputStream(context, key).close();
			} catch (IOException e1) {
			}
		}
	}
	
	/**
	 * 单个xls文件
	 * @param sheet
	 * @param row
	 * @param context
	 * @param limit
	 */
	protected void fillColAfterCol(WritableSheet sheet, int row,
			ListDataContext context, int limit) {
		List<MergedCell> mergedCells = JxlUtil.getMergedCells(sheet);
		int c = row;
		int s = 1;
		while (context.scrollNext()) {
			if (s > 100) break;
			sheet.insertColumn(++c);
			sheet.setColumnView(c, sheet.getColumnView(row));
			for (int r = 0; r <= sheet.getRows(); r++) {
				WritableCell source = sheet.getWritableCell(row, r);
				if (skipMergedCell()
						&& JxlUtil.isMergedCell(source, mergedCells)) {
					continue;
				}
				cloneCell(context, sheet, source, c, r);
			}
			s++;
		}
		sheet.removeColumn(row);
	}
	
	/**
	 * 复制sheet格式
	 * @param sheet
	 * @param newtype
	 */
	protected void fillColContentFromInit(WritableSheet sheet, WritableSheet newsheet) {
		//复制模板格式
		for (int i = 0; i < 4; i++) {
			newsheet.insertColumn(i);
			newsheet.setColumnView(i, sheet.getColumnView(i));
			
			for (int r = 0; r <= sheet.getRows(); r++) {
				WritableCell source = sheet.getWritableCell(i, r);
				
				cloneCell(newsheet, source, i, r);
			}
		}
		//合并表头
		try {
			newsheet.mergeCells(0, 0, 2, 0);
			newsheet.mergeCells(0, 3, 0, 14);
		} catch (RowsExceededException e) {
		} catch (WriteException e) {
		}
	}
	
	/**
	 * 扩展模板格式
	 * @param sheet
	 * @param source
	 * @param column
	 * @param row
	 */
	protected void cloneCell(WritableSheet sheet,
			WritableCell source, int column, int row) {
		WritableCell newCell = null;
		jxl.format.CellFormat format = source.getCellFormat();
		if (source instanceof Label) {
			String content = ((Label) source).getString();
			
			newCell = new Label(column, row, String.valueOf(content), format);
		} else if (source instanceof jxl.write.Number)
			newCell = new jxl.write.Number(column, row,
					((jxl.write.Number) source).getValue(), format);
		else if (source instanceof jxl.write.Boolean)
			newCell = new jxl.write.Boolean(column, row,
					((jxl.write.Boolean) source).getValue(), format);
		else if (source instanceof jxl.write.DateTime)
			newCell = new jxl.write.DateTime(column, row,
					((jxl.write.DateTime) source).getDate(), format);
		else if (source instanceof Blank) {
			newCell = source.copyTo(column, row);
		}
		if (newCell == null) {
			newCell = source.copyTo(column, row);
		}
		try {
			sheet.addCell(newCell);
		} catch (WriteException e) {
			throw new RuntimeException(e);
		}
	}
	
}
