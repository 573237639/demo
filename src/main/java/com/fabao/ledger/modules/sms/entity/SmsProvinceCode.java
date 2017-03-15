 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.sms.entity;

import com.fabaoframework.modules.mybatis.BaseEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class SmsProvinceCode extends BaseEntity {
	
	//alias
	public static final String TABLE_ALIAS = "SmsProvinceCode";
	public static final String ALIAS_VAC_PROVINCE_NAME = "vacProvinceName";
	public static final String ALIAS_VAC_PROVINCE_CODE = "vacProvinceCode";
	public static final String ALIAS_VAC_PROVINCE_CAP_CODE = "vacProvinceCapCode";
	public static final String ALIAS_VAC_PROVINCE_WAP_CODE = "vacProvinceWapCode";
	
	//date formats
	
	//columns START
	private java.lang.String vacProvinceName;
	private java.lang.String vacProvinceCode;
	private java.lang.String vacProvinceCapCode;
	private java.lang.String vacProvinceWapCode;
	//columns END

	public SmsProvinceCode(){
	}

	public SmsProvinceCode(
		java.lang.Long id
	){
		this.id = id;
	}

	public void setVacProvinceName(java.lang.String value) {
		this.vacProvinceName = value;
	}
	
	public java.lang.String getVacProvinceName() {
		return this.vacProvinceName;
	}
	public void setVacProvinceCode(java.lang.String value) {
		this.vacProvinceCode = value;
	}
	
	public java.lang.String getVacProvinceCode() {
		return this.vacProvinceCode;
	}
	public void setVacProvinceCapCode(java.lang.String value) {
		this.vacProvinceCapCode = value;
	}
	
	public java.lang.String getVacProvinceCapCode() {
		return this.vacProvinceCapCode;
	}
	public void setVacProvinceWapCode(java.lang.String value) {
		this.vacProvinceWapCode = value;
	}
	
	public java.lang.String getVacProvinceWapCode() {
		return this.vacProvinceWapCode;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("VacProvinceName",getVacProvinceName())		
		.append("VacProvinceCode",getVacProvinceCode())		
		.append("VacProvinceCapCode",getVacProvinceCapCode())		
		.append("VacProvinceWapCode",getVacProvinceWapCode())		
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
		.append(getVacProvinceName())
		.append(getVacProvinceCode())
		.append(getVacProvinceCapCode())
		.append(getVacProvinceWapCode())
		.append(getCreator())
		.append(getModifier())
		.append(getGmtCreated())
		.append(getGmtModified())
		.append(getIsDeleted())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof SmsProvinceCode == false) return false;
		if(this == obj) return true;
		SmsProvinceCode other = (SmsProvinceCode)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getVacProvinceName(),other.getVacProvinceName())

		.append(getVacProvinceCode(),other.getVacProvinceCode())

		.append(getVacProvinceCapCode(),other.getVacProvinceCapCode())

		.append(getVacProvinceWapCode(),other.getVacProvinceWapCode())

		.append(getCreator(),other.getCreator())

		.append(getModifier(),other.getModifier())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getGmtModified(),other.getGmtModified())

		.append(getIsDeleted(),other.getIsDeleted())

			.isEquals();
	}
}

