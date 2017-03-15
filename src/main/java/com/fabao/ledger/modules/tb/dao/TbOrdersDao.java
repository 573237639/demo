 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.tb.dao;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import com.fabao.ledger.modules.tb.entity.TbOrders;
import com.fabaoframework.modules.mybatis.BaseMybatis3Dao;


@Component
public class TbOrdersDao extends BaseMybatis3Dao<TbOrders>{


	public int findCountByPageRequestAndEntity(Map<String, Object> tbOrders){
		return (Integer)getSqlSession().selectOne(getQueryPath("getCountByPageAndEntity"), tbOrders);
	}
	public List<TbOrders> findByPageRequestAndEntity(Map<String,Object> tbOrders,RowBounds rowBounds){
		return getSqlSession().selectList(getQueryPath("getByPageAndEntity"), tbOrders,rowBounds);
	}
	public void updateList(List<TbOrders> tbOrderList){
		 List<Integer> list = new ArrayList<Integer>();
		 for(TbOrders tbOrders : tbOrderList){
			 list.add(tbOrders.getId().intValue());
		 }
		 getSqlSession().selectList(getQueryPath("updateList"), list);
	}
	public List<TbOrders> getMapGroupBySerial(){
		return getSqlSession().selectList(getQueryPath("getMapGroupBySerial"));
	}
	public void disableBat(String ids){
		getSqlSession().update(getQueryPath("disableBat"), ids);
	}
	public void assignedUser(Map<String,Object> param){
		getSqlSession().selectList(getQueryPath("assignedUser"), param);
	}
	public List<TbOrders> getAlertOrderList(TbOrders tbOrders){
		return getSqlSession().selectList(getQueryPath("getAlertOrderList"), tbOrders);
	}
	
	public Long getCountBySerial(String serial){
		if(null == serial ||  serial.equals("")){
			return 0l;
		}
		TbOrders tbOrders = new TbOrders();
		tbOrders.setVacOrderSerial(serial);
		return  getSqlSession().selectOne(getQueryPath("getCountByPage"), tbOrders);
	}
	
	
}
