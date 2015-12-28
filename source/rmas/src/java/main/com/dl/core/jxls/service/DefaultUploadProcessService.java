package com.dl.core.jxls.service;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.dl.core.jxls.common.model.ParameterSet;
import com.dl.core.jxls.entity.ReportConfig;
import com.dl.core.jxls.model.ReportConfigSupport;
import com.dl.core.jxls.model.UploadDataModel;
import com.dl.core.jxls.parse.ExcelTemplateParser;
import com.dl.core.jxls.parse.model.TableModel;
import com.dl.core.jxls.validation.ValidateService;

/**
 * 默认的数据上报处理类，通过DefaultExcelParser解析文件，并通过DefaultExcelParser对解析到的数据进行处理.
 * 
 * @author dylan
 * @date 2012-9-28 下午2:48:23
 */

@Service(DefaultUploadProcessService.DEFAULT_UPLOAD_PROCESS_SERVICE_BEAN_NAME)
public class DefaultUploadProcessService implements UploadProcessService {
	public static final String DEFAULT_UPLOAD_PROCESS_SERVICE_BEAN_NAME = "upload.defaultUploadProcessService";

	@Qualifier("upload.defaultValidateService")
	private ValidateService validateService;

	@Override
	public UploadDataModel process(ReportConfig reportConfig,
			InputStream inputStream, ParameterSet parameters) {
		ExcelTemplateParser excelParser = getExcelParser(reportConfig,
				inputStream);
		if (excelParser instanceof ReportConfigSupport) {
			((ReportConfigSupport) excelParser).setReportConfig(reportConfig);
		}
		// 解析excel文件的内容
		TableModel table = excelParser.parseTemplate();

		// 数据处理和校验
		return validateService.processAndValidate(reportConfig, table,
				parameters);
	}

	/**
	 * 获取excel数据解析程序,excel数据解析程序负责对excel文件的内容进行解析
	 * 
	 * @return
	 */
	protected ExcelTemplateParser getExcelParser(ReportConfig reportConfig,
			InputStream inputStream) {
		ExcelTemplateParser parser = new ExcelTemplateParser();
		parser.setResource(inputStream);
		parser.setParseRange(safeInt(reportConfig.getStartRow()),
				safeInt(reportConfig.getStartCol()),
				safeInt(reportConfig.getEndRow()),
				safeInt(reportConfig.getEndCol()));
		parser.setSheetName(reportConfig.getSheetName());
		return parser;
	}

	private int safeInt(Integer value) {
		return value == null ? 0 : value;
	}

	/**
	 * 设置excel数据处理和校验程序
	 * 
	 * @param validateService
	 */
	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}

	/**
	 * 数据处理和校验程序,从excel文件中解析到信息之后，将由数据解析和校验程序负责对解析到的excel内容进行处理和校验
	 * 
	 * @return
	 */
	public ValidateService getValidateService() {
		return validateService;
	}

}
