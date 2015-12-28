package com.dl.core.jxls.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * SpringContextHolder
 * 
 * @author dylan
 * @date 2012-6-4 上午10:02:24
 */
public class SpringContextHolder implements ApplicationContextAware {
	private static Log log = LogFactory.getLog(SpringContextHolder.class);

	private SpringContextHolder() {
	}

	private static ApplicationContext applicationContext;

	/**
	 * 根据BEAN ID 获取已经定义好的BEAN
	 * 
	 * @param beanName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		return (T) applicationContext.getBean(beanName);
	}

	public static <T> T getBean(Class<T> beanClazz) {
		return (T) applicationContext.getBean(beanClazz);
	}

	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		if (applicationContext != null) {
			throw new BeanCreationException(
					"ApplicationContextHolder already holded 'applicationContext'.");
		}
		applicationContext = context;
		log.info("holded applicationContext,displayName:"
				+ applicationContext.getDisplayName());
	}

	public static ApplicationContext getApplicationContext() {
		if (applicationContext == null)
			throw new IllegalStateException(
					"'applicationContext' property is null,ApplicationContextHolder not yet init.");
		return applicationContext;
	}

}
