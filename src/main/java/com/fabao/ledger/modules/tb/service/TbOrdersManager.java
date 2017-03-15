/**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.fabao.ledger.modules.tb.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fabao.ledger.common.pojo.PoJoSet;
import com.fabao.ledger.common.utils.CommonField;
import com.fabao.ledger.modules.sms.dao.SmsCityCodeDao;
import com.fabao.ledger.modules.sms.dao.SmsProvinceCodeDao;
import com.fabao.ledger.modules.sys.dao.SysSpecialtyDao;
import com.fabao.ledger.modules.sys.dao.SysUserDao;
import com.fabao.ledger.modules.sys.entity.SysSpecialty;
import com.fabao.ledger.modules.sys.entity.SysUser;
import com.fabao.ledger.modules.tb.dao.TbClientsDao;
import com.fabao.ledger.modules.tb.dao.TbOrdersDao;
import com.fabao.ledger.modules.tb.entity.TbClients;
import com.fabao.ledger.modules.tb.entity.TbOrders;
import com.fabaoframework.modules.mapper.PageBeanUtilsBean;
import com.fabaoframework.modules.mybatis.BaseManager;
import com.fabaoframework.modules.page.Page;
import com.fabaoframework.modules.page.PageRequest;
import com.google.common.collect.Maps;

@Component
@Transactional(rollbackFor = Exception.class)
public class TbOrdersManager extends BaseManager<TbOrdersDao, TbOrders> {
	private static final Logger log = Logger.getLogger(TbOrdersManager.class);
	@Autowired
	private TbOrdersDao tbOrdersDao;
	@Autowired
	private SysSpecialtyDao sysSpecialtyDao;
	@Autowired
	private TbClientsDao tbClientsDao;
	@Autowired
	private SmsProvinceCodeDao smsProvinceCodeDao;
	@Autowired
	private SmsCityCodeDao smsCityCodeDao;
	@Autowired
	private SysUserDao sysUserDao;
	
	@Override
	@Autowired
	public void setEntityDao(TbOrdersDao tbOrdersDao) {
		this.entityDao = tbOrdersDao;
	}

	public Map<String, Object> tbOrdersList(int page, int rows, TbOrders tbOrders ) throws Exception {
		//业务类型查询
		if(null != tbOrders.getVacOrderBusinessType() && !tbOrders.getVacOrderBusinessType().equals("")){
			tbOrders.setVacOrderBusinessType(sysSpecialtyDao.getBusinessTypes(tbOrders.getVacOrderBusinessType()));
		}
		tbOrders.setIsDeleted(tbOrders.getIsDeleted() != null ? tbOrders.getIsDeleted() : false);
		PageRequest<TbOrders> pr = new PageRequest<TbOrders>(tbOrders);
		pr.setPageNo(page);
		pr.setPageSize(rows);
		pr.setSortColumns("gmt_created desc");
		Page<TbOrders> pages = findByPageRequestAndEntity(pr);
		Map<String,Object> res = Maps.newHashMap();
		List<TbOrders> tbList = pages.getResult();
		for(TbOrders tb: tbList){
			tb.setVacOrderBusinessName(getBusinessNameByType(tb.getVacOrderBusinessType()));
		}
		res.put("rows", tbList);
		res.put("total", pages.getTotalCount());
		return res;
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
	
	/**
	 * 获取同一个号码的工单数
	 * @param number
	 * @return
	 */
	public Long getOrderCountBySerial(String serial) throws Exception{
		return tbOrdersDao.getCountBySerial(serial);
	}
	
	
	//根据法律领域类型code获取法律领域名称
	public String getBusinessNameByType(String type) throws Exception{
		if(null == type || type.equals("")){
			log.info("获取id="+type+"的法律领域名称");
			return "";
		}
		SysSpecialty sysSpecialty = sysSpecialtyDao.getById(Long.valueOf(type));
		if(null != sysSpecialty && !sysSpecialty.equals("")){
			return sysSpecialty.getVacName();
		}else{
			log.info("获取id="+type+"的法律领域对象为空");
			return "";
		}
	}
	//根据省份ID获取省份名称
	public String getProvinceNameById(String pid) throws Exception{
		if(null == pid || pid.equals("")){
			return "";
		}
		return smsProvinceCodeDao.getById(Long.valueOf(pid)).getVacProvinceName();
	}
	//根据地市ID获取地市名称
	public String getCityNameById(String cid) throws Exception{
		if(null == cid || cid.equals("")){
		return "";
		}
		return smsCityCodeDao.getById(Long.valueOf(cid)).getVacCityName();
	}
	public Page<TbOrders> findByPageRequestAndEntity(PageRequest<TbOrders> pageRequest) throws Exception{
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
		
		int totalCount = tbOrdersDao.findCountByPageRequestAndEntity(param);
		if(totalCount<=0){
			return new Page<TbOrders>(pageRequest,0);
		}
		Page<TbOrders> page = new Page<TbOrders>(pageRequest,totalCount);
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRow", page.getFirstResult()+page.getPageSize());
		page.setSortColumns(pageRequest.getSortColumns());
		filters.put("sortColumns", page.getSortColumns());
		filters.putAll(param);
		RowBounds rowBounds = new RowBounds(page.getFirstResult(),page.getPageSize());
		List<TbOrders>  list = tbOrdersDao.findByPageRequestAndEntity(filters, rowBounds);
		page.setResult(list);
		return page;
	}
	
	/**
	 * 获取工单流水号对应的工单数
	 * @return
	 * @throws Exception
	 */
	public List<TbOrders> getMapGroupBySerial() throws Exception{
		return this.entityDao.getMapGroupBySerial();
	}
	
	public void disableBat(String ids) throws Exception{
		tbOrdersDao.disableBat(ids);
	}
	public void assignedUser(Map<String,Object> param){
		tbOrdersDao.assignedUser(param);
	}
	public Integer findCountByPageRequestAndEntity(Map<String, Object> param){
		return tbOrdersDao.findCountByPageRequestAndEntity(param);
	}

	public void saveOrder(TbOrders tbOrders)  throws Exception{
//		addTime(tbOrders);
		tbOrdersDao.update(tbOrders);
	}
	
	public void saveCallOrder(TbOrders tbOrders)  throws Exception{
		addTime(tbOrders);
		tbOrdersDao.insert(tbOrders);
	}

	private void addTime(TbOrders tbOrders) {
		Date date=new Date();
		Calendar ca=Calendar.getInstance();
		if(tbOrders.getVacOrderType()==CommonField.ORDER_TYPE_COMMON){
			ca.setTime(date);
			ca.add(Calendar.DATE, 3);
			tbOrders.setDateOrderTerm(ca.getTime());
		}else if(tbOrders.getVacOrderType()==CommonField.ORDER_TYPE_URGENT){
			ca.setTime(date);
			ca.add(Calendar.HOUR_OF_DAY, 3);
			tbOrders.setDateOrderTerm(ca.getTime());
		}
	}
	public void saveLedgerOrder(TbClients tbClients, TbOrders tbOrders) throws Exception{
		if (null != tbClients) {
			PoJoSet.getTbClientsByWrite(tbClients);
		}
		addTime(tbOrders);
		tbClientsDao.update(tbClients);
		tbOrdersDao.insert(tbOrders);
	}
	
	public  List<TbOrders> getAlertOrderList(TbOrders tbOrders) throws Exception{
		List<TbOrders> tbList = tbOrdersDao.getAlertOrderList(tbOrders);
		return tbList;
	}
	
}
