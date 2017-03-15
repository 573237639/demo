 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fabaoframework.modules.mybatis.BaseManager;
import com.fabao.ledger.modules.cms.entity.CmsOrganization;
import com.fabao.ledger.modules.cms.dao.CmsOrganizationDao;

@Component
@Transactional(rollbackFor = Exception.class)
public class CmsOrganizationManager extends BaseManager<CmsOrganizationDao,CmsOrganization>{
@Autowired
private CmsOrganizationDao cmsOrganizationDao;
@Override
@Autowired
public void setEntityDao(CmsOrganizationDao cmsOrganizationDao ) {
	this.entityDao=cmsOrganizationDao;
}
/**
 *批量删除
 *@param ids 
 */
public void disableBat(String ids) throws Exception{
	cmsOrganizationDao.disableBat(ids);
}
}
