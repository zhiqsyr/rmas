package com.dl.rmas.web.vm.statis;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import com.dl.rmas.common.enums.ProduceType;
import com.dl.rmas.dto.EmployeeTrackDto;
import com.dl.rmas.entity.SnProduce;
import com.dl.rmas.service.SnProduceService;
import com.dl.rmas.web.vm.system.PageVM;

/**
 * 员工生产效益统计
 * 
 * @author dongbz 2015-1-30
 */
public class EmployeeProduceStatisVM extends PageVM {

	@WireVariable
	private SnProduceService snProduceService;
	
	private SnProduce query;
	
	private List<EmployeeTrackDto> result;
	
	@Init
	public void init() {
		query = new SnProduce();
		query.setEndTimeFrom(DateUtils.addMonths(new Date(), -3));
		pagingDto.setPageSize(20);
		
		onSearch();
	}
	
	@Override
	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onSearch() {
		result = snProduceService.queryTrackByQueryDto(query, pagingDto);
	}
	
	@Command
	public void onExport() throws Exception {
		snProduceService.doExport(query);
	}
	
	@Command
	public void onExportDetail(@BindingParam("produceType")String produceType) throws Exception {
		query.setProduceType(Enum.valueOf(ProduceType.class, produceType));
		
		snProduceService.doExportDetail(query);
	}
	
	public SnProduce getQuery() {
		return query;
	}

	public void setQuery(SnProduce query) {
		this.query = query;
	}

	public List<EmployeeTrackDto> getResult() {
		return result;
	}

}
