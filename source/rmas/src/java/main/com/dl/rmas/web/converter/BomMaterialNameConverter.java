package com.dl.rmas.web.converter;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;

import com.dl.rmas.entity.Bom;

@SuppressWarnings("rawtypes")
public class BomMaterialNameConverter implements Converter {
	
	@Override
	public Object coerceToBean(Object obj, Component comp, BindContext ctx) {
		return obj;
	}

	@Override
	public Object coerceToUi(Object obj, Component comp, BindContext ctx) {
		if (obj == null) {
		    return "";
		}
		
		return ((Bom) obj).getMaterialName();
	}
	
}
