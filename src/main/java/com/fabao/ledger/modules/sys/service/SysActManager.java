/**
 * Copyright (c) 2005-2010 <a href="http://git.oschina.net/taote/xframe">XFrame</a> All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.fabao.ledger.modules.sys.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fabao.ledger.modules.sys.dao.SysActDao;
import com.fabao.ledger.modules.sys.dao.SysRoleActDao;
import com.fabao.ledger.modules.sys.entity.SysAct;
import com.fabaoframework.modules.mybatis.BaseManager;
import com.google.common.collect.Lists;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class SysActManager extends BaseManager<SysActDao, SysAct> {
	
	@Autowired
	private SysRoleActDao sysRoleActDao;

	@Override
	@Resource(name="sysActDao")
	public void setEntityDao(SysActDao sysActDao) {
		this.entityDao = sysActDao;
	}

	/**
	 * 获取菜单的动作列表
	 * @param menuId
	 * @param type
	 * @return
	 */
	public List<SysAct> findMenuAct(Long menuId,int type){
		SysAct paramAct = new SysAct();
		paramAct.setMenuId(menuId.intValue());
		paramAct.setType(type);
		return this.entityDao.getAllByEntity(paramAct);
	}
	
	public void delActByActId(Long id){
		this.entityDao.delete(id);
		List<SysAct> acts = Lists.newArrayList();
		SysAct act = new SysAct();
		act.setId(id);
		acts.add(act);
		this.sysRoleActDao.delByActIds(acts);
	}
	
	public List<String> getCommonActs(List<Long> roleIds) {
		if(roleIds==null||roleIds.size()==0){
			return new ArrayList<String>();
		}
		return this.entityDao.getCommonActs(roleIds);
	}

	public List<String> getAuthorActs(List<Long> roleIds) {
		if(roleIds==null||roleIds.size()==0){
			return new ArrayList<String>();
		}
		return this.entityDao.getAuthorActs(roleIds);
	}
}
