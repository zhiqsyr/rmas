package com.dl.rmas.web.vm.produce;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
import com.dl.rmas.common.enums.FinalResult;
import com.dl.rmas.common.enums.IF;
import com.dl.rmas.entity.Bom;
import com.dl.rmas.entity.DictCode;
import com.dl.rmas.entity.Product;
import com.dl.rmas.entity.Sn;
import com.dl.rmas.entity.SnRepairMaterial;
import com.dl.rmas.service.BaseService;
import com.dl.rmas.service.BomService;
import com.dl.rmas.service.SnRepairMaterialService;
import com.dl.rmas.service.SnService;
import com.dl.rmas.web.vm.system.BaseVM;

public class QcResultVM extends BaseVM {

	public static final String URL_QC_RESULT = "/zul/produce/qc_result.zul";
	public static final String KEY_QC_RESULT = "qcResult";
	
	@WireVariable
	private SnService snService;
	@WireVariable
	private SnRepairMaterialService snRepairMaterialService;
	@WireVariable
	private BomService bomService;
	@WireVariable
	private BaseService baseService;
	
	private List<Sn> sns;
	private Integer snId;
	private FinalResult qcResult;
	private String qcRemark;
	private String macImei1N;
	private SnRepairMaterial editMaterial;
	private Bom selectedBom;
	private List<Bom> boms;
	private List<Bom> _4Slts;
//	private List<LabelValueBean<Bom>> materialNameLableValueBeans;
	private List<SnRepairMaterial> result;
	
	@Wire
	private Window editWin;
	
	@SuppressWarnings("unchecked")
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		
		sns = getArgValue(List.class, L1keyinQueryVM.KEY_SNS);
		snId = sns.get(0).getSnId();
		macImei1N = sns.get(0).getMacImei1N();
		qcResult = getArgValue(FinalResult.class, KEY_QC_RESULT);
		
//		materialNameLableValueBeans = labelValueBeanService.buildBomMaterialNameWithSelect(sns.get(0).getProductId());
		boms = bomService.queryByProductId(sns.get(0).getProductId());
		_4Slts = boms;
		editMaterial = new SnRepairMaterial();
		result = snRepairMaterialService.queryBySnId(snId);
	}

	/**
	 * <b>Function: <b>展示匹配结果
	 *
	 */
	@Command
	@NotifyChange("_4Slts")
	public void onListBoms(@BindingParam("_4Pre")String _4Pre) {
		_4Slts = new ArrayList<Bom>();
		
		for (Bom vo : boms) {
			if (vo.getMaterialName().toUpperCase().contains(_4Pre.toUpperCase())) {
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
		// QC OK 时，校验 New Imei1
		if (FinalResult.OK.equals(qcResult)) {
			// 判定 New Imei1是否必填，必填的话校验非空
			Product product = baseService.queryById(Product.class, sns.get(0).getProductId());
			DictCode dictCode = baseService.queryById(DictCode.class, product.getProductType());
			if (dictCode.getCodeOrder() != null && dictCode.getCodeOrder() == 1) {	// productType对应codeOrder=1时，必填New Imei1
				if (StringUtils.isBlank(macImei1N)) {
					showWarningBox("New IMEI1 should not be empty.");
					return;
				}
			}			
		}
		
		IF materialUsed = result.size() > 0 ? IF.YES : IF.NO;
		if (FinalResult.NG.equals(qcResult)) {
			for (SnRepairMaterial material : result) {
				snRepairMaterialService.doDelete(material);
			}
			materialUsed = IF.NO;
		}
		 
		snService.doQc(sns, qcResult, materialUsed, macImei1N, qcRemark);
		
		showInformationBox(Constants.OPERATION_COMPLETED);
		editWin.detach();
	}
	
	public FinalResult getQcResult() {
		return qcResult;
	}

	public String getQcRemark() {
		return qcRemark;
	}

	public void setQcRemark(String qcRemark) {
		this.qcRemark = qcRemark;
	}

	public String getMacImei1N() {
		return macImei1N;
	}

	public void setMacImei1N(String macImei1N) {
		this.macImei1N = macImei1N;
	}

	public Bom getSelectedBom() {
		return selectedBom;
	}

	public void setSelectedBom(Bom selectedBom) {
		this.selectedBom = selectedBom;
	}

	public SnRepairMaterial getEditMaterial() {
		return editMaterial;
	}

	public void setEditMaterial(SnRepairMaterial editMaterial) {
		this.editMaterial = editMaterial;
	}

	public List<SnRepairMaterial> getResult() {
		return result;
	}

	public List<Bom> get_4Slts() {
		return _4Slts;
	}

//	public List<LabelValueBean<Bom>> getMaterialNameLableValueBeans() {
//		return materialNameLableValueBeans;
//	}
	
}
