package com.fabao.ledger.modules.sys.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fabao.ledger.common.pojo.Message;
import com.fabao.ledger.common.utils.ConstantUtils;
import com.fabao.ledger.modules.sys.annotations.Log;
import com.fabao.ledger.modules.sys.entity.SysRole;
import com.fabao.ledger.modules.sys.service.SysMenuManager;
import com.fabao.ledger.modules.sys.service.SysRoleActManager;
import com.fabao.ledger.modules.sys.service.SysRoleManager;
import com.fabao.ledger.modules.sys.service.SysRoleMenuManager;
import com.fabao.ledger.modules.sys.service.SysUserRoleManager;
import com.fabaoframework.modules.page.Page;
import com.fabaoframework.modules.page.PageRequest;
import com.fabaoframework.modules.web.BaseController;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Controller
@RequestMapping("/sys/role")
public class RoleController extends BaseController {
	
	@Autowired
	private SysRoleManager sysRoleManager;
	
	@Autowired
	private SysMenuManager sysMenuManager;
	
	@Autowired
	private SysRoleActManager sysRoleActManager;
	
	@Autowired
	private SysRoleMenuManager sysRoleMenuManager;
	
	@Autowired
	private SysUserRoleManager sysUserRoleManager;
	
	@ModelAttribute
	public SysRole get(@RequestParam(required=false) Long id) {
		if (null!=id){
			return sysRoleManager.getById(id);
		}else{
			return new SysRole();
		}
	}

	/**
	 * 跳转角色列表页 --
	 * @param model
	 * @return
	 */
	@RequestMapping("roleList")
	public String list(Model model){
		return "modules/sys/sysRole-list";
	}
	
	/**
	 * 角色列表数据获取 --
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("roleListData")
	@ResponseBody
	public Map<String,Object> roleListData(int page , int rows){
		PageRequest<SysRole> pr = new PageRequest<SysRole>(new SysRole());
		pr.setPageNo(page);
		pr.setPageSize(rows);
		Page<SysRole> pages = sysRoleManager.findByPageRequest(pr);
		Map<String,Object> res = Maps.newHashMap();
		res.put("rows", pages.getResult());
		res.put("total", pages.getTotalCount());
		return res;
	}
	
	/**
	 * 跳转角色编辑新增页 --
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "roleInfoAE", method = RequestMethod.GET)
	public String roleInfoAE(Model model, Long rid){
		SysRole role = null;
		if(rid!=null && rid!=0l){
			role = sysRoleManager.getById(rid);
		}
		role = role == null ? new SysRole() : role;
		model.addAttribute("sysRole", role);
		
		return "modules/sys/sysRole-form";
	}
	
	
	
	@RequestMapping("form")
	@ResponseBody
	public Map<String,Object> form(SysRole role,Model model){
		model.addAttribute("role", role);
		List<Long> roleIds = Lists.newArrayList();
		if(null==role||role.getId()==null){
			roleIds = null;
		}else{
			roleIds.add(role.getId());
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("role", role);
		map.put("tree", sysMenuManager.priMenuActToTreeNode(roleIds));
		return map;
	}
	
	
	@Log
	@RequestMapping("save")
	public String save(SysRole role,String oldName, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes){
		if(role.getId()==null && sysRoleManager.selectRoleByName(role.getName())!=null){
			addMessage(redirectAttributes, "角色名已经存在");
			return "redirect:/sys/role/roleList?repage";
		}
		if(role.getId()!=null && !role.getName().equals(oldName) && sysRoleManager.selectRoleByName(role.getName())!=null){
			addMessage(redirectAttributes, "角色名已经存在");
			return "redirect:/sys/role/roleList?repage";
		}
		if(role.getId()==null){
			String treemenuids = request.getParameter("treemenuids");
			sysRoleManager.save(role);
			sysRoleActManager.saveRoleAct(role.getId(), treemenuids);
			sysRoleMenuManager.saveRoleMenu(role.getId(), treemenuids);
		}else{
			SysRole _role=get(role.getId());
			_role.setName(role.getName());
			_role.setRemarks(role.getRemarks());
			String treemenuids = request.getParameter("treemenuids");
			sysRoleManager.saveOrUpdate(_role);
			sysRoleActManager.saveRoleAct(_role.getId(), treemenuids);
			sysRoleMenuManager.saveRoleMenu(_role.getId(), treemenuids);
		}
		addMessage(redirectAttributes, "保存/修改角色:'" + role.getName() + "'成功");
		return "redirect:/sys/role/roleList?repage";
	}
		
	@Log
	@RequestMapping("update")
	@ResponseBody
	public Message update(SysRole role,String oldName, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes){
		if(!role.getName().equals(oldName)&&sysRoleManager.selectRoleByName(role.getName())!=null){
			return new Message(ConstantUtils.ERROR, "角色名已经存在");
		}
		SysRole _role=get(role.getId());
		_role.setName(role.getName());
		String treemenuids = request.getParameter("treemenuids");
		sysRoleManager.saveOrUpdate(_role);
		sysRoleActManager.saveRoleAct(_role.getId(), treemenuids);
		sysRoleMenuManager.saveRoleMenu(_role.getId(), treemenuids);
		return new Message(ConstantUtils.SUCCESS,"成功");
	}
	
	@Log
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,Object> delete(HttpServletRequest request, Long rid){
		Map<String,Object> res = Maps.newHashMap();
		res.put("code", 0);
		try {
			SysRole role = null;
			if(rid == null || rid == 0l){
				res.put("code", 1);
				res.put("msg", "删除失败请重新选择!");
			}else{
				role = sysRoleManager.getById(rid);
			}
			role.setIsDeleted(true);
			this.sysRoleManager.update(role);
			this.sysUserRoleManager.updateIsDelete(role.getId(),true);
		} catch (Exception e) {
			res.put("code", 1);
			res.put("msg", e.getMessage());
		}
		
		return res;
	}
}
