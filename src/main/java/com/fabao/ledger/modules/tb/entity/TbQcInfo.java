 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.tb.entity;

import com.fabaoframework.modules.mybatis.BaseEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class TbQcInfo extends BaseEntity {
	
	//alias
	public static final String TABLE_ALIAS = "TbQcInfo";
	public static final String ALIAS_NUM_QC_BASE_ID = "质检ID";
	public static final String ALIAS_NUM_PRO_ID = "质检项目ID";
	public static final String ALIAS_NUM_QCINFO_SCORE = "1，必合格 0，不必合格  1：质检项目中必须此项合格整个质检才算合格";
	public static final String ALIAS_NUM_QCPRO_BIT = "1、可用 0、不可用";
	
	//date formats
	
	//columns START
	private java.lang.Integer numQcBaseId;
	private java.lang.Integer numProId;
	private java.lang.Integer numQcinfoScore;
	private java.lang.Integer numQcproBit;
	//columns END

	public TbQcInfo(){
	}

	public TbQcInfo(
		java.lang.Long id
	){
		this.id = id;
	}

	public void setNumQcBaseId(java.lang.Integer value) {
		this.numQcBaseId = value;
	}
	
	public java.lang.Integer getNumQcBaseId() {
		return this.numQcBaseId;
	}
	public void setNumProId(java.lang.Integer value) {
		this.numProId = value;
	}
	
	public java.lang.Integer getNumProId() {
		return this.numProId;
	}
	public void setNumQcinfoScore(java.lang.Integer value) {
		this.numQcinfoScore = value;
	}
	
	public java.lang.Integer getNumQcinfoScore() {
		return this.numQcinfoScore;
	}
	public void setNumQcproBit(java.lang.Integer value) {
		this.numQcproBit = value;
	}
	
	public java.lang.Integer getNumQcproBit() {
		return this.numQcproBit;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("NumQcBaseId",getNumQcBaseId())		
		.append("NumProId",getNumProId())		
		.append("NumQcinfoScore",getNumQcinfoScore())		
		.append("NumQcproBit",getNumQcproBit())		
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
		.append(getNumQcBaseId())
		.append(getNumProId())
		.append(getNumQcinfoScore())
		.append(getNumQcproBit())
		.append(getIsDeleted())
		.append(getCreator())
		.append(getModifier())
		.append(getGmtCreated())
		.append(getGmtModified())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof TbQcInfo == false) return false;
		if(this == obj) return true;
		TbQcInfo other = (TbQcInfo)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getNumQcBaseId(),other.getNumQcBaseId())

		.append(getNumProId(),other.getNumProId())

		.append(getNumQcinfoScore(),other.getNumQcinfoScore())

		.append(getNumQcproBit(),other.getNumQcproBit())

		.append(getIsDeleted(),other.getIsDeleted())

		.append(getCreator(),other.getCreator())

		.append(getModifier(),other.getModifier())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getGmtModified(),other.getGmtModified())

			.isEquals();
	}
}

