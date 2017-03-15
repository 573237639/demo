 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.tb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.fabaoframework.modules.mybatis.BaseManager;
import com.fabao.ledger.modules.tb.entity.TbReexamine;
import com.fabao.ledger.modules.tb.dao.TbReexamineDao;

@Component
@Transactional(rollbackFor = Exception.class)
public class TbReexamineManager extends BaseManager<TbReexamineDao,TbReexamine>{

@Override
@Autowired
public void setEntityDao(TbReexamineDao tbReexamineDao ) {
	this.entityDao=tbReexamineDao;
}

}
