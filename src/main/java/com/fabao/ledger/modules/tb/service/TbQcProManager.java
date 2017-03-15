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

import com.fabaoframework.modules.mybatis.BaseManager;
import com.fabao.ledger.modules.tb.entity.TbQcCategory;
import com.fabao.ledger.modules.tb.entity.TbQcPro;
import com.fabao.ledger.modules.tb.dao.TbQcProDao;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Component
@Transactional(rollbackFor = Exception.class)
public class TbQcProManager extends BaseManager<TbQcProDao,TbQcPro>{
	private static final Logger logger = Logger.getLogger(TbQcProManager.class);
@Autowired
private TbQcProDao tbQcProDao;
@Override
@Autowired
public void setEntityDao(TbQcProDao tbQcProDao ) {
	this.entityDao=tbQcProDao;
}
/**
 *批量删除
 *@param ids 
 */
public void disableBat(String ids) throws Exception{
	tbQcProDao.disableBat(ids);
}

/**
 *获取质检项目
 */
public  List<Map<String, Object>> getProList(){
	logger.info("获取质检分类,start..."); 
	List<TbQcPro> list = tbQcProDao.getAll();
	List<Map<String, Object>> papersTypes = Lists.newArrayList();
	for(TbQcPro tb:list){
		Map<String, Object> m = Maps.newHashMap();
		m.put("vacQcproName", tb.getVacQcproName());
		m.put("numQcproScore", tb.getNumQcproScore());
		m.put("numQcproCheckScore", tb.getNumQcproCheckScore());
		m.put("numQcproMustCheckBit", tb.getNumQcproMustCheckBit());
		m.put("numQcproBit", tb.getNumQcproBit());
		m.put("numCategoryId", tb.getNumCategoryId());
		m.put("numQcproScore", tb.getNumQcproScore());
		m.put("id", tb.getId());
		papersTypes.add(m);
	}
	logger.info("获取质检分类,end"); 
	return papersTypes;
}
}
