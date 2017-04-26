package com.dl.core.jxls.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ClassUtils;

import com.dl.core.jxls.common.model.ParameterSet;
import com.dl.core.jxls.common.model.Parameterizable;
/**
 * Spring bean工具类
 * @author dylan
 * @date 2012-9-28 下午1:25:48
 */
public class SpringBeanUtil {
	private static final Log logger = LogFactory.getLog(SpringBeanUtil.class);

	/**
	 * 由字符串类型的列校验器配置，解析为Bean实例，这里字符串类型的配置包括可以是一个bean的id，也可以是一个类名， 而且可以接受参数;
	 * 如:#nullVal;#equalsVal?value=expected;#anotherVal?key1=value1&key2=value2
	 * 
	 * @param columnConfig
	 * 
	 * @param strV
	 * @return
	 */
	public static <T> T findBean(String beanStr) {
		String v = beanStr.trim();
		T bean = null;
		String parmStr = null;
		String vName = v;
		int i = v.indexOf("?");
		if (i > -1) {
			vName = beanStr.substring(0, i);
			parmStr = beanStr.substring(i + 1);
		}
		try {
			if (vName.startsWith("#")) {
				// spring bean
				bean = (T)SpringContextHolder.getBean(vName.substring(1));
			} else {
				String clazzName = vName;
				bean = (T) BeanUtils.instantiate(ClassUtils.forName(clazzName,
						SpringBeanUtil.class.getClassLoader()));
			}
		} catch (Exception e) {
			// ignore exception
			logger.warn("构造列校验器出错." + e.getLocalizedMessage());
			bean = null;
		}
		if (bean == null) {
			return null;
		}
		// 对其中的参数进行解析
		ParameterSet parameters = new ParameterSet();
		if (bean instanceof Parameterizable && parmStr != null) {
			String[] parms = parmStr.split("&");
			String key = null;
			String value = null;
			for (String parm : parms) {
				i = parm.indexOf("=");
				if (i == -1 || parm.length() == 0) {
					continue;
				} else if (i == parm.length() - 1) {
					key = parm.substring(0, i);
				} else {
					key = parm.substring(0, i);
					value = parm.substring(i + 1);
				}
				parameters.setParameter(key, value);
			}
			((Parameterizable) bean).setParameters(parameters);
		}
		for (String key : parameters.parameterNames()) {
			try {
				org.apache.commons.beanutils.BeanUtils.setProperty(bean, key,
						parameters.getParameter(key));
			} catch (Exception e) {
				// 参数设置出现异常，不进行处理
				logger.warn("参数配置出现异常,v=" + v);
			}
		}
		return bean;
	}
}
