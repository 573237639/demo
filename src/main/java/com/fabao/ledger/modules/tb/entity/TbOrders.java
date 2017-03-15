 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.tb.entity;

import com.fabaoframework.modules.mybatis.BaseEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class TbOrders extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8810336963256072228L;
	//alias
	public static final String TABLE_ALIAS = "TbOrders";
	public static final String ALIAS_VAC_ORDER_SERIAL = "vacOrderSerial";
	public static final String ALIAS_VAC_ORDER_NUMBER = "vacOrderNumber";
	public static final String ALIAS_VAC_ORDER_AGENT_CODE = "vacOrderAgentCode";
	public static final String ALIAS_VAC_ORDER_AGENT_NAME = "vacOrderAgentName";
	public static final String ALIAS_VAC_ORDER_BUSINESS_TYPE = "vacOrderBusinessType";
	public static final String ALIAS_VAC_ORDER_OTHER_CONTACTS = "vacOrderOtherContacts";
	public static final String ALIAS_VAC_ORDER_TITLE = "vacOrderTitle";
	public static final String ALIAS_VAC_ORDER_TYPE = "vacOrderType";
	public static final String ALIAS_DATE_ORDER_TERM = "dateOrderTerm";
	public static final String ALIAS_VAC_ORDER_LAWYER_NAME = "vacOrderLawyerName";
	public static final String ALIAS_VAC_ORDER_CREATE_REASON = "vacOrderCreateReason";
	public static final String ALIAS_VAC_ORDER_TASK_TYPE = "vacOrderTaskType";
	public static final String ALIAS_VAC_ORDER_ACCOUNT = "vacOrderAccount";
	public static final String ALIAS_VAC_ORDER_SUGGESTION = "vacOrderSuggestion";
	public static final String ALIAS_NUM_ORDER_INCIDENT_PROVINCE_ID = "numOrderIncidentProvinceId";
	public static final String ALIAS_NUM_ORDER_INCIDENT_CITY_ID = "numOrderIncidentCityId";
	public static final String ALIAS_VAC_ORDER_INCIDENT_ADDRESS = "vacOrderIncidentAddress";
	public static final String ALIAS_NUM_ORDER_CONTACT_PROVINCE_ID = "numOrderContactProvinceId";
	public static final String ALIAS_NUM_ORDER_CONTACT_CITY_ID = "numOrderContactCityId";
	public static final String ALIAS_VAC_ORDER_CONTACT_ADDRESS = "vacOrderContactAddress";
	public static final String ALIAS_NUM_ORDER_RECEIVE_PROVINCE_ID = "numOrderReceiveProvinceId";
	public static final String ALIAS_NUM_ORDER_RECEIVE_CITY_ID = "numOrderReceiveCityId";
	public static final String ALIAS_NUM_CLIENT_ID = "numClientId";
	public static final String ALIAS_NUM_ORDER_STATUS = "numOrderStatus";
	public static final String ALIAS_NUM_ORDER_USER_ID = "numOrderUserId";
	public static final String ALIAS_NUM_ALLOT_USER_ID = "numAllotUserId";
	public static final String ALIAS_VAC_ALLOT_USER_NAME = "vacAllotUserName";
	public static final String ALIAS_DATE_ALLOT_TIME = "dateAllotTime";
	
	//date formats
			public static final String FORMAT_DATE_ORDER_TERM = DATE_TIME_FORMAT;
			public static final String FORMAT_DATE_ALLOT_TIME = DATE_TIME_FORMAT;
	
	//columns START
	private java.lang.String vacOrderSerial;
	private java.lang.String vacOrderNumber;
	private java.lang.String vacOrderAgentCode;
	private java.lang.String vacOrderAgentName;
	private java.lang.String vacOrderBusinessType;
	private java.lang.String vacOrderOtherContacts;
	private java.lang.String vacOrderTitle;
	private java.lang.Integer vacOrderType;
	private java.util.Date dateOrderTerm;
	private java.lang.String vacOrderLawyerName;
	private java.lang.String vacOrderCreateReason;
	private java.lang.Integer vacOrderTaskType;
	private java.lang.String vacOrderAccount;
	private java.lang.String vacOrderSuggestion;
	private java.lang.Integer numOrderIncidentProvinceId;
	private java.lang.Integer numOrderIncidentCityId;
	private java.lang.String vacOrderIncidentAddress;
	private java.lang.Integer numOrderContactProvinceId;
	private java.lang.Integer numOrderContactCityId;
	private java.lang.String vacOrderContactAddress;
	private java.lang.Integer numOrderReceiveProvinceId;
	private java.lang.Integer numOrderReceiveCityId;
	private java.lang.Integer numClientId;
	private java.lang.Integer numOrderStatus;
	private java.lang.Integer numOrderUserId;
	private java.lang.Integer numAllotUserId;
	private java.lang.String vacAllotUserName;
	private java.util.Date dateAllotTime;
	//columns END

	private TbClients tbClients;
	private String gmtCreatedBegin;//工单产生时间开始时间
	private String gmtCreatedEnd;//工单产生时间结束时间
	private String noPassReason;//审核不通过原因
	private java.lang.String vacOrderBusinessName;//法律领域名称
	private java.lang.Integer count;
	private java.lang.String fileName;//录音名称
	private java.lang.String dateOrderTermView;//工单期限 (三个工作日  三小时)
	private java.lang.String vacOrderReceiveProvinceName;
	private java.lang.String vacOrderReceiveCityName;
	private java.lang.String VacCallAreaProvinceName;
	private java.lang.String VacCallAreaCityName;
	private java.lang.String modifierName;
	private java.lang.String vacCompleteTime;//复审时间
	private java.lang.String vacReexamineOpinion;//处理结果
	private java.lang.String vacReexamineTaskstate;//复审结果
	private java.lang.String vacSituation;//复审情况
	public TbOrders(){
	}

	public TbOrders(
		java.lang.Long id
	){
		this.id = id;
	}


	public java.lang.String getVacCompleteTime() {
		return vacCompleteTime;
	}

	public void setVacCompleteTime(java.lang.String vacCompleteTime) {
		this.vacCompleteTime = vacCompleteTime;
	}

	public java.lang.String getVacReexamineOpinion() {
		return vacReexamineOpinion;
	}

	public void setVacReexamineOpinion(java.lang.String vacReexamineOpinion) {
		this.vacReexamineOpinion = vacReexamineOpinion;
	}

	public java.lang.String getVacReexamineTaskstate() {
		return vacReexamineTaskstate;
	}

	public void setVacReexamineTaskstate(java.lang.String vacReexamineTaskstate) {
		this.vacReexamineTaskstate = vacReexamineTaskstate;
	}

	public java.lang.String getVacSituation() {
		return vacSituation;
	}

	public void setVacSituation(java.lang.String vacSituation) {
		this.vacSituation = vacSituation;
	}

	public java.lang.String getModifierName() {
		return modifierName;
	}

	public void setModifierName(java.lang.String modifierName) {
		this.modifierName = modifierName;
	}

	public java.lang.String getVacOrderReceiveProvinceName() {
		return vacOrderReceiveProvinceName;
	}

	public void setVacOrderReceiveProvinceName(
			java.lang.String vacOrderReceiveProvinceName) {
		this.vacOrderReceiveProvinceName = vacOrderReceiveProvinceName;
	}

	public java.lang.String getVacOrderReceiveCityName() {
		return vacOrderReceiveCityName;
	}

	public void setVacOrderReceiveCityName(java.lang.String vacOrderReceiveCityName) {
		this.vacOrderReceiveCityName = vacOrderReceiveCityName;
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

	public TbClients getTbClients() {
		return tbClients;
	}

	public void setTbClients(TbClients tbClients) {
		this.tbClients = tbClients;
	}
	
	public void setVacOrderSerial(java.lang.String value) {
		this.vacOrderSerial = value;
	}
	
	public java.lang.String getVacOrderSerial() {
		return this.vacOrderSerial;
	}
	public void setVacOrderNumber(java.lang.String value) {
		this.vacOrderNumber = value;
	}
	
	public java.lang.String getVacOrderNumber() {
		return this.vacOrderNumber;
	}
	public void setVacOrderAgentCode(java.lang.String value) {
		this.vacOrderAgentCode = value;
	}
	
	public java.lang.String getVacOrderAgentCode() {
		return this.vacOrderAgentCode;
	}
	public void setVacOrderAgentName(java.lang.String value) {
		this.vacOrderAgentName = value;
	}
	
	public java.lang.String getVacOrderAgentName() {
		return this.vacOrderAgentName;
	}
	public void setVacOrderBusinessType(java.lang.String value) {
		this.vacOrderBusinessType = value;
	}
	
	public java.lang.String getVacOrderBusinessType() {
		return this.vacOrderBusinessType;
	}
	public void setVacOrderOtherContacts(java.lang.String value) {
		this.vacOrderOtherContacts = value;
	}
	
	public java.lang.String getVacOrderOtherContacts() {
		return this.vacOrderOtherContacts;
	}
	public void setVacOrderTitle(java.lang.String value) {
		this.vacOrderTitle = value;
	}
	
	public java.lang.String getVacOrderTitle() {
		return this.vacOrderTitle;
	}
	public void setVacOrderType(java.lang.Integer value) {
		this.vacOrderType = value;
	}
	
	public java.lang.Integer getVacOrderType() {
		return this.vacOrderType;
	}
	public String getDateOrderTermString() {
		return date2String(getDateOrderTerm(), FORMAT_DATE_ORDER_TERM);
	}
	public void setDateOrderTermString(String value) {
		setDateOrderTerm(string2Date(value, FORMAT_DATE_ORDER_TERM,java.util.Date.class));
	}
	
	public void setDateOrderTerm(java.util.Date value) {
		this.dateOrderTerm = value;
	}
	
	public java.util.Date getDateOrderTerm() {
		return this.dateOrderTerm;
	}
	public void setVacOrderLawyerName(java.lang.String value) {
		this.vacOrderLawyerName = value;
	}
	
	public java.lang.String getVacOrderLawyerName() {
		return this.vacOrderLawyerName;
	}
	public void setVacOrderCreateReason(java.lang.String value) {
		this.vacOrderCreateReason = value;
	}
	
	public java.lang.String getVacOrderCreateReason() {
		return this.vacOrderCreateReason;
	}
	public void setVacOrderTaskType(java.lang.Integer value) {
		this.vacOrderTaskType = value;
	}
	
	public java.lang.Integer getVacOrderTaskType() {
		return this.vacOrderTaskType;
	}
	public void setVacOrderAccount(java.lang.String value) {
		this.vacOrderAccount = value;
	}
	
	public java.lang.String getVacOrderAccount() {
		return this.vacOrderAccount;
	}
	public void setVacOrderSuggestion(java.lang.String value) {
		this.vacOrderSuggestion = value;
	}
	
	public java.lang.String getVacOrderSuggestion() {
		return this.vacOrderSuggestion;
	}
	public void setNumOrderIncidentProvinceId(java.lang.Integer value) {
		this.numOrderIncidentProvinceId = value;
	}
	
	public java.lang.Integer getNumOrderIncidentProvinceId() {
		return this.numOrderIncidentProvinceId;
	}
	public void setNumOrderIncidentCityId(java.lang.Integer value) {
		this.numOrderIncidentCityId = value;
	}
	
	public java.lang.Integer getNumOrderIncidentCityId() {
		return this.numOrderIncidentCityId;
	}
	public void setVacOrderIncidentAddress(java.lang.String value) {
		this.vacOrderIncidentAddress = value;
	}
	
	public java.lang.String getVacOrderIncidentAddress() {
		return this.vacOrderIncidentAddress;
	}
	public void setNumOrderContactProvinceId(java.lang.Integer value) {
		this.numOrderContactProvinceId = value;
	}
	
	public java.lang.Integer getNumOrderContactProvinceId() {
		return this.numOrderContactProvinceId;
	}
	public void setNumOrderContactCityId(java.lang.Integer value) {
		this.numOrderContactCityId = value;
	}
	
	public java.lang.Integer getNumOrderContactCityId() {
		return this.numOrderContactCityId;
	}
	public void setVacOrderContactAddress(java.lang.String value) {
		this.vacOrderContactAddress = value;
	}
	
	public java.lang.String getVacOrderContactAddress() {
		return this.vacOrderContactAddress;
	}
	public void setNumOrderReceiveProvinceId(java.lang.Integer value) {
		this.numOrderReceiveProvinceId = value;
	}
	
	public java.lang.Integer getNumOrderReceiveProvinceId() {
		return this.numOrderReceiveProvinceId;
	}
	public void setNumOrderReceiveCityId(java.lang.Integer value) {
		this.numOrderReceiveCityId = value;
	}
	
	public java.lang.Integer getNumOrderReceiveCityId() {
		return this.numOrderReceiveCityId;
	}
	public void setNumClientId(java.lang.Integer value) {
		this.numClientId = value;
	}
	
	public java.lang.Integer getNumClientId() {
		return this.numClientId;
	}
	public void setNumOrderStatus(java.lang.Integer value) {
		this.numOrderStatus = value;
	}
	
	public java.lang.Integer getNumOrderStatus() {
		return this.numOrderStatus;
	}
	public void setNumOrderUserId(java.lang.Integer value) {
		this.numOrderUserId = value;
	}
	
	public java.lang.Integer getNumOrderUserId() {
		return this.numOrderUserId;
	}
	public void setNumAllotUserId(java.lang.Integer value) {
		this.numAllotUserId = value;
	}
	
	public java.lang.Integer getNumAllotUserId() {
		return this.numAllotUserId;
	}
	public void setVacAllotUserName(java.lang.String value) {
		this.vacAllotUserName = value;
	}
	
	public java.lang.String getVacAllotUserName() {
		return this.vacAllotUserName;
	}
	public String getDateAllotTimeString() {
		return date2String(getDateAllotTime(), FORMAT_DATE_ALLOT_TIME);
	}
	public void setDateAllotTimeString(String value) {
		setDateAllotTime(string2Date(value, FORMAT_DATE_ALLOT_TIME,java.util.Date.class));
	}
	
	public void setDateAllotTime(java.util.Date value) {
		this.dateAllotTime = value;
	}
	
	public java.util.Date getDateAllotTime() {
		return this.dateAllotTime;
	}
	
	
	
    public String getGmtCreatedBegin() {
		return gmtCreatedBegin;
	}

	public void setGmtCreatedBegin(String gmtCreatedBegin) {
		this.gmtCreatedBegin = gmtCreatedBegin;
	}

	
	
	public String getNoPassReason() {
		return noPassReason;
	}

	public void setNoPassReason(String noPassReason) {
		this.noPassReason = noPassReason;
	}

	public String getGmtCreatedEnd() {
		return gmtCreatedEnd;
	}

	public void setGmtCreatedEnd(String gmtCreatedEnd) {
		this.gmtCreatedEnd = gmtCreatedEnd;
	}

	public java.lang.String getVacOrderBusinessName() {
		return vacOrderBusinessName;
	}

	public void setVacOrderBusinessName(java.lang.String vacOrderBusinessName) {
		this.vacOrderBusinessName = vacOrderBusinessName;
	}

	public java.lang.Integer getCount() {
		return count;
	}

	public void setCount(java.lang.Integer count) {
		this.count = count;
	}
	
	
    public java.lang.String getFileName() {
		return fileName;
	}

	public void setFileName(java.lang.String fileName) {
		this.fileName = fileName;
	}

	
	public java.lang.String getDateOrderTermView() {
		return dateOrderTermView;
	}

	public void setDateOrderTermView(java.lang.String dateOrderTermView) {
		this.dateOrderTermView = dateOrderTermView;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("VacOrderSerial",getVacOrderSerial())		
		.append("VacOrderNumber",getVacOrderNumber())		
		.append("VacOrderAgentCode",getVacOrderAgentCode())		
		.append("VacOrderAgentName",getVacOrderAgentName())		
		.append("VacOrderBusinessType",getVacOrderBusinessType())		
		.append("VacOrderOtherContacts",getVacOrderOtherContacts())		
		.append("VacOrderTitle",getVacOrderTitle())		
		.append("VacOrderType",getVacOrderType())		
		.append("DateOrderTerm",getDateOrderTerm())		
		.append("VacOrderLawyerName",getVacOrderLawyerName())		
		.append("VacOrderCreateReason",getVacOrderCreateReason())		
		.append("VacOrderTaskType",getVacOrderTaskType())		
		.append("VacOrderAccount",getVacOrderAccount())		
		.append("VacOrderSuggestion",getVacOrderSuggestion())		
		.append("NumOrderIncidentProvinceId",getNumOrderIncidentProvinceId())		
		.append("NumOrderIncidentCityId",getNumOrderIncidentCityId())		
		.append("VacOrderIncidentAddress",getVacOrderIncidentAddress())		
		.append("NumOrderContactProvinceId",getNumOrderContactProvinceId())		
		.append("NumOrderContactCityId",getNumOrderContactCityId())		
		.append("VacOrderContactAddress",getVacOrderContactAddress())		
		.append("NumOrderReceiveProvinceId",getNumOrderReceiveProvinceId())		
		.append("NumOrderReceiveCityId",getNumOrderReceiveCityId())		
		.append("NumClientId",getNumClientId())		
		.append("Creator",getCreator())		
		.append("Modifier",getModifier())		
		.append("GmtCreated",getGmtCreated())		
		.append("GmtModified",getGmtModified())		
		.append("IsDeleted",getIsDeleted())		
		.append("NumOrderStatus",getNumOrderStatus())		
		.append("NumOrderUserId",getNumOrderUserId())		
		.append("NumAllotUserId",getNumAllotUserId())		
		.append("VacAllotUserName",getVacAllotUserName())		
		.append("DateAllotTime",getDateAllotTime())
		.append("tbClients",getTbClients())
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getVacOrderSerial())
		.append(getVacOrderNumber())
		.append(getVacOrderAgentCode())
		.append(getVacOrderAgentName())
		.append(getVacOrderBusinessType())
		.append(getVacOrderOtherContacts())
		.append(getVacOrderTitle())
		.append(getVacOrderType())
		.append(getDateOrderTerm())
		.append(getVacOrderLawyerName())
		.append(getVacOrderCreateReason())
		.append(getVacOrderTaskType())
		.append(getVacOrderAccount())
		.append(getVacOrderSuggestion())
		.append(getNumOrderIncidentProvinceId())
		.append(getNumOrderIncidentCityId())
		.append(getVacOrderIncidentAddress())
		.append(getNumOrderContactProvinceId())
		.append(getNumOrderContactCityId())
		.append(getVacOrderContactAddress())
		.append(getNumOrderReceiveProvinceId())
		.append(getNumOrderReceiveCityId())
		.append(getNumClientId())
		.append(getCreator())
		.append(getModifier())
		.append(getGmtCreated())
		.append(getGmtModified())
		.append(getIsDeleted())
		.append(getNumOrderStatus())
		.append(getNumOrderUserId())
		.append(getNumAllotUserId())
		.append(getVacAllotUserName())
		.append(getDateAllotTime())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof TbOrders == false) return false;
		if(this == obj) return true;
		TbOrders other = (TbOrders)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getVacOrderSerial(),other.getVacOrderSerial())

		.append(getVacOrderNumber(),other.getVacOrderNumber())

		.append(getVacOrderAgentCode(),other.getVacOrderAgentCode())

		.append(getVacOrderAgentName(),other.getVacOrderAgentName())

		.append(getVacOrderBusinessType(),other.getVacOrderBusinessType())

		.append(getVacOrderOtherContacts(),other.getVacOrderOtherContacts())

		.append(getVacOrderTitle(),other.getVacOrderTitle())

		.append(getVacOrderType(),other.getVacOrderType())

		.append(getDateOrderTerm(),other.getDateOrderTerm())

		.append(getVacOrderLawyerName(),other.getVacOrderLawyerName())

		.append(getVacOrderCreateReason(),other.getVacOrderCreateReason())

		.append(getVacOrderTaskType(),other.getVacOrderTaskType())

		.append(getVacOrderAccount(),other.getVacOrderAccount())

		.append(getVacOrderSuggestion(),other.getVacOrderSuggestion())

		.append(getNumOrderIncidentProvinceId(),other.getNumOrderIncidentProvinceId())

		.append(getNumOrderIncidentCityId(),other.getNumOrderIncidentCityId())

		.append(getVacOrderIncidentAddress(),other.getVacOrderIncidentAddress())

		.append(getNumOrderContactProvinceId(),other.getNumOrderContactProvinceId())

		.append(getNumOrderContactCityId(),other.getNumOrderContactCityId())

		.append(getVacOrderContactAddress(),other.getVacOrderContactAddress())

		.append(getNumOrderReceiveProvinceId(),other.getNumOrderReceiveProvinceId())

		.append(getNumOrderReceiveCityId(),other.getNumOrderReceiveCityId())

		.append(getNumClientId(),other.getNumClientId())

		.append(getCreator(),other.getCreator())

		.append(getModifier(),other.getModifier())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getGmtModified(),other.getGmtModified())

		.append(getIsDeleted(),other.getIsDeleted())

		.append(getNumOrderStatus(),other.getNumOrderStatus())

		.append(getNumOrderUserId(),other.getNumOrderUserId())

		.append(getNumAllotUserId(),other.getNumAllotUserId())

		.append(getVacAllotUserName(),other.getVacAllotUserName())

		.append(getDateAllotTime(),other.getDateAllotTime())

			.isEquals();
	}
}

