package com.dl.rmas.web.vm.statis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import com.dl.rmas.common.enums.SnStatus;
import com.dl.rmas.dto.SnDto;
import com.dl.rmas.entity.Order;
import com.dl.rmas.entity.Sn;
import com.dl.rmas.service.LabelValueBeanService;
import com.dl.rmas.service.OrderService;
import com.dl.rmas.service.SnService;
import com.dl.rmas.web.vm.system.PageVM;
import com.dl.rmas.web.zkmodel.LabelValueBean;

/**
 * 维修任务跟踪
 * 
 * @author dongbz 2015-1-30
 */
public class SnTrackVM extends PageVM {

	public static final String URL_SN_TRACK = "/zul/statis/sn_track.zul";
	public static final String KEY_SN_ID = "snId";
	
	@WireVariable
	private OrderService orderService;
	@WireVariable
	private LabelValueBeanService labelValueBeanService;
	@WireVariable
	private SnService snService;
	
	private List<LabelValueBean<SnStatus>> snStatusLabelValueBeans;

	private Order order;
	private SnDto query;
	private List<Sn> result;
	
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		
		snStatusLabelValueBeans = labelValueBeanService.buildByEnumClassWithSelect(SnStatus.class);
		
		Integer orderId = getArgValue(Integer.class, OrderTrackVM.KEY_ORDER_ID);
		order = orderService.queryById(Order.class, orderId);
		query = new SnDto();
		query.setOrderId(orderId);
		pagingDto.setPageSize(20);
		
		onSearch();
	}
	
	@Override
	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onSearch() {
		result = snService.querySnsBySnDto(query, pagingDto);
	}
	
	@Command
	public void onView(@BindingParam("snId")Integer snId) {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put(KEY_SN_ID, snId);
		showModal(SnProduceViewVM.URL_SN_PRODUCE_VIEW, args);
	}

	public List<LabelValueBean<SnStatus>> getSnStatusLabelValueBeans() {
		return snStatusLabelValueBeans;
	}

	public SnDto getQuery() {
		return query;
	}

	public void setQuery(SnDto query) {
		this.query = query;
	}

	public Order getOrder() {
		return order;
	}

	public List<Sn> getResult() {
		return result;
	}

}
