package com.fabao.ledger.modules.sys.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fabao.ledger.common.pojo.EasyUITree;
import com.fabao.ledger.common.pojo.ZTreeNode;
import com.fabao.ledger.common.pojo.ZTreeNodeEs;
import com.fabao.ledger.modules.sys.dao.SysActDao;
import com.fabao.ledger.modules.sys.dao.SysMenuDao;
import com.fabao.ledger.modules.sys.dao.SysRoleMenuDao;
import com.fabao.ledger.modules.sys.entity.SysAct;
import com.fabao.ledger.modules.sys.entity.SysMenu;
import com.fabao.ledger.modules.sys.entity.SysRole;
import com.fabaoframework.modules.mybatis.BaseManager;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class SysMenuManager extends BaseManager<SysMenuDao, SysMenu> {
	
	@Autowired
	private SysActDao sysActDao; 
	
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	
	@Autowired
	private SysRoleManager sysRoleManager;
	
	@Autowired
	private SysUserRoleManager sysUserRoleManager;
	@Autowired
	private SysActManager sysActManager;

	@Override
	@Resource(name="sysMenuDao")
	public void setEntityDao(SysMenuDao sysMenuDao) {
		this.entityDao = sysMenuDao;
	}

	public List<ZTreeNode> menuToTreeNode(){
		List<SysMenu> menus = getAll();
		List<ZTreeNode> nodes = Lists.newArrayList();
		nodes.add(rootNode());
		if(null!=menus){
			for(SysMenu menu:menus){
				ZTreeNode node = new ZTreeNode();
				node.setId(menu.getId());
				node.setName(menu.getName());
				node.setpId(Long.valueOf(menu.getParentId()));
				nodes.add(node);
			}
		}
		return nodes;
	}
	
	public List<ZTreeNodeEs> priMenuActToTreeNode(List<Long> roleIds){
		List<SysAct> as = sysActDao.getCommonSysActs(roleIds);
		if(null==as){
			as = Lists.newArrayList();
		}
		
		List<SysMenu> authmenus = this.entityDao.getMenuListByRoleList(roleIds);
		if(null==authmenus){
			authmenus = Lists.newArrayList();
		}
		List<SysMenu> menus = getAll();
		List<ZTreeNodeEs> nodes = Lists.newArrayList();
		if(null!=menus){
			for(SysMenu menu:menus){
				ZTreeNodeEs node = new ZTreeNodeEs();
				node.setId("m_"+menu.getId());
				node.setName(menu.getName());
				SysMenu pMenu = menu.getParentMenu();
				if(null==pMenu){
					node.setpId("m_"+0L);
				}else{
					node.setpId("m_"+pMenu.getId());
				}
				if(authmenus.contains(menu)){
					node.setChecked(true);
				}
				node.setMenuType(ZTreeNode.TYPE_MENU);
				nodes.add(node);
			}
		}
		List<SysAct> acts = sysActDao.getAllActsByType(SysAct.ACT_TYPE_AUTH);
		if(null!=acts&&acts.size()>0){
			for(SysAct act:acts){
				
				ZTreeNodeEs node = new ZTreeNodeEs();
				node.setId("a_"+act.getId());
				node.setName(act.getActName()+"["+act.getRemarks()+"]");
				node.setpId("m_"+act.getMenuId().longValue());
				node.setMenuType(ZTreeNode.TYPE_ACT);
				if(as.contains(act)){
					node.setChecked(true);
				}
				nodes.add(node);
			}
		}
		return nodes;
	}
	
	public List<ZTreeNodeEs> priRoleUserToTreeNode(){
		List<ZTreeNodeEs> nodes = Lists.newArrayList();
		
		List<Map<String,Object>> userroles=sysUserRoleManager.findUsersByRole();
		for(Map<String,Object> map:userroles){
			ZTreeNodeEs node=new ZTreeNodeEs();;
			for (Map.Entry<String,Object> entry : map.entrySet()) {
				if(StringUtils.equals(entry.getKey().toString(), "uid")){
					node.setId(entry.getValue().toString());
				}
				if(StringUtils.equals(entry.getKey().toString(), "username")){
					node.setName(entry.getValue().toString());
				}
			}
			nodes.add(node);
		}
		return nodes;
		
	}
	
	public List<SysMenu> findMenusByRoles(List<Long> roleIds){
		return getAll();
	}
	
	public List<SysMenu> getMenuTree(int pid,List<Long> roleIds){
		if(roleIds==null||roleIds.size()==0){
			return new ArrayList<SysMenu>();
		}
		List<SysRole> roleList = Lists.newArrayList();
		if(CollectionUtils.isNotEmpty(roleIds)){
			for(Long roleid:roleIds){
				SysRole role = new SysRole();
				role.setId(roleid);
				roleList.add(role);
			}
		}
		return this.entityDao.getMenuTree(pid, roleList);
	}
	
	public List<SysAct> findActsByRoles(List<Long> roleids){
		return null;
	}
	
	/**
	 * 获取子菜单
	 * @param pid 父菜单ID
	 * @param recursion 是否递归查询,true递归,false 不递归
	 * @return
	 */
	public List<SysMenu> findSubMenus(Long pid,boolean recursion){
		SysMenu paramMenu = new SysMenu();
		paramMenu.setParentId(pid.intValue());
		List<SysMenu> menus = this.entityDao.getMenusByPid(pid.intValue());
		if(0==pid){
			for(SysMenu menu:menus){
				menu.setParentMenu(rootMenu());
			}
		}
		if(recursion&&null!=menus){
			for(SysMenu menu:menus){
				menu.setSubMenus(findSubMenus(menu.getId(),recursion));
			}
		}
		return menus;
	}
	
	public final ZTreeNode rootNode(){
		ZTreeNode rootNode = new ZTreeNode();
		rootNode.setId(0L);
		rootNode.setpId(null);
		rootNode.setMenuType(SysMenu.MENU_TYPE_PARENT);
		rootNode.setName("系统顶级菜单");
		rootNode.setOpen(true);
		return  rootNode;
	}
	
	public final SysMenu rootMenu(){
		SysMenu menu = new SysMenu();
		menu.setId(0L);
		menu.setParentId(null);
		menu.setName("系统顶级菜单");
		menu.setMenuType(SysMenu.MENU_TYPE_PARENT);
		menu.setRemarks("系统菜单根对象");
		return menu;
	}
	
	public void addMenu(SysMenu menu){
		if(menu.getSort()==null){
			 Integer max = entityDao.getMaxSeq( menu.getParentId());
	         menu.setSort(max == null ? 1 : max + 1);
		}
		entityDao.insert(menu);
		//act
		if(menu.getMenuType()==SysMenu.MENU_TYPE_SON){
			SysAct act = menu.getSysAct();
			if(null==act){
				act = new SysAct();
				menu.setSysAct(act);
			}
			act.setType(SysAct.ACT_TYPE_COMMON);
            act.setRemarks("菜单所属动作");
            act.setMenuId(menu.getId().intValue());
            sysActDao.insert(act);
            menu.setActId(act.getId().intValue());
            entityDao.update(menu);
		}
	}
	
	public void editMenu(SysMenu menu){
		 if (menu.getMenuType() == SysMenu.MENU_TYPE_SON) {
	            sysActDao.update(menu.getSysAct());
	        }
		 entityDao.update(menu);
	}
	
	public void delMenu(int menuId,int type){
		 Map<String, Object> paraMap = new HashMap<String, Object>();
	        paraMap.put("menuId", menuId);
	        // 删除本菜单
	        entityDao.delete(Integer.valueOf(menuId).longValue());
	        sysRoleMenuDao.delRoleMenuByMenuId(menuId);
	        
	        // 删除关联动作
	        if (type == SysMenu.MENU_TYPE_SON) {
	        	sysActDao.delActByMenuId(menuId);
	        } else if (type == SysMenu.MENU_TYPE_PARENT) {
	            // 获得子菜单列表, 递归删除
	            List<SysMenu> menuList = findSubMenus((long)menuId, true);

	            if (menuList != null && menuList.size() > 0) {
	                Iterator<SysMenu> menuIterator = menuList.iterator();
	                while (menuIterator.hasNext()) {
	                	SysMenu subMenu = menuIterator.next();
	                    delMenu(subMenu.getId().intValue(), subMenu.getMenuType());
	                }
	            }
	        }
	        
	}
	
	 public void moveMenu(SysMenu menu, int i){
		 SysMenu curMenu = entityDao.getById(menu.getId());
		 if(null==curMenu.getParentMenu()){
			 curMenu.setParentMenu(rootMenu());
		 }
	        Map<String, Object> paraMap = new HashMap<String, Object>();
	        paraMap.put("parentId", curMenu.getParentMenu().getId());
	        paraMap.put("sortOrder", curMenu.getSort());
	       
	        SysMenu neibMenu = null;
	        if (i == 1) {
	            // 找到相邻的下一个菜单
	            paraMap.put("fun", "min");
	            paraMap.put("symbol", ">");
	            neibMenu = entityDao.getMenuByMap(paraMap);
	        } else if (i == -1) {
	            // 找到相邻的上一个菜单
	            paraMap.put("fun", "max");
	            paraMap.put("symbol", "<");
	            neibMenu = entityDao.getMenuByMap(paraMap);
	        }

	        if (neibMenu != null) { // 有相邻菜单交换order
	        	if(null==neibMenu.getParentMenu()){
	        		neibMenu.setParentMenu(rootMenu());
	   		 }
	        	
	        	int curSeq = curMenu.getSort();
	            curMenu.setSort(neibMenu.getSort());
	            entityDao.update(curMenu);
	            
	            neibMenu.setSort(curSeq);
	            entityDao.update(neibMenu);
	        }
	    }
	 
	 /*public List<ZTreeNode> getPMenuToTreeNode(){
		 	SysMenu _menu = new SysMenu();
			_menu.setIsDeleted(false);
			_menu.setMenuType(SysMenu.MENU_TYPE_PARENT);
			List<SysMenu> menus = this.getByEntity(_menu);
			List<ZTreeNode> nodes = Lists.newArrayList();
			nodes.add(rootNode());
			if(null!=menus){
				for(SysMenu menu:menus){
					ZTreeNode node = new ZTreeNode();
					node.setId(menu.getId());
					node.setName(menu.getName());
					SysMenu pMenu = menu.getParentMenu();
					if(null==pMenu){
						node.setpId(0L);
					}else{
						node.setpId(pMenu.getId());
					}
					nodes.add(node);
				}
			}
			return nodes;
		}*/

	 public List<EasyUITree> getPMenuToTreeNode(){
	 	SysMenu _menu = new SysMenu();
		_menu.setIsDeleted(false);
		_menu.setMenuType(SysMenu.MENU_TYPE_PARENT);
		List<SysMenu> menus = this.getByEntity(_menu);
		
		List<EasyUITree> tree = Lists.newArrayList();
		 for(SysMenu menu:menus){
			 EasyUITree t = new EasyUITree();
			 t.setIconCls(menu.getIcon());
			 t.setId(menu.getId().toString());
			 t.setText(menu.getName());
			 Integer pid = menu.getParentId();
			 if(null==pid){
				 pid = 0;
			 }
			 t.setPid(pid.toString());
			 tree.add(t);
		 }
		 return tree;
	}
	 /**
	  * 获取菜单树 --菜单管理
	  * @param uid
	  * @return
	  */
	 public List<EasyUITree> easyUIMenuTreeByUid(Long uid){
		 List<Long> roleIds = sysUserRoleManager.getRoleIdsByUid(uid);
		 List<SysRole> roles = Lists.newArrayList();
		 for(Long roleId : roleIds){
			 roles.add(sysRoleManager.getById(roleId));
		 }
		 List<SysMenu> menus = this.entityDao.getMenuTree(0, roles);
		 
		 return easyUIMenuTreeByUid(menus);
	 }
	 
	 /**
	  * 获取菜单树 --角色管理
	  * @param uid
	  * @return
	  */
	 public List<EasyUITree> easyUIMenuTreeByUid(Long uid, Long roleId){
		 List<Long> roleIds = sysUserRoleManager.getRoleIdsByUid(uid);
		 List<SysRole> roles = Lists.newArrayList();
		 for(Long rid : roleIds){
			 roles.add(sysRoleManager.getById(rid));
		 }
		 List<SysMenu> menus = this.entityDao.getMenuTree(0, roles);
		 
		 return easyUIMenuTreeByUid(menus,roleId);
	 }
	 
	 /**
	  * 组织菜单树结构 --菜单管理
	  * @param menus
	  * @return
	  */
	 public List<EasyUITree> easyUIMenuTreeByUid(List<SysMenu> menus){
		 List<EasyUITree> tree = Lists.newArrayList();
		 for(SysMenu menu:menus){
			 EasyUITree t = new EasyUITree();
			 t.setIconCls(menu.getIcon());
			 t.setId(menu.getId().toString());
			 t.setText(menu.getName());
			 Integer pid = menu.getParentId();
			 if(null==pid){
				 pid = 0;
			 }
			 t.setPid(pid.toString());
			 Map<String,String> attr =  Maps.newHashMap();
			 if(null!=menu.getActId()&&0!=menu.getActId()){
				 SysAct act = sysActDao.getById(menu.getActId().longValue());
				 if(null!=act){
					 attr.put("url", act.getActName());
				 }
				 
			 }
			 t.setAttributes(attr);
			 if(null!=menu.getSubMenus()&&menu.getSubMenus().size()>0){
				 t.setChildren(easyUIMenuTreeByUid(menu.getSubMenus()));
			 }
			 tree.add(t);
		 }
		 
		 return tree;
	 }
	 
	 /**
	  * 组织菜单树结构 --角色管理
	  * @param menus
	  * @param addRact
	  * @return
	  */
	 public List<EasyUITree> easyUIMenuTreeByUid(List<SysMenu> menus, Long roleId){
		 List<Long> roleIds = Lists.newArrayList();
		 roleIds.add(roleId);
		 List<SysAct>  authacts = sysActDao.getCommonSysActs(roleIds);
		 List<SysMenu> authmenus = this.entityDao.getMenuListByRoleList(roleIds);
		 
		 List<EasyUITree> tree = Lists.newArrayList();
		 for(SysMenu menu:menus){
			 EasyUITree t = new EasyUITree();
			 t.setIconCls(menu.getIcon());
			 t.setId(menu.getId().toString());
			 t.setText(menu.getName());
			 Integer pid = menu.getParentId();
			 if(null==pid){
				 pid = 0;
			 }
			 if(authmenus.contains(menu)){
				 t.setChecked(true);
			 }
			 t.setPid(pid.toString());
			 Map<String,String> attr =  Maps.newHashMap();
			 if(null!=menu.getActId()&&0!=menu.getActId()){
				 SysAct act = sysActDao.getById(menu.getActId().longValue());
				 if(null!=act){
					 attr.put("url", act.getActName());
				 }
				 
			 }
			 t.setAttributes(attr);
			 List<SysAct> sysacts = sysActManager.findMenuAct(menu.getId(), SysAct.ACT_TYPE_AUTH);
			 if(null!=menu.getSubMenus()&&menu.getSubMenus().size()>0){
				 t.setChildren(easyUIMenuTreeByUid(menu.getSubMenus(),roleId));
			 }else if(sysacts.size()!=0){
				 List<EasyUITree> treeac = Lists.newArrayList();
				 for(SysAct ac : sysacts){
					 EasyUITree at = new EasyUITree();
					 at.setId(ac.getId().toString());
					 at.setText(ac.getRemarks()  + "[" + ac.getActName() + "]");
					 at.setPid(menu.getId().toString());
					 if(authacts.contains(ac)){
						 at.setChecked(true);
					 }
					 treeac.add(at);
				 }
				 t.setChildren(treeac);
			 }
			 tree.add(t);
		 }
		 
		 return tree;
	 }
}
