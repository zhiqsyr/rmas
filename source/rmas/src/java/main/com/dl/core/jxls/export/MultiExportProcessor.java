package com.dl.core.jxls.export;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang.StringUtils;

import com.dl.core.jxls.export.model.DataContext;
import com.dl.core.jxls.export.model.ExtraCellDataModel;
import com.dl.core.jxls.export.model.ListDataContext;
import com.dl.core.jxls.export.model.MultiSheetTemplate;
import com.dl.core.jxls.export.model.Sheet;
import com.dl.core.jxls.parse.ExcelTemplateParser.ParseMode;


/**
 * @author Lucas
 * @date 2014-12-12 上午10:33:36
 */
@SuppressWarnings("rawtypes")
public class MultiExportProcessor extends DefaultExportProcessor {

	public void process(MultiSheetTemplate template, DataContext context,
			String key) {
		if (!(context instanceof ListDataContext)) {
			throw new RuntimeException("Only ListDataContext is supported!");
		}
		interalProcess(template, (ListDataContext) context, key);
	}
	
	public void interalProcess(MultiSheetTemplate template,
			ListDataContext context, String key) {
		try {
			WritableWorkbook workbook = createWorkBook(template, context,
					getOutputStream(context, key));
			WritableSheet sheet = null;
			
			List<Sheet> sheets = template.getSheets();
			if (sheets.size() > 0){
				for (int i = 0; i < sheets.size(); i++) {
					Sheet s = sheets.get(i);
					
					if (StringUtils.isNotEmpty(s.getSheetName())) {
						sheet = workbook.getSheet(s.getSheetName().trim());
					} else if (workbook.getNumberOfSheets() > 0) {
						sheet = workbook.getSheet(i);
					}
					
					if (sheet != null) {
						if (s.getSupport() != null && s.getSupport().existExtraData()){
							for (ExtraCellDataModel model : s.getSupport().getModels()) {
								WritableCell cell = sheet.getWritableCell(model.getCol(), model.getRow());
								Label label = model.getLabel();
								if (cell.getCellFormat() != null) label.setCellFormat(cell.getCellFormat());
								sheet.addCell(label);
							}
						}
						
						int row = s.getTemplateRow();
						if (s.getParseMode() == ParseMode.colAfterCol) {
							// 按列进行解析
							fillColAfterCol(sheet, row, s.getContext(), null);
						} else {
							// 按行进行解析
							fillRowAfterRow(sheet, row, s.getContext(), null);
						}
					}
				}
				
				workbook.write();
				workbook.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				getOutputStream(context, key).close();
			} catch (IOException e1) {
			}
		}
	}
	
	protected WritableWorkbook createWorkBook(MultiSheetTemplate template,
			ListDataContext context, OutputStream out) throws Exception {
		WorkbookSettings settings = new WorkbookSettings();

		Workbook templateWorkbook = Workbook
				.getWorkbook(template.getTemplate());
		WritableWorkbook workbook = Workbook.createWorkbook(out,
				templateWorkbook, settings);
		return workbook;
	}

}
