 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.tb.entity;

import com.fabaoframework.modules.mybatis.BaseEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class TbQcPro extends BaseEntity {
	
	//alias
	public static final String TABLE_ALIAS = "TbQcPro";
	public static final String ALIAS_VAC_QCPRO_NAME = "vacQcproName";
	public static final String ALIAS_NUM_QCPRO_SCORE = "numQcproScore";
	public static final String ALIAS_NUM_QCPRO_CHECK_SCORE = "numQcproCheckScore";
	public static final String ALIAS_NUM_QCPRO_MUST_CHECK_BIT = "numQcproMustCheckBit";
	public static final String ALIAS_NUM_QCPRO_BIT = "numQcproBit";
	public static final String ALIAS_NUM_CATEGORY_ID = "numCategoryId";
	
	//date formats
	
	//columns START
	private java.lang.String vacQcproName;//项目名称
	private java.lang.Integer numQcproScore;//质检分数
	private java.lang.Float numQcproCheckScore;//0-1：合格比例>1：合格分数
	private java.lang.Integer numQcproMustCheckBit;//1，必合格 0，不必合格  1：质检项目中必须此项合格整个质检才算合格
	private java.lang.Integer numQcproBit;//是否有效（1 无效，0 有效）
	private java.lang.Integer numCategoryId;//质检分类ID
	private java.lang.String vacCategoryName;//质检分类ID
	private TbQcCategory tbQcCategory;
	//columns END

	public TbQcPro(){
	}

	public TbQcPro(
		java.lang.Long id
	){
		this.id = id;
	}

	public TbQcCategory getTbQcCategory() {
		return tbQcCategory;
	}

	public void setTbQcCategory(TbQcCategory tbQcCategory) {
		this.tbQcCategory = tbQcCategory;
	}

	public java.lang.String getVacCategoryName() {
		return vacCategoryName;
	}

	public void setVacCategoryName(java.lang.String vacCategoryName) {
		this.vacCategoryName = vacCategoryName;
	}

	public void setVacQcproName(java.lang.String value) {
		this.vacQcproName = value;
	}
	
	public java.lang.String getVacQcproName() {
		return this.vacQcproName;
	}
	public void setNumQcproScore(java.lang.Integer value) {
		this.numQcproScore = value;
	}
	
	public java.lang.Integer getNumQcproScore() {
		return this.numQcproScore;
	}
	public void setNumQcproCheckScore(java.lang.Float value) {
		this.numQcproCheckScore = value;
	}
	
	public java.lang.Float getNumQcproCheckScore() {
		return this.numQcproCheckScore;
	}
	public void setNumQcproMustCheckBit(java.lang.Integer value) {
		this.numQcproMustCheckBit = value;
	}
	
	public java.lang.Integer getNumQcproMustCheckBit() {
		return this.numQcproMustCheckBit;
	}
	public void setNumQcproBit(java.lang.Integer value) {
		this.numQcproBit = value;
	}
	
	public java.lang.Integer getNumQcproBit() {
		return this.numQcproBit;
	}
	public void setNumCategoryId(java.lang.Integer value) {
		this.numCategoryId = value;
	}
	
	public java.lang.Integer getNumCategoryId() {
		return this.numCategoryId;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("VacQcproName",getVacQcproName())		
		.append("NumQcproScore",getNumQcproScore())		
		.append("NumQcproCheckScore",getNumQcproCheckScore())		
		.append("NumQcproMustCheckBit",getNumQcproMustCheckBit())		
		.append("NumQcproBit",getNumQcproBit())		
		.append("IsDeleted",getIsDeleted())		
		.append("Creator",getCreator())		
		.append("Modifier",getModifier())		
		.append("GmtCreated",getGmtCreated())		
		.append("GmtModified",getGmtModified())		
		.append("NumCategoryId",getNumCategoryId())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getVacQcproName())
		.append(getNumQcproScore())
		.append(getNumQcproCheckScore())
		.append(getNumQcproMustCheckBit())
		.append(getNumQcproBit())
		.append(getIsDeleted())
		.append(getCreator())
		.append(getModifier())
		.append(getGmtCreated())
		.append(getGmtModified())
		.append(getNumCategoryId())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof TbQcPro == false) return false;
		if(this == obj) return true;
		TbQcPro other = (TbQcPro)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getVacQcproName(),other.getVacQcproName())

		.append(getNumQcproScore(),other.getNumQcproScore())

		.append(getNumQcproCheckScore(),other.getNumQcproCheckScore())

		.append(getNumQcproMustCheckBit(),other.getNumQcproMustCheckBit())

		.append(getNumQcproBit(),other.getNumQcproBit())

		.append(getIsDeleted(),other.getIsDeleted())

		.append(getCreator(),other.getCreator())

		.append(getModifier(),other.getModifier())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getGmtModified(),other.getGmtModified())

		.append(getNumCategoryId(),other.getNumCategoryId())

			.isEquals();
	}
}

