package com.dl.core.jxls.export;

import java.io.OutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Blank;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import com.dl.core.jxls.export.model.ListDataContext;
import com.dl.core.jxls.export.model.SheetTemplate;
import com.dl.core.jxls.parse.model.MergedCell;
import com.dl.core.jxls.util.JxlUtil;

/**
 * @author Lucas
 * @date 2014-12-12 上午10:38:44
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractExportProcessor implements ExportProcessor {

	/**
	 * 假设输出流默认按此key存放在DataContext中
	 */
	public static String OUT_STREAM_STORE_KEY = "_out";
	
	
	protected boolean skipMergedCell() {
		return true;
	}
	
	protected void fillRowAfterRow(WritableSheet sheet, int row,
			ListDataContext context, ResultHolder result) {
		List<MergedCell> mergedCells = JxlUtil.getMergedCells(sheet);
		int r = row;
		while (context.scrollNext()) {
//			System.out.println("行：：："+r);
			sheet.insertRow(++r);
			for (int i = 0; i <= sheet.getColumns(); i++) {
				WritableCell source = sheet.getWritableCell(i, row);
				if (skipMergedCell()
						&& JxlUtil.isMergedCell(source, mergedCells)) {
					continue;
				}
				cloneCell(context, sheet, source, i, r);
			}
		}
		// 数据完成之后，把模板行给删掉
		sheet.removeRow(row);
	}

	protected void fillColAfterCol(WritableSheet sheet, int row,
			ListDataContext context, ResultHolder result) {
		List<MergedCell> mergedCells = JxlUtil.getMergedCells(sheet);
		int c = row;
		while (context.scrollNext()) {
			sheet.insertColumn(++c);
//			System.out.println("第"+c+"行");
			sheet.setColumnView(c, sheet.getColumnView(row));
			for (int r = 0; r <= sheet.getRows(); r++) {
				WritableCell source = sheet.getWritableCell(row, r);
				if (skipMergedCell()
						&& JxlUtil.isMergedCell(source, mergedCells)) {
					continue;
				}
				cloneCell(context, sheet, source, c, r);
			}
		}
		sheet.removeColumn(row);
	}
	
	protected void cloneCell(ListDataContext context, WritableSheet sheet,
			WritableCell source, int column, int row) {
		WritableCell newCell = null;
		jxl.format.CellFormat format = source.getCellFormat();
		if (source instanceof Label) {
			String content = ((Label) source).getString();
			if (content != null) {
				try {
					content = context.renderString(content);
				} catch (Exception e) {
					content = "";
				}
			} else {
				content = "";
			}
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
	
	protected OutputStream getOutputStream(ListDataContext context) {
		Object output = context.getData(OUT_STREAM_STORE_KEY);
		if (output instanceof OutputStream) {
			return (OutputStream) output;
		}
		throw new IllegalArgumentException("缺少输出流");
	}
	
	protected OutputStream getOutputStream(ListDataContext context, String key) {
		Object output = context.getData(key);
		if (output instanceof OutputStream) {
			return (OutputStream) output;
		}
		throw new IllegalArgumentException("缺少输出流");
	}

	protected WritableWorkbook createWorkBook(SheetTemplate template,
			ListDataContext context, OutputStream out) throws Exception {
		WorkbookSettings settings = new WorkbookSettings();

		Workbook templateWorkbook = Workbook
				.getWorkbook(template.getTemplate());
		WritableWorkbook workbook = Workbook.createWorkbook(out,
				templateWorkbook, settings);
		return workbook;
	}
}
