 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.tb.entity;

import com.fabaoframework.modules.mybatis.BaseEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class TbLedger extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "TbLedger";
	public static final String ALIAS_VAC_LEDGER_TALK_DUR = "vacLedgerTalkDur";
	public static final String ALIAS_VAC_LEDGER_SERIAL = "vacLedgerSerial";
	public static final String ALIAS_VAC_LEDGER_NUMBER = "vacLedgerNumber";
	public static final String ALIAS_NUM_LEDGER_PROVINCE_ID = "numLedgerProvinceId";
	public static final String ALIAS_NUM_LEDGER_CITY_ID = "numLedgerCityId";
	public static final String ALIAS_VAC_LEDGER_PROVINCE_NAME = "vacLedgerProvinceName";
	public static final String ALIAS_VAC_LEDGER_CITY_NAME = "vacLedgerCityName";
	public static final String ALIAS_NUM_LEDGER_CALL_QUEUE = "numLedgerCallQueue";
	public static final String ALIAS_VAC_LEDGER_AGENT_CODE = "vacLedgerAgentCode";
	public static final String ALIAS_VAC_LEDGER_AGENT_NAME = "vacLedgerAgentName";
	public static final String ALIAS_VAC_LEDGER_BUSINESS_TYPE = "vacLedgerBusinessType";
	public static final String ALIAS_VAC_LEDGER_CLIENT_ACCOUNT = "vacLedgerClientAccount";
	public static final String ALIAS_VAC_LEDGER_LAWYER_SUGGESTION = "vacLedgerLawyerSuggestion";
	public static final String ALIAS_NUM_LEDGER_HANDLE = "numLedgerHandle";
	public static final String ALIAS_NUM_CLIENT_ID = "numClientId";
	public static final String ALIAS_NUM_LEDGER_INCIDENT_PORVINCE_ID = "numLedgerIncidentPorvinceId";
	public static final String ALIAS_NUM_LEDGER_INCIDENT_CITY_ID = "numLedgerIncidentCityId";
	public static final String ALIAS_VAC_LEDGER_INCIDENT_ADDRESS = "vacLedgerIncidentAddress";
	public static final String ALIAS_NUM_LEDGER_STATUS = "numLedgerStatus";
	public static final String ALIAS_NUM_LEDGER_OPERATOR_ID = "numLedgerOperatorId";
	public static final String ALIAS_VAC_LEDGER_OPERATOR_NAME = "vacLedgerOperatorName";
	public static final String ALIAS_NUM_SPECIALTY_ID = "numSpecialtyId";
	
	//date formats
	
	//columns START
	private java.lang.String vacLedgerTalkDur;
	private java.lang.String vacLedgerSerial;
	private java.lang.String vacLedgerNumber;
	private java.lang.Integer numLedgerProvinceId;
	private java.lang.Integer numLedgerCityId;
	private java.lang.String vacLedgerProvinceName;
	private java.lang.String vacLedgerCityName;
	private java.lang.String numLedgerCallQueue;
	private java.lang.String vacLedgerAgentCode;
	private java.lang.String vacLedgerAgentName;
	private java.lang.String vacLedgerBusinessType;
	private java.lang.String vacLedgerClientAccount;
	private java.lang.String vacLedgerLawyerSuggestion;
	private java.lang.Integer numLedgerHandle;
	private java.lang.Integer numClientId;
	private java.lang.Integer numLedgerIncidentPorvinceId;
	private java.lang.Integer numLedgerIncidentCityId;
	private java.lang.String vacLedgerIncidentAddress;
	private java.lang.Integer numLedgerStatus;
	private java.lang.Integer numLedgerOperatorId;
	private java.lang.String vacLedgerOperatorName;
	private java.lang.Integer numSpecialtyId;
	//columns END
	
	
	
	private TbClients tbClients;
	private java.lang.Long isOrder;
	private String callTimeStart;
	private String callTimeEnd;
	private java.lang.String vacLedgerBusinessName;//业务类型名称
	private java.lang.String vacSpecialtyName;//业务类型名称
	private java.lang.Integer orderCount;
	private java.lang.String fileName;
	private java.lang.String vacLedgerBusinessName0;
	private java.lang.String vacLedgerBusinessName1;
	private java.lang.String vacLedgerBusinessName2;
	private java.lang.String vacLedgerBusinessName3;
	private java.lang.String vacLedgerBusinessName4;
	private java.lang.String VacCallAreaProvinceName;
	private java.lang.String VacCallAreaCityName;
	private java.lang.String modifierName;
	//date formats
	


	public TbLedger(){
	}

	public TbLedger(
		java.lang.Long id
	){
		this.id = id;
	}

	public java.lang.String getModifierName() {
		return modifierName;
	}

	public void setModifierName(java.lang.String modifierName) {
		this.modifierName = modifierName;
	}

	public java.lang.String getVacCallAreaProvinceName() {
		return VacCallAreaProvinceName;
	}

	public void setVacCallAreaProvinceName(java.lang.String vacCallAreaProvinceName) {
		VacCallAreaProvinceName = vacCallAreaProvinceName;
	}

	public java.lang.String getVacCallAreaCityName() {
		return VacCallAreaCityName;
	}

	public void setVacCallAreaCityName(java.lang.String vacCallAreaCityName) {
		VacCallAreaCityName = vacCallAreaCityName;
	}

	public void setVacLedgerSerial(java.lang.String value) {
		this.vacLedgerSerial = value;
	}
	
	public java.lang.String getVacLedgerSerial() {
		return this.vacLedgerSerial;
	}
	public void setVacLedgerNumber(java.lang.String value) {
		this.vacLedgerNumber = value;
	}
	
	public java.lang.String getVacLedgerNumber() {
		return this.vacLedgerNumber;
	}
	public void setNumLedgerProvinceId(java.lang.Integer value) {
		this.numLedgerProvinceId = value;
	}
	
	public java.lang.Integer getNumLedgerProvinceId() {
		return this.numLedgerProvinceId;
	}
	public void setNumLedgerCityId(java.lang.Integer value) {
		this.numLedgerCityId = value;
	}
	
	public java.lang.Integer getNumLedgerCityId() {
		return this.numLedgerCityId;
	}
	public void setVacLedgerProvinceName(java.lang.String value) {
		this.vacLedgerProvinceName = value;
	}
	
	public java.lang.String getVacLedgerProvinceName() {
		return this.vacLedgerProvinceName;
	}
	public void setVacLedgerCityName(java.lang.String value) {
		this.vacLedgerCityName = value;
	}
	
	public java.lang.String getVacLedgerCityName() {
		return this.vacLedgerCityName;
	}
	public void setNumLedgerCallQueue(java.lang.String value) {
		this.numLedgerCallQueue = value;
	}
	
	public java.lang.String getNumLedgerCallQueue() {
		return this.numLedgerCallQueue;
	}
	public void setVacLedgerAgentCode(java.lang.String value) {
		this.vacLedgerAgentCode = value;
	}
	
	public java.lang.String getVacLedgerAgentCode() {
		return this.vacLedgerAgentCode;
	}
	public void setVacLedgerAgentName(java.lang.String value) {
		this.vacLedgerAgentName = value;
	}
	
	public java.lang.String getVacLedgerAgentName() {
		return this.vacLedgerAgentName;
	}
	public void setVacLedgerBusinessType(java.lang.String value) {
		this.vacLedgerBusinessType = value;
	}
	
	public java.lang.String getVacLedgerBusinessType() {
		return this.vacLedgerBusinessType;
	}
	public void setVacLedgerClientAccount(java.lang.String value) {
		this.vacLedgerClientAccount = value;
	}
	
	public java.lang.String getVacLedgerClientAccount() {
		return this.vacLedgerClientAccount;
	}
	public void setVacLedgerLawyerSuggestion(java.lang.String value) {
		this.vacLedgerLawyerSuggestion = value;
	}
	
	public java.lang.String getVacLedgerLawyerSuggestion() {
		return this.vacLedgerLawyerSuggestion;
	}
	public void setNumLedgerHandle(java.lang.Integer value) {
		this.numLedgerHandle = value;
	}
	
	public java.lang.Integer getNumLedgerHandle() {
		return this.numLedgerHandle;
	}
	public void setNumClientId(java.lang.Integer value) {
		this.numClientId = value;
	}
	
	public java.lang.Integer getNumClientId() {
		return this.numClientId;
	}
	public void setNumLedgerIncidentPorvinceId(java.lang.Integer value) {
		this.numLedgerIncidentPorvinceId = value;
	}
	
	public java.lang.Integer getNumLedgerIncidentPorvinceId() {
		return this.numLedgerIncidentPorvinceId;
	}
	public void setNumLedgerIncidentCityId(java.lang.Integer value) {
		this.numLedgerIncidentCityId = value;
	}
	
	public java.lang.Integer getNumLedgerIncidentCityId() {
		return this.numLedgerIncidentCityId;
	}
	public void setVacLedgerIncidentAddress(java.lang.String value) {
		this.vacLedgerIncidentAddress = value;
	}
	
	public java.lang.String getVacLedgerIncidentAddress() {
		return this.vacLedgerIncidentAddress;
	}
	public void setNumLedgerStatus(java.lang.Integer value) {
		this.numLedgerStatus = value;
	}
	
	public java.lang.Integer getNumLedgerStatus() {
		return this.numLedgerStatus;
	}
	public void setNumLedgerOperatorId(java.lang.Integer value) {
		this.numLedgerOperatorId = value;
	}
	
	public java.lang.Integer getNumLedgerOperatorId() {
		return this.numLedgerOperatorId;
	}
	public void setVacLedgerOperatorName(java.lang.String value) {
		this.vacLedgerOperatorName = value;
	}
	
	public java.lang.String getVacLedgerOperatorName() {
		return this.vacLedgerOperatorName;
	}
	public void setNumSpecialtyId(java.lang.Integer value) {
		this.numSpecialtyId = value;
	}
	
	public java.lang.Integer getNumSpecialtyId() {
		return this.numSpecialtyId;
	}
	
	
    public TbClients getTbClients() {
		return tbClients;
	}

	public void setTbClients(TbClients tbClients) {
		this.tbClients = tbClients;
	}

	public java.lang.Long getIsOrder() {
		return isOrder;
	}

	public void setIsOrder(java.lang.Long isOrder) {
		this.isOrder = isOrder;
	}

	public String getCallTimeStart() {
		return callTimeStart;
	}
	
	public void setCallTimeStart(String callTimeStart) {
		this.callTimeStart = callTimeStart;
	}
	
	public String getCallTimeEnd() {
		return callTimeEnd;
	}
	
	public void setCallTimeEnd(String callTimeEnd) {
		this.callTimeEnd = callTimeEnd;
	}
	

	public java.lang.String getVacLedgerBusinessName() {
		return vacLedgerBusinessName;
	}


	public void setVacLedgerBusinessName(java.lang.String vacLedgerBusinessName) {
		this.vacLedgerBusinessName = vacLedgerBusinessName;
	}

	public java.lang.Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(java.lang.Integer orderCount) {
		this.orderCount = orderCount;
	}

	public java.lang.String getFileName() {
		return fileName;
	}

	public void setFileName(java.lang.String fileName) {
		this.fileName = fileName;
	}

	public java.lang.String getVacSpecialtyName() {
		return vacSpecialtyName;
	}

	public void setVacSpecialtyName(java.lang.String vacSpecialtyName) {
		this.vacSpecialtyName = vacSpecialtyName;
	}

	
    public java.lang.String getVacLedgerTalkDur() {
		return vacLedgerTalkDur;
	}

	public void setVacLedgerTalkDur(java.lang.String vacLedgerTalkDur) {
		this.vacLedgerTalkDur = vacLedgerTalkDur;
	}

	public java.lang.String getVacLedgerBusinessName0() {
		return vacLedgerBusinessName0;
	}

	public void setVacLedgerBusinessName0(java.lang.String vacLedgerBusinessName0) {
		this.vacLedgerBusinessName0 = vacLedgerBusinessName0;
	}

	public java.lang.String getVacLedgerBusinessName1() {
		return vacLedgerBusinessName1;
	}

	public void setVacLedgerBusinessName1(java.lang.String vacLedgerBusinessName1) {
		this.vacLedgerBusinessName1 = vacLedgerBusinessName1;
	}

	public java.lang.String getVacLedgerBusinessName2() {
		return vacLedgerBusinessName2;
	}

	public void setVacLedgerBusinessName2(java.lang.String vacLedgerBusinessName2) {
		this.vacLedgerBusinessName2 = vacLedgerBusinessName2;
	}

	public java.lang.String getVacLedgerBusinessName3() {
		return vacLedgerBusinessName3;
	}

	public void setVacLedgerBusinessName3(java.lang.String vacLedgerBusinessName3) {
		this.vacLedgerBusinessName3 = vacLedgerBusinessName3;
	}

	public java.lang.String getVacLedgerBusinessName4() {
		return vacLedgerBusinessName4;
	}

	public void setVacLedgerBusinessName4(java.lang.String vacLedgerBusinessName4) {
		this.vacLedgerBusinessName4 = vacLedgerBusinessName4;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("VacLedgerTalkDur",getVacLedgerTalkDur())		
		.append("VacLedgerSerial",getVacLedgerSerial())		
		.append("VacLedgerNumber",getVacLedgerNumber())		
		.append("NumLedgerProvinceId",getNumLedgerProvinceId())		
		.append("NumLedgerCityId",getNumLedgerCityId())		
		.append("VacLedgerProvinceName",getVacLedgerProvinceName())		
		.append("VacLedgerCityName",getVacLedgerCityName())		
		.append("NumLedgerCallQueue",getNumLedgerCallQueue())		
		.append("VacLedgerAgentCode",getVacLedgerAgentCode())		
		.append("VacLedgerAgentName",getVacLedgerAgentName())		
		.append("VacLedgerBusinessType",getVacLedgerBusinessType())		
		.append("VacLedgerClientAccount",getVacLedgerClientAccount())		
		.append("VacLedgerLawyerSuggestion",getVacLedgerLawyerSuggestion())		
		.append("NumLedgerHandle",getNumLedgerHandle())		
		.append("NumClientId",getNumClientId())		
		.append("NumLedgerIncidentPorvinceId",getNumLedgerIncidentPorvinceId())		
		.append("NumLedgerIncidentCityId",getNumLedgerIncidentCityId())		
		.append("VacLedgerIncidentAddress",getVacLedgerIncidentAddress())		
		.append("Creator",getCreator())		
		.append("Modifier",getModifier())		
		.append("GmtCreated",getGmtCreated())		
		.append("GmtModified",getGmtModified())		
		.append("IsDeleted",getIsDeleted())		
		.append("NumLedgerStatus",getNumLedgerStatus())		
		.append("NumLedgerOperatorId",getNumLedgerOperatorId())		
		.append("VacLedgerOperatorName",getVacLedgerOperatorName())		
		.append("NumSpecialtyId",getNumSpecialtyId())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getVacLedgerTalkDur())
		.append(getVacLedgerSerial())
		.append(getVacLedgerNumber())
		.append(getNumLedgerProvinceId())
		.append(getNumLedgerCityId())
		.append(getVacLedgerProvinceName())
		.append(getVacLedgerCityName())
		.append(getNumLedgerCallQueue())
		.append(getVacLedgerAgentCode())
		.append(getVacLedgerAgentName())
		.append(getVacLedgerBusinessType())
		.append(getVacLedgerClientAccount())
		.append(getVacLedgerLawyerSuggestion())
		.append(getNumLedgerHandle())
		.append(getNumClientId())
		.append(getNumLedgerIncidentPorvinceId())
		.append(getNumLedgerIncidentCityId())
		.append(getVacLedgerIncidentAddress())
		.append(getCreator())
		.append(getModifier())
		.append(getGmtCreated())
		.append(getGmtModified())
		.append(getIsDeleted())
		.append(getNumLedgerStatus())
		.append(getNumLedgerOperatorId())
		.append(getVacLedgerOperatorName())
		.append(getNumSpecialtyId())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof TbLedger == false) return false;
		if(this == obj) return true;
		TbLedger other = (TbLedger)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getVacLedgerTalkDur(),other.getVacLedgerTalkDur())

		.append(getVacLedgerSerial(),other.getVacLedgerSerial())

		.append(getVacLedgerNumber(),other.getVacLedgerNumber())

		.append(getNumLedgerProvinceId(),other.getNumLedgerProvinceId())

		.append(getNumLedgerCityId(),other.getNumLedgerCityId())

		.append(getVacLedgerProvinceName(),other.getVacLedgerProvinceName())

		.append(getVacLedgerCityName(),other.getVacLedgerCityName())

		.append(getNumLedgerCallQueue(),other.getNumLedgerCallQueue())

		.append(getVacLedgerAgentCode(),other.getVacLedgerAgentCode())

		.append(getVacLedgerAgentName(),other.getVacLedgerAgentName())

		.append(getVacLedgerBusinessType(),other.getVacLedgerBusinessType())

		.append(getVacLedgerClientAccount(),other.getVacLedgerClientAccount())

		.append(getVacLedgerLawyerSuggestion(),other.getVacLedgerLawyerSuggestion())

		.append(getNumLedgerHandle(),other.getNumLedgerHandle())

		.append(getNumClientId(),other.getNumClientId())

		.append(getNumLedgerIncidentPorvinceId(),other.getNumLedgerIncidentPorvinceId())

		.append(getNumLedgerIncidentCityId(),other.getNumLedgerIncidentCityId())

		.append(getVacLedgerIncidentAddress(),other.getVacLedgerIncidentAddress())

		.append(getCreator(),other.getCreator())

		.append(getModifier(),other.getModifier())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getGmtModified(),other.getGmtModified())

		.append(getIsDeleted(),other.getIsDeleted())

		.append(getNumLedgerStatus(),other.getNumLedgerStatus())

		.append(getNumLedgerOperatorId(),other.getNumLedgerOperatorId())

		.append(getVacLedgerOperatorName(),other.getVacLedgerOperatorName())

		.append(getNumSpecialtyId(),other.getNumSpecialtyId())

			.isEquals();
	}
}

