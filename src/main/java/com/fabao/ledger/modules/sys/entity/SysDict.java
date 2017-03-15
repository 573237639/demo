 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.sys.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fabaoframework.modules.mybatis.BaseEntity;


public class SysDict extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1772225481077349806L;
	//alias
	public static final String TABLE_ALIAS = "SysDict";
	public static final String ALIAS_LABEL = "label";
	public static final String ALIAS_VALUE = "value";
	public static final String ALIAS_TYPE = "type";
	public static final String ALIAS_SORT = "sort";
	public static final String ALIAS_DESCRIPTION = "description";
	
	//date formats
	
	//columns START
	private java.lang.String label;
	private java.lang.String value;
	private java.lang.String type;
	private java.lang.Integer sort;
	private java.lang.String description;
	private java.lang.String sortColumns;
	
	//columns END

	public SysDict(){
	}

	public SysDict(
		java.lang.Long id
	){
		this.id = id;
	}

	public void setLabel(java.lang.String value) {
		this.label = value;
	}
	
	public java.lang.String getLabel() {
		return this.label;
	}
	public void setValue(java.lang.String value) {
		this.value = value;
	}
	
	public java.lang.String getValue() {
		return this.value;
	}
	public void setType(java.lang.String value) {
		this.type = value;
	}
	
	public java.lang.String getType() {
		return this.type;
	}
	public void setSort(java.lang.Integer value) {
		this.sort = value;
	}
	
	public java.lang.Integer getSort() {
		return this.sort;
	}
	public void setDescription(java.lang.String value) {
		this.description = value;
	}
	
	public java.lang.String getDescription() {
		return this.description;
	}
	
    public java.lang.String getSortColumns() {
		return sortColumns;
	}

	public void setSortColumns(java.lang.String sortColumns) {
		this.sortColumns = sortColumns;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("Label",getLabel())		
		.append("Value",getValue())		
		.append("Type",getType())		
		.append("Sort",getSort())		
		.append("Description",getDescription())		
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
		.append(getLabel())
		.append(getValue())
		.append(getType())
		.append(getSort())
		.append(getDescription())
		.append(getCreator())
		.append(getModifier())
		.append(getGmtCreated())
		.append(getGmtModified())
		.append(getIsDeleted())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof SysDict == false) return false;
		if(this == obj) return true;
		SysDict other = (SysDict)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getLabel(),other.getLabel())

		.append(getValue(),other.getValue())

		.append(getType(),other.getType())

		.append(getSort(),other.getSort())

		.append(getDescription(),other.getDescription())

		.append(getCreator(),other.getCreator())

		.append(getModifier(),other.getModifier())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getGmtModified(),other.getGmtModified())

		.append(getIsDeleted(),other.getIsDeleted())

			.isEquals();
	}
}

