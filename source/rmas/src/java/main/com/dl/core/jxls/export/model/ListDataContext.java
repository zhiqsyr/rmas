package com.dl.core.jxls.export.model;

import java.util.List;
import java.util.ListIterator;

import org.springframework.util.Assert;

public class ListDataContext<E> extends DataContext {
	private List<E> datas;

	private ListIterator<E> iterator;

	public ListDataContext(List<E> datas) {
		Assert.notNull(datas);
		this.datas = datas;
		first();
	}

	public List<E> datas() {
		return this.datas;
	}

	public void first() {
		iterator = datas.listIterator();
	}

	/**
	 * 数据集定位到指定的索引,索引以0为起始
	 * 
	 * @param index
	 */
	public void position(int index) {
		if (index >= datas.size()) {
			iterator = datas.listIterator(datas.size() - 1);
		} else {
			iterator = datas.listIterator(index);
		}
	}

	/**
	 * 记录集往下滚动，如果
	 * 
	 * @return
	 */
	public boolean scrollNext() {
		if (iterator.hasNext()) {
			putData("_index",iterator.nextIndex());
			putRootData(iterator.next());
			return true;
		}
		return false;
	}
}
