package com.dl.rmas.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sm_role")
@SuppressWarnings("serial")
public class Role extends BusinessEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
	private Integer roleId;
	
	@Column(name = "role_name")
	private String roleName;
	
	@Column(name = "role_desc")
	private String roleDesc;
	
	@Transient
	private boolean isRelated;
	
	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return this.roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public boolean getIsRelated() {
		return isRelated;
	}

	public void setIsRelated(boolean isRelated) {
		this.isRelated = isRelated;
	}

	@Override
	public Serializable getId() {
		return this.roleId;
	}

}
