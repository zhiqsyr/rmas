package com.dl.rmas.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.dl.rmas.common.enums.FinalResult;
import com.dl.rmas.common.enums.IF;
import com.dl.rmas.common.enums.SnStatus;
import com.dl.rmas.entity.Order.RmaDoStatus;

@Entity
@Table(name = "t_sn")
@SuppressWarnings("serial")
public class Sn extends BusinessEntity implements Comparable<Sn> {

	public enum KeepStatus {
		/** 首次录入 */
		NEW,
		/** 在保状态 */
		WITHIN,
		/** 非保状态 */
		WITHOUT
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sn_id")
	private Integer snId;
	
	@Column(name = "sn")
	private String sn;
	
	@Column(name = "sn_index")
	private Integer snIndex;
	
	@Column(name = "keep_status")
	@Enumerated(EnumType.STRING)
	private KeepStatus keepStatus;
	
	@Column(name = "twice_back_times")
	private Integer twiceBackTimes;
	
	@Column(name = "cid_type")
	private Integer cidType;
	
	@Column(name = "customer_fault_desc")
	private Integer customerFaultDesc;
	@Transient
	private String faultCode;
	
	@Column(name = "outlet")
	private Integer outlet;
	
	@Column(name = "fail_code")
	private String failCode;
	
	@Column(name = "case_id")
	private String caseId;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "mac_imei1")
	private String macImei1;
	
	@Column(name = "mac_imei1_n")
	private String macImei1N;
	
	@Column(name = "mac_imei2")
	private String macImei2;
	
	@Column(name = "mac_imei2_n")
	private String macImei2N;
	
	@Column(name = "product_id")
	private Integer productId;
	
	@Column(name = "order_id")
	private Integer orderId;
	
	@Column(name = "hard_level")
	private Integer hardLevel;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private SnStatus status;
	
	@Column(name = "final_result")
	@Enumerated(EnumType.STRING)
	private FinalResult finalResult;
	
	@Column(name = "flasher")
	private Integer flasher;
	
	@Column(name = "flash_time")
	private Date flashTime;
	
	@Column(name = "flash_result")
	private String flashResult;
	
	@Column(name = "assigner")
	private Integer assigner;
	
	@Column(name = "assign_time")
	private Date assignTime;
	
	@Column(name = "material_used")
	@Enumerated(EnumType.STRING)
	private IF materialUsed;
	
	@Column(name = "repairer")
	private Integer repairer;
	
	@Column(name = "repair_code")
	private Integer repairCode;
	
	@Column(name = "repair_remark")
	private String repairRemark;
	
	@Column(name = "repaired_time")
	private Date repairedTime;
	
	@Column(name = "repair_result")
	private String repairResult;
	
	@Column(name = "qcer")
	private Integer qcer;
	
	@Column(name = "qc_remark")
	private String qcRemark;
	
	@Column(name = "qc_time")
	private Date qcTime;

	@Column(name = "qc_result")
	private String qcResult;

	@Column(name = "oqcer")
	private Integer oqcer;

	@Column(name = "oqc_result")
	private String oqcResult;	

	@Column(name = "oqc_remark")
	private String oqcRemark;

	@Column(name = "oqc_time")
	private Date oqcTime;
	
	@Column(name = "keyouter")
	private Integer keyouter;
	
	@Column(name = "keyout_time")
	private Date keyoutTime;
	
	@Column(name = "do_id")
	private Integer doId;
	
	@Column(name = "doer")
	private Integer doer;
	
	@Column(name = "do_time")
	private Date doTime;
	
	@Column(name = "stoper")
	private Integer stoper;
	
	@Column(name = "stop_time")
	private Date stopTime;
	
