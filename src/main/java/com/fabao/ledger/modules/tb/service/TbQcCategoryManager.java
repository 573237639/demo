 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.tb.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fabao.ledger.modules.tb.dao.TbQcCategoryDao;
import com.fabao.ledger.modules.tb.entity.TbQcCategory;
import com.fabaoframework.modules.mybatis.BaseManager;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Component
@Transactional(rollbackFor = Exception.class)
public class TbQcCategoryManager extends BaseManager<TbQcCategoryDao,TbQcCategory>{
	private static final Logger logger = Logger.getLogger(TbQcCategoryManager.class);
@Autowired
private TbQcCategoryDao tbQcCategoryDao;
@Override
@Autowired
public void setEntityDao(TbQcCategoryDao tbQcCategoryDao ) {
	this.entityDao=tbQcCategoryDao;
}
/**
 *批量删除
 *@param ids 
 */
public void disableBat(String ids) throws Exception{
	tbQcCategoryDao.disableBat(ids);
}
/**
 *获取质检分类 
 */
public  List<Map<String, Object>> getCategoryList(){
	
	logger.info("获取质检分类,start..."); 
	List<TbQcCategory> list = tbQcCategoryDao.getAll();
	List<Map<String, Object>> papersTypes = Lists.newArrayList();
	for(TbQcCategory tb:list){
		Map<String, Object> m = Maps.newHashMap();
		m.put("varCategoryName", tb.getVarCategoryName());
		m.put("id", tb.getId());
		papersTypes.add(m);
	}
	logger.info("获取质检分类,end"); 
	return papersTypes;
}
}
