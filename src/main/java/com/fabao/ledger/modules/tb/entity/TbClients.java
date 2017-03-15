 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.tb.entity;

import com.fabaoframework.modules.mybatis.BaseEntity;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class TbClients extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3511632609589819340L;
	//alias
	public static final String TABLE_ALIAS = "TbClients";
	public static final String ALIAS_VAC_CLIENT_NAME = "vacClientName";
	public static final String ALIAS_NUM_CLIENT_GENDER = "numClientGender";
	public static final String ALIAS_NUM_CLIENT_TYPE = "numClientType";
	public static final String ALIAS_NUM_CLIENT_PROVINCE_ID = "numClientProvinceId";
	public static final String ALIAS_NUM_CLIENT_CITY_ID = "numClientCityId";
	public static final String ALIAS_VAC_CLIENT_NUMBER = "vacClientNumber";
	public static final String ALIAS_VAC_CLIENT_IDENTITY_CODE = "vacClientIdentityCode";
	public static final String ALIAS_VAC_CLIENT_PASSPORT_CODE = "vacClientPassportCode";
	public static final String ALIAS_VAC_CLIENT_MILITARY_CODE = "vacClientMilitaryCode";
	public static final String ALIAS_VAC_CLIENT_ADDRESS = "vacClientAddress";
	public static final String ALIAS_VAC_CLIENT_MEMO = "vacClientMemo";
	public static final String ALIAS_VAC_CLIENT_EEP_CODE = "vacClientEepCode";
	public static final String ALIAS_NUM_CLIENT_SOURCE = "numClientSource";
	
	//date formats
	//证件类型
	private String zjType;
	private String zjCode;
	private List<TbLedger> tbLedger;
	private List<TbOrders> tbOrders;
	//columns START
	private java.lang.String vacClientName;
	private java.lang.Integer numClientGender;//性别
	private java.lang.Integer numClientType;//
	private java.lang.Integer numClientProvinceId;
	private java.lang.Integer numClientCityId;
	private java.lang.String vacClientNumber;//电话号码
	private java.lang.String vacClientIdentityCode;//身份证号码
	private java.lang.String vacClientPassportCode;//外国籍护照
	private java.lang.String vacClientMilitaryCode;//军官证
	private java.lang.String vacClientAddress;
	private java.lang.String vacClientMemo;
	private java.lang.String vacClientEepCode;//港澳台通行证
	private java.lang.String vacClientProvinceName;
	private java.lang.String vacClientCityName;
	private java.lang.Integer numClientSource;//来源
	private java.lang.String vacClientSource;
	//columns END

	public TbClients(){
	}

	public TbClients(
		java.lang.Long id
	){
		this.id = id;
	}

	public java.lang.Integer getNumClientSource() {
		return numClientSource;
	}

	public void setNumClientSource(java.lang.Integer numClientSource) {
		this.numClientSource = numClientSource;
	}

	public java.lang.String getVacClientSource() {
		return vacClientSource;
	}

	public void setVacClientSource(java.lang.String vacClientSource) {
		this.vacClientSource = vacClientSource;
	}

	public java.lang.String getVacClientProvinceName() {
		return vacClientProvinceName;
	}

	public void setVacClientProvinceName(java.lang.String vacClientProvinceName) {
		this.vacClientProvinceName = vacClientProvinceName;
	}

	public java.lang.String getVacClientCityName() {
		return vacClientCityName;
	}

	public void setVacClientCityName(java.lang.String vacClientCityName) {
		this.vacClientCityName = vacClientCityName;
	}

	public void setVacClientName(java.lang.String value) {
		this.vacClientName = value;
	}
	
	public java.lang.String getVacClientName() {
		return this.vacClientName;
	}
	public void setNumClientGender(java.lang.Integer value) {
		this.numClientGender = value;
	}
	
	public java.lang.Integer getNumClientGender() {
		return this.numClientGender;
	}
	public void setNumClientType(java.lang.Integer value) {
		this.numClientType = value;
	}
	
	public java.lang.Integer getNumClientType() {
		return this.numClientType;
	}
	public void setNumClientProvinceId(java.lang.Integer value) {
		this.numClientProvinceId = value;
	}
	
	public java.lang.Integer getNumClientProvinceId() {
		return this.numClientProvinceId;
	}
	public void setNumClientCityId(java.lang.Integer value) {
		this.numClientCityId = value;
	}
	
	public java.lang.Integer getNumClientCityId() {
		return this.numClientCityId;
	}
	public void setVacClientNumber(java.lang.String value) {
		this.vacClientNumber = value;
	}
	
	public java.lang.String getVacClientNumber() {
		return this.vacClientNumber;
	}
	public void setVacClientIdentityCode(java.lang.String value) {
		this.vacClientIdentityCode = value;
	}
	
	public java.lang.String getVacClientIdentityCode() {
		return this.vacClientIdentityCode;
	}
	public void setVacClientPassportCode(java.lang.String value) {
		this.vacClientPassportCode = value;
	}
	
	public java.lang.String getVacClientPassportCode() {
		return this.vacClientPassportCode;
	}
	public void setVacClientMilitaryCode(java.lang.String value) {
		this.vacClientMilitaryCode = value;
	}
	
	public java.lang.String getVacClientMilitaryCode() {
		return this.vacClientMilitaryCode;
	}
	public void setVacClientAddress(java.lang.String value) {
		this.vacClientAddress = value;
	}
	
	public java.lang.String getVacClientAddress() {
		return this.vacClientAddress;
	}
	public void setVacClientMemo(java.lang.String value) {
		this.vacClientMemo = value;
	}
	
	public java.lang.String getVacClientMemo() {
		return this.vacClientMemo;
	}
	public void setVacClientEepCode(java.lang.String value) {
		this.vacClientEepCode = value;
	}
	
	public java.lang.String getVacClientEepCode() {
		return this.vacClientEepCode;
	}
	
	
    public String getZjType() {
		return zjType;
	}

	public void setZjType(String zjType) {
		this.zjType = zjType;
	}

	public String getZjCode() {
		return zjCode;
	}

	public void setZjCode(String zjCode) {
		this.zjCode = zjCode;
	}

	public List<TbLedger> getTbLedger() {
		return tbLedger;
	}

	public void setTbLedger(List<TbLedger> tbLedger) {
		this.tbLedger = tbLedger;
	}

	public List<TbOrders> getTbOrders() {
		return tbOrders;
	}

	public void setTbOrders(List<TbOrders> tbOrders) {
		this.tbOrders = tbOrders;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("VacClientName",getVacClientName())		
		.append("NumClientGender",getNumClientGender())		
		.append("NumClientType",getNumClientType())		
		.append("NumClientProvinceId",getNumClientProvinceId())		
		.append("NumClientCityId",getNumClientCityId())		
		.append("VacClientNumber",getVacClientNumber())		
		.append("VacClientIdentityCode",getVacClientIdentityCode())		
		.append("VacClientPassportCode",getVacClientPassportCode())		
		.append("VacClientMilitaryCode",getVacClientMilitaryCode())		
		.append("VacClientAddress",getVacClientAddress())		
		.append("VacClientMemo",getVacClientMemo())		
		.append("Creator",getCreator())		
		.append("Modifier",getModifier())		
		.append("GmtCreated",getGmtCreated())		
		.append("GmtModified",getGmtModified())		
		.append("IsDeleted",getIsDeleted())		
		.append("VacClientEepCode",getVacClientEepCode())		
		.append("NumClientSource",getNumClientSource())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getVacClientName())
		.append(getNumClientGender())
		.append(getNumClientType())
		.append(getNumClientProvinceId())
		.append(getNumClientCityId())
		.append(getVacClientNumber())
		.append(getVacClientIdentityCode())
		.append(getVacClientPassportCode())
		.append(getVacClientMilitaryCode())
		.append(getVacClientAddress())
		.append(getVacClientMemo())
		.append(getCreator())
		.append(getModifier())
		.append(getGmtCreated())
		.append(getGmtModified())
		.append(getIsDeleted())
		.append(getVacClientEepCode())
		.append(getNumClientSource())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof TbClients == false) return false;
		if(this == obj) return true;
		TbClients other = (TbClients)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getVacClientName(),other.getVacClientName())

		.append(getNumClientGender(),other.getNumClientGender())

		.append(getNumClientType(),other.getNumClientType())

		.append(getNumClientProvinceId(),other.getNumClientProvinceId())

		.append(getNumClientCityId(),other.getNumClientCityId())

		.append(getVacClientNumber(),other.getVacClientNumber())

		.append(getVacClientIdentityCode(),other.getVacClientIdentityCode())

		.append(getVacClientPassportCode(),other.getVacClientPassportCode())

		.append(getVacClientMilitaryCode(),other.getVacClientMilitaryCode())

		.append(getVacClientAddress(),other.getVacClientAddress())

		.append(getVacClientMemo(),other.getVacClientMemo())

		.append(getCreator(),other.getCreator())

		.append(getModifier(),other.getModifier())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getGmtModified(),other.getGmtModified())

		.append(getIsDeleted(),other.getIsDeleted())

		.append(getVacClientEepCode(),other.getVacClientEepCode())
		
		.append(getNumClientSource(),other.getNumClientSource())

			.isEquals();
	}
}

