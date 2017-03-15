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

import com.fabao.ledger.common.utils.CommonField;
import com.fabao.ledger.modules.sys.dao.SysUserDao;
import com.fabao.ledger.modules.sys.entity.SysUser;
import com.fabao.ledger.modules.tb.dao.TbSupplementDao;
import com.fabao.ledger.modules.tb.entity.TbSupplement;
import com.fabaoframework.modules.mybatis.BaseManager;
import com.fabaoframework.modules.page.Page;
import com.fabaoframework.modules.page.PageRequest;
import com.google.common.collect.Maps;

@Component
@Transactional(rollbackFor = Exception.class)
public class TbSupplementManager extends BaseManager<TbSupplementDao,TbSupplement>{
	
	private static final Logger log = Logger.getLogger(TbSupplementManager.class);
	@Autowired
	private TbSupplementDao tbSupplementDao;
	@Autowired
	private SysUserDao sysUserDao;
	@Override
	@Autowired
	public void setEntityDao(TbSupplementDao tbSupplementDao ) {
		this.entityDao=tbSupplementDao;
	}
	
	public Map<String, Object> getTbSupplementList(int page, int rows, TbSupplement tbSupplement) throws Exception{
		tbSupplement.setNumSupplementStatus(tbSupplement.getNumSupplementStatus() != null ? tbSupplement.getNumSupplementStatus() : CommonField.SUPPLEMENT_STATUS_RECORD);
		PageRequest<TbSupplement> pr = new PageRequest<TbSupplement>(tbSupplement);
		pr.setPageNo(page);
		pr.setPageSize(rows);
		pr.setSortColumns("gmt_created DESC");
		Page<TbSupplement> pages = findByPageRequest(pr);
		List<TbSupplement> rList =  pages.getResult();
		for(TbSupplement tbs:rList){
			tbs.setVacCreaterName(getUserRealName(tbs.getCreator().longValue()));
		}
		
		Map<String,Object> res = Maps.newHashMap();
		res.put("rows", rList);
		res.put("total", pages.getTotalCount());
		return res;
	}
	public String getUserRealName(Long uid){
		if(null==uid||uid.equals("")){
			log.info("获取ID"+uid+"的用户名字");
			return "";
		}
		SysUser sysUser = sysUserDao.getById(uid);
		if(null!=sysUser&&!sysUser.equals("")){
			return sysUserDao.getById(uid).getRealname();
		}else{
			log.info("获取ID"+uid+"的用户对象为空");
			return "";
		}
	}
	public String getNoPassReason(TbSupplement tbSupplement) throws Exception{
		if(this.getByEntity(tbSupplement)!=null&&this.getByEntity(tbSupplement).size()>0){
			return this.getByEntity(tbSupplement).get(0).getVacSupplementContent();
		}
		return null;
		
	}
	
	public void addTbSupplement(TbSupplement tbSupplement) throws Exception{
			tbSupplement.setNumSupplementStatus(tbSupplement.getNumSupplementStatus() != null ? tbSupplement.getNumSupplementStatus() : CommonField.SUPPLEMENT_STATUS_RECORD);
			tbSupplementDao.merge(tbSupplement);
	}

}
