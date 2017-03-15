 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.sys.entity;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fabaoframework.modules.mybatis.BaseEntity;
import com.fabaoframework.modules.utils.Collections3;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;


public class SysUser extends BaseEntity {
	
	//alias
	public static final String TABLE_ALIAS = "SysUser";
	public static final String ALIAS_REALNAME = "realname";
	public static final String ALIAS_USERNAME = "username";
	public static final String ALIAS_PASSWORD = "password";
	public static final String ALIAS_USER_TYPE = "userType";
	public static final String ALIAS_PHONE = "手机号";
	public static final String ALIAS_EMAIL = "邮箱";
	public static final String ALIAS_HW_NUM = "话务工号";
	public static final String ALIAS_SEX = "性别(1、先生2、保密3、女士)";
	public static final String ALIAS_SEATS = "坐席类型";
	public static final String ALIAS_INGROUP = "所属分组";
	public static final String ALIAS_ROLE = "角色";
	public static final String ALIAS_EXT_NUM = "分机号";
	public static final String ALIAS_LAST_LOGIN_TIME = "最后登录时间";
	
	//date formats
			public static final String FORMAT_LAST_LOGIN_TIME = DATE_TIME_FORMAT;
	
	//columns START
	private java.lang.String realname;
	private java.lang.String username;
	private java.lang.String password;
	private java.lang.Integer userType;
	private java.lang.String phone;
	private java.lang.String email;
	private java.lang.String hwNum;
	private java.lang.String sex;
	private java.lang.String seats;
	private java.lang.String ingroup;
	private java.lang.String role;
	private java.lang.String extNum;
	private java.util.Date lastLoginTime;
	//columns END

	public SysUser(){
	}

	public SysUser(
		java.lang.Long id
	){
		this.id = id;
	}

	public void setRealname(java.lang.String value) {
		this.realname = value;
	}
	
	public java.lang.String getRealname() {
		return this.realname;
	}
	public void setUsername(java.lang.String value) {
		this.username = value;
	}
	
	public java.lang.String getUsername() {
		return this.username;
	}
	public void setPassword(java.lang.String value) {
		this.password = value;
	}
	
	public java.lang.String getPassword() {
		return this.password;
	}
	public void setUserType(java.lang.Integer value) {
		this.userType = value;
	}
	
	public java.lang.Integer getUserType() {
		return this.userType;
	}
	public void setPhone(java.lang.String value) {
		this.phone = value;
	}
	
	public java.lang.String getPhone() {
		return this.phone;
	}
	public void setEmail(java.lang.String value) {
		this.email = value;
	}
	
	public java.lang.String getEmail() {
		return this.email;
	}
	public void setHwNum(java.lang.String value) {
		this.hwNum = value;
	}
	
	public java.lang.String getHwNum() {
		return this.hwNum;
	}
	public void setSex(java.lang.String value) {
		this.sex = value;
	}
	
	public java.lang.String getSex() {
		return this.sex;
	}
	public void setSeats(java.lang.String value) {
		this.seats = value;
	}
	
	public java.lang.String getSeats() {
		return this.seats;
	}
	public void setIngroup(java.lang.String value) {
		this.ingroup = value;
	}
	
	public java.lang.String getIngroup() {
		return this.ingroup;
	}
	public void setRole(java.lang.String value) {
		this.role = value;
	}
	
	public java.lang.String getRole() {
		return this.role;
	}
	public void setExtNum(java.lang.String value) {
		this.extNum = value;
	}
	
	public java.lang.String getExtNum() {
		return this.extNum;
	}
	public String getLastLoginTimeString() {
		return date2String(getLastLoginTime(), FORMAT_LAST_LOGIN_TIME);
	}
	public void setLastLoginTimeString(String value) {
		setLastLoginTime(string2Date(value, FORMAT_LAST_LOGIN_TIME,java.util.Date.class));
	}
	
	public void setLastLoginTime(java.util.Date value) {
		this.lastLoginTime = value;
	}
	
	public java.util.Date getLastLoginTime() {
		return this.lastLoginTime;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("Realname",getRealname())		
		.append("Username",getUsername())		
		.append("Password",getPassword())		
		.append("UserType",getUserType())		
		.append("Phone",getPhone())		
		.append("Email",getEmail())		
		.append("HwNum",getHwNum())		
		.append("Sex",getSex())		
		.append("Seats",getSeats())		
		.append("Ingroup",getIngroup())		
		.append("Role",getRole())		
		.append("ExtNum",getExtNum())		
		.append("LastLoginTime",getLastLoginTime())		
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
		.append(getRealname())
		.append(getUsername())
		.append(getPassword())
		.append(getUserType())
		.append(getPhone())
		.append(getEmail())
		.append(getHwNum())
		.append(getSex())
		.append(getSeats())
		.append(getIngroup())
		.append(getRole())
		.append(getExtNum())
		.append(getLastLoginTime())
		.append(getCreator())
		.append(getModifier())
		.append(getGmtCreated())
		.append(getGmtModified())
		.append(getIsDeleted())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof SysUser == false) return false;
		if(this == obj) return true;
		SysUser other = (SysUser)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getRealname(),other.getRealname())

		.append(getUsername(),other.getUsername())

		.append(getPassword(),other.getPassword())

		.append(getUserType(),other.getUserType())

		.append(getPhone(),other.getPhone())

		.append(getEmail(),other.getEmail())

		.append(getHwNum(),other.getHwNum())

		.append(getSex(),other.getSex())

		.append(getSeats(),other.getSeats())

		.append(getIngroup(),other.getIngroup())

		.append(getRole(),other.getRole())

		.append(getExtNum(),other.getExtNum())

		.append(getLastLoginTime(),other.getLastLoginTime())

		.append(getCreator(),other.getCreator())

		.append(getModifier(),other.getModifier())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getGmtModified(),other.getGmtModified())

		.append(getIsDeleted(),other.getIsDeleted())

			.isEquals();
	}
	
	private List<SysRole> roleList = Lists.newArrayList();

	public List<SysRole> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<SysRole> roleList) {
		this.roleList = roleList;
	}

	@JsonIgnore
	public List<Long> getRoleIdList() {
		List<Long> roleIdList = Lists.newArrayList();
		for (SysRole role : roleList) {
			roleIdList.add(role.getId());
		}
		return roleIdList;
	}

	public void setRoleIdList(List<Long> roleIdList) {
		roleList = Lists.newArrayList();
		for (Long roleId : roleIdList) {
			SysRole role = new SysRole();
			role.setId(roleId);
			roleList.add(role);
		}
	}
    
	/**
	 * 用户拥有的角色名称字符串, 多个角色名称用','分隔.
	 */
	public String getRoleNames() {
		return Collections3.extractToString(roleList, "name", ", ");
	}
}

