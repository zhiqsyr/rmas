package com.dl.rmas.entity;

import java.io.Serializable;

import com.dl.rmas.common.cache.Idable;

@SuppressWarnings("serial")
public abstract class BaseEntity implements Serializable, Idable {

    @Override
    public boolean equals(Object obj) {
		if (obj == null) {
	 	    return false;
	 	}

		if (this == obj) {
		    return true;
		}
	
	 	if (!(getClass().equals(obj.getClass()))) {
	 	    return false;
	 	}
	 	BaseEntity other = (BaseEntity) obj;
	 	Serializable id = getId();
	 	Serializable otherId = other.getId();
	 	if (id == null && otherId == null) {
	 	    return true;
	 	}
	 	if (id == null || otherId == null) {
	 	    return false;
	 	}
	 	return id.equals(otherId);
    }

    @Override
    public int hashCode() {
	 	int hasCode = 23;
	 	Serializable id = getId();
	 	hasCode = hasCode + (id == null ? 0 : id.hashCode());
	 	return hasCode;
    }

    @Override
    public String toString() {
    	return getClass() + "[id=" + getId() + "]";
    }
}