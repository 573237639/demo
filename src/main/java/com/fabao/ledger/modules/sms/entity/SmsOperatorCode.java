 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.sms.entity;

import com.fabaoframework.modules.mybatis.BaseEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class SmsOperatorCode extends BaseEntity {
	
	//alias
	public static final String TABLE_ALIAS = "SmsOperatorCode";
	public static final String ALIAS_NUM_OPERATOR_NAME = "numOperatorName";
	public static final String ALIAS_NUM_GATEWAY_ID = "numGatewayId";
	
	//date formats
	
	//columns START
	private java.lang.String vacOperatorName;
	private java.lang.Integer numGatewayId;
	//columns END

	public SmsOperatorCode(){
	}

	public SmsOperatorCode(
		java.lang.Long id
	){
		this.id = id;
	}

	
	public java.lang.String getVacOperatorName() {
		return vacOperatorName;
	}

	public void setVacOperatorName(java.lang.String vacOperatorName) {
		this.vacOperatorName = vacOperatorName;
	}

	public void setNumGatewayId(java.lang.Integer value) {
		this.numGatewayId = value;
	}
	
	public java.lang.Integer getNumGatewayId() {
		return this.numGatewayId;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("NumOperatorName",getVacOperatorName())		
		.append("NumGatewayId",getNumGatewayId())		
		.append("Creator",getCreator())		
		.append("Modifier",getModifier())		
		.append("GmtCreated",getGmtCreated())		
		.append("GmtModified",getGmtModified())		
		.append("IsDeleted",getIsDeleted())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getVacOperatorName())
		.append(getNumGatewayId())
		.append(getCreator())
		.append(getModifier())
		.append(getGmtCreated())
		.append(getGmtModified())
		.append(getIsDeleted())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof SmsOperatorCode == false) return false;
		if(this == obj) return true;
		SmsOperatorCode other = (SmsOperatorCode)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getVacOperatorName(),other.getVacOperatorName())

		.append(getNumGatewayId(),other.getNumGatewayId())

		.append(getCreator(),other.getCreator())

		.append(getModifier(),other.getModifier())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getGmtModified(),other.getGmtModified())

		.append(getIsDeleted(),other.getIsDeleted())

			.isEquals();
	}
}

