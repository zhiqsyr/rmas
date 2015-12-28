package com.dl.rmas.web.vm.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import com.dl.rmas.common.cache.Constants;
import com.dl.rmas.common.enums.ReceiveStatus;
import com.dl.rmas.common.enums.Validity;
import com.dl.rmas.common.utils.PropertiesUtils;
import com.dl.rmas.entity.Order;
import com.dl.rmas.service.LabelValueBeanService;
import com.dl.rmas.service.OrderService;
import com.dl.rmas.service.UserRoleService;
import com.dl.rmas.web.vm.system.PageVM;
import com.dl.rmas.web.zkmodel.LabelValueBean;

public class OrderRmaCreateVM extends PageVM {

	public static final String KEY_ORDER = "order";
	
	@WireVariable
	private LabelValueBeanService labelValueBeanService;
	@WireVariable
	private OrderService orderService;
	@WireVariable
	private UserRoleService userRoleService;
	
	private List<LabelValueBean<Integer>> customerLabelValueBeans;
	private List<LabelValueBean<ReceiveStatus>> receiveStatusLabelValueBeans;
	private Order order = new Order();
	private Order queryOrder = new Order(Validity.VALID);
	private List<Order> result;
	private Order selected;
	
	@Init
	public void init() {
		customerLabelValueBeans = labelValueBeanService.buildValidCustomersWithSelect();
		receiveStatusLabelValueBeans = labelValueBeanService.buildByEnumClassWithSelect(ReceiveStatus.class);
		
		queryOrder.setReceiveStatus(ReceiveStatus.WAIT_RECEIVE);
		onSearch();
	}
	
	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onSubmit() {
		if (!showQuestionBox(PropertiesUtils.getValueInSystem("order.rma.create.onsubmit.question"))) {
			return;
		}
		
		orderService.doCreateOrder(order);
		
		showInformationBox(PropertiesUtils.getValueInSystem("order.rma.create.onsubmit.success"));
		onSearch();
	}
	
	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onSearch() {
		result = orderService.queryOrdersByQueryDto(queryOrder, pagingDto);
	}
	
	@Command
	@NotifyChange("result")
	public void onShowEdit() {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put(OrderRmaCreateVM.KEY_ORDER, selected);
		
		showModal(OrderEditVM.URL_ORDER_EDIT, args);
	}
	
	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onDelete() {
		if (selected.getTotalFinished() != null && selected.getTotalFinished() > 0) {
			showWarningBox("There are SNs in selected RMA. Can not Delete it");
			return;
		}
		
		if (!showQuestionBox(Constants.CONFIRM_TO_DELETE)) {
			return;
		}
		
		orderService.doDelete(selected);
		showInformationBox(Constants.OPERATION_COMPLETED);
		onSearch();
	}
	
	public List<LabelValueBean<Integer>> getCustomerLabelValueBeans() {
		return customerLabelValueBeans;
	}

	public List<LabelValueBean<ReceiveStatus>> getReceiveStatusLabelValueBeans() {
		return receiveStatusLabelValueBeans;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Order getQueryOrder() {
		return queryOrder;
	}

	public void setQueryOrder(Order queryOrder) {
		this.queryOrder = queryOrder;
	}

	public List<Order> getResult() {
		return result;
	}

	public Order getSelected() {
		return selected;
	}

	public void setSelected(Order selected) {
		this.selected = selected;
	}

	public boolean getIsAdmin() {
		return userRoleService.isUserAdmin(getCurrentUser().getUserId());
	}
	
}
