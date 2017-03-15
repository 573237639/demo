 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.sys.entity;

import com.fabaoframework.modules.mybatis.BaseEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class SysNotice extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1187509347761969199L;
	//alias
	public static final String TABLE_ALIAS = "SysNotice";
	public static final String ALIAS_VAC_TITLE = "vacTitle";
	public static final String ALIAS_TEXT_CONTENT = "textContent";
	
	//date formats
	
	//columns START
	private java.lang.String vacTitle;
	private java.lang.String textContent;
	//columns END

	public SysNotice(){
	}

	public SysNotice(
		java.lang.Long id
	){
		this.id = id;
	}

	public void setVacTitle(java.lang.String value) {
		this.vacTitle = value;
	}
	
	public java.lang.String getVacTitle() {
		return this.vacTitle;
	}
	public void setTextContent(java.lang.String value) {
		this.textContent = value;
	}
	
	public java.lang.String getTextContent() {
		return this.textContent;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("VacTitle",getVacTitle())		
		.append("TextContent",getTextContent())		
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
		.append(getVacTitle())
		.append(getTextContent())
		.append(getCreator())
		.append(getModifier())
		.append(getGmtCreated())
		.append(getGmtModified())
		.append(getIsDeleted())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof SysNotice == false) return false;
		if(this == obj) return true;
		SysNotice other = (SysNotice)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getVacTitle(),other.getVacTitle())

		.append(getTextContent(),other.getTextContent())

		.append(getCreator(),other.getCreator())

		.append(getModifier(),other.getModifier())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getGmtModified(),other.getGmtModified())

		.append(getIsDeleted(),other.getIsDeleted())

			.isEquals();
	}
}

