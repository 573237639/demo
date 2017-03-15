 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.tb.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import com.fabao.ledger.modules.tb.entity.TbLedger;
import com.fabaoframework.modules.mybatis.BaseMybatis3Dao;


@Component
public class TbLedgerDao extends BaseMybatis3Dao<TbLedger>{


	public int findCountByPageRequestAndEntity(Map<String, Object> tbLedger){
		return (Integer)getSqlSession().selectOne(this.getQueryPath("getCountByPageAndEntity"), tbLedger);
	}
	
	public List<TbLedger> findByPageRequestAndEntity(Map<String, Object> tbLedger,RowBounds rowBounds){
		return getSqlSession().selectList(getQueryPath("getByPageAndEntity"), tbLedger,rowBounds);
	}
	
	public void disableBat(String ids){
		getSqlSession().update(getQueryPath("disableBat"), ids);
	}
	
	public void enableBat(String ids){
		getSqlSession().update(getQueryPath("enableBat"), ids);
	}

	
}
