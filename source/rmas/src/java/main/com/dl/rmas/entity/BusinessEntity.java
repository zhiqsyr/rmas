package com.dl.rmas.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class BusinessEntity extends ValidityEntity {

	@Column(name="create_user")
	private Integer createUser;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="last_modifier")
	private Integer lastModifier;
	
	@Column(name="last_modify_time")
	private Date lastModifyTime;
	
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

	public Integer getLastModifier() {
		return this.lastModifier;
	}

	public void setLastModifier(Integer lastModifier) {
		this.lastModifier = lastModifier;
	}

	public Date getLastModifyTime() {
		return this.lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	
}
