 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.sys.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fabao.ledger.modules.sys.dao.SysRoleMenuDao;
import com.fabao.ledger.modules.sys.entity.SysRoleMenu;
import com.fabaoframework.modules.mybatis.BaseManager;

@Component
@Transactional(rollbackFor = Exception.class)
public class SysRoleMenuManager extends BaseManager<SysRoleMenuDao,SysRoleMenu>{

	@Override
	@Autowired
	public void setEntityDao(SysRoleMenuDao sysRoleMenuDao) {
		this.entityDao = sysRoleMenuDao;
	}

	public void saveRoleMenu(Long roleId,String treemenuids){
		deleteByRoleId(roleId);
		if(StringUtils.isBlank(treemenuids)){
			return;
		}
		for(String s:treemenuids.split(",")){
			if(StringUtils.isNotBlank(s)){
				if(s.startsWith("m_")){
					Integer menuId = Integer.parseInt(s.substring("m_".length()));
					SysRoleMenu roleMenu = new SysRoleMenu();
					roleMenu.setRoleId(roleId.intValue());
					roleMenu.setMenuId(menuId);
					saveOrUpdate(roleMenu);
				}
			}
		}
	}
	
	public void deleteByRoleId(Long roleId){
		this.entityDao.deleteByRoleId(roleId);
	}
	
	public static void main(String[] args) {
		String s = "m_15";
		System.out.println(s.substring("m_".length()));
	}

}
