package com.dl.rmas.dto;

import java.util.Date;

/**
 * 员工返修情况统计
 * 
 * @author dongbz 2016-3-3
 */
public class EmployeeReturnStatis {

	private String employeeNo;		// 员工编号
	private String employeeName;	// 员工名称
	private String type;			// 操作类型：REPAIR/QC 
	private Integer rsTimes;		// Re-service QTY
	private Integer escTimes;		// NTF+CID QTY
	private Integer doTimes;
	
	private Date operateTimeFrom;
	private Date operateTimeTo;
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getRsTimes() {
		return rsTimes;
	}
	public void setRsTimes(Integer rsTimes) {
		this.rsTimes = rsTimes;
	}
	public Integer getDoTimes() {
		return doTimes == null ? 1 : doTimes;
	}
	public void setDoTimes(Integer doTimes) {
		this.doTimes = doTimes;
	}
	public Integer getEscTimes() {
		return escTimes;
	}
	public void setEscTimes(Integer escTimes) {
		this.escTimes = escTimes;
	}
	public Date getOperateTimeFrom() {
		return operateTimeFrom;
	}
	public void setOperateTimeFrom(Date operateTimeFrom) {
		this.operateTimeFrom = operateTimeFrom;
	}
	public Date getOperateTimeTo() {
		return operateTimeTo;
	}
	public void setOperateTimeTo(Date operateTimeTo) {
		this.operateTimeTo = operateTimeTo;
	}
	
	/**
	 * <b>Function: <b>返修率：DO 产品中的"WHITIN"部分的数据，去除 CID,NTF除以单个人的产出，即： Within Qty-Within(NTF+CID) / DO 
	 *
	 * @return
	 */
	public Double getReserviceRate() {
		return (rsTimes - escTimes) / doTimes.doubleValue();
	}
	
}
