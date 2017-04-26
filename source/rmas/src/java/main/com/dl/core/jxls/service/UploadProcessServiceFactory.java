package com.dl.core.jxls.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 数据上报时的数据解析和校验处理服务工厂类。
 * 数据解析和校验的设计原则是按不同的reportType进行处理；所以可以按不同的reportType注册不同的数据解析和校验处理服务类。
 * 
 * @author dylan
 * @date 2012-10-8 下午3:17:28
 */
public class UploadProcessServiceFactory {
	private Map<String, UploadProcessService> uploadProcessServices;

	
	@Autowired
	@Qualifier(DefaultUploadProcessService.DEFAULT_UPLOAD_PROCESS_SERVICE_BEAN_NAME)
	private UploadProcessService defaultUploadProcessService;

	public UploadProcessService getService(String reportType) {
		UploadProcessService service = null;
		if (uploadProcessServices != null) {
			service = uploadProcessServices.get(reportType);
		}
		if (service == null) {
			service = defaultUploadProcessService;
		}
		return service;
	}
	/**
	 * 默认的数据解析和校验服务类，当根据reportType找不到对应的服务类时，将由该类提供服务.
	 */
	public UploadProcessService getDefaultUploadProcessService() {
		return defaultUploadProcessService;
	}
	/**
	 * 注册默认的数据解析和校验服务类.
	 */
	public void setDefaultUploadProcessService(
			UploadProcessService defaultUploadProcessService) {
		this.defaultUploadProcessService = defaultUploadProcessService;
	}
	/**
	 * 注册数据解析和校验服务类
	 * @param uploadProcessServices
	 */
	public void setUploadProcessServices(
			Map<String, UploadProcessService> uploadProcessServices) {
		this.uploadProcessServices = uploadProcessServices;
	}
}
