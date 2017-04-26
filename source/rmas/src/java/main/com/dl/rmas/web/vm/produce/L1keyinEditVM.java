package com.dl.rmas.web.vm.produce;

import java.util.ArrayList;
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
import com.dl.rmas.common.utils.PropertiesUtils;
import com.dl.rmas.dto.RepairingDto;
import com.dl.rmas.entity.Sn;
import com.dl.rmas.entity.User;
import com.dl.rmas.service.SnService;
import com.dl.rmas.service.UserService;
import com.dl.rmas.web.vm.system.BaseVM;

public class L1keyinEditVM extends BaseVM {

	public static final String URL_L1KEYIN_EDIT = "/zul/produce/l1keyin_edit.zul";
	
	@WireVariable
	private SnService snService;
	@WireVariable
	private UserService userService;
	
	private List<User> users;
	private List<User> _4Slts;
	private List<Sn> sns;
	private Integer selectedUserId;
	private List<RepairingDto> repairingDtos;
	private Boolean isMidh;
	
	@Wire
	private Window editWin;
	
	@SuppressWarnings("unchecked")
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		
		users = userService.queryAllSortedUsers();
		_4Slts = users;
		
		sns = getArgValue(List.class, L1keyinQueryVM.KEY_SNS);
		repairingDtos = snService.queryUserRepairingCount();
		isMidh = getArgValue(Boolean.class, MidhQueryVM.KEY_MIDH);
	}
	
	/**
	 * <b>Function: <b>展示匹配结果
	 *
	 */
	@Command
	@NotifyChange("_4Slts")
	public void onListUsers(@BindingParam("_4Pre")String _4Pre) {
		_4Slts = new ArrayList<User>();
		
		for (User vo : users) {
			if (vo.getUserName().toUpperCase().contains(_4Pre.toUpperCase())) {
				_4Slts.add(vo);
			}
		}
	}
	
	@Command
	@NotifyChange({"selectedUserId"})
	public void onSlt(@BindingParam("sltLb")Listbox sltLb, @BindingParam("bb")Bandbox bb) {
		if (sltLb.getSelectedItem() == null) {
			selectedUserId = null;
		} else {
			selectedUserId = ((User) sltLb.getSelectedItem().getValue()).getUserId();
		}
		
		bb.close();
	}		
	
	@Command
	public void onSubmit() {
		if (!showQuestionBox(PropertiesUtils.getValueInSystem("l1keyin.confirm"))) {
			return;
		}
		
		snService.doAssign(sns, selectedUserId, isMidh);
		
		showInformationBox(Constants.OPERATION_COMPLETED);
		editWin.detach();
	}

	public List<User> get_4Slts() {
		return _4Slts;
	}

	public Integer getSelectedUserId() {
		return selectedUserId;
	}

	public void setSelectedUserId(Integer selectedUserId) {
		this.selectedUserId = selectedUserId;
	}

	public List<RepairingDto> getRepairingDtos() {
		return repairingDtos;
	}
	
}
