 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.fabaoframework.modules.mybatis.BaseManager;
import com.fabao.ledger.modules.cms.entity.CmsOpinion;
import com.fabao.ledger.modules.cms.dao.CmsOpinionDao;

@Component
@Transactional(rollbackFor = Exception.class)
public class CmsOpinionManager extends BaseManager<CmsOpinionDao,CmsOpinion>{

@Override
@Autowired
public void setEntityDao(CmsOpinionDao cmsOpinionDao ) {
	this.entityDao=cmsOpinionDao;
}

}
