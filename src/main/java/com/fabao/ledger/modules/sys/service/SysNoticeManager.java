 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.sys.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.fabaoframework.modules.mybatis.BaseManager;
import com.fabaoframework.modules.page.Page;
import com.fabaoframework.modules.page.PageRequest;
import com.google.common.collect.Maps;
import com.fabao.ledger.modules.sys.entity.SysNotice;
import com.fabao.ledger.modules.sys.dao.SysNoticeDao;

@Component
@Transactional(rollbackFor = Exception.class)
public class SysNoticeManager extends BaseManager<SysNoticeDao,SysNotice>{
	
	@Autowired
	private SysNoticeDao sysNoticeDao;
	
	@Override
	@Autowired
	public void setEntityDao(SysNoticeDao sysNoticeDao ) {
		this.entityDao=sysNoticeDao;
	}
	
	
	public Map<String, Object> getNoticeListByPage(int page, int rows, SysNotice sys) throws Exception{
		PageRequest<SysNotice> pr = new PageRequest<SysNotice>(sys);
		pr.setPageNo(page);
		pr.setPageSize(rows);
		pr.setSortColumns("gmt_modified DESC");
		Map<String,Object> res  = Maps.newHashMap();
		Page<SysNotice> pages = this.findByPageRequest(pr);
		res.put("rows", pages.getResult());
		res.put("total", pages.getTotalCount());
		return res;
	}
	
	public void saveOrUpdateNotice(SysNotice sysNotice) throws Exception{
		sysNotice.setIsDeleted(false);
		sysNoticeDao.merge(sysNotice);
	}

}
