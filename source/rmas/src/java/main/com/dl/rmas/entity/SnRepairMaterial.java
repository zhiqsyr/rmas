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
@Table(name = "t_sn_repair_material")
@SuppressWarnings("serial")
public class SnRepairMaterial extends BusinessEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rm_id")
	private Integer rmId;
	
	@Column(name = "bom_id")
	private Integer bomId;
	
	@Column(name = "number")
	private Integer number;
	
	@Column(name = "sn_id")
	private Integer snId;
	
	@Transient
	private Bom bom;
	
	public Integer getRmId() {
		return this.rmId;
	}

	public void setRmId(Integer rmId) {
		this.rmId = rmId;
	}

	public Integer getBomId() {
		return bomId;
	}

	public void setBomId(Integer bomId) {
		this.bomId = bomId;
	}

	public Integer getNumber() {
		return this.number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getSnId() {
		return snId;
	}

	public void setSnId(Integer snId) {
		this.snId = snId;
	}

	public Bom getBom() {
		return bom == null ? new Bom() : bom;
	}

	public void setBom(Bom bom) {
		this.bom = bom;
	}

	@Override
	public Serializable getId() {
		return this.rmId;
	}

}
