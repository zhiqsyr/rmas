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

import org.apache.commons.lang.StringUtils;

import com.dl.rmas.common.enums.KeyinStatus;
import com.dl.rmas.common.enums.ReceiveStatus;
import com.dl.rmas.common.enums.Validity;

@Entity
@Table(name = "t_order")
@SuppressWarnings("serial")
public class Order extends ValidityEntity {
	
	public enum RmaDoStatus { ONLINE, DONE }

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
	private Integer orderId;
	
	@Column(name = "rma")
	private String rma;
	
	@Column(name = "custrma")
	private String custrma;
	
	@Column(name = "customer_id")
	private Integer customerId;
	@Transient
	private String companyName;
	
	@Column(name = "express_com")
	private String expressCom;
	
	@Column(name = "express_no")
	private String expressNo;
	
	@Column(name = "boxes")
	private Integer boxes;
	
	@Column(name = "remarks")
	private String remarks;
	
	@Column(name = "tat_level")
	private Integer tatLevel;
	
	@Column(name = "close_time")
	private Date closeTime;
	
	@Column(name = "total_qty")
	private Integer totalQty;
	
	@Column(name = "total_finished")
	private Integer totalFinished;
	
	@Column(name = "total_remain")
	private Integer totalRemain;
	
	@Column(name = "keyin_status")
	@Enumerated(EnumType.STRING)
	private KeyinStatus keyinStatus;
	
	@Column(name = "receive_status")
	@Enumerated(EnumType.STRING)
	private ReceiveStatus receiveStatus;
	
	@Column(name = "do_status")
	@Enumerated(EnumType.STRING)
	private RmaDoStatus doStatus;
	
	@Column(name = "receiver")
	private Integer receiver;
	
	@Column(name = "receive_time")
	private Date receiveTime;
	
	@Column(name = "last_modifier")
	private Integer lastModifier;
	
	@Column(name = "last_modify_time")
	private Date lastModifyTime;

	@Column(name = "received_pdf_path")
	private String receivedPdfPath;	// received pdf 存储路径
	
	@Transient
	private Date closeTimeFrom;
	@Transient
	private Date closeTimeTo;
	@Transient
	private String receiverName;
	@Transient
	private Date receiveTimeFrom;
	@Transient
	private Date receiveTimeTo;
	
	public Order() {
		
	}
	
	public Order(Validity validity) {
		setValidity(validity);
	}
	
	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getRma() {
		return this.rma;
	}

	public void setRma(String rma) {
		this.rma = rma;
	}

	public String getCustrma() {
		return this.custrma;
	}

	public void setCustrma(String custrma) {
		this.custrma = custrma;
	}

	public Integer getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getExpressCom() {
		return this.expressCom;
	}

	public void setExpressCom(String expressCom) {
		this.expressCom = expressCom;
	}

	public String getExpressNo() {
		return this.expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public Integer getBoxes() {
		return this.boxes;
	}

	public void setBoxes(Integer boxes) {
		this.boxes = boxes;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getTatLevel() {
		return tatLevel;
	}

	public void setTatLevel(Integer tatLevel) {
		this.tatLevel = tatLevel;
	}

	public Date getCloseTime() {
		return this.closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public Integer getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(Integer totalQty) {
		this.totalQty = totalQty;
	}

	public Integer getTotalFinished() {
		return totalFinished;
	}

	public void setTotalFinished(Integer totalFinished) {
		this.totalFinished = totalFinished;
	}

	public Integer getTotalRemain() {
		return totalRemain;
	}

	public void setTotalRemain(Integer totalRemain) {
		this.totalRemain = totalRemain;
	}

	public KeyinStatus getKeyinStatus() {
		return keyinStatus;
	}

	public void setKeyinStatus(KeyinStatus keyinStatus) {
		this.keyinStatus = keyinStatus;
	}

	public ReceiveStatus getReceiveStatus() {
		return receiveStatus;
	}

	public void setReceiveStatus(ReceiveStatus receiveStatus) {
		this.receiveStatus = receiveStatus;
	}

	public RmaDoStatus getDoStatus() {
		return doStatus;
	}

	public void setDoStatus(RmaDoStatus doStatus) {
		this.doStatus = doStatus;
	}

	public Integer getReceiver() {
		return this.receiver;
	}

	public void setReceiver(Integer receiver) {
		this.receiver = receiver;
	}

	public Date getReceiveTime() {
		return this.receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public Integer getLastModifier() {
		return this.lastModifier;
	}

	public void setLastModifier(Integer lastModifier) {
		this.lastModifier = lastModifier;
	}

	public Date getLastModifyTime() {
		return this.lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getReceivedPdfPath() {
		return receivedPdfPath;
	}
	
	public String getReceivedPdfPathFormatted() {
		if (StringUtils.isBlank(receivedPdfPath)) {
			return receivedPdfPath;
		}
		
		return receivedPdfPath.substring(receivedPdfPath.lastIndexOf("/") + 1);
	}

	public void setReceivedPdfPath(String receivedPdfPath) {
		this.receivedPdfPath = receivedPdfPath;
	}

	public Date getCloseTimeFrom() {
		return closeTimeFrom;
	}

	public void setCloseTimeFrom(Date closeTimeFrom) {
		this.closeTimeFrom = closeTimeFrom;
	}

	public Date getCloseTimeTo() {
		return closeTimeTo;
	}

	public void setCloseTimeTo(Date closeTimeTo) {
		this.closeTimeTo = closeTimeTo;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public Date getReceiveTimeFrom() {
		return receiveTimeFrom;
	}

	public void setReceiveTimeFrom(Date receiveTimeFrom) {
		this.receiveTimeFrom = receiveTimeFrom;
	}

	public Date getReceiveTimeTo() {
		return receiveTimeTo;
	}

	public void setReceiveTimeTo(Date receiveTimeTo) {
		this.receiveTimeTo = receiveTimeTo;
	}

	@Override
	public Serializable getId() {
		return this.orderId;
	}

}
