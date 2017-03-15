package com.fabao.ledger.modules.sys.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;
import com.fabao.ledger.common.pojo.EasyUITree;
import com.fabao.ledger.modules.sys.annotations.Log;
import com.fabao.ledger.modules.sys.annotations.NoAuthor;
import com.fabao.ledger.modules.sys.entity.SysAct;
import com.fabao.ledger.modules.sys.entity.SysMenu;
import com.fabao.ledger.modules.sys.service.SysActManager;
import com.fabao.ledger.modules.sys.service.SysMenuManager;
import com.fabaoframework.modules.utils.JsonUtil;
import com.fabaoframework.modules.web.BaseController;
import com.google.common.collect.Lists;

@Controller
@RequestMapping("/sys/menus")
public class MenusController extends BaseController{
	
	@Autowired
	private SysMenuManager sysMenuManager;
	@Autowired
	private SysActManager sysActManager;


	@RequestMapping("tree")
	@ResponseBody
	public String menuTree(){
		return JsonUtil.createJsonFromObject(sysMenuManager.menuToTreeNode());
	}
	
	/**
	 * 显示菜单管理页面 --
	 * @param model
	 * @return
	 */
	@RequestMapping("list")
	public String index(Model model,HttpServletRequest request){
		String pid = request.getParameter("id");
		model.addAttribute("redPid", pid == null ? -1 : pid);
		return "modules/sys/menus-list";
	}
	
	/**
	 * 显示菜单信息页面 easy panel --
	 * @param model
	 * @param mid 菜单id
	 * @param pid 父id
	 * @return
	 */
	@RequestMapping(value="menuinfo")
	public String menuinfo(Long id,Model model){
		SysMenu curMenu = null;
		if(null==id || id==0){
			curMenu = sysMenuManager.rootMenu();
		}else{
			curMenu = sysMenuManager.getById(id);
			if(null==curMenu.getParentMenu()){
				curMenu.setParentMenu(sysMenuManager.rootMenu());
			}
		}
		model.addAttribute("curMenu", curMenu);
		model.addAttribute("subMenus",sysMenuManager.findSubMenus(id, false));
		model.addAttribute("commonActs", sysActManager.findMenuAct(id, SysAct.ACT_TYPE_COMMON));
		model.addAttribute("authActs", sysActManager.findMenuAct(id, SysAct.ACT_TYPE_AUTH));
		return "modules/sys/menus-info";
	}
	
