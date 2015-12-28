package com.dl.rmas.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sm_product")
@SuppressWarnings("serial")
public class Product extends BusinessEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
	private Integer productId;
	
	@Column(name = "pn")
	private String pn;
	
	@Column(name = "brand")
	private String brand;
	
	@Column(name = "price")
	private Double price;
	
	@Column(name = "product_type")
	private Integer productType;
	
	@Column(name = "model_no")
	private String modelNo;
	
	@Column(name = "pcb_type")
	private String pcbType;
	
	@Column(name = "keep_days")
	private Integer keepDays;

	public Integer getProductId() {
		return this.productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getPn() {
		return this.pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getProductType() {
		return this.productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public String getModelNo() {
		return this.modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public String getPcbType() {
		return this.pcbType;
	}

	public void setPcbType(String pcbType) {
		this.pcbType = pcbType;
	}

	public Integer getKeepDays() {
		return this.keepDays;
	}

	public void setKeepDays(Integer keepDays) {
		this.keepDays = keepDays;
	}

	@Override
	public Serializable getId() {
		return this.productId;
	}

}
