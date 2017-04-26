package com.dl.core.jxls.validation.converter;

/**
 * 数据转换失败异常
 * 
 * @author dylan
 * @date 2012-10-9 下午2:13:49
 */
public class DataConvertException extends RuntimeException {
	private static final long serialVersionUID = -7564751435376939734L;

	public DataConvertException() {
	}

	public DataConvertException(String message) {
		super(message);
	}
}
