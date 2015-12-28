package com.dl.core.jxls.model;

import java.io.Serializable;

/**
 * 值映射
 * 
 * @author dylan
 * @date 2013-5-13 上午10:54:46
 */
public class ValueMapper implements Serializable {
	private static final long serialVersionUID = -3116735652062826869L;
	private String value;
	private String label;

	public ValueMapper(String value) {
		this(value, value);
	}

	public ValueMapper(String label, String value) {
		this.label = label;
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return label + "=" + value;
	}

}
