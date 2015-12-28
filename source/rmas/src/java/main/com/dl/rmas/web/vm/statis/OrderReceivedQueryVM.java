package com.dl.rmas.web.vm.statis;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Filedownload;

import com.dl.rmas.common.enums.KeyinStatus;
import com.dl.rmas.common.enums.Validity;
import com.dl.rmas.entity.Order;
import com.dl.rmas.service.LabelValueBeanService;
import com.dl.rmas.service.OrderService;
import com.dl.rmas.web.vm.system.PageVM;
import com.dl.rmas.web.zkmodel.LabelValueBean;

public class OrderReceivedQueryVM extends PageVM {

	@WireVariable
	private LabelValueBeanService labelValueBeanService;
	@WireVariable
	private OrderService orderService;
	
	private List<LabelValueBean<Integer>> customerLabelValueBeans;
	private Order queryOrder = new Order(Validity.VALID);
	private List<Order> result;
	
	@Init
	public void init() {
		customerLabelValueBeans = labelValueBeanService.buildValidCustomersWithSelect();
		
		queryOrder.setKeyinStatus(KeyinStatus.CLOSE);
		pagingDto.setPageSize(20);
		onSearch();
	}
	
	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onSearch() {
		result = orderService.queryOrdersByQueryDto(queryOrder, pagingDto);
	}
	
	@Command
	public void onDownload(@BindingParam("filePath")String filePath) throws FileNotFoundException {
		Filedownload.save(new File(filePath), "application/x-download");
	}
	
	public List<LabelValueBean<Integer>> getCustomerLabelValueBeans() {
		return customerLabelValueBeans;
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

}
