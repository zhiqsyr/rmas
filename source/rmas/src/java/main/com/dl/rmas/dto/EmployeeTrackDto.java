package com.dl.rmas.dto;

import java.math.BigDecimal;

/**
 * 员工效益统计
 * 
 * @author dongbz 2015-1-30
 */
public class EmployeeTrackDto {

	private Integer userId;
	private String userNo;
	private String userName;
	private BigDecimal keyinCount;
	private BigDecimal midhCount;
	private BigDecimal flashCount;
	private BigDecimal flashNgCount;
	private BigDecimal l1keyinCount;
	private BigDecimal stopRepairCount;
	private BigDecimal repairCount;
	private BigDecimal repairNgCount;
	private BigDecimal qcCount;
	private BigDecimal qcNgCount;
	private BigDecimal oqcCount;
	private BigDecimal oqcNgCount;
	private BigDecimal keyoutCount;
	private BigDecimal doCount;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public BigDecimal getKeyinCount() {
		return keyinCount;
	}
	public void setKeyinCount(BigDecimal keyinCount) {
		this.keyinCount = keyinCount;
	}
	public BigDecimal getMidhCount() {
		return midhCount;
	}
	public void setMidhCount(BigDecimal midhCount) {
		this.midhCount = midhCount;
	}
	public BigDecimal getFlashCount() {
		return flashCount;
	}
	public void setFlashCount(BigDecimal flashCount) {
		this.flashCount = flashCount;
	}
	public BigDecimal getL1keyinCount() {
		return l1keyinCount;
	}
	public void setL1keyinCount(BigDecimal l1keyinCount) {
		this.l1keyinCount = l1keyinCount;
	}
	public BigDecimal getStopRepairCount() {
		return stopRepairCount;
	}
	public void setStopRepairCount(BigDecimal stopRepairCount) {
		this.stopRepairCount = stopRepairCount;
	}
	public BigDecimal getRepairCount() {
		return repairCount;
	}
	public void setRepairCount(BigDecimal repairCount) {
		this.repairCount = repairCount;
	}
	public BigDecimal getQcCount() {
		return qcCount;
	}
	public void setQcCount(BigDecimal qcCount) {
		this.qcCount = qcCount;
	}
	public BigDecimal getKeyoutCount() {
		return keyoutCount;
	}
	public void setKeyoutCount(BigDecimal keyoutCount) {
		this.keyoutCount = keyoutCount;
	}
	public BigDecimal getDoCount() {
		return doCount;
	}
	public void setDoCount(BigDecimal doCount) {
		this.doCount = doCount;
	}
	public BigDecimal getFlashNgCount() {
		return flashNgCount;
	}
	public void setFlashNgCount(BigDecimal flashNgCount) {
		this.flashNgCount = flashNgCount;
	}
	public BigDecimal getRepairNgCount() {
		return repairNgCount;
	}
	public void setRepairNgCount(BigDecimal repairNgCount) {
		this.repairNgCount = repairNgCount;
	}
	public BigDecimal getQcNgCount() {
		return qcNgCount;
	}
	public void setQcNgCount(BigDecimal qcNgCount) {
		this.qcNgCount = qcNgCount;
	}
	public BigDecimal getOqcCount() {
		return oqcCount;
	}
	public void setOqcCount(BigDecimal oqcCount) {
		this.oqcCount = oqcCount;
	}
	public BigDecimal getOqcNgCount() {
		return oqcNgCount;
	}
	public void setOqcNgCount(BigDecimal oqcNgCount) {
		this.oqcNgCount = oqcNgCount;
	}
	public Double getFlashPassRate() {
		if (flashNgCount.intValue() == 0) {
			return 1D;
		}
		
		return flashCount.doubleValue()/flashCount.add(flashNgCount).doubleValue();
	}
	public Double getRepairPassRate() {
		if (repairNgCount.intValue() == 0) {
			return 1D;
		}
		
		return repairCount.doubleValue()/repairCount.add(repairNgCount).doubleValue();
	}
	public Double getQcPassRate() {
		if (qcNgCount.intValue() == 0) {
			return 1D;
		}
		
		return qcCount.doubleValue()/qcCount.add(qcNgCount).doubleValue();
	}
	
}
