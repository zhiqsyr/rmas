package com.dl.rmas.web.vm.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import com.dl.rmas.common.cache.Constants;
import com.dl.rmas.entity.DictCode;
import com.dl.rmas.entity.DictType;
import com.dl.rmas.service.DictTypeCodeService;

public class ConfigQueryVM extends BaseVM {

	public static final String KEY_CONFIG = "config";
	public static final String KEY_TYPE_ID = "typeId";
	
	@WireVariable
	private DictTypeCodeService dictTypeCodeService;
	
	private List<DictType> dictTypes;
	private List<DictCode> dictCodes;
	
	private DictType selectedType;
	private DictCode selectedCode;
	
	@Init
	public void init() {
		dictTypes = dictTypeCodeService.queryAllValid(DictType.class);
	}
	

	@Command
	@NotifyChange({"dictCodes", "selectedCode"})
	public void onSelectType() {
		dictCodes = dictTypeCodeService.queryDictCodesByDictTypeId(selectedType.getTypeId());
		selectedCode = null;
	}
	
	@Command
	@NotifyChange({"dictCodes"})
	public void onShowAdd() {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put(KEY_TYPE_ID, selectedType.getTypeId());
		showModal(ConfigEditVM.URL_CONFIG_EDIT, args);
		
		dictCodes = dictTypeCodeService.queryDictCodesByDictTypeId(selectedType.getTypeId());
	}
	
	@Command
	@NotifyChange({"dictCodes"})
	public void onShowEdit() {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put(KEY_CONFIG, selectedCode);
		showModal(ConfigEditVM.URL_CONFIG_EDIT, args);
	}
	
	@Command
	@NotifyChange({"dictCodes", "selectedCode"})
	public void onDelete() {
		if (!showQuestionBox(Constants.CONFIRM_TO_DELETE)) {
			return;
		}
		
		dictTypeCodeService.doRemove(selectedCode);
		dictCodes = dictTypeCodeService.queryDictCodesByDictTypeId(selectedType.getTypeId());
		selectedCode = null;
	}
	
	public DictType getSelectedType() {
		return selectedType;
	}

	public void setSelectedType(DictType selectedType) {
		this.selectedType = selectedType;
	}

	public DictCode getSelectedCode() {
		return selectedCode;
	}

	public void setSelectedCode(DictCode selectedCode) {
		this.selectedCode = selectedCode;
	}

	public List<DictType> getDictTypes() {
		return dictTypes;
	}

	public List<DictCode> getDictCodes() {
		return dictCodes;
	}
	
}
