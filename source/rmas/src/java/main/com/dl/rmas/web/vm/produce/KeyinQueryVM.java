package com.dl.rmas.web.vm.produce;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import com.dl.rmas.common.enums.KeyinStatus;
import com.dl.rmas.dto.SnDto;
import com.dl.rmas.entity.Sn;
import com.dl.rmas.service.LabelValueBeanService;
import com.dl.rmas.service.SnService;
import com.dl.rmas.web.vm.system.PageVM;
import com.dl.rmas.web.zkmodel.LabelValueBean;

public class KeyinQueryVM extends PageVM {

	public static final String URL_KEYIN_QUERY = "/zul/produce/keyin_query.zul";
	public static final String KEY_SN = "sn";
	
	@WireVariable
	private LabelValueBeanService labelValueBeanService;
	@WireVariable
	private SnService snService;
	
	private List<LabelValueBean<KeyinStatus>> typeStatusLabelValueBeans;
	private SnDto snDto = new SnDto();
	private List<Sn> result;
	private Sn selected;
	
	@Init
	public void init() {
		typeStatusLabelValueBeans = labelValueBeanService.buildByEnumClassWithSelect(KeyinStatus.class);
		
		snDto.setKeyinStatus(KeyinStatus.OPEN);
		onSearch();
	}
	
	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onSearch() {
		result = snService.querySnsBySnDto(snDto, pagingDto);
	}
	
	@Command
	@NotifyChange({"result", "pagingDto", "selected"})
	public void onShowAdd() {
		showModal(KeyinEditVM.URL_KEYIN_EDIT);
		
		onSearch();
		selected = null;
	}
	
	@Command
	@NotifyChange({"result", "pagingDto", "selected"})
	public void onShowEdit() {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put(KeyinQueryVM.KEY_SN, selected);
		showModal(KeyinEditVM.URL_KEYIN_EDIT, args);
		
		onSearch();
		selected = null;
	}
	
	@Command
	public void onShowPrint() throws Exception {
		snService.doPrint(selected.getSnId());
	}

	public List<LabelValueBean<KeyinStatus>> getTypeStatusLabelValueBeans() {
		return typeStatusLabelValueBeans;
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

	public Sn getSelected() {
		return selected;
	}

	public void setSelected(Sn selected) {
		this.selected = selected;
	}

}
