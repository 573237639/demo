 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.tb.entity;

import com.fabaoframework.modules.mybatis.BaseEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class TbReexamine extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1334048824284491520L;
	//alias
	public static final String TABLE_ALIAS = "TbReexamine";
	public static final String ALIAS_NUM_REEXAMINE_SOURCEID = "numReexamineSourceid";
	public static final String ALIAS_NUM_REEXAMINE_TASKSTATE = "numReexamineTaskstate";
	public static final String ALIAS_DATE_COMPLETE_TIME = "dateCompleteTime";
	public static final String ALIAS_VAC_REEXAMINE_OPINION = "vacReexamineOopinion";
	public static final String ALIAS_DATE_ORDER_AUDIT = "dateOrderAudit";
	public static final String ALIAS_VAC_ORDER_TITLE = "vacOrderTitle";
	public static final String ALIAS_VAC_REEXAMINE_NAME = "vacReexamineName ";
	public static final String ALIAS_VAC_REEXAMINE_NUMBER = "vacReexamineNumber";
	
	//date formats
			public static final String FORMAT_DATE_COMPLETE_TIME = DATE_TIME_FORMAT;
			public static final String FORMAT_DATE_ORDER_AUDIT = DATE_TIME_FORMAT;
	
	//columns START
	private java.lang.Integer numReexamineSourceid;
	private java.lang.Integer numReexamineTaskstate;
	private java.util.Date dateCompleteTime;
	private java.lang.String vacReexamineOpinion;
	private java.util.Date dateOrderAudit;
	private java.lang.String vacOrderTitle;
	private java.lang.String vacTaskStateName;
	private java.lang.String vacReexamineName;
	private java.lang.String vacReexamineNumber;
	private java.lang.String vacSituation;
	private java.lang.String trialTimeStart;
	private java.lang.String trialTimeEnd;
	//columns END

	public TbReexamine(){
	}

	public TbReexamine(
		java.lang.Long id
	){
		this.id = id;
	}

	
	public java.lang.String getTrialTimeStart() {
		return trialTimeStart;
	}

	public void setTrialTimeStart(java.lang.String trialTimeStart) {
		this.trialTimeStart = trialTimeStart;
	}

	public java.lang.String getTrialTimeEnd() {
		return trialTimeEnd;
	}

	public void setTrialTimeEnd(java.lang.String trialTimeEnd) {
		this.trialTimeEnd = trialTimeEnd;
	}

	public java.lang.String getVacSituation() {
		return vacSituation;
	}

	public void setVacSituation(java.lang.String vacSituation) {
		this.vacSituation = vacSituation;
	}

	public java.lang.String getVacTaskStateName() {
		return vacTaskStateName;
	}

	public void setVacTaskStateName(java.lang.String vacTaskStateName) {
		this.vacTaskStateName = vacTaskStateName;
	}

	public void setNumReexamineSourceid(java.lang.Integer value) {
		this.numReexamineSourceid = value;
	}
	
	public java.lang.Integer getNumReexamineSourceid() {
		return this.numReexamineSourceid;
	}
	public void setNumReexamineTaskstate(java.lang.Integer value) {
		this.numReexamineTaskstate = value;
	}
	
	public java.lang.Integer getNumReexamineTaskstate() {
		return this.numReexamineTaskstate;
	}
	public String getDateCompleteTimeString() {
		return date2String(getDateCompleteTime(), FORMAT_DATE_COMPLETE_TIME);
	}
	public void setDateCompleteTimeString(String value) {
		setDateCompleteTime(string2Date(value, FORMAT_DATE_COMPLETE_TIME,java.util.Date.class));
	}
	
	public void setDateCompleteTime(java.util.Date value) {
		this.dateCompleteTime = value;
	}
	
	public java.util.Date getDateCompleteTime() {
		return this.dateCompleteTime;
	}
	public void setVacReexamineOpinion(java.lang.String value) {
		this.vacReexamineOpinion = value;
	}
	
	public java.lang.String getVacReexamineOpinion() {
		return this.vacReexamineOpinion;
	}
	public String getDateOrderAuditString() {
		return date2String(getDateOrderAudit(), FORMAT_DATE_ORDER_AUDIT);
	}
	public void setDateOrderAuditString(String value) {
		setDateOrderAudit(string2Date(value, FORMAT_DATE_ORDER_AUDIT,java.util.Date.class));
	}
	
	public void setDateOrderAudit(java.util.Date value) {
		this.dateOrderAudit = value;
	}
	
	public java.util.Date getDateOrderAudit() {
		return this.dateOrderAudit;
	}
	public void setVacOrderTitle(java.lang.String value) {
		this.vacOrderTitle = value;
	}
	
	public java.lang.String getVacOrderTitle() {
		return this.vacOrderTitle;
	}
	
	
	
    public java.lang.String getVacReexamineName() {
		return vacReexamineName;
	}

	public void setVacReexamineName(java.lang.String vacReexamineName) {
		this.vacReexamineName = vacReexamineName;
	}

	public java.lang.String getVacReexamineNumber() {
		return vacReexamineNumber;
	}

	public void setVacReexamineNumber(java.lang.String vacReexamineNumber) {
		this.vacReexamineNumber = vacReexamineNumber;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("NumReexamineSourceid",getNumReexamineSourceid())		
		.append("NumReexamineTaskstate",getNumReexamineTaskstate())		
		.append("DateCompleteTime",getDateCompleteTime())		
		.append("VacReexamineOpinion",getVacReexamineOpinion())		
		.append("Creator",getCreator())		
		.append("Modifier",getModifier())		
		.append("GmtCreated",getGmtCreated())		
		.append("GmtModified",getGmtModified())		
		.append("IsDeleted",getIsDeleted())		
		.append("DateOrderAudit",getDateOrderAudit())		
		.append("VacOrderTitle",getVacOrderTitle())		
		.append("VacReexamineName",getVacReexamineName())	
		.append("VacReexamineNumber",getVacReexamineNumber())	
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getNumReexamineSourceid())
		.append(getNumReexamineTaskstate())
		.append(getDateCompleteTime())
		.append(getVacReexamineOpinion())
		.append(getCreator())
		.append(getModifier())
		.append(getGmtCreated())
		.append(getGmtModified())
		.append(getIsDeleted())
		.append(getDateOrderAudit())
		.append(getVacOrderTitle())
		.append(getVacReexamineName())
		.append(getVacReexamineNumber())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof TbReexamine == false) return false;
		if(this == obj) return true;
		TbReexamine other = (TbReexamine)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getNumReexamineSourceid(),other.getNumReexamineSourceid())

		.append(getNumReexamineTaskstate(),other.getNumReexamineTaskstate())

		.append(getDateCompleteTime(),other.getDateCompleteTime())

		.append(getVacReexamineOpinion(),other.getVacReexamineOpinion())

		.append(getCreator(),other.getCreator())

		.append(getModifier(),other.getModifier())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getGmtModified(),other.getGmtModified())

		.append(getIsDeleted(),other.getIsDeleted())

		.append(getDateOrderAudit(),other.getDateOrderAudit())

		.append(getVacOrderTitle(),other.getVacOrderTitle())

		.append(getVacReexamineName(),other.getVacReexamineName())
		
		.append(getVacReexamineNumber(),other.getVacReexamineNumber())
		
			.isEquals();
	}
}

