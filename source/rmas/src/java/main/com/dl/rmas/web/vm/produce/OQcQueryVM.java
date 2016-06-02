package com.dl.rmas.web.vm.produce;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.dl.rmas.common.cache.Constants;
import com.dl.rmas.common.enums.FinalResult;
import com.dl.rmas.common.enums.SnStatus;
import com.dl.rmas.dto.SnDto;
import com.dl.rmas.entity.Sn;
import com.dl.rmas.service.SnService;
import com.dl.rmas.web.vm.system.PageVM;

public class OQcQueryVM extends PageVM {

	@WireVariable
	private SnService snService;
	
	private SnDto snDto;
	
	private List<Sn> result;

	@Init
	public void init() {
		snDto = new SnDto();
		snDto.setOqcResult("OK");
		snDto.setStatus(SnStatus.WAIT_DO);
		
		pagingDto.setPageSize(20);
		onSearch();
	}
	
	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onSearch() {
		result = snService.querySnsBySnDto(snDto, pagingDto);
	}
	
	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onOk(@BindingParam("resultListbox")Listbox resultListbox) {
		Set<Listitem> selected = resultListbox.getSelectedItems();
		if (selected.size() == 0) {
			showWarningBox("Please select the SNs for OQC OK");
			return;
		}
		if (!showQuestionBox("Confirm to OQC OK ?")) {
			return;
		}
		
		List<Sn> selectedSns = new ArrayList<Sn>(selected.size());
		for (Listitem listitem : selected) {
			selectedSns.add((Sn) listitem.getValue());
		}
		
		snService.doOQc(selectedSns, FinalResult.OK, null);//oqcOK的remark为空
		
		showInformationBox(Constants.OPERATION_COMPLETED);
		onSearch();
	}

	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onNg(@BindingParam("resultListbox")Listbox resultListbox) {
		Set<Listitem> selected = resultListbox.getSelectedItems();
		if (selected.size() == 0) {
			showWarningBox("Please select the SNs for OQC NG");
			return;
		}
		if (!showQuestionBox("Confirm to OQC NG ?")) {
			return;
		}
		
		List<Sn> selectedSns = new ArrayList<Sn>(selected.size());
		for (Listitem listitem : selected) {
			selectedSns.add((Sn) listitem.getValue());
		}
		
//		snService.doOQc(selectedSns, FinalResult.NG);
//		
//		showInformationBox(Constants.OPERATION_COMPLETED);
		
		Map<String, Object> args = new HashMap<String, Object>();
		args.put(L1keyinQueryVM.KEY_SNS, selectedSns);
		args.put(OQcResultVM.KEY_OQC_RESULT, FinalResult.NG);
		showModal(OQcResultVM.URL_OQC_RESULT, args);
		
		onSearch();
	}
	
	public SnDto getSnDto() {
		return snDto;
	}

	public void setSnDto(SnDto snDto) {
		this.snDto = snDto;
	}

	public List<Sn> getResult() {
		return result;
	}

}
