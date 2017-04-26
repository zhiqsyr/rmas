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

import com.dl.rmas.common.enums.FinalResult;
import com.dl.rmas.common.enums.SnStatus;
import com.dl.rmas.common.utils.PropertiesUtils;
import com.dl.rmas.dto.SnDto;
import com.dl.rmas.entity.Sn;
import com.dl.rmas.service.SnService;
import com.dl.rmas.web.vm.system.PageVM;

public class QcQueryVM extends PageVM {

	@WireVariable
	private SnService snService;
	
	private SnDto snDto;
	
	private List<Sn> result;

	@Init
	public void init() {
		snDto = new SnDto();
		snDto.setStatus(SnStatus.WAIT_QC);
		
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
			showWarningBox(PropertiesUtils.getValueInSystem("qc.query.ok.validate"));
			return;
		}
		if (!showQuestionBox("Confirm to QC OK ?")) {
			return;
		}
		
		List<Sn> selectedSns = new ArrayList<Sn>(selected.size());
		for (Listitem listitem : selected) {
			selectedSns.add((Sn) listitem.getValue());
		}
		
		Map<String, Object> args = new HashMap<String, Object>();
		args.put(L1keyinQueryVM.KEY_SNS, selectedSns);
		args.put(QcResultVM.KEY_QC_RESULT, FinalResult.OK);
		showModal(QcResultVM.URL_QC_RESULT, args);
		
		onSearch();
	}

	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onNg(@BindingParam("resultListbox")Listbox resultListbox) {
		Set<Listitem> selected = resultListbox.getSelectedItems();
		if (selected.size() == 0) {
			showWarningBox(PropertiesUtils.getValueInSystem("qc.query.ng.validate"));
			return;
		}
		if (!showQuestionBox("Confirm to QC NG ?")) {
			return;
		}
		
		List<Sn> selectedSns = new ArrayList<Sn>(selected.size());
		for (Listitem listitem : selected) {
			selectedSns.add((Sn) listitem.getValue());
		}
		
		Map<String, Object> args = new HashMap<String, Object>();
		args.put(L1keyinQueryVM.KEY_SNS, selectedSns);
		args.put(QcResultVM.KEY_QC_RESULT, FinalResult.NG);
		showModal(QcResultVM.URL_QC_RESULT, args);
		
		onSearch();
	}
	
	@Command
	@NotifyChange({"result"})
	public void onEditImei(@BindingParam("sn")Sn sn) {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put(KeyinQueryVM.KEY_SN, sn);
		
		showModal(ImeiEditVM.URL_IMEI_EDIT, args);
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
