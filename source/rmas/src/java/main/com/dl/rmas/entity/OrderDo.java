package com.dl.rmas.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.dl.rmas.common.utils.DateUtils;

@Entity
@Table(name = "t_order_do")
@SuppressWarnings("serial")
public class OrderDo extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "do_id")
	private Integer doId;
	
	@Column(name = "do_rma")
	private String doRma;
	 
	@Column(name = "order_id")
	private Integer orderId;
	
	@Column(name = "customer_id")
	private Integer customerId;
	 
	@Column(name = "excel_path")
	private String excelPath;
	@Column(name = "word_path")
	private String wordPath;
	
	@Column(name = "dor")
	private Integer dor;
	 
	@Column(name = "do_time")
	private Date doTime;
	@Transient
	private Date doTimeFrom;
	@Transient
	private Date doTimeTo;
	
	@Transient
	private String rma;
	
	public Integer getDoId() {
		return this.doId;
	}

	public void setDoId(Integer doId) {
		this.doId = doId;
	}

	public String getDoRma() {
		return this.doRma;
	}

	public void setDoRma(String doRma) {
		this.doRma = doRma;
	}

	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getExcelPath() {
		return excelPath;
	}
	
	public String getExcelPathFormatted() {
		if (StringUtils.isBlank(excelPath)) {
			return excelPath;
		}
		
		return excelPath.substring(excelPath.lastIndexOf("/") + 1);
	}

	public void setExcelPath(String excelPath) {
		this.excelPath = excelPath;
	}

	public String getWordPath() {
		return wordPath;
	}
	
	public String getWordPathFormatted() {
		if (StringUtils.isBlank(wordPath)) {
			return wordPath;
		}
		
		return wordPath.substring(wordPath.lastIndexOf("/") + 1);
	}

	public void setWordPath(String wordPath) {
		this.wordPath = wordPath;
	}

	public Integer getDor() {
		return this.dor;
	}

	public void setDor(Integer dor) {
		this.dor = dor;
	}

	public Date getDoTime() {
		return this.doTime;
	}
	
	public String getDoTimeFormatted() {
		return DateUtils.formateToDDMMYYYY(this.doTime);
	}

	public void setDoTime(Date doTime) {
		this.doTime = doTime;
	}

	public Date getDoTimeFrom() {
		return doTimeFrom;
	}

	public void setDoTimeFrom(Date doTimeFrom) {
		this.doTimeFrom = doTimeFrom;
	}

	public Date getDoTimeTo() {
		return doTimeTo;
	}

	public void setDoTimeTo(Date doTimeTo) {
		this.doTimeTo = doTimeTo;
	}

	public String getRma() {
		return rma;
	}

	public void setRma(String rma) {
		this.rma = rma;
	}

	@Override
	public Serializable getId() {
		return this.doId;
	}

}
