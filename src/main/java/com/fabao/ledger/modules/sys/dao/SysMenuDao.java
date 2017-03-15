 /**
 * Copyright (c) 2005-2010 <a href="http://git.oschina.net/taote/xframe">XFrame</a> All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.sys.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fabao.ledger.modules.sys.entity.SysMenu;
import com.fabao.ledger.modules.sys.entity.SysRole;
import com.fabaoframework.modules.mybatis.BaseMybatis3Dao;
import com.google.common.collect.Lists;




@Component
public class SysMenuDao extends BaseMybatis3Dao<SysMenu>{
	

	public int getMaxSeq(int pid){
		Object obj =  this.getSqlSession().selectOne(this.getQueryPath("getMaxSeq"), pid);
		if(null==obj){
			return 0;
		}else{
			return (Integer)obj;
		}
	}
	
	public SysMenu getMenuByMap(Map<String,Object> map){
		return (SysMenu)this.getSqlSession().selectOne(this.getQueryPath("getNeibMenu"), map);
	}
	
	public List<SysMenu> getMenusByType(Map<String,Integer> map){
		return this.getSqlSession().selectList(this.getQueryPath("getMenusByType"), map);
	}

	public List<SysMenu> getMenusByPid(int pid){
		return this.getSqlSession().selectList(this.getQueryPath("getMenusByPid"), pid);
	}
	
	public List<SysMenu> getMenuTree(Integer pid,List<SysRole> roleList){
		Map<String,Object> paraMap = new HashMap<String,Object>();
		SysMenu paraMenu = new SysMenu();
		paraMenu.setParentId(pid);
		paraMap.put("menu", paraMenu);
		paraMap.put("roleList", roleList);
		
		List<SysMenu> menus = new ArrayList<SysMenu>();
		menus = getSqlSession().selectList(this.getQueryPath("getMenus"), paraMap);
		if(menus!=null && menus.size()>0){
			Iterator<SysMenu> ite = menus.iterator();
			while(ite.hasNext()){
				SysMenu menu = ite.next();
//				SysAct  act = sysActDao.getById(menu.getActId().longValue());
//				menu.setSysAct(act);
				menu.setSubMenus(getMenuTree((menu.getId()).intValue(),roleList));
			}
		}
		return menus;
	} 
	
	public List<SysMenu> getMenuListByRoleList(List<Long> roleIds){
		if(null==roleIds||roleIds.size()==0){
			return Lists.newArrayList();
		}
		return getSqlSession().selectList(this.getQueryPath("getMenuListByRoleList"), roleIds);
	}
}
