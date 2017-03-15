 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.fabaoframework.modules.mybatis.BaseManager;
import com.fabao.ledger.modules.sys.entity.SysSpecialty;
import com.fabao.ledger.modules.sys.dao.SysSpecialtyDao;

@Component
@Transactional(rollbackFor = Exception.class)
public class SysSpecialtyManager extends BaseManager<SysSpecialtyDao,SysSpecialty>{

@Override
@Autowired
public void setEntityDao(SysSpecialtyDao sysSpecialtyDao ) {
	this.entityDao=sysSpecialtyDao;
}

/**
 * 通过业务类型id获取所有子级业务类型id字符串
 * @param businessType
 * @return
 */
@Cacheable(value="SysSpecialty",key="#businessType")
public String getBusinessTypes(String businessType){
	return this.entityDao.getBusinessTypes(businessType);
}

@Override
@Cacheable(value="SysSpecialty",key="#id")
public SysSpecialty getById(Long id) {
	// TODO Auto-generated method stub
	return super.getById(id);
}

@Override
@Cacheable(value="SysSpecialty",key="#ids")
public List<SysSpecialty> getByIds(List<Integer> ids) {
	// TODO Auto-generated method stub
	return super.getByIds(ids);
}

@Override
@Cacheable(value="SysSpecialty")
public List<SysSpecialty> getAll() {
	// TODO Auto-generated method stub
	return super.getAll();
}

@Override
@CacheEvict(value="SysSpecialty",allEntries=true,beforeInvocation=true)
public void saveOrUpdate(SysSpecialty entity) {
	// TODO Auto-generated method stub
	super.saveOrUpdate(entity);
}

@Override
@CacheEvict(value="SysSpecialty",allEntries=true,beforeInvocation=true)
public void save(SysSpecialty entity) {
	// TODO Auto-generated method stub
	super.save(entity);
}

@Override
@CacheEvict(value="SysSpecialty",allEntries=true,beforeInvocation=true)
public void update(SysSpecialty entity) {
	// TODO Auto-generated method stub
	super.update(entity);
}

@Override
@Cacheable(value="SysSpecialty",key="#entity")
public List<SysSpecialty> getByEntity(SysSpecialty entity) {
	// TODO Auto-generated method stub
	return super.getByEntity(entity);
}



}
