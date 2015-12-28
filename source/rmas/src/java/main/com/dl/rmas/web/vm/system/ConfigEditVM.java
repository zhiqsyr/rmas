package com.dl.rmas.web.vm.system;

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
import com.dl.rmas.entity.DictCode;
import com.dl.rmas.service.DictTypeCodeService;

public class ConfigEditVM extends BaseVM {

	public static final String URL_CONFIG_EDIT = "/zul/system/config_edit.zul";
	
	@WireVariable
	private DictTypeCodeService dictTypeCodeService;
	
	private DictCode dictCode;
	
	@Wire
	private Window editWin;
	
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		
		dictCode = getArgValue(DictCode.class, ConfigQueryVM.KEY_CONFIG);
		if (dictCode == null) {
			dictCode = new DictCode();
			dictCode.setTypeId(getArgValue(Integer.class, ConfigQueryVM.KEY_TYPE_ID));
		}
	}
	
	@Command
	public void onSubmit() {
		dictTypeCodeService.doCreateOrModifyDictCode(dictCode);
		
		showInformationBox(Constants.OPERATION_COMPLETED);
		editWin.detach();
	}

	public DictCode getDictCode() {
		return dictCode;
	}

	public void setDictCode(DictCode dictCode) {
		this.dictCode = dictCode;
	}
	
}
