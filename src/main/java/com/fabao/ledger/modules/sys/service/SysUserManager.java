/**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.fabao.ledger.modules.sys.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fabao.ledger.modules.sys.dao.SysUserDao;
import com.fabao.ledger.modules.sys.entity.SysUser;
import com.fabaoframework.modules.mybatis.BaseManager;
import com.fabaoframework.modules.utils.CryptUtils;
import com.google.common.collect.Maps;

@Component
@Transactional(rollbackFor = Exception.class)
public class SysUserManager extends BaseManager<SysUserDao, SysUser> {

	@Override
	@Autowired
	public void setEntityDao(SysUserDao sysUserDao) {
		this.entityDao = sysUserDao;
	}
	
	@Override
	@Cacheable(value="SysUser",key="#id")
	public SysUser getById(Long id) {
		// TODO Auto-generated method stub
		return super.getById(id);
	}

	@Override
	@CacheEvict(value="SysUser",allEntries=true,beforeInvocation=true)
	public void saveOrUpdate(SysUser entity) {
		// TODO Auto-generated method stub
		super.saveOrUpdate(entity);
	}

	@Override
	@CacheEvict(value="SysUser",allEntries=true,beforeInvocation=true)
	public void save(SysUser entity) {
		// TODO Auto-generated method stub
		super.save(entity);
	}

	@Override
	@CacheEvict(value="SysUser",allEntries=true,beforeInvocation=true)
	public void update(SysUser entity) {
		// TODO Auto-generated method stub
		super.update(entity);
	}

	public SysUser findUserByNameAndPass(String name, String password) {
		Map<String, String> m = Maps.newHashMap();
		if (StringUtils.isNotBlank(name)) {
			m.put("name", name);
		}
		m.put("password", CryptUtils.MD5(password));
		return this.entityDao.getByNameAndPass(m);
	}
	
	//重置密码
	public void resetPwdByUids(String uids) {
		this.entityDao.resetPwdByUids(uids);
	}
	
	/**
	 * 获取所有工号和真实名称集合
	 * @param type 律师类别
	 * @return
	 */
	public Map<String,String> getMapByAll(String type){
		Map<String,String> map = Maps.newHashMap();
		List<SysUser> list = this.entityDao.getAll();
		if(null != list && list.size() > 0){
			for(SysUser u : list){
				map.put(u.getHwNum(), u.getRealname());
			}
		}
		return map;
	}
 
}
