 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.sys.dao;


import java.util.List;

import org.springframework.stereotype.Component;

import com.fabao.ledger.modules.sys.entity.SysDict;
import com.fabaoframework.modules.mybatis.BaseMybatis3Dao;


@Component
public class SysDictDao extends BaseMybatis3Dao<SysDict>{

	public List<String> getTypes(){
		return this.getSqlSession().selectList(this.getQueryPath("getTypes"));
	}
}
