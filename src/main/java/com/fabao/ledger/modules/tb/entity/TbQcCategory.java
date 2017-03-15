 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.tb.entity;

import com.fabaoframework.modules.mybatis.BaseEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class TbQcCategory extends BaseEntity {
	
	//alias
	public static final String TABLE_ALIAS = "TbQcCategory";
	public static final String ALIAS_VAR_CATEGORY_NAME = "varCategoryName";
	public static final String ALIAS_NUM_CATEGORY_RANK = "numCategoryRank";
	public static final String ALIAS_NUM_CATEGORY_TYPE = "numCategoryType";
	
	//date formats
	
	//columns START
	private java.lang.String varCategoryName;
	private java.lang.Integer numCategoryRank;
	private java.lang.Integer numCategoryType;
	private java.lang.String sortColumns;
	//columns END

	public TbQcCategory(){
	}

	public TbQcCategory(
		java.lang.Long id
	){
		this.id = id;
	}

	public java.lang.String getSortColumns() {
		return sortColumns;
	}

	public void setSortColumns(java.lang.String sortColumns) {
		this.sortColumns = sortColumns;
	}

	public void setVarCategoryName(java.lang.String value) {
		this.varCategoryName = value;
	}
	
	public java.lang.String getVarCategoryName() {
		return this.varCategoryName;
	}
	public void setNumCategoryRank(java.lang.Integer value) {
		this.numCategoryRank = value;
	}
	
	public java.lang.Integer getNumCategoryRank() {
		return this.numCategoryRank;
	}
	public void setNumCategoryType(java.lang.Integer value) {
		this.numCategoryType = value;
	}
	
	public java.lang.Integer getNumCategoryType() {
		return this.numCategoryType;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("VarCategoryName",getVarCategoryName())		
		.append("NumCategoryRank",getNumCategoryRank())		
		.append("NumCategoryType",getNumCategoryType())		
		.append("IsDeleted",getIsDeleted())		
		.append("Creator",getCreator())		
		.append("Modifier",getModifier())		
		.append("GmtCreated",getGmtCreated())		
		.append("GmtModified",getGmtModified())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getVarCategoryName())
		.append(getNumCategoryRank())
		.append(getNumCategoryType())
		.append(getIsDeleted())
		.append(getCreator())
		.append(getModifier())
		.append(getGmtCreated())
		.append(getGmtModified())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof TbQcCategory == false) return false;
		if(this == obj) return true;
		TbQcCategory other = (TbQcCategory)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getVarCategoryName(),other.getVarCategoryName())

		.append(getNumCategoryRank(),other.getNumCategoryRank())

		.append(getNumCategoryType(),other.getNumCategoryType())

		.append(getIsDeleted(),other.getIsDeleted())

		.append(getCreator(),other.getCreator())

		.append(getModifier(),other.getModifier())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getGmtModified(),other.getGmtModified())

			.isEquals();
	}
}

