package com.dl.rmas.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dl.rmas.common.enums.Validity;

@Entity
@Table(name = "sm_bom")
@SuppressWarnings("serial")
public class Bom extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bom_id")
	private Integer bomId;
	
	@Column(name = "cno")
	private String cno;
	
	@Column(name = "bom_desc")
	private String bomDesc;
	
	@Column(name = "sno")
	private String sno;
	
	@Column(name = "item_cate")
	private String itemCate;
	
	@Column(name = "category")
	private String category;
	
	@Column(name = "material_no")
	private String materialNo;

	@Column(name = "material_name")
	private String materialName;
	
	@Column(name = "material_charact")
	private String materialCharact;
	
	@Column(name = "manufacturer")
	private String manufacturer;
	
	@Column(name = "manufacturer_model")
	private String manufacturerModel;
	
	@Column(name = "dosage")
	private String dosage;
	
	@Column(name = "ino")
	private String ino;
	
	@Column(name = "product_id")
	private Integer productId;

	@Column(name="creater")
	private Integer creater;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name = "validity")
	@Enumerated(EnumType.STRING)
	private Validity validity = Validity.VALID;
	
	public Integer getBomId() {
		return this.bomId;
	}

	public void setBomId(Integer bomId) {
		this.bomId = bomId;
	}

	public String getCno() {
		return this.cno;
	}

	public void setCno(String cno) {
		this.cno = cno;
	}

	public String getBomDesc() {
		return this.bomDesc;
	}

	public void setBomDesc(String bomDesc) {
		this.bomDesc = bomDesc;
	}

	public String getSno() {
		return this.sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
	}

	public String getItemCate() {
		return this.itemCate;
	}

	public void setItemCate(String itemCate) {
		this.itemCate = itemCate;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getMaterialNo() {
		return this.materialNo;
	}

	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}

	public String getMaterialName() {
		return this.materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMaterialCharact() {
		return this.materialCharact;
	}

	public void setMaterialCharact(String materialCharact) {
		this.materialCharact = materialCharact;
	}

	public String getManufacturer() {
		return this.manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getManufacturerModel() {
		return this.manufacturerModel;
	}

	public void setManufacturerModel(String manufacturerModel) {
		this.manufacturerModel = manufacturerModel;
	}

	public String getDosage() {
		return this.dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

	public String getIno() {
		return this.ino;
	}

	public void setIno(String ino) {
		this.ino = ino;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getCreater() {
		return creater;
	}

	public void setCreater(Integer creater) {
		this.creater = creater;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Validity getValidity() {
		return validity;
	}

	public void setValidity(Validity validity) {
		this.validity = validity;
	}

	@Override
	public Serializable getId() {
		return this.bomId;
	}

}
