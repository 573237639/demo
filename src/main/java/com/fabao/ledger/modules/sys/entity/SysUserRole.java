 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.sys.entity;

import com.fabaoframework.modules.mybatis.BaseEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class SysUserRole extends BaseEntity {
	
	//alias
	public static final String TABLE_ALIAS = "SysUserRole";
	public static final String ALIAS_USER_ID = "userId";
	public static final String ALIAS_ROLE_ID = "roleId";
	
	//date formats
	
	//columns START
	private java.lang.Integer userId;
	private java.lang.Integer roleId;
	//columns END

	public SysUserRole(){
	}

	public SysUserRole(
		java.lang.Long id
	){
		this.id = id;
	}

	public void setUserId(java.lang.Integer value) {
		this.userId = value;
	}
	
	public java.lang.Integer getUserId() {
		return this.userId;
	}
	public void setRoleId(java.lang.Integer value) {
		this.roleId = value;
	}
	
	public java.lang.Integer getRoleId() {
		return this.roleId;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("UserId",getUserId())		
		.append("RoleId",getRoleId())		
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
		.append(getUserId())
		.append(getRoleId())
		.append(getCreator())
		.append(getModifier())
		.append(getGmtCreated())
		.append(getGmtModified())
		.append(getIsDeleted())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof SysUserRole == false) return false;
		if(this == obj) return true;
		SysUserRole other = (SysUserRole)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getUserId(),other.getUserId())

		.append(getRoleId(),other.getRoleId())

		.append(getCreator(),other.getCreator())

		.append(getModifier(),other.getModifier())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getGmtModified(),other.getGmtModified())

		.append(getIsDeleted(),other.getIsDeleted())

			.isEquals();
	}
}

