 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.sms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.fabaoframework.modules.mybatis.BaseManager;
import com.fabao.ledger.modules.sms.entity.SmsOperatorCode;
import com.fabao.ledger.modules.sms.dao.SmsOperatorCodeDao;

@Component
@Transactional(rollbackFor = Exception.class)
public class SmsOperatorCodeManager extends BaseManager<SmsOperatorCodeDao,SmsOperatorCode>{

	@Override
	@Autowired
	public void setEntityDao(SmsOperatorCodeDao smsOperatorCodeDao ) {
		this.entityDao=smsOperatorCodeDao;
	}

	
}
