package com.dl.rmas.web.vm.system;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import com.dl.rmas.common.cache.Constants;
import com.dl.rmas.common.enums.Validity;
import com.dl.rmas.entity.Bom;
import com.dl.rmas.service.BomService;

/**
 * BOM LIST展示页面
 * 
 * @author dongbz
 *
 * 2015-3-1
 */
public class BomListViewVM extends PageVM {

	public static final String URL_BOM_LIST_VIEW = "/zul/system/bom_list_view.zul";

	@WireVariable
	private BomService bomService;
	
	private Bom query = new Bom();
	private List<Bom> result;
	
	@Init
	public void init() {
		pagingDto.setPageSize(10);
		
		query.setProductId(getArgValue(Integer.class, ProductQueryVM.KEY_PRODUCT_ID));
		query.setValidity(Validity.VALID);
		onSearch();
	}
	
	@Override
	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onSearch() {
		result = bomService.queryBomsByQueryDto(query, pagingDto);
	}

	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onRemove(@BindingParam("bom")Bom bom) {
		if (!showQuestionBox(Constants.CONFIRM_TO_DELETE)) {
			return;
		}
		
		bom.setValidity(Validity.INVALID);
		bomService.doUpdate(bom);
		
		showInformationBox(Constants.OPERATION_COMPLETED);
		onSearch();
	}
	
	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onDeleteAll() {
		if (!showQuestionBox(Constants.CONFIRM_TO_DELETE)) {
			return;
		}
		
		bomService.doDeleteAllByProductId(query.getProductId());
		
		showInformationBox(Constants.OPERATION_COMPLETED);
		onSearch();
	}
	
	public Bom getQuery() {
		return query;
	}

	public void setQuery(Bom query) {
		this.query = query;
	}

	public List<Bom> getResult() {
		return result;
	}
	
}
