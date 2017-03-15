 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.tb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fabao.ledger.common.pojo.PoJoSet;
import com.fabao.ledger.modules.tb.dao.TbClientsDao;
import com.fabao.ledger.modules.tb.entity.TbClients;
import com.fabaoframework.modules.mybatis.BaseManager;

@Component
@Transactional(rollbackFor = Exception.class)
public class TbClientsManager extends BaseManager<TbClientsDao,TbClients>{
	@Autowired
	private TbClientsDao tbClientsDao;
@Override
@Autowired
public void setEntityDao(TbClientsDao tbClientsDao ) {
	this.entityDao=tbClientsDao;
}

public TbClients getTbClientsByMobile(String mobile) throws Exception{
	TbClients tbClients = new TbClients();
	tbClients.setVacClientNumber(mobile);
	try {
		List<TbClients> list = this.getByEntity(tbClients);
		if (null != list && list.size() > 0) {
			tbClients = list.get(0);
			if (null != tbClients) {
				PoJoSet.getTbClientsByRead(tbClients);
			}
		} 
	} catch (Exception e) {
		log.error(e.getMessage());
	}
	return tbClients;
}
/**
 *批量删除
 *@param ids 
 */
public void disableBat(String ids) throws Exception{
	tbClientsDao.disableBat(ids);
}
}
