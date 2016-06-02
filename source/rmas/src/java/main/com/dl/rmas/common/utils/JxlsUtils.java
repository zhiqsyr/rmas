package com.dl.rmas.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.ClassPathResource;

/**
 * JXLS 导出工具类
 * 
 * @author dongbz 2015-9-9
 */
public class JxlsUtils {

	/**
	 * <b>Function: <b>导出数据 beans 到 excel，返回 excel 路径（folderPath + excelName）
	 *
	 * @param beans	数据源
	 * @param folderPath	excel文件夹路径（应用服务器同盘根目录 + folderPath）
	 * @param templatePath	模板相对路径
	 * @param excelName		excel 名称
	 * @return	excel 路径（folderPath + excelName）
	 * @throws Exception 
	 */
	public static String exportExcel(Map<String, Object> beans, 
			String folderPath, String templatePath, String excelName) throws Exception {
		File fold = new File(folderPath);
		if (!fold.isDirectory()) {
			fold.mkdirs();
		}
		
		XLSTransformer transformer = new XLSTransformer();
		ClassPathResource cpr = new ClassPathResource(templatePath);
		Workbook wb = transformer.transformXLS(cpr.getInputStream(), beans);
		OutputStream os = new FileOutputStream(new File(folderPath + excelName));
		wb.write(os);
		os.flush();
		os.close();		
		
		return folderPath + excelName;
	}
	
}
