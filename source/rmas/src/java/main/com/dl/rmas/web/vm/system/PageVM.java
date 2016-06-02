package com.dl.rmas.web.vm.system;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

import com.dl.rmas.web.zkmodel.PagingDto;
import com.dl.rmas.web.zkmodel.Sorter;

/**
 * 分页
 * 
 * @author dongbz 2014-12-23
 */
public abstract class PageVM extends BaseVM {

	protected PagingDto pagingDto = new PagingDto();
	
	public abstract void onSearch();
	
	/**
	 * 排序
	 * 
	 * @param event
	 */
	@Command
	@NotifyChange({"result", "pagingDto"})
	public void onSort(@BindingParam("prop")String propertyName, @BindingParam("ascending")boolean ascending) {
		Sorter sorter = new Sorter(propertyName, ascending);
		pagingDto.setSorters(pagingDto.addSorterToSorters(pagingDto.getSorters(), sorter));

		onSearch();
	}
	
	public PagingDto getPagingDto() {
		return pagingDto;
	}

	public void setPagingDto(PagingDto pagingDto) {
		this.pagingDto = pagingDto;
	}
	
}
