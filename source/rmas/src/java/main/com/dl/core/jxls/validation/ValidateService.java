package com.dl.core.jxls.validation;

import java.util.List;

import com.dl.core.jxls.common.model.ParameterSet;
import com.dl.core.jxls.entity.ReportConfig;
import com.dl.core.jxls.model.UploadDataModel;
import com.dl.core.jxls.parse.model.TableModel;

/**
 * 数据校验服务类
 * 
 * @author dylan
 * @date 2012-9-28 上午10:15:27
 */
public interface ValidateService {
	/**
	 * 进行数据处理和数据校验
	 * 
	 * @param reportConfig
	 * @param tableData
	 * @param parameters
	 * @return
	 */
	public UploadDataModel processAndValidate(ReportConfig reportConfig,
			TableModel tableData, ParameterSet parameters);
}
