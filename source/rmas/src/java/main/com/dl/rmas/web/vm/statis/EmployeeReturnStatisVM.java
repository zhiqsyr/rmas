package com.dl.rmas.web.vm.statis;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import com.dl.rmas.dto.EmployeeReturnStatis;
import com.dl.rmas.service.SnProduceService;
import com.dl.rmas.web.vm.system.BaseVM;

/**
 * 员工返修情况统计
 * 
 * @author dongbz 2015-1-30
 */
public class EmployeeReturnStatisVM extends BaseVM {

	@WireVariable
	private SnProduceService snProduceService;
	
	private EmployeeReturnStatis query;
	
	private List<EmployeeReturnStatis> result;
	
	@Init
	public void init() {
		query = new EmployeeReturnStatis();
		query.setOperateTimeFrom(DateUtils.addMonths(new Date(), -3));
		
		onSearch();
	}
	
	@Command
	@NotifyChange({"result"})
	public void onSearch() {
		result = snProduceService.queryEmployeeReturnStatis(query);
	}
	
	@Command
	public void onExport() throws Exception {
		snProduceService.doExportReturn(result);
	}

	@Command
	public void onExportDetail() throws Exception {
		snProduceService.doExportReturnDetail(query);
	}

	public EmployeeReturnStatis getQuery() {
		return query;
	}

	public void setQuery(EmployeeReturnStatis query) {
		this.query = query;
	}

	public List<EmployeeReturnStatis> getResult() {
		return result;
	}	
	
}
