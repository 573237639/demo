package com.fabao.ledger.modules.sys.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;
import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import com.fabao.ledger.common.utils.ConstantUtils;
import com.fabao.ledger.common.web.CommonController;
import com.fabao.ledger.modules.oracle.service.OracleManager;
import com.fabao.ledger.modules.sys.annotations.NoUser;
import com.fabao.ledger.modules.sys.entity.SysMenu;
import com.fabao.ledger.modules.sys.entity.SysUser;
import com.fabao.ledger.modules.sys.service.SysActManager;
import com.fabao.ledger.modules.sys.service.SysMenuManager;
import com.fabao.ledger.modules.sys.service.SysUserManager;
import com.fabao.ledger.modules.sys.service.SysUserRoleManager;
import com.fabaoframework.modules.utils.IUser;

@Controller
public class LoginController extends CommonController{

	private final static Logger logger = Logger.getLogger(LoginController.class);
	
	@Autowired
	private SysUserManager sysUserManager;
	@Autowired
	private SysMenuManager sysMenuManager;
	@Autowired
	private SysUserRoleManager sysUserRoleManager;
	@Autowired
	private SysActManager sysActManager;
	
	/**
	 * 管理登录
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	@NoUser
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/sys/sysLogin";
	}
	
	/**
	 * 登录失败，真正登录的POST请求由Filter完成
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@NoUser
	public String login(@RequestParam(value="name",required=true) String name,@RequestParam(value="password",required=true) String password, @RequestParam(value="macAddress") String macAddress, HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes) throws IllegalAccessException, InvocationTargetException {
		SysUser user = null;
		user = this.sysUserManager.findUserByNameAndPass(name,password);
		if(null==user){
			request.setAttribute("message","邮箱或密码错误");
			return "modules/sys/sysLogin";
		}
		SSOToken token = new SSOToken();
		token.setId(user.getId());
		token.setTime(System.currentTimeMillis());
		token.setData(user.getUsername());
		SSOHelper.setSSOCookie(request,  response, token, true);
		List<Long> roleIds = sysUserRoleManager.getRoleIdsByUid(user.getId());
		
		List<SysMenu> menus = sysMenuManager.getMenuTree(0, roleIds);
		request.getSession().setAttribute(ConstantUtils.MENU_SESSION, menus);
		
		Set<String> acts = new HashSet<String>();
		acts.addAll(sysActManager.getCommonActs(roleIds));
		acts.addAll(sysActManager.getAuthorActs(roleIds));
		request.getSession().setAttribute(ConstantUtils.ACT_SESSION,acts);
		
		IUser u = new IUser();
		u.setId(user.getId());
		u.setUsername(user.getUsername());
		request.getSession().setAttribute("iuser", u);
		request.getSession().setAttribute("macAddress", macAddress);
		user.setLastLoginTime(new Date());
		sysUserManager.update(user);
		return "redirect:/";
	}
	
	/**
	 * 登录成功，进入管理首页
	 */
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response,Model model) {
		
		//获取用户信息
		SSOToken token = SSOHelper.getToken(request);
		model.addAttribute("token", token);
		SysUser sysUser = sysUserManager.getById(token.getId());
		if(sysUser != null){
			model.addAttribute("agentID", sysUser.getHwNum());//工号8001
			model.addAttribute("realname", sysUser.getRealname());//真实姓名
			model.addAttribute("idleAlertTimelong", 30);//工号8001
		}
		model.addAttribute("agentDN", getAgentDn(request));//分机号1071
		model.addAttribute("monitor", OracleManager.getAgentTypeByAgentId(getAgentId(sysUserManager, request)));//是否班长席
		return "modules/sys/home";
	}
	
	@RequestMapping("/logout")
	@ResponseBody
	@Login(action=Action.Skip)
	public Map<String,Object> logout(HttpServletRequest request,HttpServletResponse response){
		request.getSession().invalidate();
		SSOHelper.clearLogin(request,  response);
		return resMap(CODE_SUCCESS, "成功退出！");
	}
	
	/**
	 * 获取工号
	 * @param sysUserManager
	 * @param token
	 * @return
	 */
	public static String getAgentId(SysUserManager sysUserManager,HttpServletRequest request){
		SSOToken token = SSOHelper.getToken(request);
		SysUser sysUser = sysUserManager.getById(token.getId());
		if(sysUser != null){
			return sysUser.getHwNum();
		}
		return "";
	}
	
	/**
	 * 获取分机号
	 * @param request
	 * @return
	 */
	public static String getAgentDn(HttpServletRequest request){
		String mac = request.getSession().getAttribute("macAddress") != null ? request.getSession().getAttribute("macAddress").toString() : null;
		String agentDN = OracleManager.getAgentDnByMac(mac);
		logger.info("通过mac["+mac+"]获取分机号agentDN["+agentDN+"]");
		return agentDN;
	}
	
	  public static String getIpAddress(HttpServletRequest request) { 
		    String ip = request.getHeader("x-forwarded-for"); 
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		      ip = request.getHeader("Proxy-Client-IP"); 
		    } 
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		      ip = request.getHeader("WL-Proxy-Client-IP"); 
		    } 
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		      ip = request.getHeader("HTTP_CLIENT_IP"); 
		    }                   
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		      ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
		    } 
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		      ip = request.getRemoteAddr(); 
		    } 
		    return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip; 
		  } 
	  
	  /** 
	   * 通过IP地址获取MAC地址 
	   * @param ip String,127.0.0.1格式 
	   * @return mac String 
	   * @throws Exception 
	   */  
	  public static String getMACAddress(String ip)  {  
	      String line = "";  
	      String macAddress = "";  
	      final String MAC_ADDRESS_PREFIX_EN = "MAC Address = ";  
	      final String MAC_ADDRESS_PREFIX_CN = "MAC 地址 =";
	      final String LOOPBACK_ADDRESS = "127.0.0.1";  
	      try {
			//如果为127.0.0.1,则获取本地MAC地址。  
			if (LOOPBACK_ADDRESS.equals(ip)) {
				InetAddress inetAddress = InetAddress.getLocalHost();
				//貌似此方法需要JDK1.6。  
				byte[] mac = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();
				//下面代码是把mac地址拼装成String  
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < mac.length; i++) {
					if (i != 0) {
						sb.append("-");
					}
					//mac[i] & 0xFF 是为了把byte转化为正整数  
					String s = Integer.toHexString(mac[i] & 0xFF);
					sb.append(s.length() == 1 ? 0 + s : s);
				}
				//把字符串所有小写字母改为大写成为正规的mac地址并返回  
				macAddress = sb.toString().trim().toUpperCase();
				return macAddress;
			} 
		} catch (Exception e) {
			// TODO: handle exception
		}
		//获取非本地IP的MAC地址  
	      try {  
	          Process p = Runtime.getRuntime().exec("netstat -A " + ip);  
	          InputStreamReader isr = new InputStreamReader(p.getInputStream(),"gb2312");  
	          BufferedReader br = new BufferedReader(isr);  
	          while ((line = br.readLine()) != null) {  
	              if (line != null) {  
	                  int index_en = line.indexOf(MAC_ADDRESS_PREFIX_EN);  
	                  if (index_en != -1) {  
	                      macAddress = line.substring(index_en + MAC_ADDRESS_PREFIX_EN.length()).trim().toUpperCase();  
	                  } 
	                  int index_cn = line.indexOf(MAC_ADDRESS_PREFIX_CN);  
	                  if (index_cn != -1) {  
	                      macAddress = line.substring(index_cn + MAC_ADDRESS_PREFIX_CN.length()).trim().toUpperCase();  
	                  } 
	                  
	              }  
	          }  
	          br.close();  
	      } catch (IOException e) {  
	    	  logger.info("获取非本地IP的MAC地址异常,"+e.fillInStackTrace());
	      }  
	      return macAddress;  
	  }  
	 
	
}
