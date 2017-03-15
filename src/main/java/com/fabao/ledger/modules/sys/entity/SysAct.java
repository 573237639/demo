 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.sys.entity;

import com.fabaoframework.modules.mybatis.BaseEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class SysAct extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7663305863864514221L;
	//alias
	public static final String TABLE_ALIAS = "SysAct";
	public static final String ALIAS_ACT_NAME = "actName";
	public static final String ALIAS_TARGET = "target";
	public static final String ALIAS_TYPE = "type";
	public static final String ALIAS_MENU_ID = "menuId";
	public static final String ALIAS_REMARKS = "remarks";
	
	//date formats
	
	//columns START
	private java.lang.String actName;
	private java.lang.String target;
	private Integer type;
	private java.lang.Integer menuId;
	private java.lang.String remarks;
	//columns END

	public SysAct(){
	}

	public SysAct(
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
	public void setTarget(java.lang.String value) {
		this.target = value;
	}
	
	public java.lang.String getTarget() {
		return this.target;
	}
	public void setType(Integer value) {
		this.type = value;
	}
	
	public Integer getType() {
		return this.type;
	}
	public void setMenuId(java.lang.Integer value) {
		this.menuId = value;
	}
	
	public java.lang.Integer getMenuId() {
		return this.menuId;
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
		.append("Target",getTarget())		
		.append("Type",getType())		
		.append("MenuId",getMenuId())		
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
		.append(getTarget())
		.append(getType())
		.append(getMenuId())
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
		if(obj instanceof SysAct == false) return false;
		if(this == obj) return true;
		SysAct other = (SysAct)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getActName(),other.getActName())

		.append(getTarget(),other.getTarget())

		.append(getType(),other.getType())

		.append(getMenuId(),other.getMenuId())

		.append(getRemarks(),other.getRemarks())

		.append(getCreator(),other.getCreator())

		.append(getModifier(),other.getModifier())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getGmtModified(),other.getGmtModified())

		.append(getIsDeleted(),other.getIsDeleted())

			.isEquals();
	}
    //-----------------------------------------------------------------
    public static final int ACT_TYPE_COMMON=1;//菜单动作
	public static final int ACT_TYPE_AUTH=2;//授权动作
}

