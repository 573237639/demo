 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.tb.dao;


import org.springframework.stereotype.Component;

import com.fabao.ledger.modules.tb.entity.TbCallStay;
import com.fabaoframework.modules.mybatis.BaseMybatis3Dao;


@Component
public class TbCallStayDao extends BaseMybatis3Dao<TbCallStay>{

	public void updateBat(String ids){
		getSqlSession().update(getQueryPath("updateBat"), ids);
	}
}
