 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.sms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fabao.ledger.modules.sms.dao.SmsMobileAreaDao;
import com.fabao.ledger.modules.sms.entity.SmsMobileArea;
import com.fabaoframework.modules.mybatis.BaseManager;

@Component
@Transactional(rollbackFor = Exception.class)
public class SmsMobileAreaManager extends BaseManager<SmsMobileAreaDao,SmsMobileArea>{

	@Override
	@Autowired
	public void setEntityDao(SmsMobileAreaDao smsMobileAreaDao ) {
		this.entityDao=smsMobileAreaDao;
	}

	public SmsMobileArea getMobileNumberInfo(String mobileNumber) {
		if(mobileNumber==null||"".equals(mobileNumber)){
			return null;
		}
		SmsMobileArea smsMobileArea = new SmsMobileArea();
		smsMobileArea.setVacSegStart(mobileNumber.substring(0, 7));
		List<SmsMobileArea> mobileAreaList = this.entityDao.getAllByEntity(smsMobileArea);
		if(mobileAreaList==null||mobileAreaList.size()==0){
			return null;
		}
		return mobileAreaList.get(0);
	}
}
