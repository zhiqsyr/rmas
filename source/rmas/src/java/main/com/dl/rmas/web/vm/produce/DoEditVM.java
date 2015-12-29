package com.dl.rmas.web.vm.produce;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import com.dl.rmas.common.cache.Constants;
import com.dl.rmas.common.enums.SnStatus;
import com.dl.rmas.common.utils.PropertiesUtils;
import com.dl.rmas.dto.SnDto;
import com.dl.rmas.entity.Sn;
import com.dl.rmas.service.SnService;
import com.dl.rmas.service.UserRoleService;
import com.dl.rmas.web.vm.system.BaseVM;

/**
 * 出货，扫描 SN 添加
 * 
 * @author dongbz 2015-12-24
 */
public class DoEditVM extends BaseVM {

	@WireVariable
	private SnService snService;
	@WireVariable
	private UserRoleService userRoleService;
	
	private SnDto snDto = new SnDto();
	
	private Integer customerId;						// 客户ID
	private List<Sn> result = new ArrayList<Sn>();
	
	private Sn selected;
	
	@Init
	public void init() {
		snDto.setStatus(SnStatus.WAIT_DO);
	}
	
	/**
	 * <b>Function: <b>添加 SN
	 *
	 */
	@Command
	@NotifyChange({"result"})
	public void onAdd() {
		if (StringUtils.isBlank(snDto.getRma())
				&& snDto.getSnIndex() == null
				&& StringUtils.isBlank(snDto.getSn())) {
			showWarningBox("Please input some data");
			return;
		}
		
		// 校验： 1）客户属于同一家
		List<Sn> sns = snService.querySnsBySnDto(snDto, null);
		if (sns.size() == 0) {
			showInformationBox("There's no SN fit");
			return;
		}
		
		Sn vo = sns.get(0);
		if (customerId == null) {								// 首次设置客户ID
			customerId = vo.getOrder().getCustomerId();
		}

		// 客户是否属于同一家
		for (Sn sn : sns) {
			if (customerId != sn.getOrder().getCustomerId()) {	
				showWarningBox("There is SN of other custom");
				return;
			}
		}
		
		// 新加 SN 放在 result 前面
		List<Sn> added = new ArrayList<Sn>(result.size() + sns.size());	
		for (Sn sn : sns) {
			if (result.contains(sn)) {		// 已经加过，不再添加
				continue;
			}
			added.add(sn);
		}
		
		added.addAll(result);
		result = added;
	}
	
	@Command
	@NotifyChange("snDto")
	public void onClear() {
		snDto.setSn(null);
	}
	
	@Command
	@NotifyChange("result")
	public void onRemove(@BindingParam("sn")Sn sn) {
		result.remove(sn);
	}
	
	@Command
	@NotifyChange({"result"})
	public void onCreateRMA() throws Exception {
		if (result.size() == 0) {
			showWarningBox(PropertiesUtils.getValueInSystem("do.query.validate"));
			return;
		}
		
		if (!showQuestionBox(PropertiesUtils.getValueInSystem("do.confirm"))) {
			return;
		}
		
		snService.doDo(result);
		result = new ArrayList<Sn>();
	}
	
	@Command
	@NotifyChange({"result"})
	public void onReturnQC() throws Exception {
		if (selected == null) {
			showWarningBox("Please select the SN for Return to QC");
			return;
		}
		
		if (!showQuestionBox("Confirm to return to QC ?")) {
			return;
		}
		
		List<Sn> selectedSns = new ArrayList<Sn>(1);
		selectedSns.add(selected);
		snService.doReturnQc(selectedSns);
		showInformationBox(Constants.OPERATION_COMPLETED);
		result.remove(selected);
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

	public void setSelected(Sn selected) {
		this.selected = selected;
	}

	public Sn getSelected() {
		return selected;
	}
	
}
