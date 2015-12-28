package com.dl.rmas.web.converter;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;

import com.dl.rmas.common.utils.PropertiesUtils;

/**
 * 枚举字符串值转换器
 * 
 * @author dongbz 2015-1-30
 */
@SuppressWarnings("rawtypes")
public class EnumTypeStringConverter implements Converter {

	@Override
	public Object coerceToBean(Object obj, Component comp, BindContext ctx) {
		return obj;
	}

	@Override
	public Object coerceToUi(Object obj, Component comp, BindContext ctx) {
		if (obj == null || ctx.getConverterArg("clazz") == null) {
		    return "";
		}
		
		String clazz = (String) ctx.getConverterArg("clazz");
		
		return PropertiesUtils.getValueInEnums(clazz + "." + obj);
	}

}
