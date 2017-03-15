 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.sys.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Component;

import com.baomidou.kisso.common.encrypt.MD5;
import com.fabao.ledger.modules.sys.entity.SysUser;
import com.fabaoframework.modules.mybatis.BaseMybatis3Dao;
import com.google.common.collect.Lists;


@Component
public class SysUserDao extends BaseMybatis3Dao<SysUser>{
	public SysUser getByNameAndPass(Map<String,String> map){
		return getSqlSession().selectOne(this.getQueryPath("getByNameAndPass"),map);
	}

	//重置密码
	public void resetPwdByUids(String uids) {
		List<Long> uidList = Lists.newArrayList();
		String[] uidArr = uids.split(",");
		for(int i=0;i<uidArr.length;i++){
			uidList.add(Long.valueOf(uidArr[i]));
		}
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("uids",uidList);
		map.put("password", MD5.toMD5("111111"));
		getSqlSession().update(this.getQueryPath("resetPwdByUids"), map);
	}
}
