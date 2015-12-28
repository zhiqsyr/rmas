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
import com.dl.rmas.common.enums.SnStatus;
import com.dl.rmas.common.utils.PropertiesUtils;
import com.dl.rmas.dto.SnDto;
import com.dl.rmas.entity.Sn;
import com.dl.rmas.service.SnService;
import com.dl.rmas.service.UserRoleService;
import com.dl.rmas.web.vm.system.PageVM;

public class DoQueryVM extends PageVM {

	@WireVariable
	private SnService snService;
	@WireVariable
	private UserRoleService userRoleService;
	
	private SnDto snDto;
	
	private List<Sn> result;
	
	@Init
	public void init() {
		snDto = new SnDto();
		snDto.setStatus(SnStatus.WAIT_DO);
		
		onSearch();
	}
	
	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onSearch() {
		result = snService.querySnsBySnDto(snDto, null);
	}

	@Command
	@NotifyChange({"result"})
	public void onEditImei(@BindingParam("sn")Sn sn) {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put(KeyinQueryVM.KEY_SN, sn);
		
		showModal(ImeiEditVM.URL_IMEI_EDIT, args);
	}
	
	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onCreateRMA(@BindingParam("resultListbox")Listbox resultListbox) throws Exception {
		Set<Listitem> selected = resultListbox.getSelectedItems();
		if (selected.size() == 0) {
			showWarningBox(PropertiesUtils.getValueInSystem("do.query.validate"));
			return;
		}
		
		List<Sn> selectedSns = new ArrayList<Sn>(selected.size());
		for (Listitem listitem : selected) {
			selectedSns.add((Sn) listitem.getValue());
		}
		
		Integer orderId = selectedSns.get(0).getOrderId();
		for (Sn sn : selectedSns) {
			if (!orderId.equals(sn.getOrderId())) {
				showWarningBox(PropertiesUtils.getValueInSystem("do.validate"));
				return;
			}
		}
		
		if (!showQuestionBox(PropertiesUtils.getValueInSystem("do.confirm"))) {
			return;
		}
		
		snService.doDo(selectedSns);
		onSearch();
	}
	
	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onReturnQC(@BindingParam("resultListbox")Listbox resultListbox) throws Exception {
		Set<Listitem> selected = resultListbox.getSelectedItems();
		if (selected.size() == 0) {
			showWarningBox("Please select the SNs to return to QC");
			return;
		}
		
		List<Sn> selectedSns = new ArrayList<Sn>(selected.size());
		for (Listitem listitem : selected) {
			selectedSns.add((Sn) listitem.getValue());
		}
		
		if (!showQuestionBox("Confirm to return to QC ?")) {
			return;
		}
		
		snService.doReturnQc(selectedSns);
		showInformationBox(Constants.OPERATION_COMPLETED);
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

	public boolean isReturnBtnVisible() {
		return userRoleService.isUserAdmin(getCurrentUser().getUserId());
	}
	
}
