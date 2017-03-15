 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.tb.entity;

import com.fabaoframework.modules.mybatis.BaseEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class TbCallStay extends BaseEntity {
	
	//alias
	public static final String TABLE_ALIAS = "TbCallStay";
	public static final String ALIAS_VAC_SERIAL = "流水号";
	public static final String ALIAS_DATE_CREATETIME = "质检时间";
	public static final String ALIAS_NUM_STATE = "质检状态";
	
	//date formats
			public static final String FORMAT_DATE_CREATETIME = DATE_TIME_FORMAT;
	
	//columns START
	private java.lang.String vacSerial;
	private java.util.Date dateCreatetime;
	private java.lang.Integer numState;
	//columns END

	public TbCallStay(){
	}

	public TbCallStay(
		java.lang.Long id
	){
		this.id = id;
	}

	public void setVacSerial(java.lang.String value) {
		this.vacSerial = value;
	}
	
	public java.lang.String getVacSerial() {
		return this.vacSerial;
	}
	public String getDateCreatetimeString() {
		return date2String(getDateCreatetime(), FORMAT_DATE_CREATETIME);
	}
	public void setDateCreatetimeString(String value) {
		setDateCreatetime(string2Date(value, FORMAT_DATE_CREATETIME,java.util.Date.class));
	}
	
	public void setDateCreatetime(java.util.Date value) {
		this.dateCreatetime = value;
	}
	
	public java.util.Date getDateCreatetime() {
		return this.dateCreatetime;
	}
	public void setNumState(java.lang.Integer value) {
		this.numState = value;
	}
	
	public java.lang.Integer getNumState() {
		return this.numState;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("VacSerial",getVacSerial())		
		.append("DateCreatetime",getDateCreatetime())		
		.append("NumState",getNumState())		
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
		.append(getVacSerial())
		.append(getDateCreatetime())
		.append(getNumState())
		.append(getCreator())
		.append(getModifier())
		.append(getGmtCreated())
		.append(getGmtModified())
		.append(getIsDeleted())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof TbCallStay == false) return false;
		if(this == obj) return true;
		TbCallStay other = (TbCallStay)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getVacSerial(),other.getVacSerial())

		.append(getDateCreatetime(),other.getDateCreatetime())

		.append(getNumState(),other.getNumState())

		.append(getCreator(),other.getCreator())

		.append(getModifier(),other.getModifier())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getGmtModified(),other.getGmtModified())

		.append(getIsDeleted(),other.getIsDeleted())

			.isEquals();
	}
}

