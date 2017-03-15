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


public class SysMenu extends BaseEntity {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -7825096980235130762L;
	//alias
	public static final String TABLE_ALIAS = "SysMenu";
	public static final String ALIAS_NAME = "name";
	public static final String ALIAS_PARENT_ID = "parentId";
	public static final String ALIAS_ICON = "icon";
	public static final String ALIAS_SORT = "sort";
	public static final String ALIAS_MENU_TYPE = "menuType";
	public static final String ALIAS_REMARKS = "remarks";
	public static final String ALIAS_ACT_ID = "actId";
	
	//date formats
	
	//columns START
	private java.lang.String name;
	private java.lang.Integer parentId;
	private java.lang.String icon;
	private java.lang.Integer sort;
	private Integer menuType;
	private java.lang.String remarks;
	private java.lang.Integer actId;
	//columns END

	public SysMenu(){
	}

	public SysMenu(
		java.lang.Long id
	){
		this.id = id;
	}

	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setParentId(java.lang.Integer value) {
		this.parentId = value;
	}
	
	public java.lang.Integer getParentId() {
		return this.parentId;
	}
	public void setIcon(java.lang.String value) {
		this.icon = value;
	}
	
	public java.lang.String getIcon() {
		return this.icon;
	}
	public void setSort(java.lang.Integer value) {
		this.sort = value;
	}
	
	public java.lang.Integer getSort() {
		return this.sort;
	}
	public void setMenuType(Integer value) {
		this.menuType = value;
	}
	
	public Integer getMenuType() {
		return this.menuType;
	}
	public void setRemarks(java.lang.String value) {
		this.remarks = value;
	}
	
	public java.lang.String getRemarks() {
		return this.remarks;
	}
	public void setActId(java.lang.Integer value) {
		this.actId = value;
	}
	
	public java.lang.Integer getActId() {
		return this.actId;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("Name",getName())		
		.append("ParentId",getParentId())		
		.append("Icon",getIcon())		
		.append("Sort",getSort())		
		.append("MenuType",getMenuType())		
		.append("Remarks",getRemarks())		
		.append("ActId",getActId())		
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
		.append(getName())
		.append(getParentId())
		.append(getIcon())
		.append(getSort())
		.append(getMenuType())
		.append(getRemarks())
		.append(getActId())
		.append(getCreator())
		.append(getModifier())
		.append(getGmtCreated())
		.append(getGmtModified())
		.append(getIsDeleted())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof SysMenu == false) return false;
		if(this == obj) return true;
		SysMenu other = (SysMenu)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getName(),other.getName())

		.append(getParentId(),other.getParentId())

		.append(getIcon(),other.getIcon())

		.append(getSort(),other.getSort())

		.append(getMenuType(),other.getMenuType())

		.append(getRemarks(),other.getRemarks())

		.append(getActId(),other.getActId())

		.append(getCreator(),other.getCreator())

		.append(getModifier(),other.getModifier())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getGmtModified(),other.getGmtModified())

		.append(getIsDeleted(),other.getIsDeleted())

			.isEquals();
	}
    
    //-------------------------------------------------------
    public static final int MENU_TYPE_PARENT=1; //父级菜单
	public static final int MENU_TYPE_SON=2; //叶子菜单
	
	public String getMenuTypeString(){
		if(null==menuType){
			return "";
		}
		switch (menuType){
		case MENU_TYPE_PARENT:
			return "父级菜单";
		case MENU_TYPE_SON:
			return "叶子菜单";
		default:
			return "菜单类型错误";
		}
	}
	
	 private SysMenu parentMenu;
	    private SysAct sysAct;
	    private List<SysMenu> subMenus;

		public SysMenu getParentMenu() {
			return parentMenu;
		}

		public void setParentMenu(SysMenu parentMenu) {
			if(null!=parentMenu){
				this.parentId = parentMenu.getId().intValue();
			}else{
				this.parentId = 0;
			}
			this.parentMenu = parentMenu;
		}

		public SysAct getSysAct() {
			return sysAct;
		}

		public void setSysAct(SysAct sysAct) {
			if(null!=sysAct && sysAct.getId()!=null){
				this.actId = sysAct.getId().intValue();
			}
			this.sysAct = sysAct;
		}

		public List<SysMenu> getSubMenus() {
			return subMenus;
		}

		public void setSubMenus(List<SysMenu> subMenus) {
			this.subMenus = subMenus;
		}
}

