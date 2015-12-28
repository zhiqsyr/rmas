package com.dl.rmas.web.converter;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;

import com.dl.rmas.common.utils.PropertiesUtils;

@SuppressWarnings("rawtypes")
public class EnumTypeConverter implements Converter {

	@Override
	public Object coerceToBean(Object obj, Component comp, BindContext ctx) {
		return obj;
	}

	@Override
	public Object coerceToUi(Object obj, Component comp, BindContext ctx) {
		if (obj == null) {
		    return "";
		}
		
		if (!(obj instanceof Enum<?>)) {
		    throw new IllegalArgumentException("Parameter Object [" + obj + "] must be an instance of java.lang.Enum");
		}
		
		Enum<?> enumObj = (Enum<?>) obj;
		return PropertiesUtils.getValueInEnums(enumObj.getClass().getName() + "." + enumObj.name());
	}

}
