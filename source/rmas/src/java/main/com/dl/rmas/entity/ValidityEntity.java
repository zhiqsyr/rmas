package com.dl.rmas.entity;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import com.dl.rmas.common.enums.Validity;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class ValidityEntity extends BaseEntity {

	@Column(name = "validity")
	@Enumerated(EnumType.STRING)
	private Validity validity;

	public Validity getValidity() {
		return validity;
	}

	public void setValidity(Validity validity) {
		this.validity = validity;
	}
	
}
