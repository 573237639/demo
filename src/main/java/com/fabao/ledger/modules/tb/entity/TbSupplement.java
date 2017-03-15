 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.tb.entity;

import com.fabaoframework.modules.mybatis.BaseEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class TbSupplement extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7198606603658459897L;
	//alias
	public static final String TABLE_ALIAS = "TbSupplement";
	public static final String ALIAS_VAR_SUPPLEMENT_TYPE = "varSupplementType";
	public static final String ALIAS_VAC_SUPPLEMENT_CONTENT = "vacSupplementContent";
	public static final String ALIAS_NUM_LEDGER_ORDER_ID = "numLedgerOrderId";
	public static final String ALIAS_NUM_SUPPLEMENT_STATUS = "numSupplementStatus";
	
	//date formats
	
	//columns START
	private java.lang.String varSupplementType;
	private java.lang.String vacSupplementContent;
	private java.lang.Integer numLedgerOrderId;
	private java.lang.Integer numSupplementStatus;
	private java.lang.String vacCreaterName;
	//columns END

	public TbSupplement(){
	}

	public TbSupplement(
		java.lang.Long id
	){
		this.id = id;
	}

	public java.lang.String getVacCreaterName() {
		return vacCreaterName;
	}

	public void setVacCreaterName(java.lang.String vacCreaterName) {
		this.vacCreaterName = vacCreaterName;
	}

	public void setVarSupplementType(java.lang.String value) {
		this.varSupplementType = value;
	}
	
	public java.lang.String getVarSupplementType() {
		return this.varSupplementType;
	}
	public void setVacSupplementContent(java.lang.String value) {
		this.vacSupplementContent = value;
	}
	
	public java.lang.String getVacSupplementContent() {
		return this.vacSupplementContent;
	}
	public void setNumLedgerOrderId(java.lang.Integer value) {
		this.numLedgerOrderId = value;
	}
	
	public java.lang.Integer getNumLedgerOrderId() {
		return this.numLedgerOrderId;
	}
	public void setNumSupplementStatus(java.lang.Integer value) {
		this.numSupplementStatus = value;
	}
	
	public java.lang.Integer getNumSupplementStatus() {
		return this.numSupplementStatus;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("VarSupplementType",getVarSupplementType())		
		.append("VacSupplementContent",getVacSupplementContent())		
		.append("NumLedgerOrderId",getNumLedgerOrderId())		
		.append("NumSupplementStatus",getNumSupplementStatus())		
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
		.append(getVarSupplementType())
		.append(getVacSupplementContent())
		.append(getNumLedgerOrderId())
		.append(getNumSupplementStatus())
		.append(getCreator())
		.append(getModifier())
		.append(getGmtCreated())
		.append(getGmtModified())
		.append(getIsDeleted())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof TbSupplement == false) return false;
		if(this == obj) return true;
		TbSupplement other = (TbSupplement)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getVarSupplementType(),other.getVarSupplementType())

		.append(getVacSupplementContent(),other.getVacSupplementContent())

		.append(getNumLedgerOrderId(),other.getNumLedgerOrderId())

		.append(getNumSupplementStatus(),other.getNumSupplementStatus())

		.append(getCreator(),other.getCreator())

		.append(getModifier(),other.getModifier())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getGmtModified(),other.getGmtModified())

		.append(getIsDeleted(),other.getIsDeleted())

			.isEquals();
	}
}

