 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.cms.entity;

import com.fabaoframework.modules.mybatis.BaseEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class CmsOrganization extends BaseEntity {
	
	//alias
	public static final String TABLE_ALIAS = "CmsOrganization";
	public static final String ALIAS_VAC_ORG_NAME = "vacOrgName";
	public static final String ALIAS_VAC_ORG_ADDRESS = "vacOrgAddress";
	public static final String ALIAS_VAC_ORG_TIME = "vacOrgTime";
	public static final String ALIAS_NUM_ORG_TYPE = "numOrgType";
	public static final String ALIAS_VAC_ORG_TEL = "vacOrgTel";
	
	//date formats
	
	//columns START
	private java.lang.String vacOrgName;
	private java.lang.String vacOrgAddress;
	private java.lang.String vacOrgTime;
	private java.lang.Integer numOrgType;
	private java.lang.String vacOrgTel;
	private java.lang.String vacCityName;
	//columns END

	public CmsOrganization(){
	}

	public CmsOrganization(
		java.lang.Long id
	){
		this.id = id;
	}

	public java.lang.String getVacCityName() {
		return vacCityName;
	}

	public void setVacCityName(java.lang.String vacCityName) {
		this.vacCityName = vacCityName;
	}

	public void setVacOrgName(java.lang.String value) {
		this.vacOrgName = value;
	}
	
	public java.lang.String getVacOrgName() {
		return this.vacOrgName;
	}
	public void setVacOrgAddress(java.lang.String value) {
		this.vacOrgAddress = value;
	}
	
	public java.lang.String getVacOrgAddress() {
		return this.vacOrgAddress;
	}
	public void setVacOrgTime(java.lang.String value) {
		this.vacOrgTime = value;
	}
	
	public java.lang.String getVacOrgTime() {
		return this.vacOrgTime;
	}
	public void setNumOrgType(java.lang.Integer value) {
		this.numOrgType = value;
	}
	
	public java.lang.Integer getNumOrgType() {
		return this.numOrgType;
	}
	public void setVacOrgTel(java.lang.String value) {
		this.vacOrgTel = value;
	}
	
	public java.lang.String getVacOrgTel() {
		return this.vacOrgTel;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("VacOrgName",getVacOrgName())		
		.append("VacOrgAddress",getVacOrgAddress())		
		.append("VacOrgTime",getVacOrgTime())		
		.append("NumOrgType",getNumOrgType())		
		.append("VacOrgTel",getVacOrgTel())		
		.append("IsDeleted",getIsDeleted())		
		.append("Creator",getCreator())		
		.append("GmtCreated",getGmtCreated())		
		.append("GmtModified",getGmtModified())		
		.append("Modifier",getModifier())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getVacOrgName())
		.append(getVacOrgAddress())
		.append(getVacOrgTime())
		.append(getNumOrgType())
		.append(getVacOrgTel())
		.append(getIsDeleted())
		.append(getCreator())
		.append(getGmtCreated())
		.append(getGmtModified())
		.append(getModifier())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof CmsOrganization == false) return false;
		if(this == obj) return true;
		CmsOrganization other = (CmsOrganization)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getVacOrgName(),other.getVacOrgName())

		.append(getVacOrgAddress(),other.getVacOrgAddress())

		.append(getVacOrgTime(),other.getVacOrgTime())

		.append(getNumOrgType(),other.getNumOrgType())

		.append(getVacOrgTel(),other.getVacOrgTel())

		.append(getIsDeleted(),other.getIsDeleted())

		.append(getCreator(),other.getCreator())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getGmtModified(),other.getGmtModified())

		.append(getModifier(),other.getModifier())

			.isEquals();
	}
}

