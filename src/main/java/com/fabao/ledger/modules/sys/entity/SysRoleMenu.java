 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.sys.entity;

import com.fabaoframework.modules.mybatis.BaseEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class SysRoleMenu extends BaseEntity {
	
	//alias
	public static final String TABLE_ALIAS = "SysRoleMenu";
	public static final String ALIAS_ROLE_ID = "roleId";
	public static final String ALIAS_MENU_ID = "menuId";
	
	//date formats
	
	//columns START
	private java.lang.Integer roleId;
	private java.lang.Integer menuId;
	//columns END

	public SysRoleMenu(){
	}

	public SysRoleMenu(
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
	public void setMenuId(java.lang.Integer value) {
		this.menuId = value;
	}
	
	public java.lang.Integer getMenuId() {
		return this.menuId;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("RoleId",getRoleId())		
		.append("MenuId",getMenuId())		
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
		.append(getMenuId())
		.append(getCreator())
		.append(getModifier())
		.append(getGmtCreated())
		.append(getGmtModified())
		.append(getIsDeleted())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof SysRoleMenu == false) return false;
		if(this == obj) return true;
		SysRoleMenu other = (SysRoleMenu)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getRoleId(),other.getRoleId())

		.append(getMenuId(),other.getMenuId())

		.append(getCreator(),other.getCreator())

		.append(getModifier(),other.getModifier())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getGmtModified(),other.getGmtModified())

		.append(getIsDeleted(),other.getIsDeleted())

			.isEquals();
	}
}

