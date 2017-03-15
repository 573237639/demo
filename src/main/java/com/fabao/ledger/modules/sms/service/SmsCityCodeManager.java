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

import com.fabao.ledger.modules.sms.dao.SmsCityCodeDao;
import com.fabao.ledger.modules.sms.dao.SmsProvinceCodeDao;
import com.fabao.ledger.modules.sms.entity.SmsCityCode;
import com.fabao.ledger.modules.sms.entity.SmsProvinceCode;
import com.fabaoframework.modules.mybatis.BaseManager;

@Component
@Transactional(rollbackFor = Exception.class)
public class SmsCityCodeManager extends BaseManager<SmsCityCodeDao,SmsCityCode>{
	
	private SmsProvinceCodeDao provinceCodeDao;
	@Override
	@Autowired
	public void setEntityDao(SmsCityCodeDao smsCityCodeDao ) {
		this.entityDao=smsCityCodeDao;
	}
	
	
	@Autowired
	public void setProvinceCodeDao(SmsProvinceCodeDao provinceCodeDao) {
		this.provinceCodeDao = provinceCodeDao;
	}



	@Cacheable(value="SmsCityCodes", key="#provId")
	public List<SmsCityCode> getCityListByProvId(String provId){
		SmsCityCode city = new SmsCityCode();
		city.setNumProvinceId(Integer.parseInt(provId));
		List<SmsCityCode> cityList = this.entityDao.getAllByEntity(city);
		return cityList;
	}
	
	
	@Cacheable(value="SmsCityCodes", key="#capCode")
	public List<SmsCityCode> getCityListByCapCode(String capCode){
		SmsProvinceCode province = new SmsProvinceCode();
		province.setVacProvinceCapCode(capCode);
		List<SmsProvinceCode> prov = provinceCodeDao.getAllByEntity(province);
		SmsCityCode cityCode = new SmsCityCode();
		cityCode.setNumProvinceId(prov.get(0).getId().intValue());
		List<SmsCityCode> cityList = this.entityDao.getAllByEntity(cityCode);
		return cityList;
	}
	
	@Cacheable(value="SmsCityCodes", key="#region")
	public SmsCityCode getCityByRegion(String region){
		SmsCityCode cityCode = new SmsCityCode();
		cityCode.setVacCityRegion(region);
		List<SmsCityCode> cityList = this.entityDao.getAllByEntity(cityCode);
		if(cityList==null||cityList.size()==0){
			return null;
		}
		return cityList.get(0);
	}

}
