package com.dl.core.jxls.service.store;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * 数据保存服务类工厂,设计的原理是可以支持针对不同类型的reportType进行不同的保存逻辑处理。所以需要根据reportType注册相应的保存服务类。
 * 如果相应的reportType类没有对应的数据保存类，则返回默认的保存服务{@link DefaultDataStoreService}
 * 
 * @author dylan
 * @date 2012-10-9 下午3:49:45
 */
public class DataStoreServiceFactory {
	@Autowired
	@Qualifier(DefaultDataStoreService.DEFAULT_DATA_STORE_SERVICE_BEAN_NAME)
	private DataStoreService defaultDataStoreService;

	private Map<String, DataStoreService> dataStoreServices;
	/**
	 * 根据reportType获取对应的数据保存类，如果没有注册，则使用默认的保存服务进行保存处理.
	 * @see DefaultDataStoreService
	 * @param reportType
	 * @return
	 */
	public DataStoreService getService(String reportType) {
		DataStoreService service = null;
		if (dataStoreServices != null) {
			service = dataStoreServices.get(reportType);
		}
		if (service == null) {
			service = defaultDataStoreService;
		}
		return service;
	}

	public void setDefaultDataStoreService(
			DataStoreService defaultDataStoreService) {
		this.defaultDataStoreService = defaultDataStoreService;
	}

	public void setDataStoreServices(
			Map<String, DataStoreService> dataStoreServices) {
		this.dataStoreServices = dataStoreServices;
	}

}
