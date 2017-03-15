/**
 * Copyright (c) 2005-2010 http://git.oschina.net/taote/xframe
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.fabao.ledger.modules.sys.dao;

import org.springframework.stereotype.Component;

import com.fabao.ledger.modules.sys.entity.SysRoleMenu;
import com.fabaoframework.modules.mybatis.BaseMybatis3Dao;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */
@Component
public class SysRoleMenuDao extends BaseMybatis3Dao<SysRoleMenu> {
	
	public void deleteByRoleId(Long roleId){
		getSqlSession().delete(this.getQueryPath("deleteByRoleId"), roleId);
	}
	
	public void delRoleMenuByMenuId(int menuId){
		getSqlSession().delete(this.getQueryPath("delRoleMenuByMenuId"), menuId);
	}
}
