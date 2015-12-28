package com.dl.rmas.dto;

import org.apache.commons.lang.StringUtils;

/**
 * template_produce_query.xls dto
 * 
 * @author dongbz 2015-7-20
 */
public class ProduceDto {

	private Integer produceId;
	private Integer snId;
	private String userName;
	private String custrma;
	private String rma;
	private Integer snIndex;
	private String sn;
	private String pn;
	private String pcbType;
	private String productType;
	private String keyinStatus;
	private Integer hardLevel;
	private String receiveTime;
	private String createTime;
	private String assignTime;
	private String repairedTime;
	private String qcTime;
	private String doTime;
	private String closeTime;
	private String status;
	private String flasherName;
	private String flasherNo;
	private String flashResult;
	private String repairerName;
	private String repairerNo;
	private String repairCode;
	private String repairRemark;
	private String repairResult;
	private String materialUsed;
	private String partName;
	private Integer usedAmount;
	private String qcerName;
	private String qcerNo;
	private String qcResult;
	private String qcRemark;
	private String stoperName;
	private String stoperNo;
	private String finalResult;
	private String stopReason;
	private String keepStatus;
	private String macImei1;
	private String macImei1N;
	private String macImei2;
	private String macImei2N;
	
	private String employeeNo;
	private String employeeName;
	private String produceType;
	
	private String cidTypeFormatted;
	private String customerFaultDescFormatted;
	private String outletFormatted;
	private String failCode;
	private String caseId;
	private String remark;
	
