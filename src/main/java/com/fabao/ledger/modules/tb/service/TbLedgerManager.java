 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.tb.service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fabao.ledger.common.pojo.PoJoSet;
import com.fabao.ledger.modules.sys.dao.SysSpecialtyDao;
import com.fabao.ledger.modules.sys.dao.SysUserDao;
import com.fabao.ledger.modules.sys.entity.SysSpecialty;
import com.fabao.ledger.modules.sys.entity.SysUser;
import com.fabao.ledger.modules.tb.dao.TbClientsDao;
import com.fabao.ledger.modules.tb.dao.TbLedgerDao;
import com.fabao.ledger.modules.tb.dao.TbOrdersDao;
import com.fabao.ledger.modules.tb.entity.TbClients;
import com.fabao.ledger.modules.tb.entity.TbLedger;
import com.fabaoframework.modules.mapper.PageBeanUtilsBean;
import com.fabaoframework.modules.mybatis.BaseManager;
import com.fabaoframework.modules.page.Page;
import com.fabaoframework.modules.page.PageRequest;
import com.google.common.collect.Maps;

@Component
@Transactional(rollbackFor = Exception.class)
public class TbLedgerManager extends BaseManager<TbLedgerDao,TbLedger>{

	@Autowired
	private TbLedgerDao tbLedgerDao; 
	@Autowired
	private TbClientsDao tbClientsDao; 
	@Autowired
	private TbOrdersDao tbOrdersDao; 
	@Autowired
	private SysSpecialtyDao sysSpecialtyDao;
	@Autowired
	private SysUserDao sysUserDao;
	@Override
	@Autowired
	public void setEntityDao(TbLedgerDao tbLedgerDao ) {
		this.entityDao=tbLedgerDao;
	}
	
	public Page<TbLedger> findByPageRequestAndEntity(PageRequest<TbLedger> pageRequest) throws Exception{
		Map<String, Object> param = null;
		try {
			param = new PageBeanUtilsBean().describe(pageRequest.getSearchDo());
			param.putAll(pageRequest.getFilters());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		int totalCount = tbLedgerDao.findCountByPageRequestAndEntity(param);
		
		if (totalCount <= 0) {
			return new Page<TbLedger>(pageRequest, 0);
		}
	
		Page<TbLedger> page = new Page<TbLedger>(pageRequest, totalCount);
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		page.setSortColumns(pageRequest.getSortColumns());
		filters.put("sortColumns", page.getSortColumns());
		filters.putAll(param);
		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<TbLedger> list = tbLedgerDao.findByPageRequestAndEntity(filters, rowBounds);
		page.setResult(list);
		return page;
	}
	public String getModifierName(Integer uid){
		if(null == uid || uid.equals("")){
			log.info("获取id="+uid+"的真实姓名");
			return "";
		}
		SysUser sysUser = sysUserDao.getById(uid.longValue()); 
		if(null != sysUser && !sysUser.equals("")){
			return sysUser.getRealname();
		}
		return null;
	}
	

	
	public Map<String, Object> getHistoryLedger(int page, int rows, TbLedger tbLedger) throws Exception{
		//业务类型查询
		if(null != tbLedger.getVacLedgerBusinessType() && !tbLedger.getVacLedgerBusinessType().equals("")){
			tbLedger.setVacLedgerBusinessType(sysSpecialtyDao.getBusinessTypes(tbLedger.getVacLedgerBusinessType()));
		}
		PageRequest<TbLedger> pr = new PageRequest<TbLedger>(tbLedger);
		pr.setPageNo(page);
		pr.setPageSize(rows);
		pr.setSortColumns("gmt_created DESC");
		Page<TbLedger> pages = findByPageRequestAndEntity(pr);
		Map<String,Object> res = Maps.newHashMap();
		List<TbLedger> tbList = pages.getResult();
		for(TbLedger tb: tbList){
			tb.setModifierName(getModifierName(tb.getModifier()));//获取修改人名称
			tb.setVacLedgerBusinessName(getBusinessNameByType(tb.getVacLedgerBusinessType()));//获取业务类型
			tb.setIsOrder(tbOrdersDao.getCountBySerial(tb.getVacLedgerSerial()));//获取工单数
		}
		res.put("rows", tbList);
		res.put("total", pages.getTotalCount());
		return res;
	}
	
	/**
	 * 获取业务类型名称
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public String getBusinessNameByType(String type) throws Exception{
		if(type == null || type.equals("")){
			return "";
		}
		SysSpecialty sysSpecialty = sysSpecialtyDao.getById(Long.valueOf(type));
		if(null != sysSpecialty && !sysSpecialty.equals("")){
			return sysSpecialty.getVacName();
		}else{
			return "";
		}
	}

	
	
	/**
	 * 设为无效
	 * @param ids
	 * @throws Exception
	 */
	public void disableBat(String ids) throws Exception{
			tbLedgerDao.disableBat(ids);
	}
	
	/**
	 * 设为有效
	 * @param ids
	 * @return
	 */
	public void enableBat(String ids) throws Exception{
			tbLedgerDao.enableBat(ids);
	}
	
	public TbLedger getTbLedgerBySerial(String serial) throws Exception{
		TbLedger l  = new TbLedger();
		l.setVacLedgerSerial(serial);
		List<TbLedger> tbLedgerList = this.getByEntity(l);
		if(null != tbLedgerList && tbLedgerList.size() > 0){
			return tbLedgerList.get(0);
		}
		return null;
	}
	
	public TbLedger getTbLedgerByNumber(String number) throws Exception{
		TbLedger l  = new TbLedger();
		l.setVacLedgerNumber(number);
		List<TbLedger> tbLedgerList = this.getByEntity(l);
		if(null != tbLedgerList && tbLedgerList.size() > 0){
			return tbLedgerList.get(0);
		}
		return null;
	}
	
	public Integer findCountByPageRequestAndEntity(Map<String, Object> param) throws Exception{
		return tbLedgerDao.findCountByPageRequestAndEntity(param);
	}
	
	
	public void saveLedger(TbClients tbClients, TbLedger tbLedger) throws Exception{
		if (null != tbClients) {
			PoJoSet.getTbClientsByWrite(tbClients);
		}
		tbClientsDao.update(tbClients);
		tbLedgerDao.update(tbLedger);
	}


}
