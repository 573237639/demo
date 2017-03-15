package com.fabao.ledger.modules.sys.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fabao.ledger.modules.sys.entity.SysAct;
import com.fabaoframework.modules.mybatis.BaseMybatis3Dao;
import com.google.common.collect.Lists;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


@Component
public class SysActDao extends BaseMybatis3Dao<SysAct>{
	
	@Autowired
	private SysRoleActDao sysRoleActDao;

	public List<String> getCommonActs(List<Long> roleIds){
		if(CollectionUtils.isEmpty(roleIds)){
			return Lists.newArrayList();
		}
		return this.getSqlSession().selectList(this.getQueryPath("getCommonActionList"), roleIds);
	}
	
	public List<String> getAuthorActs(List<Long> roleIds){
		if(CollectionUtils.isEmpty(roleIds)){
			return Lists.newArrayList();
		}
		return this.getSqlSession().selectList(this.getQueryPath("getAuthorActionList"), roleIds);
	}
	
	public List<SysAct> getAllActsByType(int type){
		return this.getSqlSession().selectList(this.getQueryPath("getAllActsByType"), type);
	}
	
	public List<SysAct> getCommonSysActs(List<Long> roleIds){
		if(CollectionUtils.isEmpty(roleIds)){
			return Lists.newArrayList();
		}
		
		return this.getSqlSession().selectList(this.getQueryPath("getCommonActsByRoleIds"), roleIds);
	}

	public void delActByMenuId(int menuId){
		List<SysAct> actions = findBymenuId(menuId);
		if(actions.size() > 0){
			sysRoleActDao.delByActIds(actions);
			getSqlSession().delete(this.getQueryPath("delActByMenuId"), menuId);
		}
	}
	
	public List<SysAct> findBymenuId(int menuId){
		return getSqlSession().selectList(getQueryPath("findBymenuId"), menuId);
	}
}
