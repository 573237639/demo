 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.tb.dao;


import org.springframework.stereotype.Component;

import com.fabao.ledger.modules.tb.entity.TbQcCategory;
import com.fabaoframework.modules.mybatis.BaseMybatis3Dao;


@Component
public class TbQcCategoryDao extends BaseMybatis3Dao<TbQcCategory>{
	/**
	 *批量删除
	 *@param ids 
	 */
	public void disableBat(String ids){
		getSqlSession().update(getQueryPath("disableBat"), ids);
	}

}
