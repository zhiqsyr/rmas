package com.dl.rmas.web.vm.produce;

import java.util.ArrayList;
import java.util.List;

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
import com.dl.rmas.common.enums.FinalResult;
import com.dl.rmas.common.utils.PropertiesUtils;
import com.dl.rmas.entity.Sn;
import com.dl.rmas.service.SnService;
import com.dl.rmas.web.vm.system.BaseVM;
import com.dl.rmas.web.zkmodel.LabelValueBean;

public class L1keyinFinalVM extends BaseVM {

	public static final String URL_L1KEYIN_FINAL = "/zul/produce/l1keyin_final.zul";
	
	@WireVariable
	private SnService snService;
	
	private List<LabelValueBean<FinalResult>> finalResultLabelValueBeans;
	private List<Sn> sns;
	private Sn selected;
	
	@Wire
	private Window editWin;
	
	@SuppressWarnings("unchecked")
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		
		finalResultLabelValueBeans = new ArrayList<LabelValueBean<FinalResult>>();
		finalResultLabelValueBeans.add(new LabelValueBean<FinalResult>(Constants.WITH_SELECT, null));
		finalResultLabelValueBeans.add(new LabelValueBean<FinalResult>(FinalResult.NG.name(), FinalResult.NG));
		finalResultLabelValueBeans.add(new LabelValueBean<FinalResult>(FinalResult.CANCEL.name(), FinalResult.CANCEL));
		finalResultLabelValueBeans.add(new LabelValueBean<FinalResult>(FinalResult.CID.name(), FinalResult.CID));
		
		sns = getArgValue(List.class, L1keyinQueryVM.KEY_SNS);
		selected = new Sn();
	}
	
	@Command
	public void onSubmit() {
		if (!showQuestionBox(PropertiesUtils.getValueInSystem("l1keyin.final.confirm"))) {
			return;
		}
		
		snService.doFinal(sns, selected.getFinalResult(), selected.getStopReason());
		
		showInformationBox(Constants.OPERATION_COMPLETED);
		editWin.detach();
	}

	public List<LabelValueBean<FinalResult>> getFinalResultLabelValueBeans() {
		return finalResultLabelValueBeans;
	}

	public Sn getSelected() {
		return selected;
	}

	public void setSelected(Sn selected) {
		this.selected = selected;
	}

}
