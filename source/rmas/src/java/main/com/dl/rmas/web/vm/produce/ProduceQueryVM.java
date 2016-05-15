package com.dl.rmas.web.vm.produce;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import com.dl.rmas.common.cache.Constants;
import com.dl.rmas.common.enums.FinalResult;
import com.dl.rmas.common.enums.KeyinStatus;
import com.dl.rmas.common.enums.SnStatus;
import com.dl.rmas.common.utils.PropertiesUtils;
import com.dl.rmas.dto.SnDto;
import com.dl.rmas.entity.Order.RmaDoStatus;
import com.dl.rmas.entity.Sn;
import com.dl.rmas.entity.Sn.KeepStatus;
import com.dl.rmas.service.LabelValueBeanService;
import com.dl.rmas.service.SnService;
import com.dl.rmas.web.vm.system.PageVM;
import com.dl.rmas.web.zkmodel.LabelValueBean;

/**
 * 综合查询
 * 
 * @author dongbz
 * 2015-1-20
 */
public class ProduceQueryVM extends PageVM {

	@WireVariable
	private LabelValueBeanService labelValueBeanService;
	@WireVariable
	private SnService snService;
	
	private SnDto snDto = new SnDto();
	
	private List<LabelValueBean<Integer>> tatLevelLabelValueBeans;
	private List<LabelValueBean<KeyinStatus>> typeStatusLabelValueBeans;
	private List<LabelValueBean<KeepStatus>> keepStatusLabelValueBeans;	
	private List<LabelValueBean<SnStatus>> snStatusLabelValueBeans;
	private List<LabelValueBean<FinalResult>> finalResultLabelValueBeans;
	private List<LabelValueBean<RmaDoStatus>> rmaDoStatusLabelValueBeans;
	
	private List<Sn> result;
	
	@Init
	public void init() {
		tatLevelLabelValueBeans = labelValueBeanService.buildByDictTypeIdWithSelect(Constants.TYPE_ID_TAT_LEVEL);
		typeStatusLabelValueBeans = labelValueBeanService.buildByEnumClassWithSelect(KeyinStatus.class);
		keepStatusLabelValueBeans = labelValueBeanService.buildByEnumClassWithSelect(KeepStatus.class);
		snStatusLabelValueBeans = labelValueBeanService.buildByEnumClassWithSelect(SnStatus.class);
		finalResultLabelValueBeans = labelValueBeanService.buildByEnumClassWithSelect(FinalResult.class);
		rmaDoStatusLabelValueBeans = labelValueBeanService.buildByEnumClassWithSelect(RmaDoStatus.class);
		
		snDto.setReceiveDateFrom(DateUtils.addMonths(new Date(), -3));
		pagingDto.setPageSize(15);
		
		onSearch();
	}
	
	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onSearch() {
		result = snService.querySnsBySnDto(snDto, pagingDto);
	}

	/**
	 * 导出查询结果到Excel
	 * @throws Exception 
	 */
	@Command
	public void onExport() throws Exception {
		if (!showQuestionBox(PropertiesUtils.getValueInSystem("produce.query.export.quesiton"))) {
			return;
		}
		
		// 导出
		snService.doExportExcel(snDto);
	}
	
	/**
	 * 导出查询结果到Excel Part Name
	 * @throws Exception 
	 */
	@Command
	public void onExportPartName() throws Exception {
		if (!showQuestionBox(PropertiesUtils.getValueInSystem("produce.query.export.quesiton"))) {
			return;
		}
		
		// 导出
		snService.doExportExcelLine(snDto);
	}
	
	public SnDto getSnDto() {
		return snDto;
	}

	public void setSnDto(SnDto snDto) {
		this.snDto = snDto;
	}

	public List<LabelValueBean<Integer>> getTatLevelLabelValueBeans() {
		return tatLevelLabelValueBeans;
	}

	public List<LabelValueBean<KeyinStatus>> getTypeStatusLabelValueBeans() {
		return typeStatusLabelValueBeans;
	}

	public List<LabelValueBean<KeepStatus>> getKeepStatusLabelValueBeans() {
		return keepStatusLabelValueBeans;
	}

	public List<LabelValueBean<SnStatus>> getSnStatusLabelValueBeans() {
		return snStatusLabelValueBeans;
	}

	public List<LabelValueBean<FinalResult>> getFinalResultLabelValueBeans() {
		return finalResultLabelValueBeans;
	}

	public List<LabelValueBean<RmaDoStatus>> getRmaDoStatusLabelValueBeans() {
		return rmaDoStatusLabelValueBeans;
	}

	public List<Sn> getResult() {
		return result;
	}

}
