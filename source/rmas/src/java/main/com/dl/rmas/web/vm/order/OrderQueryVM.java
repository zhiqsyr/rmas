package com.dl.rmas.web.vm.order;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Filedownload;

import com.dl.rmas.common.enums.ReceiveStatus;
import com.dl.rmas.common.enums.Validity;
import com.dl.rmas.entity.Order;
import com.dl.rmas.service.LabelValueBeanService;
import com.dl.rmas.service.OrderService;
import com.dl.rmas.web.vm.system.PageVM;
import com.dl.rmas.web.zkmodel.LabelValueBean;

public class OrderQueryVM extends PageVM {

	public static final String URL_ORDER_QUERY = "/zul/order/order_query.zul";
	
	@WireVariable
	private LabelValueBeanService labelValueBeanService;
	@WireVariable
	private OrderService orderService;
	
	private List<LabelValueBean<Integer>> customerLabelValueBeans;
	private List<LabelValueBean<ReceiveStatus>> receiveStatusLabelValueBeans;
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
	public void onSearch() {
		result = orderService.queryOrdersByQueryDto(queryOrder, pagingDto);
	}
	
	@Command
	public void onExport() throws Exception {
		orderService.doExportReceive(queryOrder);
	}
	
	@Command
	@NotifyChange("result")
	public void onShowEdit() {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put(OrderRmaCreateVM.KEY_ORDER, selected);
		
		showModal(OrderEditVM.URL_ORDER_EDIT, args);
	}
	
	@Command
	public void onDownload(@BindingParam("filePath")String filePath) throws FileNotFoundException {
		Filedownload.save(new File(filePath), "application/x-download");
	}
	
	public List<LabelValueBean<Integer>> getCustomerLabelValueBeans() {
		return customerLabelValueBeans;
	}

	public List<LabelValueBean<ReceiveStatus>> getReceiveStatusLabelValueBeans() {
		return receiveStatusLabelValueBeans;
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
	
}
