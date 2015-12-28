package com.dl.core.jxls.export;

import com.dl.core.jxls.export.model.DataContext;
import com.dl.core.jxls.export.model.SheetTemplate;

public interface ExportProcessor<T> {
	/**
	 * @param template
	 * @param context
	 * @param result
	 */
	public void process(SheetTemplate template, DataContext context,
			ResultHolder<T> result);
	
	public void process(SheetTemplate template, DataContext context,
			String key);
	
	public void process(SheetTemplate template, DataContext context,
			String key, int limit);
	
	/**
	 * 数据返回的结果
	 * @author dylan
	 * @date 2013-6-26 下午4:11:27
	 * @param <T>
	 */
	public static class ResultHolder<T> {
		private T result;

		public T getResult() {
			return result;
		}

		public void setResult(T result) {
			this.result = result;
		}

	}
}
