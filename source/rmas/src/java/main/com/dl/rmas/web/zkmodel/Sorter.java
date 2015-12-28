package com.dl.rmas.web.zkmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Sorter implements Serializable {

	public static final String ASC = "ASC";
    public static final String DESC = "DESC";
    
    private String propertyName;
    
    private boolean ascending;

    public Sorter() {

    }
    
    public Sorter(String propertyName, boolean ascending) {
    	this.propertyName = propertyName;
    	this.ascending = ascending;
    }
    
	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public boolean isAscending() {
		return ascending;
	}

	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}
	
}
