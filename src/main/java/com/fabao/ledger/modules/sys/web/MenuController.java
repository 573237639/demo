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
import com.fabao.ledger.common.pojo.ZTreeNode;
import com.fabao.ledger.modules.sys.annotations.Log;
import com.fabao.ledger.modules.sys.annotations.NoAuthor;
import com.fabao.ledger.modules.sys.entity.SysAct;
import com.fabao.ledger.modules.sys.entity.SysMenu;
import com.fabao.ledger.modules.sys.service.SysActManager;
import com.fabao.ledger.modules.sys.service.SysMenuManager;
import com.fabaoframework.modules.utils.JsonUtil;
import com.fabaoframework.modules.web.BaseController;

@Controller
@RequestMapping("/sys/menu")
public class MenuController extends BaseController{
	
	@Autowired
	private SysMenuManager sysMenuManager;
	@Autowired
	private SysActManager sysActManager;

	@RequestMapping("tree")
	@ResponseBody
	public String menuTree(){
		return JsonUtil.createJsonFromObject(sysMenuManager.menuToTreeNode());
	}
	
	@RequestMapping(value={"list",""})
	public String index(Model model){
		model.addAttribute("nodes",JsonUtil.createJsonFromObject(sysMenuManager.menuToTreeNode()));
		return "modules/sys/menu-list";
	}
	
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
		return "modules/sys/menu-info";
	}
	
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
	
	@RequestMapping("formEdit")
	public String formEdit(Long id,Model model){
		SysMenu menu = sysMenuManager.getById(id);
		if(null==menu.getParentMenu()){
			menu.setParentMenu(sysMenuManager.rootMenu());
		}
		model.addAttribute("menu", menu);
		
		return "modules/sys/menu-formEdit";
	}
	
	@Log
	@RequestMapping("save")
	public String save(SysMenu menu,Model model,RedirectAttributes redirectAttributes){
		if (!beanValidator(model, menu)){
			return formAdd(menu.getParentId().longValue(), model);
		}
		sysMenuManager.addMenu(menu);
		
		addMessage(redirectAttributes, "保存菜单:'" + menu.getName() + "'成功");
		return "redirect:/sys/menu/menuinfo?repage&id="+menu.getParentId();
	}
	@Log
	@RequestMapping("edit")
	public String edit(SysMenu menu,Model model,RedirectAttributes redirectAttributes){
		if (!beanValidator(model, menu)){
			return formEdit(menu.getId(), model);
		}
		sysMenuManager.editMenu(menu);
		
		addMessage(redirectAttributes, "保存菜单:'" + menu.getName() + "'成功");
		return "redirect:/sys/menu/menuinfo?repage&id="+menu.getId();
	}
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
		
		return "redirect:/sys/menu/menuinfo?repage&id="+pid;
	}
	@Log
	@RequestMapping("down")
	public String down(Long id,Long pid){
		SysMenu menu = new SysMenu();
		menu.setId(id);
		sysMenuManager.moveMenu(menu, 1);
		if(null==pid){
			pid=0L;
		}
		return "redirect:/sys/menu/menuinfo?repage&id="+pid;
	}
	@Log
	@RequestMapping("up")
	public String up(Long id,Long pid){
		SysMenu menu = new SysMenu();
		menu.setId(id);
		sysMenuManager.moveMenu(menu, -1);
		if(null==pid){
			pid=0L;
		}
		return "redirect:/sys/menu/menuinfo?repage&id="+pid;
	}
	
	@RequestMapping("pMenu")
	@ResponseBody
	@NoAuthor
	public List<ZTreeNode> pMenu(){
		return null;//sysMenuManager.getPMenuToTreeNode();
	}
	
	@RequestMapping("authorMenuTree")
	@NoAuthor
	@ResponseBody
	public List<EasyUITree> authorMenuTree(HttpServletRequest request,HttpServletResponse response){
		SSOToken token = SSOHelper.getToken(request);
		return sysMenuManager.easyUIMenuTreeByUid(token.getId());
	}
}
