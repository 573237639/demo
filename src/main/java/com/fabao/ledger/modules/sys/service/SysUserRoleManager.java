 package com.fabao.ledger.modules.sys.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fabao.ledger.modules.sys.dao.SysUserRoleDao;
import com.fabao.ledger.modules.sys.entity.SysUserRole;
import com.fabaoframework.modules.mybatis.BaseManager;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class SysUserRoleManager extends BaseManager<SysUserRoleDao,SysUserRole>{

	@Override
	@Autowired
	public void setEntityDao(SysUserRoleDao sysUserRoleDao ) {
		this.entityDao=sysUserRoleDao;
	}

	public void saveUserRole(Long uid,List<Long> roleIds){
		this.entityDao.deleteByUid(uid);
		if(CollectionUtils.isNotEmpty(roleIds)){
			for(Long roleId:roleIds){
				SysUserRole userRole = new SysUserRole();
				userRole.setIsDeleted(false);
				userRole.setRoleId(roleId.intValue());
				userRole.setUserId(uid.intValue());
				this.entityDao.insert(userRole);
			}
		}
	}
	
	/**
	 * 根据用户id获取用户的角色id列表
	 * @param uid
	 * @return
	 */
	public List<Long> getRoleIdsByUid(long uid){
		return this.entityDao.getRoleIdsByUid(uid);
	}
	
	public List<Map<String,Object>> findUsersByRole(){
		return this.entityDao.findUsersByRole();
	}
	public void updateIsDelete(Long roleId,Boolean isDeleted){
		 this.entityDao.updateIsDelete(roleId,isDeleted);
	}
}
