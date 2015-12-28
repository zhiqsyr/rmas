package com.dl.rmas.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sm_role_menu")
@SuppressWarnings("serial")
public class RoleMenu extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_menu_id")
	private Integer roleMenuId;
	
	@Column(name = "role_id")
	private Integer roleId;
	
	@Column(name = "menu_id")
	private Integer menuId;
	
	@Column(name = "create_user")
	private Integer createUser;
	
	@Column(name = "create_time")
	private Date createTime;

	public Integer getRoleMenuId() {
		return roleMenuId;
	}

	public void setRoleMenuId(Integer roleMenuId) {
		this.roleMenuId = roleMenuId;
	}

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getMenuId() {
		return this.menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public Integer getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public Serializable getId() {
		return this.roleMenuId;
	}

}
