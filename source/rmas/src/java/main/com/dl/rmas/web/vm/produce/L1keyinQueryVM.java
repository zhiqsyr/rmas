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

import com.dl.rmas.common.enums.SnStatus;
import com.dl.rmas.common.utils.PropertiesUtils;
import com.dl.rmas.dto.SnDto;
import com.dl.rmas.entity.Sn;
import com.dl.rmas.service.LabelValueBeanService;
import com.dl.rmas.service.SnService;
import com.dl.rmas.web.vm.system.PageVM;
import com.dl.rmas.web.zkmodel.LabelValueBean;

public class L1keyinQueryVM extends PageVM {

	public static final String KEY_SNS = "sns";
	public static final String KEY_SN = "sn";
	
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
		snDto.setStatus(SnStatus.WAIT_L1KEYIN);
		
		onSearch();
	}
	
	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onSearch() {
		result = snService.querySnsBySnDto(snDto, pagingDto);
	}
	
	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onShowAssign(@BindingParam("resultListbox")Listbox resultListbox) {
		Set<Listitem> selected = resultListbox.getSelectedItems();
		if (selected.size() == 0) {
			showWarningBox(PropertiesUtils.getValueInSystem("l1keyin.query.validate"));
			return;
		}
		
		List<Sn> selectedSns = new ArrayList<Sn>(selected.size());
		for (Listitem listitem : selected) {
			selectedSns.add((Sn) listitem.getValue());
		}
		
		Map<String, Object> args = new HashMap<String, Object>();
		args.put(KEY_SNS, selectedSns);
		showModal(L1keyinEditVM.URL_L1KEYIN_EDIT, args);
		
		onSearch();
	}
	
	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onFinishRepair(@BindingParam("resultListbox")Listbox resultListbox) {
		Set<Listitem> selected = resultListbox.getSelectedItems();
		if (selected.size() == 0) {
			showWarningBox(PropertiesUtils.getValueInSystem("l1keyin.final.validate"));
			return;
		}
		
		List<Sn> selectedSns = new ArrayList<Sn>(selected.size());
		for (Listitem listitem : selected) {
			selectedSns.add((Sn) listitem.getValue());
		}
		
		Map<String, Object> args = new HashMap<String, Object>();
		args.put(KEY_SNS, selectedSns);
		showModal(L1keyinFinalVM.URL_L1KEYIN_FINAL, args);
		
		onSearch();
	}
	
	public List<LabelValueBean<Integer>> getCustomerLabelValueBeans() {
		return customerLabelValueBeans;
	}

	public List<Sn> getResult() {
		return result;
	}

	public SnDto getSnDto() {
		return snDto;
	}

	public void setSnDto(SnDto snDto) {
		this.snDto = snDto;
	}

}
