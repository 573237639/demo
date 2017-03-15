package com.fabao.ledger.modules.tb.web;


import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fabao.ledger.common.pojo.PoJoSet;
import com.fabao.ledger.common.utils.WebUtils;
import com.fabao.ledger.modules.tb.entity.TbClients;
import com.fabao.ledger.modules.tb.entity.TbQcCategory;
import com.fabao.ledger.modules.tb.entity.TbQcPro;
import com.fabao.ledger.modules.tb.service.TbQcCategoryManager;
import com.fabaoframework.modules.page.Page;
import com.fabaoframework.modules.page.PageRequest;
import com.fabaoframework.modules.web.BaseController;
import com.google.common.collect.Maps;

@Controller
@RequestMapping("/tb/callStay")
public class CallStayController extends BaseController {
	private static final Logger log = Logger.getLogger(CallStayController.class);
	@Autowired
	private TbQcCategoryManager tbQcCategoryManager;
	/**
	 * 质检分类列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/qccategoryList")
	public String qccategoryList(Model model){
		return "modules/tb/callStay/callStay-list";
	}
	/**
	 * 质检分类列表数据获取
	 * @param page
	 * @param rows
	 * @param tbQcCategory
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/qccategoryListData")
	public Map<String,Object> qccategoryListData(@RequestParam("page")int page ,@RequestParam("rows") int rows, TbQcCategory tbQcCategory){
		tbQcCategory.setIsDeleted(tbQcCategory.getIsDeleted() != null ? tbQcCategory.getIsDeleted() : false);//默认查有效的
		PageRequest<TbQcCategory> pr = new PageRequest<TbQcCategory>(tbQcCategory);
		pr.setPageNo(page);
		pr.setPageSize(rows);
		Page<TbQcCategory> pages = tbQcCategoryManager.findByPageRequest(pr);
		Map<String,Object> res = Maps.newHashMap();
		res.put("rows", pages.getResult());
		res.put("total", pages.getTotalCount());
		return res;
	}
	/** 
	 * 修改质检分类信息
	 * @param tbQcCategory
	 */
	@ResponseBody
	@RequestMapping(value="/editQccategory/{id}",method=RequestMethod.POST)
	public Map<String,Object> editQccategory(TbQcCategory tbQcCategory){
		Map<String,Object> res = Maps.newHashMap();
		res.put("code", 0);
		try {
			if (null != tbQcCategory) {
//				PoJoSet.getTbClientsByWrite(tbClient);
			}
			tbQcCategory.setIsDeleted(tbQcCategory.getIsDeleted() != null ? tbQcCategory.getIsDeleted() : false);
			tbQcCategoryManager.update(tbQcCategory);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(WebUtils.getTrace(e));
			res.put("code", 1);
			res.put("message", "修改质检分类信息异常");
		}
		 return res;
	}
	
	/** 
	 * 新增质检分类信息
	 * @param tbQcCategory
	 */
	@ResponseBody
	@RequestMapping(value="/addQccategory",method=RequestMethod.POST)
	public Map<String,Object> addQccategory(TbQcCategory tbQcCategory){
		Map<String,Object> res = Maps.newHashMap();
		res.put("code", 0);
		try {
			if (null != tbQcCategory) {
//				PoJoSet.getTbClientsByWrite(tbClient);
			}
			tbQcCategoryManager.save(tbQcCategory);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(WebUtils.getTrace(e));
			res.put("code", 1);
			res.put("message", "保存质检分类异常");
		}
		 return res;
	}
	/**
	 * 质检分类删除 动作,逻辑删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/deleteQccategory")
	@ResponseBody
	public Map<String,Object> deleteQccategory(@RequestParam("id") String ids){
		Map<String,Object> res = Maps.newHashMap();
		res.put("code", 0);
		try {
			tbQcCategoryManager.disableBat(ids);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(WebUtils.getTrace(e));
			res.put("code", 1);
			res.put("message", "删除质检分类信息异常");
		}
		return res;
	}
	/**
	 *质检分类列表
	 *@param id 
	 */
	@ResponseBody
	@RequestMapping(value="/findCategory",method=RequestMethod.POST)	
	public JSONArray findCategory(){
		List<Map<String, Object>> list = tbQcCategoryManager.getCategoryList();
		JSONArray vactgoryJson = JSONArray.fromObject(list);
		return vactgoryJson;
	}
}
