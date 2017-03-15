 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.cms.entity;

import com.fabaoframework.modules.mybatis.BaseEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class CmsOpinion extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7156026023400133964L;
	//alias
	public static final String TABLE_ALIAS = "CmsOpinion";
	public static final String ALIAS_VAR_OPINION_CONTENT = "varOpinionContent";
	public static final String ALIAS_VAR_OPINION_TYPE = "varOpinionType";
	public static final String ALIAS_VAR_OPINION_TITLE = "varOpinionTitle";
	public static final String ALIAS_VAR_OPINION_ID = "varOpinionId";
	
	//date formats
	
	//columns START
	private java.lang.String varOpinionContent;
	private java.lang.String varOpinionType;
	private java.lang.String varOpinionTitle;
	private java.lang.String varOpinionId;
	private java.lang.String varCreaterName;
	//columns END

	public CmsOpinion(){
	}

	public CmsOpinion(
		java.lang.Long id
	){
		this.id = id;
	}

	
	public java.lang.String getVarCreaterName() {
		return varCreaterName;
	}

	public void setVarCreaterName(java.lang.String varCreaterName) {
		this.varCreaterName = varCreaterName;
	}

	public void setVarOpinionContent(java.lang.String value) {
		this.varOpinionContent = value;
	}
	
	public java.lang.String getVarOpinionContent() {
		return this.varOpinionContent;
	}
	public void setVarOpinionType(java.lang.String value) {
		this.varOpinionType = value;
	}
	
	public java.lang.String getVarOpinionType() {
		return this.varOpinionType;
	}
	public void setVarOpinionTitle(java.lang.String value) {
		this.varOpinionTitle = value;
	}
	
	public java.lang.String getVarOpinionTitle() {
		return this.varOpinionTitle;
	}
	public void setVarOpinionId(java.lang.String value) {
		this.varOpinionId = value;
	}
	
	public java.lang.String getVarOpinionId() {
		return this.varOpinionId;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("VarOpinionContent",getVarOpinionContent())		
		.append("VarOpinionType",getVarOpinionType())		
		.append("VarOpinionTitle",getVarOpinionTitle())		
		.append("Creator",getCreator())		
		.append("Modifier",getModifier())		
		.append("GmtCreated",getGmtCreated())		
		.append("GmtModified",getGmtModified())		
		.append("IsDeleted",getIsDeleted())		
		.append("VarOpinionId",getVarOpinionId())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getVarOpinionContent())
		.append(getVarOpinionType())
		.append(getVarOpinionTitle())
		.append(getCreator())
		.append(getModifier())
		.append(getGmtCreated())
		.append(getGmtModified())
		.append(getIsDeleted())
		.append(getVarOpinionId())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof CmsOpinion == false) return false;
		if(this == obj) return true;
		CmsOpinion other = (CmsOpinion)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getVarOpinionContent(),other.getVarOpinionContent())

		.append(getVarOpinionType(),other.getVarOpinionType())

		.append(getVarOpinionTitle(),other.getVarOpinionTitle())

		.append(getCreator(),other.getCreator())

		.append(getModifier(),other.getModifier())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getGmtModified(),other.getGmtModified())

		.append(getIsDeleted(),other.getIsDeleted())

		.append(getVarOpinionId(),other.getVarOpinionId())

			.isEquals();
	}
}

