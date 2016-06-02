package com.dl.rmas.web.zkmodel;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页dto
 * 
 * @author dongbz 2014-12-23
 */
public class PagingDto {

	private Integer pageSize = 10;
	private Long totalSize;
	private int activePage;
	
	private List<Sorter> sorters = new ArrayList<Sorter>();
	
	public PagingDto() {

	}
	
	public PagingDto(Long totalSize, int activePage) {
		this.totalSize = totalSize;
		this.activePage = activePage;
	}
	
	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Long getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(Long totalSize) {
		this.totalSize = totalSize;
	}
	public int getActivePage() {
		if (totalSize == null) {
			return activePage;
		}
		
		if (totalSize.intValue() < activePage * pageSize) {	// 可能查询条件变化，导致 totalSize 变化；当小于已查出数量时，查出第一页结果
			activePage = 0;
		}
		
		return activePage;
	}
	public void setActivePage(int activePage) {
		this.activePage = activePage;
	}

	public List<Sorter> getSorters() {
		return sorters;
	}

	public void setSorters(List<Sorter> sorters) {
		this.sorters = sorters;
	}
	
	/**
	 * 向sorters的最前列添加sorter；假如sorters中存在相同的propertyName，删除已有sorter
	 * 
	 * @param sorters
	 * @param sorter
	 * @return
	 */
	public List<Sorter> addSorterToSorters(List<Sorter> sorters, Sorter sorter) {
		for (Sorter _sorter : sorters) {
			if (_sorter.getPropertyName().equals(sorter.getPropertyName())) {
				sorters.remove(_sorter);
				break;
			}
		}
		
		List<Sorter> result = new ArrayList<Sorter>(); 
		result.add(sorter);
		result.addAll(sorters);
		
		return result;
	}
	
}
