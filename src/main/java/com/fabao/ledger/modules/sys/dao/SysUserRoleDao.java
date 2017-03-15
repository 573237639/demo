 /**
 * Copyright (c) 2005-2010 woyo.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.sys.dao;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fabao.ledger.modules.sys.entity.SysUserRole;
import com.fabaoframework.modules.mybatis.BaseMybatis3Dao;


@Component
public class SysUserRoleDao extends BaseMybatis3Dao<SysUserRole>{


	public void saveUserRole(List<SysUserRole> userRoles){
		for(SysUserRole userRole:userRoles){
			this.merge(userRole);
		}
	}
	
	public void deleteByUid(Long uid){
		this.getSqlSession().delete(this.getQueryPath("deleteByUid"), uid);
	}
	
	public void deleteByRoleId(Long roleid){
		this.getSqlSession().delete(this.getQueryPath("deleteByRoleId"), roleid);
	}
	
	public List<Long> getRoleIdsByUid(Long uid){
		return getSqlSession().selectList(this.getQueryPath("getRoleIdsByUid"), uid);
	}
	
	public List<Map<String,Object>> findUsersByRole(){
		return getSqlSession().selectList(this.getQueryPath("getUserByRole"));
	}
	public void updateIsDelete(Long roleId,Boolean isDeleted){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("roleId",roleId);
		map.put("isDeleted", isDeleted);
		this.getSqlSession().update("updateIsDelete",map);
	}
}
