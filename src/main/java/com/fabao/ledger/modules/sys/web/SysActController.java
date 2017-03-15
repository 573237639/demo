package com.fabao.ledger.modules.sys.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fabao.ledger.modules.sys.annotations.Log;
import com.fabao.ledger.modules.sys.annotations.NoAuthor;
import com.fabao.ledger.modules.sys.entity.SysAct;
import com.fabao.ledger.modules.sys.service.SysActManager;
import com.fabao.ledger.modules.sys.service.SysMenuManager;
import com.fabaoframework.modules.lang.Lang;
import com.fabaoframework.modules.web.BaseController;


@Controller
@RequestMapping("/sys/act")
public class SysActController extends BaseController {
	
	@Autowired
	private SysActManager sysActManager;
	@Autowired
	private SysMenuManager sysMenuManager; 

	@ModelAttribute
	public SysAct get(@RequestParam(required=false) Long id){
		SysAct act = new SysAct();
		if(null!=id&&0!=id){
			act = sysActManager.getById(id);
		}
		return act;
	}
	
	@RequestMapping("/noauthor")
	@NoAuthor
	public String noAuthor(){
		return "modules/sys/noauthor";
	}
	
	@RequestMapping("form")
	public String form(SysAct sysAct,@RequestParam(required=false)Long menuId,Model model){
		model.addAttribute("sysAct",sysAct);
		if(null==menuId||0==menuId){
			model.addAttribute("menu", sysMenuManager.rootMenu());
		}else{
			model.addAttribute("menu", sysMenuManager.getById(menuId));
		}
		return "modules/sys/act-form";
	}

	@Log
	@RequestMapping("save")
	public String save(SysAct sysAct,HttpServletRequest request, Model model, RedirectAttributes redirectAttributes){
		if (!beanValidator(model, sysAct)){
			return form(sysAct, sysAct.getMenuId().longValue(),model);
		}
		sysActManager.saveOrUpdate(sysAct);
		addMessage(redirectAttributes, "保存动作'" + sysAct.getActName() + "'成功");
		return "redirect:/sys/menu/menuinfo?repage&id="+sysAct.getMenuId();
	}
	
	@Log
	@RequestMapping("delete")
	public String delete(Long id){
		SysAct act = sysActManager.getById(id);
		if(null==act){
			Lang.makeThrow("您要删除的对象不存在!!!");
		}
		this.sysActManager.delActByActId(id);
		return "redirect:/sys/menu/menuinfo?repage&id="+act.getMenuId();
	}
}
