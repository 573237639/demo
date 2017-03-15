package com.fabao.ledger.modules.sys.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;
import com.baomidou.kisso.common.encrypt.MD5;
import com.fabao.ledger.common.web.CommonController;
import com.fabao.ledger.modules.sys.entity.SysRole;
import com.fabao.ledger.modules.sys.entity.SysUser;
import com.fabao.ledger.modules.sys.service.SysRoleManager;
import com.fabao.ledger.modules.sys.service.SysUserManager;
import com.fabao.ledger.modules.sys.service.SysUserRoleManager;
import com.fabaoframework.modules.page.Page;
import com.fabaoframework.modules.page.PageRequest;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Controller
@RequestMapping("/sys/user")
public class UserController extends CommonController {
	
	@Autowired
	private SysUserManager sysUserManager;
	@Autowired
	private SysRoleManager sysRoleManager;
	@Autowired
	private SysUserRoleManager sysUserRoleManager;

	@RequestMapping("userEditPwd")
	public String userEditPsw(HttpServletRequest request,Model model){
		SSOToken token = SSOHelper.getToken(request);
		model.addAttribute("token", token);
		return "modules/sys/userEditPwd";
	}
	
	@RequestMapping("editPwd")
	@ResponseBody
	public Map<String,Object> editPwd(SysUser user){
		Map<String,Object> res = resMap(CODE_SUCCESS,"ok");
		if(null!=user&&null!=user.getId()&&0!=user.getId()&&StringUtils.isNotBlank(user.getPassword())){
			SysUser oldUser = sysUserManager.getById(user.getId());
			if(null==oldUser){
				return resMap(CODE_FAIL,"用户不存在!");
			}
			oldUser.setPassword(MD5.toMD5(user.getPassword()));
			sysUserManager.update(oldUser);
			return res;
		}else{
			return resMap(CODE_FAIL,"参数不完整！");
		}
	}
	
	/**
	 * 用户列表页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("userList")
	public String userList(HttpServletRequest request,Model model){
		SSOToken token = SSOHelper.getToken(request);
		model.addAttribute("token", token);
		return "modules/sys/sysUser-list";
	}
	/**
	 * 用户新增/编辑页面 跳转
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "userInfoAE", method = RequestMethod.GET)
	public String userInfoAE(HttpServletRequest request,Model model, Long uid){
		SSOToken token = SSOHelper.getToken(request);
		model.addAttribute("token", token);
		SysUser user = null;
		List<Long> userRoleIds = Lists.newArrayList(); 
		if(uid != null && uid != 0l){
			user = sysUserManager.getById(uid);
			userRoleIds = sysUserRoleManager.getRoleIdsByUid(uid);
		}
		user = user == null ? new SysUser() : user;
		model.addAttribute("sysUser", user);
		model.addAttribute("userRoleIds", userRoleIds);
		//获取角色
		SysRole ro = new SysRole();
		ro.setIsDeleted(false);
		List<SysRole> roles = sysRoleManager.getByEntity(ro);
		model.addAttribute("roles", roles);
		return "modules/sys/sysUser-form";
	}
	
	/**
	 * 用户新增/编辑 动作
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "userInfoAE", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> userInfoAE(HttpServletRequest request, SysUser user, String roleIds){
		Map<String,Object> res = Maps.newHashMap();
		res.put("code", 0);
		try {
			//功能角色
			List<Long> roleArr = Lists.newArrayList();
			String[] rids = roleIds.split(",");
			for(int i=0;i<rids.length;i++){
				roleArr.add(Long.valueOf(rids[i]));
			}

			if("".equals(user.getPassword()) || null == user.getPassword()){
				user.setPassword(MD5.toMD5("111111"));	//默认初始密码
			}
			user.setIsDeleted(false);
			sysUserManager.saveOrUpdate(user);
			//进行角色设置
			sysUserRoleManager.saveUserRole(user.getId(), roleArr);
		} catch (Exception e) {
			res.put("code", 1);
			res.put("msg", e.getMessage());
		}
		return res;
	}
	
	/**
	 * 用户列表数据获取
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("userListData")
	@ResponseBody
	public Map<String,Object> userListData(int page , int rows, SysUser user){
		PageRequest<SysUser> pr = new PageRequest<SysUser>(user);
		pr.setPageNo(page);
		pr.setPageSize(rows);
		Page<SysUser> pages = sysUserManager.findByPageRequest(pr);
		Map<String,Object> res = Maps.newHashMap();
		res.put("rows", pages.getResult());
		res.put("total", pages.getTotalCount());
		return res;
	}
	
	/**
	 * 用户删除 动作,逻辑删除
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteUser(HttpServletRequest request, Long uid){
		Map<String,Object> res = Maps.newHashMap();
		res.put("code", 0);
		try {
			SysUser user = null;
			if(uid == null || uid == 0l){
				res.put("code", 1);
				res.put("msg", "删除失败请重新选择!");
			}else{
				user = sysUserManager.getById(uid);
			}
			user.setIsDeleted(true);
			sysUserManager.update(user);
		} catch (Exception e) {
			res.put("code", 1);
			res.put("msg", e.getMessage());
		}
		return res;
	}
	
	/**
	 * 重置密码
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "resetPwd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> resetPwd(HttpServletRequest request, String uids){
		Map<String,Object> res = Maps.newHashMap();
		res.put("code", 0);
		try {
			sysUserManager.resetPwdByUids(uids);
		} catch (Exception e) {
			res.put("code", 1);
			res.put("msg", e.getMessage());
		}
		return res;
	}
	
	/**
	 * 个人中心页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("userInfo")
	public String userInfo(HttpServletRequest request,Model model){
		SSOToken token = SSOHelper.getToken(request);
		SysUser user = sysUserManager.getById(token.getId());
//		SysUser user = sysUserManager.getById(18l);
		model.addAttribute("token", token);
		model.addAttribute("sysUser", user);
		return "modules/sys/sysUser-center";
	}
	
	/**
	 * 个人中心页面信息修改
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "userCenterUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> userCenterUpdate(HttpServletRequest request, SysUser user, int changepwd){
		Map<String,Object> res = Maps.newHashMap();
		res.put("code", 0);
		try {
			System.err.println(changepwd);
			SysUser user_db = sysUserManager.getById(user.getId());
			user_db.setUsername(user.getUsername());
			user_db.setEmail(user.getEmail());
			user_db.setPhone(user.getPhone());
			user_db.setRealname(user.getRealname());
			if(changepwd == 1){
				user_db.setPassword(MD5.toMD5(user.getPassword()));	//默认初始密码
			}
			sysUserManager.update(user_db);
		} catch (Exception e) {
			res.put("code", 1);
			res.put("msg", e.getMessage());
		}
		return res;
	}
	
}
