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
import javax.persistence.Transient;

import com.dl.rmas.common.enums.ProduceType;

@Entity
@Table(name = "t_sn_produce")
@SuppressWarnings("serial")
public class SnProduce extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "produce_id")
	private Integer produceId;
	
	@Column(name = "sn_id")
	private Integer snId;
	 
	@Column(name = "producer")
	private Integer producer;
	
	@Column(name = "produce_type")
	@Enumerated(EnumType.STRING)
	private ProduceType produceType;
	
	@Column(name = "result")
	private String result;
	
	@Column(name = "result_remark")
	private String resultRemark;
	
	@Column(name = "repair_code")
	private Integer repairCode;
	
	@Column(name = "start_time")
	private Date startTime;
	
	@Column(name = "end_time")
	private Date endTime;

	@Transient
	private String employeeNo;	// 员工编号
	@Transient
	private String employeeName;	// 员工姓名
	@Transient
	private Date endTimeFrom;
	@Transient
	private Date endTimeTo;
	
	public Integer getProduceId() {
		return this.produceId;
	}

	public void setProduceId(Integer produceId) {
		this.produceId = produceId;
	}

	public Integer getSnId() {
		return this.snId;
	}

	public void setSnId(Integer snId) {
		this.snId = snId;
	}

	public Integer getProducer() {
		return this.producer;
	}

	public void setProducer(Integer producer) {
		this.producer = producer;
	}

	public ProduceType getProduceType() {
		return produceType;
	}

	public void setProduceType(ProduceType produceType) {
		this.produceType = produceType;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResultRemark() {
		return this.resultRemark;
	}

	public void setResultRemark(String resultRemark) {
		this.resultRemark = resultRemark;
	}

	public Integer getRepairCode() {
		return this.repairCode;
	}

	public void setRepairCode(Integer repairCode) {
		this.repairCode = repairCode;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Date getEndTimeFrom() {
		return endTimeFrom;
	}

	public void setEndTimeFrom(Date endTimeFrom) {
		this.endTimeFrom = endTimeFrom;
	}

	public Date getEndTimeTo() {
		return endTimeTo;
	}

	public void setEndTimeTo(Date endTimeTo) {
		this.endTimeTo = endTimeTo;
	}

	@Override
	public Serializable getId() {
		return this.produceId;
	}

}
