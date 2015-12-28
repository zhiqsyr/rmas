package com.dl.rmas.web.converter;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.spring.SpringUtil;

import com.dl.rmas.entity.DictCode;
import com.dl.rmas.service.DictTypeCodeService;

@SuppressWarnings("rawtypes")
public class DictCodeIdNameConverter implements Converter {

	@Override
	public Object coerceToBean(Object obj, Component comp, BindContext ctx) {
		return obj;
	}

	@Override
	public Object coerceToUi(Object obj, Component comp, BindContext ctx) {
		if (obj == null) {
		    return "";
		}
		
		DictTypeCodeService dictTypeCodeService = (DictTypeCodeService) SpringUtil.getBean("dictTypeCodeService");
		DictCode dc = dictTypeCodeService.queryById(DictCode.class, Integer.parseInt(obj.toString()));
		if (dc != null) {
			return dc.getCodeName();
		} else {
			return "";
		}
	}
	
}
