 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.sms.entity;

import com.fabaoframework.modules.mybatis.BaseEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class SmsCityCode extends BaseEntity {
	
	//alias
	public static final String TABLE_ALIAS = "SmsCityCode";
	public static final String ALIAS_NUM_PROVINCE_ID = "numProvinceId";
	public static final String ALIAS_VAC_CITY_NAME = "vacCityName";
	public static final String ALIAS_VAC_CITY_REGION = "vacCityRegion";
	public static final String ALIAS_NUM_CITY_INTER_ID = "numCityInterId";
	
	//date formats
	
	//columns START
	private java.lang.Integer numProvinceId;
	private java.lang.String vacCityName;
	private java.lang.String vacCityRegion;
	private java.lang.Integer numCityInterId;
	//columns END

	public SmsCityCode(){
	}

	public SmsCityCode(
		java.lang.Long id
	){
		this.id = id;
	}

	public void setNumProvinceId(java.lang.Integer value) {
		this.numProvinceId = value;
	}
	
	public java.lang.Integer getNumProvinceId() {
		return this.numProvinceId;
	}
	public void setVacCityName(java.lang.String value) {
		this.vacCityName = value;
	}
	
	public java.lang.String getVacCityName() {
		return this.vacCityName;
	}
	public void setVacCityRegion(java.lang.String value) {
		this.vacCityRegion = value;
	}
	
	public java.lang.String getVacCityRegion() {
		return this.vacCityRegion;
	}
	public void setNumCityInterId(java.lang.Integer value) {
		this.numCityInterId = value;
	}
	
	public java.lang.Integer getNumCityInterId() {
		return this.numCityInterId;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("NumProvinceId",getNumProvinceId())		
		.append("VacCityName",getVacCityName())		
		.append("VacCityRegion",getVacCityRegion())		
		.append("NumCityInterId",getNumCityInterId())		
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
		.append(getNumProvinceId())
		.append(getVacCityName())
		.append(getVacCityRegion())
		.append(getNumCityInterId())
		.append(getCreator())
		.append(getModifier())
		.append(getGmtCreated())
		.append(getGmtModified())
		.append(getIsDeleted())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof SmsCityCode == false) return false;
		if(this == obj) return true;
		SmsCityCode other = (SmsCityCode)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getNumProvinceId(),other.getNumProvinceId())

		.append(getVacCityName(),other.getVacCityName())

		.append(getVacCityRegion(),other.getVacCityRegion())

		.append(getNumCityInterId(),other.getNumCityInterId())

		.append(getCreator(),other.getCreator())

		.append(getModifier(),other.getModifier())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getGmtModified(),other.getGmtModified())

		.append(getIsDeleted(),other.getIsDeleted())

			.isEquals();
	}
}

