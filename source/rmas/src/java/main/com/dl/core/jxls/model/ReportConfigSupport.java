package com.dl.core.jxls.model;

import com.dl.core.jxls.entity.ReportConfig;

/**
 * ReportConfig关联支持类，统一设置和获取的接口
 * 
 * @see ReportConfig
 * @author dylan
 * @date 2012-10-8 下午3:24:21
 */
public interface ReportConfigSupport {
	/**
	 * 设置reportConfig
	 * 
	 * @param reportConfig
	 */
	public void setReportConfig(ReportConfig reportConfig);

	/**
	 * 获取报表配置模型
	 * 
	 * @return
	 */
	public ReportConfig getReportConfig();
}
