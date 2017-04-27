package com.dl.rmas.web.vm.produce;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import com.dl.rmas.common.cache.Constants;
import com.dl.rmas.common.enums.IF;
import com.dl.rmas.common.enums.RepairResult;
import com.dl.rmas.entity.Bom;
import com.dl.rmas.entity.DictCode;
import com.dl.rmas.entity.Sn;
import com.dl.rmas.entity.SnRepairMaterial;
import com.dl.rmas.service.BomService;
import com.dl.rmas.service.DictTypeCodeService;
import com.dl.rmas.service.LabelValueBeanService;
import com.dl.rmas.service.SnRepairMaterialService;
import com.dl.rmas.service.SnService;
import com.dl.rmas.web.vm.system.BaseVM;
import com.dl.rmas.web.zkmodel.LabelValueBean;

public class RepairResultVM extends BaseVM {

	public static final String URL_REPAIR_RESULT = "/zul/produce/repair_result.zul";
	public static final String KEY_REPAIR_RESULT = "repairResult";
	
	@WireVariable
	private LabelValueBeanService labelValueBeanService;
	@WireVariable
	private SnService snService;
	@WireVariable
	private DictTypeCodeService dictTypeCodeService;
	@WireVariable
	private SnRepairMaterialService snRepairMaterialService;
	@WireVariable
	private BomService bomService;
	
	private List<LabelValueBean<Integer>> maintainCodeLabelValueBeans;
	private List<Sn> sns;
	private Integer snId;
	private RepairResult repairResult;
	private Integer selectedCodeId;
	private boolean codeDescYes = false;
	private String repairRemark;
	private SnRepairMaterial editMaterial;
	private Bom selectedBom;
	private List<Bom> boms;
	private List<Bom> _4Slts = new LinkedList<Bom>();
	private List<SnRepairMaterial> result;
	
	@Wire
	private Window editWin;
	
	@SuppressWarnings("unchecked")
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		maintainCodeLabelValueBeans = labelValueBeanService.buildByDictTypeIdWithSelect(Constants.TYPE_ID_MAINTAIN_CODE);
		
		sns = getArgValue(List.class, L1keyinQueryVM.KEY_SNS);
		snId = sns.get(0).getSnId();
		repairResult = getArgValue(RepairResult.class, KEY_REPAIR_RESULT);
		
		
		boms = bomService.queryByProductId(sns.get(0).getProductId());
		_4Slts = boms;
		editMaterial = new SnRepairMaterial();
		result = snRepairMaterialService.queryBySnId(snId);
	}
	
	@Command
	@NotifyChange("codeDescYes")
	public void onDecide() {
		if (selectedCodeId != null) {
			DictCode code = dictTypeCodeService.queryById(DictCode.class, selectedCodeId);
			if (IF.YES.name().equals(code.getCodeDesc())) {
				codeDescYes = true;
			} else {
				codeDescYes = false;
			}
		}  else {
			codeDescYes = false;
		}
	}
	
	/**
	 * <b>Function: <b>展示匹配结果
	 *
	 */
	@Command
	@NotifyChange("_4Slts")
	public void onListBoms(@BindingParam("_4Pre")String _4Pre, @BindingParam("type")String type) {
		_4Slts = new ArrayList<Bom>();
		
		for (Bom vo : boms) {
			if ("NAME".equals(type) && vo.getMaterialName().toUpperCase().contains(_4Pre.toUpperCase())) {
				_4Slts.add(vo);
			} else if ("INO".equals(type) && vo.getIno() != null && vo.getIno().toUpperCase().contains(_4Pre.toUpperCase())) {
				_4Slts.add(vo);
			}
		}
	}
	
	@Command
	@NotifyChange({"selectedBom"})
	public void onSlt(@BindingParam("sltLb")Listbox sltLb, @BindingParam("bb")Bandbox bb) {
		if (sltLb.getSelectedItem() == null) {
			selectedBom = null;
		} else {
			selectedBom = (Bom) sltLb.getSelectedItem().getValue();
		}
		
		bb.close();
	}
	
	@Command
	@NotifyChange({"result", "editMaterial", "selectedBom"})
	public void onEditMaterial() {
		if (selectedBom == null) {
			showWarningBox("Please select a bom.");
			return;
		}

		editMaterial.setSnId(snId);
		editMaterial.setBomId(selectedBom.getBomId());
		snRepairMaterialService.doCreateOrModify(editMaterial);
		
		showInformationBox(Constants.OPERATION_COMPLETED);
		result = snRepairMaterialService.queryBySnId(snId);
		editMaterial = new SnRepairMaterial();
		selectedBom = null;
	}
	
	@Command
	@NotifyChange({"editMaterial", "selectedBom"})
	public void onSelectMaterial(@BindingParam("selectedMaterial")SnRepairMaterial selectedMaterial) {
		editMaterial = selectedMaterial;
		selectedBom = bomService.queryById(Bom.class, editMaterial.getBomId());
	}
	
	@Command
	@NotifyChange({"result", "editMaterial", "selectedBom"})
	public void onDeleteMaterial(@BindingParam("selectedMaterial")SnRepairMaterial selectedMaterial) {
		if (!showQuestionBox(Constants.CONFIRM_TO_DELETE)) {
			return;
		}
		
		snRepairMaterialService.doDelete(selectedMaterial);
		
		showInformationBox(Constants.OPERATION_COMPLETED);
		result = snRepairMaterialService.queryBySnId(snId);
		editMaterial = new SnRepairMaterial();
		selectedBom = null;
	}
	
	@Command
	public void onSubmit() {
		IF materialUsed = result.size() > 0 ? IF.YES : IF.NO;
		if (IF.NO.equals(codeDescYes)) {
			for (SnRepairMaterial deleted : result) {
				snRepairMaterialService.doDelete(deleted);
			}
			materialUsed = IF.NO;
		}
		
		snService.doRepair(sns, repairResult, selectedCodeId, materialUsed, repairRemark);
		
		showInformationBox(Constants.OPERATION_COMPLETED);
		editWin.detach();
	}
	
	public List<LabelValueBean<Integer>> getMaintainCodeLabelValueBeans() {
		return maintainCodeLabelValueBeans;
	}

	public RepairResult getRepairResult() {
		return repairResult;
	}

	public Integer getSelectedCodeId() {
		return selectedCodeId;
	}

	public void setSelectedCodeId(Integer selectedCodeId) {
		this.selectedCodeId = selectedCodeId;
	}

	public boolean getCodeDescYes() {
		return codeDescYes;
	}

	public String getRepairRemark() {
		return repairRemark;
	}

	public void setRepairRemark(String repairRemark) {
		this.repairRemark = repairRemark;
	}

	public SnRepairMaterial getEditMaterial() {
		return editMaterial;
	}

	public void setEditMaterial(SnRepairMaterial editMaterial) {
		this.editMaterial = editMaterial;
	}

	public Bom getSelectedBom() {
		return selectedBom;
	}

	public void setSelectedBom(Bom selectedBom) {
		this.selectedBom = selectedBom;
	}

	public List<Bom> get_4Slts() {
		return _4Slts;
	}

	public List<SnRepairMaterial> getResult() {
		return result;
	}

}
