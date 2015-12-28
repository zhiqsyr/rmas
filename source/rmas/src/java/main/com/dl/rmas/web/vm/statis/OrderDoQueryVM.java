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

import com.dl.rmas.entity.OrderDo;
import com.dl.rmas.service.OrderDoService;
import com.dl.rmas.web.vm.system.PageVM;

public class OrderDoQueryVM extends PageVM {

	@WireVariable
	private OrderDoService orderDoService;
	
	private OrderDo query;
	
	private List<OrderDo> result;
	
	@Init
	public void init() {
		query = new OrderDo();
		pagingDto.setPageSize(20);
		
		onSearch();
	}
	
	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onSearch() {
		result = orderDoService.queryOrderDosByQueryDto(query, pagingDto);
	}

	@Command
	public void onDownload(@BindingParam("filePath")String filePath) throws FileNotFoundException {
		Filedownload.save(new File(filePath), "application/x-download");
	}
	
	public OrderDo getQuery() {
		return query;
	}

	public void setQuery(OrderDo query) {
		this.query = query;
	}

	public void setResult(List<OrderDo> result) {
		this.result = result;
	}

	public List<OrderDo> getResult() {
		return result;
	}
	
}
