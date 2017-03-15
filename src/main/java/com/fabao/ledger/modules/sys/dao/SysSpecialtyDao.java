 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.sys.dao;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.fabao.ledger.modules.sys.entity.SysSpecialty;
import com.fabaoframework.modules.mybatis.BaseMybatis3Dao;


@Component
public class SysSpecialtyDao extends BaseMybatis3Dao<SysSpecialty>{

	/**
	 * 通过业务类型id获取所有子级业务类型id字符串
	 * @param businessType
	 * @return
	 */
	public String getBusinessTypes(String businessType){
		SysSpecialty sysSpecialty = this.getById(Long.valueOf(businessType));
		if(sysSpecialty.getNumLeaf() == 1){
			return businessType;
		}
		StringBuffer ids = new StringBuffer();
		List<SysSpecialty> list  = this.getAll();
		if(null != list && list.size() > 0){
			List<SysSpecialty> sonList = new ArrayList<SysSpecialty>();
			for(SysSpecialty s : list){
				 if(s.getNumParentId().toString().equals(businessType)){
					 sonList.add(s);
				 }
			}
			setIds(list,sonList,ids);
		}
		if(ids.length() == 0){
			return "";
		}
		return ids.toString().substring(1);
	}

	public void setIds(List<SysSpecialty> list,List<SysSpecialty> sonlist,StringBuffer ids){
		for(SysSpecialty sons : sonlist){
			if(sons.getNumLeaf() == 1){
				ids.append(",");
				ids.append(sons.getId());
			}else{
				List<SysSpecialty> sonList2 = new ArrayList<SysSpecialty>();
				for(SysSpecialty pars : list){
					 if(pars.getNumParentId().toString().equals(sons.getId().toString())){
						 sonList2.add(pars);
					 }
				}
				setIds(list,sonList2,ids);
			}
		}
	}
}
