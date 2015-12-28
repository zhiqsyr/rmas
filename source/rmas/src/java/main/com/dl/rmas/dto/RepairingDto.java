package com.dl.rmas.dto;

import java.math.BigInteger;

/**
 * 员工在修产品数量
 * 
 * @author dongbz 2015-1-6
 */
public class RepairingDto {

	private String userNo;	// 员工编号
	private String userName;	// 员工姓名
	private BigInteger repairingCount;	// 在修产品数
	
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
	public BigInteger getRepairingCount() {
		return repairingCount;
	}
	public void setRepairingCount(BigInteger repairingCount) {
		this.repairingCount = repairingCount;
	}
	
}
