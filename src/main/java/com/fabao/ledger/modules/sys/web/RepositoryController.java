package com.fabao.ledger.modules.sys.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;
import com.fabao.ledger.modules.cms.entity.CmsOpinion;
import com.fabao.ledger.modules.cms.service.CmsOpinionManager;
import com.fabao.ledger.modules.repository.service.RepositoryManager;
import com.fabao.ledger.modules.sys.entity.SysUser;
import com.fabao.ledger.modules.sys.service.SysUserManager;
import com.fabaoframework.modules.page.Page;
import com.fabaoframework.modules.page.PageRequest;
import com.google.common.collect.Maps;

/**
 * @Description: 知识库管理
 * @author FB - LISI
 * @date 2016年10月27日 下午2:57:44
 */
@Controller
@RequestMapping("/sys/repository")
public class RepositoryController {

	@Autowired
	private CmsOpinionManager cmsOpinionManager;
	@Autowired
	private SysUserManager sysUserManager;
	/**
	 * 跳转知识库 
	 * @param model
	 * @return
	 */
	@RequestMapping("page")
	public String page(Model model, HttpServletRequest request){
		request.setAttribute("repType", request.getParameter("repType"));
		return "modules/sys/repository-list";
	}
	
	
	/**
	 * 查询知识库信息
	 * @param request
	 * @param page 当前页
	 * @param row 页大小
	 * @param repType 知识类型
	 * @param title 标题
	 * @return
	 */
	@RequestMapping("getRepInfos")
	@ResponseBody
	public Map<String,Object> getRepInfos(HttpServletRequest request,int page, int rows, String repType, String title){
		if(null == repType || repType.equals("")){
			repType = "type1";//默认查法律法规
		}
		JSONObject result = RepositoryManager.getRepInfos(page, rows, repType, title);
		Map<String,Object> res  = Maps.newHashMap();
		if(result.containsKey("status") && "200".equals(result.getString("status"))){
			res.put("rows", result.getJSONObject("content").getJSONArray("rbList"));
			res.put("total", result.getJSONObject("content").getInt("pageCnt"));
		}
		return res;
	}
	
	/**
	 * 根据Id查询知识库信息
	 * @param request
	 * @param id 知识id
	 * @param repType 知识类型
	 * @return
	 */
	@RequestMapping("getRepInfoById")
	@ResponseBody
	public Map<String,Object> getRepInfoById(HttpServletRequest request,String id,String repType){
		JSONObject result = RepositoryManager.getRepInfoById(id,repType);
		Map<String,Object> res  = Maps.newHashMap();
		res.put("code", 0);
		if(result.containsKey("status") && "200".equals(result.getString("status"))){
			res.put("code", 200);
			res.put("info", result.getJSONObject("content"));
		}
		return res;
	}
	
	/**
	 * 添加或者更新知识库信息
	 * @param request
	 * @param id 知识id
	 * @param content 知识内容
	 * @param type 知识类型
	 * @param title 标题
	 * @return
	 */
	@RequestMapping("addOrUpdate")
	@ResponseBody
	public Map<String,Object> addOrUpdate(HttpServletRequest request,String id, String type, String title, String content){
		SSOToken token = SSOHelper.getToken(request);
		long userId = token.getId();
		JSONObject result = RepositoryManager.addOrUpdateRepository(id, type, title, content, String.valueOf(userId));
		Map<String,Object> res  = Maps.newHashMap();
		res.put("code", 0);
		if(result.containsKey("status") && "200".equals(result.getString("status"))){
			//操作成功
			res.put("code", 200);
		}
		return res;
	}
	
	/**
	 * 跳转知识编辑新增页 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "repositoryAE", method = RequestMethod.GET)
	public String dictInfoAE(HttpServletRequest request, Long did){
		Map<String, String> repository = Maps.newHashMap();
		repository.put("id", "1");
		repository.put("title", "1");
		repository.put("content", "1");
		request.setAttribute("repository",repository);
		return "modules/sys/repository-form";
	}
	
	/**
	 * 添加知识意见
	 * @param request
	 * @return
	 */
	@RequestMapping("addrepSuggest")
	@ResponseBody
	public Map<String,Object> addrepSuggest(HttpServletRequest request,CmsOpinion cms){
		SSOToken token = SSOHelper.getToken(request);
		long userId = token.getId();
		cms.setCreator(Integer.valueOf(String.valueOf(userId)));
		Map<String,Object> res  = Maps.newHashMap();
		res.put("code", 200);
		try {
			cmsOpinionManager.save(cms);
		} catch (Exception e) {
			res.put("code", 0);
			res.put("msg", e.getMessage());
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * 跳转意见栏
	 * @param model
	 * @return
	 */
	@RequestMapping("suggests")
	public String suggests(Model model, HttpServletRequest request){
		return "modules/sys/suggest-list";
	}
	
	
	
	
	public String getCreaterName(Integer uid){
		if(null == uid || uid.equals("")){
			return "";
		}
		SysUser sysUser = sysUserManager.getById(uid.longValue()); 
		if(null != sysUser && !sysUser.equals("")){
			return sysUser.getRealname();
		}else{
			return "";
		}
		
	}
	/**
	 * 知识意见列表
	 * @param request
	 * @return
	 */
	@RequestMapping("getSuggests")
	@ResponseBody
	public Map<String,Object> getSuggests(HttpServletRequest request,int page , int rows){
		PageRequest<CmsOpinion> pr = new PageRequest<CmsOpinion>(new CmsOpinion());
		pr.setPageNo(page);
		pr.setPageSize(rows);
		Map<String,Object> res  = Maps.newHashMap();
		Page<CmsOpinion> pages = cmsOpinionManager.findByPageRequest(pr);
		List<CmsOpinion> cmsList = pages.getResult();
		for(CmsOpinion cmsop: cmsList){
			cmsop.setVarCreaterName(getCreaterName(cmsop.getCreator()));
		}
		res.put("rows", pages.getResult());
		res.put("total", pages.getTotalCount());
		return res;
	}
	
	

}
