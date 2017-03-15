 /**
 * Copyright (c) 2005-2010 http://git.oschina.net/taote/xframe
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.sys.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fabao.ledger.modules.sys.entity.SysRole;
import com.fabaoframework.modules.mybatis.BaseMybatis3Dao;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


@Component
public class SysRoleDao extends BaseMybatis3Dao<SysRole>{

	public List<SysRole> getRolesByUid(Long uid){
		return getSqlSession().selectList(this.getQueryPath("getRolesByUid"),uid);
	}

	public List<Map<String,Object>> validRolelist(){
		return this.getSqlSession().selectList(this.getQueryPath("validRolelist"));
	}
	
	public SysRole selectRoleByName(String name){
		return this.getSqlSession().selectOne(this.getQueryPath("selectRoleByName"),name);
	}
}
