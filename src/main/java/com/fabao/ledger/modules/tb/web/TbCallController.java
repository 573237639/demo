package com.fabao.ledger.modules.tb.web;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;
import com.fabao.ledger.common.pojo.PoJoSet;
import com.fabao.ledger.common.utils.WebUtils;
import com.fabao.ledger.modules.sys.entity.SysUser;
import com.fabao.ledger.modules.sys.service.SysUserManager;
import com.fabao.ledger.modules.tb.entity.TbCall;
import com.fabao.ledger.modules.tb.entity.TbClients;
import com.fabao.ledger.modules.tb.entity.TbQcCategory;
import com.fabao.ledger.modules.tb.entity.TbQcPro;
import com.fabao.ledger.modules.tb.service.TbCallManager;
import com.fabao.ledger.modules.tb.service.TbCallStayManager;
import com.fabao.ledger.modules.tb.service.TbQcCategoryManager;
import com.fabao.ledger.modules.tb.service.TbQcProManager;
import com.fabaoframework.modules.page.Page;
import com.fabaoframework.modules.page.PageRequest;
import com.fabaoframework.modules.web.BaseController;
import com.google.common.collect.Maps;

@Controller
@RequestMapping("/tb/callStay")
public class TbCallController extends BaseController {
	private static final Logger log = Logger.getLogger(TbCallController.class);
	@Autowired
	private TbCallManager tbCallManager;
	@Autowired
	private TbCallStayManager tbCallStayManager;
	@Autowired
	private TbQcProManager tbQcProManager;
	@Autowired
	private SysUserManager sysUserManager;
	/**
	 * 质检分类列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/tbcallList")
	public String tbcallList(Model model){
		return "modules/tb/callStay/call-list";
	}
	/**
	 * 质检分类列表数据获取
	 * @param page
	 * @param rows
	 * @param tbQcCategory
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/tbcallListData")
	public Map<String,Object> tbcallListData(@RequestParam("page")int page ,@RequestParam("rows") int rows, TbCall tbCall){
		PageRequest<TbCall> pr = new PageRequest<TbCall>(tbCall);
		pr.setPageNo(page);
		pr.setPageSize(rows);
		Page<TbCall> pages = tbCallManager.findByPageRequest(pr);
		Map<String,Object> res = Maps.newHashMap();
		res.put("rows", pages.getResult());
		res.put("total", pages.getTotalCount());
		return res;
	}
	
	@RequestMapping("/callStaySave")
	@ResponseBody
	public Map<String,Object> callStaySave(HttpServletRequest request,@RequestParam("serialArr[]") String serialArr,
			@RequestParam("proArr") String proArr,@RequestParam("summary") String summary,
			@RequestParam("improve") String improve,@RequestParam("comment") String comment,
			@RequestParam("memo") String memo,@RequestParam("scores") String scores){
		Map<String,Object> res = Maps.newHashMap();
		try {
		Map<String, String> dataMap = Maps.newHashMap() ;
		res.put("code", 0);
		dataMap.put("serialArr", serialArr);
		dataMap.put("proArr", proArr);
		dataMap.put("summary", summary);
		dataMap.put("improve", improve);
		dataMap.put("comment", comment);
		dataMap.put("memo", memo);
		dataMap.put("scores", scores);
		//获取配置表的总分比例  如果未配置就取默认配置
//		Float proportion = !Util.isEmpty(SysManager.getConfig("points_proportion"))?Float.valueOf(SysManager.getConfig("points_proportion").toString()):points;
//		dataMap.put("proportion", proportion.toString());
		//获取缓存质检项目 累计所有项目积分
		List<TbQcPro> qcProList = tbQcProManager.getAll();
		JSONArray jsonArr = new JSONArray(dataMap.get("proArr"));
		int totalScore =0;
		for (int j = 0; j < jsonArr.length(); j++) {
			JSONObject jsonObj = jsonArr.getJSONObject(j);
			for (int i = 0; i < qcProList.size(); i++) {
				if(qcProList.get(i).getId().equals(jsonObj.getLong("pro_id"))){
					totalScore += (null != qcProList.get(i).getNumQcproScore() && !qcProList.get(i).getNumQcproScore().equals("")) ? qcProList.get(i).getNumQcproScore():0;
				}
			}
		}
		dataMap.put("totalScore", Integer.valueOf(totalScore).toString());
		//获取律师姓名
		Map<String, String> map = sysUserManager.getMapByAll(null);
		//获取用户对象
		SSOToken token = SSOHelper.getToken(request);
		SysUser sysUser = sysUserManager.getById(token.getId());
		dataMap.put("hwNum", sysUser.getHwNum());
		dataMap.put("userName", sysUser.getRealname());
		tbCallManager.addQCBaseAll(dataMap,map);
		} catch (Exception e) {
			logger.error("质检录入失败", e);
			res.put("code", 1);
			res.put("message", "工单信息保存失败");
		}
		return res;
	}
	
	
}
