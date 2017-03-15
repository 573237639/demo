/**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.fabao.ledger.modules.sys.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fabao.ledger.modules.sys.dao.SysRoleActDao;
import com.fabao.ledger.modules.sys.dao.SysRoleDao;
import com.fabao.ledger.modules.sys.dao.SysRoleMenuDao;
import com.fabao.ledger.modules.sys.dao.SysUserRoleDao;
import com.fabao.ledger.modules.sys.entity.SysRole;
import com.fabaoframework.modules.mybatis.BaseManager;

@Component
@Transactional(rollbackFor = Exception.class)
public class SysRoleManager extends BaseManager<SysRoleDao, SysRole> {

	@Override
	@Autowired
	public void setEntityDao(SysRoleDao sysRoleDao) {
		this.entityDao = sysRoleDao;
	}
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	@Autowired
	private SysRoleActDao sysRoleActDao;

	public SysRole selectRoleByName(String name){
		if(StringUtils.isBlank(name)){
			return null;
		}
		return this.entityDao.selectRoleByName(name);
	}
	
	
	public List<SysRole> getRolesByUid(Long uid){
		return this.entityDao.getRolesByUid(uid);
	}
	
	public List<Map<String,Object>> validRolelist(){
		return this.entityDao.validRolelist();
	}
	
	
	public void deleteRole(Long id){
		this.entityDao.delete(id);
		sysUserRoleDao.deleteByRoleId(id);
		sysRoleMenuDao.deleteByRoleId(id);
		sysRoleActDao.deleteByRoleId(id);
	}
}
