package com.dl.rmas.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单跟踪dto
 * 
 * @author dongbz 2015-1-28
 */
public class OrderTrackDto {
	
	private Integer orderId;
	private String rma;
	private String custrma;
	private Date closeTime;
	private String receiveStatus;
	private String keyinStatus;
	
	private String totalQty;
	private BigDecimal waitMidhCount;
	private BigDecimal waitFlashCount;
	private BigDecimal waitL1keyinCount;
	private BigDecimal waitReparingCount;
	private BigDecimal waitQcCount;
	private BigDecimal waitDoCount;
	private BigDecimal doneCount;
	
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
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
	public Date getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}
	public String getReceiveStatus() {
		return receiveStatus;
	}
	public void setReceiveStatus(String receiveStatus) {
		this.receiveStatus = receiveStatus;
	}
	public String getKeyinStatus() {
		return keyinStatus;
	}
	public void setKeyinStatus(String keyinStatus) {
		this.keyinStatus = keyinStatus;
	}
	public String getDoStatus() {
		return doneCount.toString().equals(totalQty) ? "DONE" : "ONLINE";
	}
	public String getTotalQty() {
		return totalQty;
	}
	public void setTotalQty(String totalQty) {
		this.totalQty = totalQty;
	}
	public BigDecimal getWaitMidhCount() {
		return waitMidhCount;
	}
	public void setWaitMidhCount(BigDecimal waitMidhCount) {
		this.waitMidhCount = waitMidhCount;
	}
	public BigDecimal getWaitFlashCount() {
		return waitFlashCount;
	}
	public void setWaitFlashCount(BigDecimal waitFlashCount) {
		this.waitFlashCount = waitFlashCount;
	}
	public BigDecimal getWaitL1keyinCount() {
		return waitL1keyinCount;
	}
	public void setWaitL1keyinCount(BigDecimal waitL1keyinCount) {
		this.waitL1keyinCount = waitL1keyinCount;
	}
	public BigDecimal getWaitReparingCount() {
		return waitReparingCount;
	}
	public void setWaitReparingCount(BigDecimal waitReparingCount) {
		this.waitReparingCount = waitReparingCount;
	}
	public BigDecimal getWaitQcCount() {
		return waitQcCount;
	}
	public void setWaitQcCount(BigDecimal waitQcCount) {
		this.waitQcCount = waitQcCount;
	}
	public BigDecimal getWaitDoCount() {
		return waitDoCount;
	}
	public void setWaitDoCount(BigDecimal waitDoCount) {
		this.waitDoCount = waitDoCount;
	}
	public BigDecimal getOnlineCount() {
		return waitMidhCount.add(waitFlashCount).add(waitL1keyinCount).add(waitReparingCount).add(waitQcCount).add(waitDoCount);
	}
	public BigDecimal getDoneCount() {
		return doneCount;
	}
	public void setDoneCount(BigDecimal doneCount) {
		this.doneCount = doneCount;
	}
	
}
