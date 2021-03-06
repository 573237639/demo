 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fabao.ledger.modules.sys.dao.SysLogDao;
import com.fabao.ledger.modules.sys.entity.SysLog;
import com.fabaoframework.modules.mybatis.BaseManager;

@Component
@Transactional(rollbackFor = Exception.class)
public class SysLogManager extends BaseManager<SysLogDao,SysLog>{

@Override
@Autowired
public void setEntityDao(SysLogDao sysLogDao ) {
	this.entityDao=sysLogDao;
}

}
