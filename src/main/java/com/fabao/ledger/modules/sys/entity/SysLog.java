 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.sys.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fabaoframework.modules.mybatis.BaseEntity;


public class SysLog extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7428417930358189780L;
	//alias
	public static final String TABLE_ALIAS = "SysLog";
	public static final String ALIAS_ACT_NAME = "actName";
	public static final String ALIAS_REQ_PARAM = "reqParam";
	public static final String ALIAS_ADMIN_NAME = "adminName";
	public static final String ALIAS_REMARKS = "remarks";
	
	//date formats
	
	//columns START
	private java.lang.String actName;
	private java.lang.String reqParam;
	private java.lang.String adminName;
	private java.lang.String remarks;
	//columns END

	public SysLog(){
	}

	public SysLog(
		java.lang.Long id
	){
		this.id = id;
	}

	public void setActName(java.lang.String value) {
		this.actName = value;
	}
	
	public java.lang.String getActName() {
		return this.actName;
	}
	public void setReqParam(java.lang.String value) {
		this.reqParam = value;
	}
	
	public java.lang.String getReqParam() {
		return this.reqParam;
	}
	public void setAdminName(java.lang.String value) {
		this.adminName = value;
	}
	
	public java.lang.String getAdminName() {
		return this.adminName;
	}
	public void setRemarks(java.lang.String value) {
		this.remarks = value;
	}
	
	public java.lang.String getRemarks() {
		return this.remarks;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("ActName",getActName())		
		.append("ReqParam",getReqParam())		
		.append("AdminName",getAdminName())		
		.append("Remarks",getRemarks())		
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
		.append(getActName())
		.append(getReqParam())
		.append(getAdminName())
		.append(getRemarks())
		.append(getCreator())
		.append(getModifier())
		.append(getGmtCreated())
		.append(getGmtModified())
		.append(getIsDeleted())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof SysLog == false) return false;
		if(this == obj) return true;
		SysLog other = (SysLog)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getActName(),other.getActName())

		.append(getReqParam(),other.getReqParam())

		.append(getAdminName(),other.getAdminName())

		.append(getRemarks(),other.getRemarks())

		.append(getCreator(),other.getCreator())

		.append(getModifier(),other.getModifier())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getGmtModified(),other.getGmtModified())

		.append(getIsDeleted(),other.getIsDeleted())

			.isEquals();
	}
}

