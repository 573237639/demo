 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.sys.entity;

import com.fabaoframework.modules.mybatis.BaseEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class SysSpecialty extends BaseEntity {
	
	//alias
	public static final String TABLE_ALIAS = "SysSpecialty";
	public static final String ALIAS_VAC_NAME = "vacName";
	public static final String ALIAS_NUM_PARENT_ID = "numParentId";
	public static final String ALIAS_NUM_CURRENT_LEVEL = "numCurrentLevel";
	public static final String ALIAS_NUM_LEAF = "numLeaf";
	public static final String ALIAS_NUM_SORT = "numSort";
	
	//date formats
	
	//columns START
	private java.lang.String vacName;
	private java.lang.Integer numParentId;
	private java.lang.Integer numCurrentLevel;
	private Integer numLeaf;
	private java.lang.Integer numSort;
	//columns END

	public SysSpecialty(){
	}

	public SysSpecialty(
		java.lang.Long id
	){
		this.id = id;
	}

	public void setVacName(java.lang.String value) {
		this.vacName = value;
	}
	
	public java.lang.String getVacName() {
		return this.vacName;
	}
	public void setNumParentId(java.lang.Integer value) {
		this.numParentId = value;
	}
	
	public java.lang.Integer getNumParentId() {
		return this.numParentId;
	}
	public void setNumCurrentLevel(java.lang.Integer value) {
		this.numCurrentLevel = value;
	}
	
	public java.lang.Integer getNumCurrentLevel() {
		return this.numCurrentLevel;
	}
	public void setNumLeaf(Integer value) {
		this.numLeaf = value;
	}
	
	public Integer getNumLeaf() {
		return this.numLeaf;
	}
	public void setNumSort(java.lang.Integer value) {
		this.numSort = value;
	}
	
	public java.lang.Integer getNumSort() {
		return this.numSort;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("VacName",getVacName())		
		.append("NumParentId",getNumParentId())		
		.append("NumCurrentLevel",getNumCurrentLevel())		
		.append("NumLeaf",getNumLeaf())		
		.append("NumSort",getNumSort())		
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
		.append(getVacName())
		.append(getNumParentId())
		.append(getNumCurrentLevel())
		.append(getNumLeaf())
		.append(getNumSort())
		.append(getCreator())
		.append(getModifier())
		.append(getGmtCreated())
		.append(getGmtModified())
		.append(getIsDeleted())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof SysSpecialty == false) return false;
		if(this == obj) return true;
		SysSpecialty other = (SysSpecialty)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getVacName(),other.getVacName())

		.append(getNumParentId(),other.getNumParentId())

		.append(getNumCurrentLevel(),other.getNumCurrentLevel())

		.append(getNumLeaf(),other.getNumLeaf())

		.append(getNumSort(),other.getNumSort())

		.append(getCreator(),other.getCreator())

		.append(getModifier(),other.getModifier())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getGmtModified(),other.getGmtModified())

		.append(getIsDeleted(),other.getIsDeleted())

			.isEquals();
	}
}

