package com.dl.rmas.web.vm.statis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import com.dl.rmas.dto.OrderTrackDto;
import com.dl.rmas.entity.Order;
import com.dl.rmas.entity.Order.RmaDoStatus;
import com.dl.rmas.service.LabelValueBeanService;
import com.dl.rmas.service.OrderService;
import com.dl.rmas.web.vm.system.PageVM;
import com.dl.rmas.web.zkmodel.LabelValueBean;

/**
 * 订单跟踪
 * 
 * @author dongbz 2015-1-28
 */
public class OrderTrackVM extends PageVM {

	public static final String KEY_ORDER_ID = "orderId";
	
	@WireVariable
	private LabelValueBeanService labelValueBeanService;
	@WireVariable
	private OrderService orderService;
	
	private List<LabelValueBean<Integer>> customerLabelValueBeans;
	private List<LabelValueBean<RmaDoStatus>> rmaDoStatusLabelValueBeans;
	
	private Order query;
	private List<OrderTrackDto> result;
	private OrderTrackDto selected;
	
	@Init
	public void init() {
		customerLabelValueBeans = labelValueBeanService.buildValidCustomersWithSelect();
		rmaDoStatusLabelValueBeans = labelValueBeanService.buildByEnumClassWithSelect(RmaDoStatus.class);
		
		query = new Order();
		query.setCloseTimeFrom(DateUtils.addMonths(new Date(), -3));
		pagingDto.setPageSize(20);
	
		onSearch();
	}
	
	@Override
	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onSearch() {
		result = orderService.queryTrackByQueryDto(query, pagingDto);
	}

	@Command
	public void onExport() throws Exception {
		orderService.doExportTrack(query);
	}
	
	@Command
	public void onView(@BindingParam("orderId")Integer orderId) {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put(KEY_ORDER_ID, orderId);
		showModal(SnTrackVM.URL_SN_TRACK, args);
	}
	
	public List<LabelValueBean<Integer>> getCustomerLabelValueBeans() {
		return customerLabelValueBeans;
	}

	public List<LabelValueBean<RmaDoStatus>> getRmaDoStatusLabelValueBeans() {
		return rmaDoStatusLabelValueBeans;
	}

	public Order getQuery() {
		return query;
	}

	public void setQuery(Order query) {
		this.query = query;
	}

	public List<OrderTrackDto> getResult() {
		return result;
	}

	public OrderTrackDto getSelected() {
		return selected;
	}

	public void setSelected(OrderTrackDto selected) {
		this.selected = selected;
	}

}
