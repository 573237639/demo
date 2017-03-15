 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.sms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fabao.ledger.modules.sms.dao.SmsProvinceCodeDao;
import com.fabao.ledger.modules.sms.entity.SmsProvinceCode;
import com.fabaoframework.modules.mybatis.BaseManager;

@Component
@Transactional(rollbackFor = Exception.class)
public class SmsProvinceCodeManager extends BaseManager<SmsProvinceCodeDao,SmsProvinceCode>{

	@Override
	@Autowired
	public void setEntityDao(SmsProvinceCodeDao smsProvinceCodeDao ) {
		this.entityDao=smsProvinceCodeDao;
	}
	
	
	@Cacheable(value="SmsProvinceCodes")
	public List<SmsProvinceCode> getProvinceList() {
		List<SmsProvinceCode> provinceList = this.entityDao.getAll();
		return provinceList;
	}

	@Cacheable(value="SmsProvinceCodes", key="#provId")
	public SmsProvinceCode getProvinceById(Long provId) {
		SmsProvinceCode province =this.entityDao.getById(provId);
		return province;
	}

}
