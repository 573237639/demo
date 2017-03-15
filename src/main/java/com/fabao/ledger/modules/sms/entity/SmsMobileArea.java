 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.sms.entity;

import com.fabaoframework.modules.mybatis.BaseEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class SmsMobileArea extends BaseEntity {
	
	//alias
	public static final String TABLE_ALIAS = "SmsMobileArea";
	public static final String ALIAS_NUM_OPERATOR_ID = "numOperatorId";
	public static final String ALIAS_NUM_PROVINCE_ID = "numProvinceId";
	public static final String ALIAS_NUM_CITY_ID = "numCityId";
	public static final String ALIAS_VAC_SEG_START = "vacSegStart";
	public static final String ALIAS_VAC_SEG_END = "vacSegEnd";
	public static final String ALIAS_NUM_SEG_TYPE = "numSegType";
	
	//date formats
	
	//columns START
	private java.lang.Integer numOperatorId;
	private java.lang.Integer numProvinceId;
	private java.lang.Integer numCityId;
	private java.lang.String vacSegStart;
	private java.lang.String vacSegEnd;
	private java.lang.Integer numSegType;
	//columns END

	public SmsMobileArea(){
	}

	public SmsMobileArea(
		java.lang.Long id
	){
		this.id = id;
	}

	public void setNumOperatorId(java.lang.Integer value) {
		this.numOperatorId = value;
	}
	
	public java.lang.Integer getNumOperatorId() {
		return this.numOperatorId;
	}
	public void setNumProvinceId(java.lang.Integer value) {
		this.numProvinceId = value;
	}
	
	public java.lang.Integer getNumProvinceId() {
		return this.numProvinceId;
	}
	public void setNumCityId(java.lang.Integer value) {
		this.numCityId = value;
	}
	
	public java.lang.Integer getNumCityId() {
		return this.numCityId;
	}
	public void setVacSegStart(java.lang.String value) {
		this.vacSegStart = value;
	}
	
	public java.lang.String getVacSegStart() {
		return this.vacSegStart;
	}
	public void setVacSegEnd(java.lang.String value) {
		this.vacSegEnd = value;
	}
	
	public java.lang.String getVacSegEnd() {
		return this.vacSegEnd;
	}
	public void setNumSegType(java.lang.Integer value) {
		this.numSegType = value;
	}
	
	public java.lang.Integer getNumSegType() {
		return this.numSegType;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("NumOperatorId",getNumOperatorId())		
		.append("NumProvinceId",getNumProvinceId())		
		.append("NumCityId",getNumCityId())		
		.append("VacSegStart",getVacSegStart())		
		.append("VacSegEnd",getVacSegEnd())		
		.append("NumSegType",getNumSegType())		
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
		.append(getNumOperatorId())
		.append(getNumProvinceId())
		.append(getNumCityId())
		.append(getVacSegStart())
		.append(getVacSegEnd())
		.append(getNumSegType())
		.append(getCreator())
		.append(getModifier())
		.append(getGmtCreated())
		.append(getGmtModified())
		.append(getIsDeleted())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof SmsMobileArea == false) return false;
		if(this == obj) return true;
		SmsMobileArea other = (SmsMobileArea)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getNumOperatorId(),other.getNumOperatorId())

		.append(getNumProvinceId(),other.getNumProvinceId())

		.append(getNumCityId(),other.getNumCityId())

		.append(getVacSegStart(),other.getVacSegStart())

		.append(getVacSegEnd(),other.getVacSegEnd())

		.append(getNumSegType(),other.getNumSegType())

		.append(getCreator(),other.getCreator())

		.append(getModifier(),other.getModifier())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getGmtModified(),other.getGmtModified())

		.append(getIsDeleted(),other.getIsDeleted())

			.isEquals();
	}
}

