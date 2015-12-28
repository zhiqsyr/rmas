package com.dl.rmas.web.vm.order;

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
import com.dl.rmas.common.enums.ReceiveStatus;
import com.dl.rmas.entity.Order;
import com.dl.rmas.service.OrderService;
import com.dl.rmas.web.vm.system.BaseVM;

public class OrderEditVM extends BaseVM {
	
	public static final String URL_ORDER_EDIT = "/zul/order/order_edit.zul";
	
	@WireVariable
	private OrderService orderService;
	
	private Order receiveOrder;
	
	@Wire
	private Window editWin;
	
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		
		receiveOrder = getArgValue(Order.class, OrderRmaCreateVM.KEY_ORDER);
	}
	
	@Command
	public void onSubmit() {
		receiveOrder.setReceiveStatus(ReceiveStatus.RECEIVED);
		
		orderService.doModifyOrder(receiveOrder);
		showInformationBox(Constants.OPERATION_COMPLETED);
		editWin.detach();
	}

	public Order getReceiveOrder() {
		return receiveOrder;
	}

	public void setReceiveOrder(Order receiveOrder) {
		this.receiveOrder = receiveOrder;
	}
	
}
