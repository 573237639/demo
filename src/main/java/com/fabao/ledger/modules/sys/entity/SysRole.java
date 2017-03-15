 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.sys.entity;

import com.fabaoframework.modules.mybatis.BaseEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class SysRole extends BaseEntity {
	
	//alias
	public static final String TABLE_ALIAS = "SysRole";
	public static final String ALIAS_NAME = "name";
	public static final String ALIAS_REMARKS = "remarks";
	
	//date formats
	
	//columns START
	private java.lang.String name;
	private java.lang.String remarks;
	//columns END

	public SysRole(){
	}

	public SysRole(
		java.lang.Long id
	){
		this.id = id;
	}

	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getName() {
		return this.name;
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
		.append("Name",getName())		
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
		.append(getName())
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
		if(obj instanceof SysRole == false) return false;
		if(this == obj) return true;
		SysRole other = (SysRole)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getName(),other.getName())

		.append(getRemarks(),other.getRemarks())

		.append(getCreator(),other.getCreator())

		.append(getModifier(),other.getModifier())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getGmtModified(),other.getGmtModified())

		.append(getIsDeleted(),other.getIsDeleted())

			.isEquals();
	}
}

