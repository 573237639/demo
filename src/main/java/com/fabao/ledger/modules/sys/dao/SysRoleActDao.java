/**
 * Copyright (c) 2005-2010 http://git.oschina.net/taote/xframe
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.fabao.ledger.modules.sys.dao;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fabao.ledger.modules.sys.entity.SysAct;
import com.fabao.ledger.modules.sys.entity.SysRoleAct;
import com.fabaoframework.modules.mybatis.BaseMybatis3Dao;

@Component
public class SysRoleActDao extends BaseMybatis3Dao<SysRoleAct> {

	public void deleteByRoleId(Long roleId){
		getSqlSession().delete(this.getQueryPath("deleteByRoleId"), roleId);
	}
	
	public void delByActIds(List<SysAct> acts){
		List<Integer> list = new ArrayList<Integer>();
		for(SysAct act:acts){
			list.add(act.getId().intValue());
		}
		this.getSqlSession().delete(this.getQueryPath("delByActIds"), list);
	}
}