	/**
	 * 跳转添加菜单  --
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("formAdd")
	public String formAdd(Long id,Model model){
		SysMenu parentMenu = null;
		if(null==id||id==0){
			parentMenu = sysMenuManager.rootMenu();
		}else{
			parentMenu = sysMenuManager.getById(id);
		}
		model.addAttribute("parentMenu", parentMenu);
		
		return "modules/sys/menu-formAdd";
	}
	
	/**
	 * 跳转菜单编辑 --
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("formEdit")
	public String formEdit(Long id,Model model){
		SysMenu menu = sysMenuManager.getById(id);
		if(null==menu.getParentMenu()){
			menu.setParentMenu(sysMenuManager.rootMenu());
		}
		model.addAttribute("menu", menu);
		
		return "modules/sys/menus-formEdit";
	}
	
	/**
	 * 保存菜单 --
	 * @param menu
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@Log
	@RequestMapping(value="save")
	public String save(SysMenu menu,String actName,Model model,RedirectAttributes redirectAttributes){
		if (!beanValidator(model, menu)){
			return formAdd(menu.getParentId().longValue(), model);
		}
		if(menu.getMenuType()==SysMenu.MENU_TYPE_SON){
			SysAct act = new  SysAct();
			act.setActName(actName);
			menu.setSysAct(act);
		}
		sysMenuManager.addMenu(menu);
		
		addMessage(redirectAttributes, "保存菜单:'" + menu.getName() + "'成功");
		return "redirect:/sys/menus/list?repage&id="+menu.getParentId();
	}
	@Log
	@RequestMapping("edit")
	public String edit(SysMenu menu,String actName,Model model,RedirectAttributes redirectAttributes){
		if (!beanValidator(model, menu)){
			return formEdit(menu.getId(), model);
		}
		//获取db信息
		SysMenu menu_db = sysMenuManager.getById(menu.getId());
		menu_db.setParentId(menu.getParentId());
		menu_db.setName(menu.getName());
		menu_db.setIcon(menu.getIcon());
		menu_db.setRemarks(menu.getRemarks());
		menu_db.setMenuType(menu.getMenuType());
		//叶子菜单/ 动作
		SysAct act_db = menu_db.getSysAct();
		if(menu.getMenuType()==SysMenu.MENU_TYPE_SON){
			if(act_db == null){
				act_db = new SysAct();
			}
			act_db.setActName(actName);
			menu_db.setSysAct(act_db);
		}else{
			menu_db.setSysAct(null);
			if(act_db != null){
				sysActManager.delActByActId(act_db.getId());
			}
		}
		sysMenuManager.editMenu(menu_db);
		
		addMessage(redirectAttributes, "保存菜单:'" + menu.getName() + "'成功");
		return "redirect:/sys/menus/list?repage&id=" + menu.getId();
	}
	/**
	 * 删除菜单 --
	 * @param id
	 * @return
	 */
	@Log
	@RequestMapping("delete")
	public String delete(Long id){
		SysMenu menu = sysMenuManager.getById(id);
		int pid = 0;
		if(null!=menu){
			sysMenuManager.delMenu(menu.getId().intValue(), menu.getMenuType());
			SysMenu pmenu = menu.getParentMenu();
			if(null!=pmenu){
				pid = pmenu.getId().intValue();
			}
		}
		
		return "redirect:/sys/menus/list?repage&id="+pid;
	}
	/**
	 * 下移 --
	 * @param id
	 * @param pid
	 * @return
	 */
	@Log
	@RequestMapping("down")
	public String down(Long id,Long pid){
		SysMenu menu = new SysMenu();
		menu.setId(id);
		sysMenuManager.moveMenu(menu, 1);
		if(null==pid){
			pid=0L;
		}
		return "redirect:/sys/menus/list?repage&id="+pid;
	}
	/**
	 * 上移 --
	 * @param id
	 * @param pid
	 * @return
	 */
	@Log
	@RequestMapping("up")
	public String up(Long id,Long pid){
		SysMenu menu = new SysMenu();
		menu.setId(id);
		sysMenuManager.moveMenu(menu, -1);
		if(null==pid){
			pid=0L;
		}
		return "redirect:/sys/menus/list?repage&id="+pid;
	}
	
	/*@RequestMapping("pMenu")
	@ResponseBody
	@NoAuthor
	public List<ZTreeNode> pMenu(){
		return sysMenuManager.getPMenuToTreeNode();
	}*/
	
	/**
	 * 获取菜单数据 easyui --
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("authorMenuTree")
	@NoAuthor
	@ResponseBody
	public List<EasyUITree> authorMenuTree(HttpServletRequest request,HttpServletResponse response){
		String parentNode = request.getParameter("pnode");
		SSOToken token = SSOHelper.getToken(request);
		List<EasyUITree> trees = Lists.newArrayList();
		EasyUITree tree = new EasyUITree();
		tree.setText("系统顶级菜单");
		tree.setId("0");
		tree.setIconCls("chart_bar");
		if(parentNode != null){
			tree.setChildren(sysMenuManager.getPMenuToTreeNode());	
		}else{
//			tree.setChildren(sysMenuManager.easyUIMenuTreeByUid(0l));	//全部
			tree.setChildren(sysMenuManager.easyUIMenuTreeByUid(token.getId()));	
		}
		trees.add(tree);
		return trees;
	}
	
	/**
	 * 获取菜单数据 系统权限配置 easyui --
	 * @param request
	 * @param response 
	 * @return
	 */
	@RequestMapping("roleActMenuTree")
	@NoAuthor
	@ResponseBody
	public List<EasyUITree> roleActMenuTree(HttpServletRequest request,HttpServletResponse response, Long roleId){
//		SSOToken token = SSOHelper.getToken(request);
		System.err.println("-------------------");
		return sysMenuManager.easyUIMenuTreeByUid(0l,roleId);	//全部菜单
//		return sysMenuManager.easyUIMenuTreeByUid(token.getId(),roleId);	//全部菜单
	}
}
