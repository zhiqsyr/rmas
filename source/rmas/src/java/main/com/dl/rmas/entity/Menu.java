package com.dl.rmas.entity;

// Generated 2014-11-25 9:04:24 by Hibernate Tools 3.4.0.CR1

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.dl.rmas.common.enums.IF;

@Entity
@Table(name = "sm_menu")
@SuppressWarnings("serial")
public class Menu extends ValidityEntity {

	@Id
    @Column(name = "menu_id")
	private Integer menuId;
	
	@Column(name = "menu_name")
	private String menuName;
	
	@Column(name = "menu_url")
	private String menuUrl;
	
	@Column(name = "menu_desc")
	private String menuDesc;
	
	@Column(name = "menu_order")
	private Integer menuOrder;
	
	@Column(name = "leaf")
	@Enumerated(EnumType.STRING)
	private IF leaf;
	
	@Column(name = "parent_id")
	private Integer parentId;

	@Transient
	private boolean isUsed;
	
	public Integer getMenuId() {
		return this.menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return this.menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getMenuDesc() {
		return this.menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	public Integer getMenuOrder() {
		return this.menuOrder;
	}

	public void setMenuOrder(Integer menuOrder) {
		this.menuOrder = menuOrder;
	}

	public IF getLeaf() {
		return leaf;
	}

	public void setLeaf(IF leaf) {
		this.leaf = leaf;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	@Override
	public Serializable getId() {
		return this.menuId;
	}

}
