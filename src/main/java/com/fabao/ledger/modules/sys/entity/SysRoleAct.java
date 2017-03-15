 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.sys.entity;

import com.fabaoframework.modules.mybatis.BaseEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class SysRoleAct extends BaseEntity {
	
	//alias
	public static final String TABLE_ALIAS = "SysRoleAct";
	public static final String ALIAS_ROLE_ID = "roleId";
	public static final String ALIAS_ACT_ID = "actId";
	
	//date formats
	
	//columns START
	private java.lang.Integer roleId;
	private java.lang.Integer actId;
	//columns END

	public SysRoleAct(){
	}

	public SysRoleAct(
		java.lang.Long id
	){
		this.id = id;
	}

	public void setRoleId(java.lang.Integer value) {
		this.roleId = value;
	}
	
	public java.lang.Integer getRoleId() {
		return this.roleId;
	}
	public void setActId(java.lang.Integer value) {
		this.actId = value;
	}
	
	public java.lang.Integer getActId() {
		return this.actId;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("RoleId",getRoleId())		
		.append("ActId",getActId())		
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
		.append(getRoleId())
		.append(getActId())
		.append(getCreator())
		.append(getModifier())
		.append(getGmtCreated())
		.append(getGmtModified())
		.append(getIsDeleted())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof SysRoleAct == false) return false;
		if(this == obj) return true;
		SysRoleAct other = (SysRoleAct)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getRoleId(),other.getRoleId())

		.append(getActId(),other.getActId())

		.append(getCreator(),other.getCreator())

		.append(getModifier(),other.getModifier())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getGmtModified(),other.getGmtModified())

		.append(getIsDeleted(),other.getIsDeleted())

			.isEquals();
	}
}