	@Column(name = "stop_reason")
	private String stopReason;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "order_id", insertable = false, updatable = false)
	private Order order;

	@Transient
	private String pn;
	@Transient
	private List<SnProduce> snProduces;
	@Transient
	private String pcbType;
	
	public Integer getSnId() {
		return this.snId;
	}

	public void setSnId(Integer snId) {
		this.snId = snId;
	}

	public String getSn() {
		return this.sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
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

	public KeepStatus getKeepStatus() {
		return keepStatus;
	}

	public void setKeepStatus(KeepStatus keepStatus) {
		this.keepStatus = keepStatus;
	}

	public Integer getTwiceBackTimes() {
		return this.twiceBackTimes;
	}

	public void setTwiceBackTimes(Integer twiceBackTimes) {
		this.twiceBackTimes = twiceBackTimes;
	}

	public Integer getCidType() {
		return this.cidType;
	}

	public void setCidType(Integer cidType) {
		this.cidType = cidType;
	}

	public Integer getCustomerFaultDesc() {
		return this.customerFaultDesc;
	}

	public void setCustomerFaultDesc(Integer customerFaultDesc) {
		this.customerFaultDesc = customerFaultDesc;
	}

	public String getFaultCode() {
		return faultCode;
	}

	public void setFaultCode(String faultCode) {
		this.faultCode = faultCode;
	}

	public Integer getOutlet() {
		return this.outlet;
	}

	public void setOutlet(Integer outlet) {
		this.outlet = outlet;
	}

	public String getFailCode() {
		return this.failCode;
	}

	public void setFailCode(String failCode) {
		this.failCode = failCode;
	}

	public String getCaseId() {
		return this.caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMacImei1() {
		return this.macImei1;
	}

	public void setMacImei1(String macImei1) {
		this.macImei1 = macImei1;
	}

	public String getMacImei2() {
		return this.macImei2;
	}

	public void setMacImei2(String macImei2) {
		this.macImei2 = macImei2;
	}

	public String getMacImei1N() {
		return macImei1N;
	}

	public void setMacImei1N(String macImei1N) {
		this.macImei1N = macImei1N;
	}

	public String getMacImei2N() {
		return macImei2N;
	}

	public void setMacImei2N(String macImei2N) {
		this.macImei2N = macImei2N;
	}

	public Integer getProductId() {
		return this.productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getHardLevel() {
		return this.hardLevel;
	}

	public void setHardLevel(Integer hardLevel) {
		this.hardLevel = hardLevel;
	}

	public SnStatus getStatus() {
		return status;
	}

	public void setStatus(SnStatus status) {
		this.status = status;
	}
	
	public RmaDoStatus getSnDoStatus() {
		return SnStatus.DONE.equals(status) ? RmaDoStatus.DONE : RmaDoStatus.ONLINE;
	}	

	public FinalResult getFinalResult() {
		return finalResult;
	}

	public void setFinalResult(FinalResult finalResult) {
		this.finalResult = finalResult;
	}

	public Integer getFlasher() {
		return flasher;
	}

	public void setFlasher(Integer flasher) {
		this.flasher = flasher;
	}

	public Date getFlashTime() {
		return flashTime;
	}

	public void setFlashTime(Date flashTime) {
		this.flashTime = flashTime;
	}

	public Integer getAssigner() {
		return this.assigner;
	}

	public void setAssigner(Integer assigner) {
		this.assigner = assigner;
	}

	public Date getAssignTime() {
		return this.assignTime;
	}

	public void setAssignTime(Date assignTime) {
		this.assignTime = assignTime;
	}

	public IF getMaterialUsed() {
		return this.materialUsed;
	}

	public void setMaterialUsed(IF materialUsed) {
		this.materialUsed = materialUsed;
	}

	public Integer getRepairer() {
		return this.repairer;
	}

	public void setRepairer(Integer repairer) {
		this.repairer = repairer;
	}

	public Integer getRepairCode() {
		return repairCode;
	}

	public void setRepairCode(Integer repairCode) {
		this.repairCode = repairCode;
	}

	public String getRepairRemark() {
		return repairRemark;
	}

	public void setRepairRemark(String repairRemark) {
		this.repairRemark = repairRemark;
	}

	public Date getRepairedTime() {
		return this.repairedTime;
	}

	public void setRepairedTime(Date repairedTime) {
		this.repairedTime = repairedTime;
	}

	public Integer getQcer() {
		return this.qcer;
	}

	public void setQcer(Integer qcer) {
		this.qcer = qcer;
	}

	public String getQcRemark() {
		return qcRemark;
	}

	public void setQcRemark(String qcRemark) {
		this.qcRemark = qcRemark;
	}
	
	public String getOqcRemark() {
		return oqcRemark;
	}

	public void setOqcRemark(String oqcRemark) {
		this.oqcRemark = oqcRemark;
	}
	
	public Date getQcTime() {
		return this.qcTime;
	}

	public void setQcTime(Date qcTime) {
		this.qcTime = qcTime;
	}

	public Date getOqcTime() {
		return oqcTime;
	}

	public void setOqcTime(Date oqcTime) {
		this.oqcTime = oqcTime;
	}

	public Integer getKeyouter() {
		return this.keyouter;
	}

	public void setKeyouter(Integer keyouter) {
		this.keyouter = keyouter;
	}

	public Date getKeyoutTime() {
		return this.keyoutTime;
	}

	public void setKeyoutTime(Date keyoutTime) {
		this.keyoutTime = keyoutTime;
	}

	public Integer getDoId() {
		return this.doId;
	}

	public void setDoId(Integer doId) {
		this.doId = doId;
	}

	public Integer getDoer() {
		return this.doer;
	}

	public void setDoer(Integer doer) {
		this.doer = doer;
	}

	public Date getDoTime() {
		return this.doTime;
	}

	public void setDoTime(Date doTime) {
		this.doTime = doTime;
	}

	public Integer getStoper() {
		return this.stoper;
	}

	public void setStoper(Integer stoper) {
		this.stoper = stoper;
	}

	public Date getStopTime() {
		return this.stopTime;
	}

	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}

	public String getStopReason() {
		return this.stopReason;
	}
	
	public String getStopReasonFormatted() {
		return this.stopReason == null ? "" : stopReason;
	}

	public void setStopReason(String stopReason) {
		this.stopReason = stopReason;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getPn() {
		return pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

	public List<SnProduce> getSnProduces() {
		return snProduces;
	}

	public void setSnProduces(List<SnProduce> snProduces) {
		this.snProduces = snProduces;
	}

	public String getFlashResult() {
		return flashResult;
	}

	public void setFlashResult(String flashResult) {
		this.flashResult = flashResult;
	}

	public String getRepairResult() {
		return repairResult;
	}

	public void setRepairResult(String repairResult) {
		this.repairResult = repairResult;
	}

	public String getQcResult() {
		return qcResult;
	}

	public void setQcResult(String qcResult) {
		this.qcResult = qcResult;
	}

	public Integer getOqcer() {
		return oqcer;
	}

	public void setOqcer(Integer oqcer) {
		this.oqcer = oqcer;
	}

	public String getOqcResult() {
		return oqcResult;
	}

	public void setOqcResult(String oqcResult) {
		this.oqcResult = oqcResult;
	}

	public String getRma() {
		if (order != null) {
			return order.getRma();
		}
		return null;
	}

	public String getPcbType() {
		return pcbType;
	}

	public void setPcbType(String pcbType) {
		this.pcbType = pcbType;
	}

	@Override
	public Serializable getId() {
		return this.snId;
	}

	/**
	 * 首先比较 rma ，其次比较 snIndex ，由小到大排序
	 */
	@Override
	public int compareTo(Sn vo) {
		if (vo == null) {
			return -1;
		}
		
		int rmaCt = this.getRma().compareTo(vo.getRma());
		if (rmaCt == 0) {
			return this.snIndex - vo.getSnIndex();			
		} else {
			return rmaCt;
		}
	}

}
