package com.dl.rmas.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dl.rmas.common.enums.Validity;

@Entity
@Table(name = "sm_customer")
@SuppressWarnings("serial")
public class Customer extends BusinessEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
	private Integer customerId;
	
	@Column(name = "short_name")
	private String shortName;
	
	@Column(name = "full_name")
	private String fullName;
	
	@Column(name = "contactor_name")
	private String contactorName;
	
	@Column(name = "mobilephone")
	private String mobilephone;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "fixphone")
	private String fixphone;
	
	@Column(name = "addr")
	private String addr;
	
	@Column(name = "ship_via")
	private String shipVia;
	
	@Column(name = "attn")
	private String attn;
	
	public Customer(){
		
	}
	
	public Customer(Validity validity){
		setValidity(validity);
	}

	public Integer getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getContactorName() {
		return this.contactorName;
	}

	public void setContactorName(String contactorName) {
		this.contactorName = contactorName;
	}

	public String getMobilephone() {
		return this.mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFixphone() {
		return this.fixphone;
	}

	public void setFixphone(String fixphone) {
		this.fixphone = fixphone;
	}

	public String getAddr() {
		return addr;
	}
	
	public String getAddrFormatted() {
		return addr == null ? "" : addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getShipVia() {
		return shipVia;
	}

	public void setShipVia(String shipVia) {
		this.shipVia = shipVia;
	}

	public String getAttn() {
		return attn;
	}

	public void setAttn(String attn) {
		this.attn = attn;
	}

	@Override
	public Serializable getId() {
		return this.customerId;
	}

}
