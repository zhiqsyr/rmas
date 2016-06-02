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

import com.dl.rmas.dto.SnDto;
import com.dl.rmas.entity.Sn;
import com.dl.rmas.service.SnService;
import com.dl.rmas.web.vm.system.PageVM;

public class KeyoutQueryVM extends PageVM {

	@WireVariable
	private SnService snService;
	
	private SnDto snDto;
	
	private List<Sn> result;
	
	@Init
	public void init() {
		snDto = new SnDto();
//		snDto.setStatus(SnStatus.WAIT_KEYOUT);
		
		onSearch();
	}
	
	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onSearch() {
		result = snService.querySnsBySnDto(snDto, pagingDto);
	}

	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onConfirm(@BindingParam("resultListbox")Listbox resultListbox) {
		Set<Listitem> selected = resultListbox.getSelectedItems();
		if (selected.size() == 0) {
			showWarningBox("请选择确认通过的任务");
			return;
		}
		
		
		if (!showQuestionBox("确认通过所选任务?")) {
			return;
		}
		
		List<Sn> selectedSns = new ArrayList<Sn>(selected.size());
		for (Listitem listitem : selected) {
			selectedSns.add((Sn) listitem.getValue());
		}
		
		snService.doKeyout(selectedSns);
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
