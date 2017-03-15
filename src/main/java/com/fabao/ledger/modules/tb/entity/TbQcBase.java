 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.tb.entity;

import com.fabaoframework.modules.mybatis.BaseEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class TbQcBase extends BaseEntity {
	
	//alias
	public static final String TABLE_ALIAS = "TbQcBase";
	public static final String ALIAS_VAC_QCBASE_SERIAL = "vacQcbaseSerial";
	public static final String ALIAS_VAC_LAW_ID = "vacLawId";
	public static final String ALIAS_VAC_LAWYER_NAME = "vacLawyerName";
	public static final String ALIAS_DATE_QCBASE_CONSULT = "dateQcbaseConsult";
	public static final String ALIAS_NUM_QCBASE_SCORE = "numQcbaseScore";
	public static final String ALIAS_NUM_QCBASE_CHECK_BIT = "numQcbaseCheckBit";
	public static final String ALIAS_NUM_QCBASE_CHECK_STATE = "numQcbaseCheckState";
	public static final String ALIAS_VAC_QCBASE_SUMMARY = "vacQcbaseSummary";
	public static final String ALIAS_VAC_QCBASE_IMPROVE = "vacQcbaseImprove";
	public static final String ALIAS_VAC_QCBASE_COMMENT = "vacQcbaseComment";
	public static final String ALIAS_VAC_QCBASE_MEMO = "vacQcbaseMemo";
	public static final String ALIAS_VAC_QCBASE_NAME = "vacQcbaseName";
	public static final String ALIAS_DATE_QCBASE_TIME = "dateQcbaseTime";
	
	//date formats
			public static final String FORMAT_DATE_QCBASE_CONSULT = DATE_TIME_FORMAT;
			public static final String FORMAT_DATE_QCBASE_TIME = DATE_TIME_FORMAT;
	
	//columns START
	private java.lang.String vacQcbaseSerial;//流水号
	private java.lang.String vacLawId;//律师工号
	private java.lang.String vacLawyerName;//律师姓名
	private java.util.Date dateQcbaseConsult;//咨询日期
	private java.lang.Integer numQcbaseScore;//质检得分
	private java.lang.Integer numQcbaseCheckBit;//是否合格
	private java.lang.Integer numQcbaseCheckState;//质检审核状态
	private java.lang.String vacQcbaseSummary;//用户问题
	private java.lang.String vacQcbaseImprove;//律师意见
	private java.lang.String vacQcbaseComment;//综合评价与改进建议
	private java.lang.String vacQcbaseMemo;//备注
	private java.lang.String vacQcbaseName;//质检员
	private java.util.Date dateQcbaseTime;//质检日期
	//columns END

	public TbQcBase(){
	}

	public TbQcBase(
		java.lang.Long id
	){
		this.id = id;
	}

	public void setVacQcbaseSerial(java.lang.String value) {
		this.vacQcbaseSerial = value;
	}
	
	public java.lang.String getVacQcbaseSerial() {
		return this.vacQcbaseSerial;
	}
	public void setVacLawId(java.lang.String value) {
		this.vacLawId = value;
	}
	
	public java.lang.String getVacLawId() {
		return this.vacLawId;
	}
	public void setVacLawyerName(java.lang.String value) {
		this.vacLawyerName = value;
	}
	
	public java.lang.String getVacLawyerName() {
		return this.vacLawyerName;
	}
	public String getDateQcbaseConsultString() {
		return date2String(getDateQcbaseConsult(), FORMAT_DATE_QCBASE_CONSULT);
	}
	public void setDateQcbaseConsultString(String value) {
		setDateQcbaseConsult(string2Date(value, FORMAT_DATE_QCBASE_CONSULT,java.util.Date.class));
	}
	
	public void setDateQcbaseConsult(java.util.Date value) {
		this.dateQcbaseConsult = value;
	}
	
	public java.util.Date getDateQcbaseConsult() {
		return this.dateQcbaseConsult;
	}
	public void setNumQcbaseScore(java.lang.Integer value) {
		this.numQcbaseScore = value;
	}
	
	public java.lang.Integer getNumQcbaseScore() {
		return this.numQcbaseScore;
	}
	public void setNumQcbaseCheckBit(java.lang.Integer value) {
		this.numQcbaseCheckBit = value;
	}
	
	public java.lang.Integer getNumQcbaseCheckBit() {
		return this.numQcbaseCheckBit;
	}
	public void setNumQcbaseCheckState(java.lang.Integer value) {
		this.numQcbaseCheckState = value;
	}
	
	public java.lang.Integer getNumQcbaseCheckState() {
		return this.numQcbaseCheckState;
	}
	public void setVacQcbaseSummary(java.lang.String value) {
		this.vacQcbaseSummary = value;
	}
	
	public java.lang.String getVacQcbaseSummary() {
		return this.vacQcbaseSummary;
	}
	public void setVacQcbaseImprove(java.lang.String value) {
		this.vacQcbaseImprove = value;
	}
	
	public java.lang.String getVacQcbaseImprove() {
		return this.vacQcbaseImprove;
	}
	public void setVacQcbaseComment(java.lang.String value) {
		this.vacQcbaseComment = value;
	}
	
	public java.lang.String getVacQcbaseComment() {
		return this.vacQcbaseComment;
	}
	public void setVacQcbaseMemo(java.lang.String value) {
		this.vacQcbaseMemo = value;
	}
	
	public java.lang.String getVacQcbaseMemo() {
		return this.vacQcbaseMemo;
	}
	public void setVacQcbaseName(java.lang.String value) {
		this.vacQcbaseName = value;
	}
	
	public java.lang.String getVacQcbaseName() {
		return this.vacQcbaseName;
	}
	public String getDateQcbaseTimeString() {
		return date2String(getDateQcbaseTime(), FORMAT_DATE_QCBASE_TIME);
	}
	public void setDateQcbaseTimeString(String value) {
		setDateQcbaseTime(string2Date(value, FORMAT_DATE_QCBASE_TIME,java.util.Date.class));
	}
	
	public void setDateQcbaseTime(java.util.Date value) {
		this.dateQcbaseTime = value;
	}
	
	public java.util.Date getDateQcbaseTime() {
		return this.dateQcbaseTime;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("VacQcbaseSerial",getVacQcbaseSerial())		
		.append("VacLawId",getVacLawId())		
		.append("VacLawyerName",getVacLawyerName())		
		.append("DateQcbaseConsult",getDateQcbaseConsult())		
		.append("NumQcbaseScore",getNumQcbaseScore())		
		.append("NumQcbaseCheckBit",getNumQcbaseCheckBit())		
		.append("NumQcbaseCheckState",getNumQcbaseCheckState())		
		.append("VacQcbaseSummary",getVacQcbaseSummary())		
		.append("VacQcbaseImprove",getVacQcbaseImprove())		
		.append("VacQcbaseComment",getVacQcbaseComment())		
		.append("VacQcbaseMemo",getVacQcbaseMemo())		
		.append("VacQcbaseName",getVacQcbaseName())		
		.append("DateQcbaseTime",getDateQcbaseTime())		
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
		.append(getVacQcbaseSerial())
		.append(getVacLawId())
		.append(getVacLawyerName())
		.append(getDateQcbaseConsult())
		.append(getNumQcbaseScore())
		.append(getNumQcbaseCheckBit())
		.append(getNumQcbaseCheckState())
		.append(getVacQcbaseSummary())
		.append(getVacQcbaseImprove())
		.append(getVacQcbaseComment())
		.append(getVacQcbaseMemo())
		.append(getVacQcbaseName())
		.append(getDateQcbaseTime())
		.append(getIsDeleted())
		.append(getCreator())
		.append(getModifier())
		.append(getGmtCreated())
		.append(getGmtModified())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof TbQcBase == false) return false;
		if(this == obj) return true;
		TbQcBase other = (TbQcBase)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getVacQcbaseSerial(),other.getVacQcbaseSerial())

		.append(getVacLawId(),other.getVacLawId())

		.append(getVacLawyerName(),other.getVacLawyerName())

		.append(getDateQcbaseConsult(),other.getDateQcbaseConsult())

		.append(getNumQcbaseScore(),other.getNumQcbaseScore())

		.append(getNumQcbaseCheckBit(),other.getNumQcbaseCheckBit())

		.append(getNumQcbaseCheckState(),other.getNumQcbaseCheckState())

		.append(getVacQcbaseSummary(),other.getVacQcbaseSummary())

		.append(getVacQcbaseImprove(),other.getVacQcbaseImprove())

		.append(getVacQcbaseComment(),other.getVacQcbaseComment())

		.append(getVacQcbaseMemo(),other.getVacQcbaseMemo())

		.append(getVacQcbaseName(),other.getVacQcbaseName())

		.append(getDateQcbaseTime(),other.getDateQcbaseTime())

		.append(getIsDeleted(),other.getIsDeleted())

		.append(getCreator(),other.getCreator())

		.append(getModifier(),other.getModifier())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getGmtModified(),other.getGmtModified())

			.isEquals();
	}
}

