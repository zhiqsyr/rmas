package com.dl.core.jxls.export;

import java.util.Date;
import java.util.List;

public class EDto {
private String num;//编号
	
	private String eventName;//事件名称
	
	private String eventDept;//事发单位
	
	private String investigationDept;//调查单位
	
	private String investigationUser;//调查人员
	
	private String describe;//概述
	
	private String caseDesc; // 事件经过
	
	private String process;//调查经过
	
	private String staff; // 人员描述
	private List<String> personOfBaseList;//基本人员
	
	private String facilityOfBase;//基本设施
	
	private String environment;//环境
	
	private String reconnaissance;//现场情况
	
	private String damageDesc; // 损伤情况
	
	private String personnelOfDamage;//损伤人员
	
	private String facilityOfDamage;//损伤设备
	
	private String investRecode; // 调查笔录（事件叙述）
	private String analyzeDesc; // 数据及相关记录分析
	private String otherDesc; // 其他 
	
	private String analysisOfCauses;//原因分析
	
	private String conclusion;//调查结论
	private String eventCharacDesc; // 事件性质
	
	private String measure; // 整改措施
	
	private String suggest;//建议
	private List<String> personOfSuggestList; // 被处罚人员描述
	
	private String teamIdea;//调查组意见
	
	private String headmanOfTeam;//负责人
	
	private Date investigationTime; //日期

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventDept() {
		return eventDept;
	}

	public void setEventDept(String eventDept) {
		this.eventDept = eventDept;
	}

	public String getInvestigationDept() {
		return investigationDept;
	}

	public void setInvestigationDept(String investigationDept) {
		this.investigationDept = investigationDept;
	}

	public String getInvestigationUser() {
		return investigationUser;
	}

	public void setInvestigationUser(String investigationUser) {
		this.investigationUser = investigationUser;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getCaseDesc() {
		return caseDesc;
	}

	public void setCaseDesc(String caseDesc) {
		this.caseDesc = caseDesc;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public String getStaff() {
		return staff;
	}

	public void setStaff(String staff) {
		this.staff = staff;
	}

	public List<String> getPersonOfBaseList() {
		return personOfBaseList;
	}

	public void setPersonOfBaseList(List<String> personOfBaseList) {
		this.personOfBaseList = personOfBaseList;
	}

	public String getFacilityOfBase() {
		return facilityOfBase;
	}

	public void setFacilityOfBase(String facilityOfBase) {
		this.facilityOfBase = facilityOfBase;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getReconnaissance() {
		return reconnaissance;
	}

	public void setReconnaissance(String reconnaissance) {
		this.reconnaissance = reconnaissance;
	}

	public String getDamageDesc() {
		return damageDesc;
	}

	public void setDamageDesc(String damageDesc) {
		this.damageDesc = damageDesc;
	}

	public String getPersonnelOfDamage() {
		return personnelOfDamage;
	}

	public void setPersonnelOfDamage(String personnelOfDamage) {
		this.personnelOfDamage = personnelOfDamage;
	}

	public String getFacilityOfDamage() {
		return facilityOfDamage;
	}

	public void setFacilityOfDamage(String facilityOfDamage) {
		this.facilityOfDamage = facilityOfDamage;
	}

	public String getInvestRecode() {
		return investRecode;
	}

	public void setInvestRecode(String investRecode) {
		this.investRecode = investRecode;
	}

	public String getAnalyzeDesc() {
		return analyzeDesc;
	}

	public void setAnalyzeDesc(String analyzeDesc) {
		this.analyzeDesc = analyzeDesc;
	}

	public String getOtherDesc() {
		return otherDesc;
	}

	public void setOtherDesc(String otherDesc) {
		this.otherDesc = otherDesc;
	}

	public String getAnalysisOfCauses() {
		return analysisOfCauses;
	}

	public void setAnalysisOfCauses(String analysisOfCauses) {
		this.analysisOfCauses = analysisOfCauses;
	}

	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}

	public String getEventCharacDesc() {
		return eventCharacDesc;
	}

	public void setEventCharacDesc(String eventCharacDesc) {
		this.eventCharacDesc = eventCharacDesc;
	}

	public String getMeasure() {
		return measure;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}

	public String getSuggest() {
		return suggest;
	}

	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}

	public List<String> getPersonOfSuggestList() {
		return personOfSuggestList;
	}

	public void setPersonOfSuggestList(List<String> personOfSuggestList) {
		this.personOfSuggestList = personOfSuggestList;
	}

	public String getTeamIdea() {
		return teamIdea;
	}

	public void setTeamIdea(String teamIdea) {
		this.teamIdea = teamIdea;
	}

	public String getHeadmanOfTeam() {
		return headmanOfTeam;
	}

	public void setHeadmanOfTeam(String headmanOfTeam) {
		this.headmanOfTeam = headmanOfTeam;
	}

	public Date getInvestigationTime() {
		return investigationTime;
	}

	public void setInvestigationTime(Date investigationTime) {
		this.investigationTime = investigationTime;
	}
}
