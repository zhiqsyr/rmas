package com.dl.rmas.web.converter;

import java.util.List;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

/**
 * 下拉选择使用的converter
 *
 */
@SuppressWarnings("rawtypes")
public class SelectMoldConverter implements Converter {
	
	@Override
	public Object coerceToBean(Object obj, Component comp, BindContext ctx) {
		if (obj instanceof Listitem) {
			return ((Listitem) obj).getValue();
		}
		return null;
	}

	@Override
	public Object coerceToUi(Object obj, Component comp, BindContext ctx) {
		if (comp instanceof Listbox) {
			List<Listitem> items = ((Listbox) comp).getItems();
			
			for (Listitem item : items) {
				if (obj == null && item.getValue() == null) {
					item.setSelected(true);
					return item;
				} else if (obj != null && obj.equals(item.getValue())) {
					item.setSelected(true);
					return item;
				}
			}
		}
		return null;
	}
	
}
