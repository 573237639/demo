 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.sys.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fabao.ledger.common.utils.CommonField;
import com.fabao.ledger.modules.sys.dao.SysDictDao;
import com.fabao.ledger.modules.sys.entity.SysDict;
import com.fabaoframework.modules.mybatis.BaseManager;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Component
@Transactional(rollbackFor = Exception.class)
public class SysDictManager extends BaseManager<SysDictDao,SysDict>{
	private final static Logger logger = Logger.getLogger(SysDictManager.class);
	@Override
	@Autowired
	public void setEntityDao(SysDictDao sysDictDao ) {
		this.entityDao=sysDictDao;
	}

	
	@Override
//	@CacheEvict(value="myCache",allEntries=true,beforeInvocation=true)
	@Cacheable(value="SysDict")
	public List<SysDict> getAll() {
		// TODO Auto-generated method stub
		return super.getAll();
	}
	
	@Override
	@CacheEvict(value="SysDict",allEntries=true,beforeInvocation=true)
	public void saveOrUpdate(SysDict entity) {
		// TODO Auto-generated method stub
		super.saveOrUpdate(entity);
	}


	@Override
	@CacheEvict(value="SysDict",allEntries=true,beforeInvocation=true)
	public void save(SysDict entity) {
		// TODO Auto-generated method stub
		super.save(entity);
	}


	@Override
	@CacheEvict(value="SysDict",allEntries=true,beforeInvocation=true)
	public void update(SysDict entity) {
		// TODO Auto-generated method stub
		super.update(entity);
	}


	/**
	 * 获取来电队列
	 * @param pskill
	 * @return
	 */
	public String getQueueName(String pskill){
		List<Map<String, Object>> list = getListByType(CommonField.PSKILL_QUEUE);
		for(Map<String, Object> map : list){
			if(map.get("value").toString().equals(pskill)){
				return map.get("label").toString();
			}
		}
		return "";
	}
	
	public List<Map<String, Object>> getTypes(){
		List<String> types = this.entityDao.getTypes();
		List<Map<String, Object>> reTypes = Lists.newArrayList();
		for(String s: types){
			Map<String, Object> m = Maps.newHashMap();
			m.put("type", s);
			reTypes.add(m);
		}
		return reTypes;
	}
	
	public List<Map<String, Object>> getListByType(String type){
		
		logger.info("获取字典数据["+type+"],start..."); 
		SysDict entity = new SysDict();
		entity.setType(type);
		entity.setSortColumns(" sort desc");
		List<SysDict> list = this.entityDao.getAllByEntity(entity);
		List<Map<String, Object>> papersTypes = Lists.newArrayList();
		
		for(SysDict s:list){
			Map<String, Object> m = Maps.newHashMap();
			m.put("label", s.getLabel());
			m.put("value", s.getValue());
			papersTypes.add(m);
		}
		logger.info("获取字典数据["+type+"],end"); 
		return papersTypes;
	}
	

	
 	
	
}
