 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.sys.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fabao.ledger.modules.sys.dao.SysRoleActDao;
import com.fabao.ledger.modules.sys.entity.SysRoleAct;
import com.fabaoframework.modules.mybatis.BaseManager;

@Component
@Transactional(rollbackFor = Exception.class)
public class SysRoleActManager extends BaseManager<SysRoleActDao,SysRoleAct>{

	@Override
	@Autowired
	public void setEntityDao(SysRoleActDao sysRoleActDao) {
		this.entityDao = sysRoleActDao;
	}

	public void deleteByRoleId(Long roleId){
		this.entityDao.deleteByRoleId(roleId);
	}
	
	public void saveRoleAct(Long roleId,String treemenuids){
		deleteByRoleId(roleId);
		if(StringUtils.isBlank(treemenuids)){
			return;
		}
		for(String s:treemenuids.split(",")){
			if(StringUtils.isNotBlank(s)){
				if(s.startsWith("a_")){
					Integer actId = Integer.parseInt(s.substring("a_".length()));
					SysRoleAct roleAct = new SysRoleAct();
					roleAct.setRoleId(roleId.intValue());
					roleAct.setActId(actId);
					saveOrUpdate(roleAct);
				}
			}
		}
	}
}
