package com.dl.rmas.web.vm.produce;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.dl.rmas.common.enums.SnStatus;
import com.dl.rmas.common.utils.PropertiesUtils;
import com.dl.rmas.dto.SnDto;
import com.dl.rmas.entity.Sn;
import com.dl.rmas.service.LabelValueBeanService;
import com.dl.rmas.service.SnService;
import com.dl.rmas.web.vm.system.PageVM;
import com.dl.rmas.web.zkmodel.LabelValueBean;

public class FlashQueryVM extends PageVM {

	@WireVariable
	private LabelValueBeanService labelValueBeanService;
	@WireVariable
	private SnService snService;
	
	private List<LabelValueBean<Integer>> customerLabelValueBeans;
	private SnDto snDto;
	
	private List<Sn> result;
	
	@Init
	public void init() {
		customerLabelValueBeans = labelValueBeanService.buildValidCustomersWithSelect();
		
		snDto = new SnDto();
//		snDto.setKeyinStatus(KeyinStatus.CLOSE);
		snDto.setStatus(SnStatus.WAIT_FLASH);
		
		onSearch();
	}
	
	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onSearch() {
		result = snService.querySnsBySnDto(snDto, pagingDto);
	}

	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onFlashOk(@BindingParam("resultListbox")Listbox resultListbox) {
		Set<Listitem> selected = resultListbox.getSelectedItems();
		if (selected.size() == 0) {
			showWarningBox(PropertiesUtils.getValueInSystem("flash.ok.validate"));
			return;
		}
		
		if (!showQuestionBox(PropertiesUtils.getValueInSystem("flash.ok.confirm"))) {
			return;
		}
		
		List<Sn> selectedSns = new ArrayList<Sn>(selected.size());
		for (Listitem listitem : selected) {
			selectedSns.add((Sn) listitem.getValue());
		}
		
		snService.doFlashOk(selectedSns);
		showInformationBox("FLASH OK SUCCESS");
		onSearch();
	}
	
	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onFlashReturn(@BindingParam("resultListbox")Listbox resultListbox) {
		Set<Listitem> selected = resultListbox.getSelectedItems();
		if (selected.size() == 0) {
			showWarningBox(PropertiesUtils.getValueInSystem("flash.return.validate"));
			return;
		}
		
		if (!showQuestionBox(PropertiesUtils.getValueInSystem("flash.return.confirm"))) {
			return;
		}
		
		List<Sn> selectedSns = new ArrayList<Sn>(selected.size());
		for (Listitem listitem : selected) {
			selectedSns.add((Sn) listitem.getValue());
		}
		
		snService.doFlashReturn(selectedSns);
		showInformationBox("FLASH RETURN SUCCESS");
		onSearch();
	}
	
	public List<LabelValueBean<Integer>> getCustomerLabelValueBeans() {
		return customerLabelValueBeans;
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
