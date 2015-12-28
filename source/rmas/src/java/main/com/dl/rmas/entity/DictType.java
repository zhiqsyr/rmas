package com.dl.rmas.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dl.rmas.common.enums.ShowWhich;

@Entity
@Table(name = "sm_dict_type")
@SuppressWarnings("serial")
public class DictType extends ValidityEntity {

	@Id
    @Column(name = "type_id")
	private Integer typeId;
	
	@Column(name = "type_key")
	private String typeKey;
	
	@Column(name = "type_name")
	private String typeName;
	
	@Column(name = "modifiable")
	private String modifiable;

	@Column(name = "show_which")
	private ShowWhich showWhich;
	
	public Integer getTypeId() {
		return this.typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getTypeKey() {
		return this.typeKey;
	}

	public void setTypeKey(String typeKey) {
		this.typeKey = typeKey;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getModifiable() {
		return this.modifiable;
	}

	public void setModifiable(String modifiable) {
		this.modifiable = modifiable;
	}

	public ShowWhich getShowWhich() {
		return showWhich;
	}

	public void setShowWhich(ShowWhich showWhich) {
		this.showWhich = showWhich;
	}

	@Override
	public Serializable getId() {
		return this.typeId;
	}

}