	public Integer getProduceId() {
		return produceId;
	}
	public void setProduceId(Integer produceId) {
		this.produceId = produceId;
	}
	public Integer getSnId() {
		return snId;
	}
	public void setSnId(Integer snId) {
		this.snId = snId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCustrma() {
		return custrma;
	}
	public void setCustrma(String custrma) {
		this.custrma = custrma;
	}
	public String getRma() {
		return rma;
	}
	public void setRma(String rma) {
		this.rma = rma;
	}
	public Integer getSnIndex() {
		return snIndex;
	}
	
	public String getSnIndexFormatted() {
		if (snIndex == null) {
			return null;
		}
		
		return StringUtils.leftPad(String.valueOf(snIndex), 3, '0');
	}

	public void setSnIndex(Integer snIndex) {
		this.snIndex = snIndex;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getPn() {
		return pn;
	}
	public void setPn(String pn) {
		this.pn = pn;
	}
	public String getPcbType() {
		return pcbType;
	}
	public void setPcbType(String pcbType) {
		this.pcbType = pcbType;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getKeyinStatus() {
		return keyinStatus;
	}
	public void setKeyinStatus(String keyinStatus) {
		this.keyinStatus = keyinStatus;
	}
	public Integer getHardLevel() {
		return hardLevel;
	}
	public void setHardLevel(Integer hardLevel) {
		this.hardLevel = hardLevel;
	}
	public String getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getAssignTime() {
		return assignTime;
	}
	public void setAssignTime(String assignTime) {
		this.assignTime = assignTime;
	}
	public String getRepairedTime() {
		return repairedTime;
	}
	public void setRepairedTime(String repairedTime) {
		this.repairedTime = repairedTime;
	}
	public String getQcTime() {
		return qcTime;
	}
	public void setQcTime(String qcTime) {
		this.qcTime = qcTime;
	}
	public String getDoTime() {
		return doTime;
	}
	public void setDoTime(String doTime) {
		this.doTime = doTime;
	}
	public String getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFlasherName() {
		return flasherName;
	}
	public void setFlasherName(String flasherName) {
		this.flasherName = flasherName;
	}
	public String getFlasherNo() {
		return flasherNo;
	}
	public void setFlasherNo(String flasherNo) {
		this.flasherNo = flasherNo;
	}
	public String getFlashResult() {
		return flashResult;
	}
	public void setFlashResult(String flashResult) {
		this.flashResult = flashResult;
	}
	public String getRepairerName() {
		return repairerName;
	}
	public void setRepairerName(String repairerName) {
		this.repairerName = repairerName;
	}
	public String getRepairerNo() {
		return repairerNo;
	}
	public void setRepairerNo(String repairerNo) {
		this.repairerNo = repairerNo;
	}
	public String getRepairCode() {
		return repairCode;
	}
	public void setRepairCode(String repairCode) {
		this.repairCode = repairCode;
	}
	public String getRepairRemark() {
		return repairRemark;
	}
	public void setRepairRemark(String repairRemark) {
		this.repairRemark = repairRemark;
	}
	public String getRepairResult() {
		return repairResult;
	}
	public void setRepairResult(String repairResult) {
		this.repairResult = repairResult;
	}
	public String getMaterialUsed() {
		return materialUsed;
	}
	public void setMaterialUsed(String materialUsed) {
		this.materialUsed = materialUsed;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public Integer getUsedAmount() {
		return usedAmount;
	}
	public void setUsedAmount(Integer usedAmount) {
		this.usedAmount = usedAmount;
	}
	public String getQcerName() {
		return qcerName;
	}
	public void setQcerName(String qcerName) {
		this.qcerName = qcerName;
	}
	public String getQcerNo() {
		return qcerNo;
	}
	public void setQcerNo(String qcerNo) {
		this.qcerNo = qcerNo;
	}
	public String getQcResult() {
		return qcResult;
	}
	public void setQcResult(String qcResult) {
		this.qcResult = qcResult;
	}
	public String getQcRemark() {
		return qcRemark;
	}
	public void setQcRemark(String qcRemark) {
		this.qcRemark = qcRemark;
	}
	public String getStoperName() {
		return stoperName;
	}
	public void setStoperName(String stoperName) {
		this.stoperName = stoperName;
	}
	public String getStoperNo() {
		return stoperNo;
	}
	public void setStoperNo(String stoperNo) {
		this.stoperNo = stoperNo;
	}
	public String getFinalResult() {
		return finalResult;
	}
	public void setFinalResult(String finalResult) {
		this.finalResult = finalResult;
	}
	public String getStopReason() {
		return stopReason;
	}
	public void setStopReason(String stopReason) {
		this.stopReason = stopReason;
	}
	public String getKeepStatus() {
		return keepStatus;
	}
	public void setKeepStatus(String keepStatus) {
		this.keepStatus = keepStatus;
	}
	public String getMacImei1() {
		return macImei1;
	}
	public void setMacImei1(String macImei1) {
		this.macImei1 = macImei1;
	}
	public String getMacImei1N() {
		return macImei1N;
	}
	public void setMacImei1N(String macImei1N) {
		this.macImei1N = macImei1N;
	}
	public String getMacImei2() {
		return macImei2;
	}
	public void setMacImei2(String macImei2) {
		this.macImei2 = macImei2;
	}
	public String getMacImei2N() {
		return macImei2N;
	}
	public void setMacImei2N(String macImei2N) {
		this.macImei2N = macImei2N;
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
	public String getProduceType() {
		return produceType;
	}
	public void setProduceType(String produceType) {
		this.produceType = produceType;
	}
	public String getCidTypeFormatted() {
		return cidTypeFormatted;
	}
	public void setCidTypeFormatted(String cidTypeFormatted) {
		this.cidTypeFormatted = cidTypeFormatted;
	}
	public String getCustomerFaultDescFormatted() {
		return customerFaultDescFormatted;
	}
	public void setCustomerFaultDescFormatted(String customerFaultDescFormatted) {
		this.customerFaultDescFormatted = customerFaultDescFormatted;
	}
	public String getOutletFormatted() {
		return outletFormatted;
	}
	public void setOutletFormatted(String outletFormatted) {
		this.outletFormatted = outletFormatted;
	}
	public String getFailCode() {
		return failCode;
	}
	public void setFailCode(String failCode) {
		this.failCode = failCode;
	}
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
