package com.dl.rmas.web.vm.statis;

import java.util.List;

import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import com.dl.rmas.entity.SnProduce;
import com.dl.rmas.service.SnProduceService;
import com.dl.rmas.web.vm.system.BaseVM;

/**
 * SN生产流程展示
 * 
 * @author dongbz 2015-1-30
 */
public class SnProduceViewVM extends BaseVM {

	public static final String URL_SN_PRODUCE_VIEW = "/zul/statis/snProduce_view.zul";
	
	@WireVariable
	private SnProduceService snProduceService;
	
	private List<SnProduce> result;
	
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		
		Integer snId = getArgValue(Integer.class, SnTrackVM.KEY_SN_ID);
		result = snProduceService.querySnProducesBySnId(snId);
	}

	public List<SnProduce> getResult() {
		return result;
	}
	
}
