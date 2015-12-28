package com.dl.rmas.dto;

import java.util.Date;

import com.dl.rmas.common.enums.KeyinStatus;
import com.dl.rmas.common.enums.ReceiveStatus;
import com.dl.rmas.entity.Sn;
import com.dl.rmas.entity.Order.RmaDoStatus;

@SuppressWarnings("serial")
public class SnDto extends Sn {

	private Integer orderId;
	private Integer customerId;
	private String pn;
	private String rma;
	private String custrma;;
	private ReceiveStatus receiveStatus;
	private Date receiveDateFrom;
	private Date receiveDateTo;
	private Date closeDateFrom;
	private Date closeDateTo;
	private Date doTimeFrom;
	private Date doTimeEnd;
	private String createUserName;
	private Date createTimeFrom; // keyin时间
	private Date createTimeTo;	
	private KeyinStatus keyinStatus;
	private String repairerName;
	private Integer tatLevel;
	private RmaDoStatus snDoStatus;
	private String oqcResult;
	
	public String getRepairerName() {
		return repairerName;
	}
	public void setRepairerName(String repairerName) {
		this.repairerName = repairerName;
	}
	public Integer getOrderId() {
		return orderId;
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
	public String getPn() {
		return pn;
	}
	public void setPn(String pn) {
		this.pn = pn;
	}
	public String getRma() {
		return rma;
	}
	public void setRma(String rma) {
		this.rma = rma;
	}
	public String getCustrma() {
		return custrma;
	}
	public void setCustrma(String custrma) {
		this.custrma = custrma;
	}
	public ReceiveStatus getReceiveStatus() {
		return receiveStatus;
	}
	public void setReceiveStatus(ReceiveStatus receiveStatus) {
		this.receiveStatus = receiveStatus;
	}
	public Date getReceiveDateFrom() {
		return receiveDateFrom;
	}
	public void setReceiveDateFrom(Date receiveDateFrom) {
		this.receiveDateFrom = receiveDateFrom;
	}
	public Date getReceiveDateTo() {
		return receiveDateTo;
	}
	public void setReceiveDateTo(Date receiveDateTo) {
		this.receiveDateTo = receiveDateTo;
	}
	public Date getCloseDateFrom() {
		return closeDateFrom;
	}
	public void setCloseDateFrom(Date closeDateFrom) {
		this.closeDateFrom = closeDateFrom;
	}
	public Date getCloseDateTo() {
		return closeDateTo;
	}
	public void setCloseDateTo(Date closeDateTo) {
		this.closeDateTo = closeDateTo;
	}
	public Date getDoTimeFrom() {
		return doTimeFrom;
	}
	public void setDoTimeFrom(Date doTimeFrom) {
		this.doTimeFrom = doTimeFrom;
	}
	public Date getDoTimeEnd() {
		return doTimeEnd;
	}
	public void setDoTimeEnd(Date doTimeEnd) {
		this.doTimeEnd = doTimeEnd;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public Date getCreateTimeFrom() {
		return createTimeFrom;
	}
	public void setCreateTimeFrom(Date createTimeFrom) {
		this.createTimeFrom = createTimeFrom;
	}
	public Date getCreateTimeTo() {
		return createTimeTo;
	}
	public void setCreateTimeTo(Date createTimeTo) {
		this.createTimeTo = createTimeTo;
	}
	public KeyinStatus getKeyinStatus() {
		return keyinStatus;
	}
	public void setKeyinStatus(KeyinStatus keyinStatus) {
		this.keyinStatus = keyinStatus;
	}
	public Integer getTatLevel() {
		return tatLevel;
	}
	public void setTatLevel(Integer tatLevel) {
		this.tatLevel = tatLevel;
	}
	public RmaDoStatus getSnDoStatus() {
		return snDoStatus;
	}
	public void setSnDoStatus(RmaDoStatus snDoStatus) {
		this.snDoStatus = snDoStatus;
	}
	public String getOqcResult() {
		return oqcResult;
	}
	public void setOqcResult(String oqcResult) {
		this.oqcResult = oqcResult;
	}
	
}
