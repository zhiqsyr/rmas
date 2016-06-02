package com.dl.rmas.web.vm.produce;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import com.dl.rmas.entity.Sn;
import com.dl.rmas.service.SnService;
import com.dl.rmas.web.vm.system.BaseVM;

/**
 * KEYIN打印标签
 * 
 * @author dongbz 2015-2-26
 */
public class KeyinPrintVM extends BaseVM {

	public static final String URL_KEYIN_PRINT = "/zul/produce/keyin_print.zul?snId=";
	
	@WireVariable
	private SnService snService;
	
	private Sn sn;
	
	@Init
	public void init() {
		String snId = getRequestParameter("snId");
		sn = snService.queryById(Sn.class, Integer.parseInt(snId));
	}

	public Sn getSn() {
		return sn;
	}
	
}
