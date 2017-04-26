package com.dl.rmas.web.vm.produce;

import org.apache.commons.lang.StringUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Window;

import com.dl.rmas.common.cache.Constants;
import com.dl.rmas.entity.Sn;
import com.dl.rmas.service.SnService;
import com.dl.rmas.web.vm.system.BaseVM;

/**
 * 编辑IMEI
 * 
 * @author dongbz
 *
 * 2015-3-24
 */
public class ImeiEditVM extends BaseVM {

	public static final String URL_IMEI_EDIT = "/zul/produce/imei_edit.zul";
	
	@WireVariable
	private SnService snService;
	
	private Sn edit;
	
	@Wire
	private Window editWin;
	
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		
		edit = getArgValue(Sn.class, KeyinQueryVM.KEY_SN);
		if (StringUtils.isBlank(edit.getMacImei1N())) {
			edit.setMacImei1N(edit.getMacImei1());
		}
		if (StringUtils.isBlank(edit.getMacImei2N())) {
			edit.setMacImei2N(edit.getMacImei2());
		}
	}
	
	@Command
	public void onSave() {
		snService.doModifySn(edit);
		
		showInformationBox(Constants.OPERATION_COMPLETED);
		editWin.detach();
	}

	public Sn getEdit() {
		return edit;
	}

	public void setEdit(Sn edit) {
		this.edit = edit;
	}
	
}
