 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.tb.dao;


import java.util.List;

import org.springframework.stereotype.Component;

import com.fabao.ledger.modules.tb.entity.TbCall;
import com.fabaoframework.modules.mybatis.BaseMybatis3Dao;


@Component
public class TbCallDao extends BaseMybatis3Dao<TbCall>{
	public List<TbCall> getBySerials(String ids){
		return getSqlSession().selectList(getQueryPath("getBySerials"), ids);
	}
}
