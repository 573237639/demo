 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.tb.entity;

import com.fabaoframework.modules.mybatis.BaseEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class TbCall extends BaseEntity {
	
	//alias
	public static final String TABLE_ALIAS = "TbCall";
	
	//date formats
	
	//columns START
	//columns END

	public TbCall(){
	}

	public TbCall(
		java.lang.Long id
	){
		this.id = id;
	}

    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof TbCall == false) return false;
		if(this == obj) return true;
		TbCall other = (TbCall)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

			.isEquals();
	}
}

