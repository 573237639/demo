package com.fabao.ledger.modules.cms.web;

import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fabao.ledger.common.utils.CommonField;
import com.fabao.ledger.common.utils.WebUtils;
import com.fabao.ledger.modules.cms.entity.CmsOrganization;
import com.fabao.ledger.modules.cms.service.CmsOrganizationManager;
import com.fabao.ledger.modules.sms.service.SmsCityCodeManager;
import com.fabao.ledger.modules.sys.service.SysDictManager;
import com.fabaoframework.modules.page.Page;
import com.fabaoframework.modules.page.PageRequest;
import com.fabaoframework.modules.web.BaseController;
import com.google.common.collect.Maps;


@Controller
@RequestMapping("/cms/organization")
public class OrganizationController extends BaseController {
	private static final Logger log = Logger.getLogger(OrganizationController.class);
	@Autowired
	private CmsOrganizationManager cmsOrganizationManager;
	@Autowired
	private SmsCityCodeManager smsCityCodeManager;
	@Autowired
	private SysDictManager sysDictManager;
	/**
	 * 机构列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/organizationList")
	public String organizationList(Model model){
		model.addAttribute("orgTypeList", JSONArray.fromObject(sysDictManager.getListByType(CommonField.ORG_LAW_TYPE)));
		return "modules/cms/organization/organization-list";
	}
	/**
	 * 机构列表数据获取
	 * @param page
	 * @param rows
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/organizationListData")
	public Map<String,Object> organizationListData(@RequestParam("page")int page ,@RequestParam("rows") int rows, CmsOrganization cmsOrganization){
		cmsOrganization.setIsDeleted(cmsOrganization.getIsDeleted() != null ? cmsOrganization.getIsDeleted() : false);//默认查有效的
		if(null != cmsOrganization.getVacCityName() && !cmsOrganization.getVacCityName().equals("")){
			cmsOrganization.setVacOrgName(cmsOrganization.getVacCityName());
		}
		PageRequest<CmsOrganization> pr = new PageRequest<CmsOrganization>(cmsOrganization);
		pr.setPageNo(page);
		pr.setPageSize(rows);
		Page<CmsOrganization> pages = cmsOrganizationManager.findByPageRequest(pr);
		Map<String,Object> res = Maps.newHashMap();
//		List<CmsOrganization> cmsList = pages.getResult();
//		for(CmsOrganization cmsOrg: cmsList){
//			try {
//				if(cmsOrg.get() != null){
//					tb.setVacClientCityName(getCityNameById(tb.getNumClientCityId().toString()));
//				}
//			} catch (Exception e) {
//				log.error("获取包含地市的机构名称异常："+e.getMessage());
//			}
//		}
		res.put("rows", pages.getResult());
		res.put("total", pages.getTotalCount());
		return res;
	}
	/** 
	 * 修改机构信息
	 * @param cmsOrganization
	 */
	@ResponseBody
	@RequestMapping(value="/editCmsOrganization/{id}",method=RequestMethod.POST)
	public Map<String,Object> editCmsOrganization(CmsOrganization cmsOrg){
		Map<String,Object> res = Maps.newHashMap();
		res.put("code", 0);
		try {
			if (null != cmsOrg) {
//				PoJoSet.getTbClientsByWrite(cmsOrganization);
			}
			cmsOrg.setIsDeleted(cmsOrg.getIsDeleted() != null ? cmsOrg.getIsDeleted() : false);
			cmsOrganizationManager.update(cmsOrg);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(WebUtils.getTrace(e));
			res.put("code", 1);
			res.put("message", "修改机构信息异常");
		}
		 return res;
	}
	
	/** 
	 * 新增机构信息
	 * @param cmsOrganization
	 */
	@ResponseBody
	@RequestMapping(value="/addCmsOrganization",method=RequestMethod.POST)
	public Map<String,Object> addCmsOrganization(CmsOrganization cmsOrg){
		Map<String,Object> res = Maps.newHashMap();
		res.put("code", 0);
		try {
			if (null != cmsOrg) {
//				PoJoSet.getTbClientsByWrite(tbClient);
			}
			cmsOrganizationManager.save(cmsOrg);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(WebUtils.getTrace(e));
			res.put("code", 1);
			res.put("message", "保存机构信息异常");
		}
		 return res;
	}
	/**
	 * 机构删除 动作,逻辑删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/deleteCmsOrganization")
	@ResponseBody
	public Map<String,Object> deleteCmsOrganization(@RequestParam("id") String ids){
		Map<String,Object> res = Maps.newHashMap();
		res.put("code", 0);
		try {
			cmsOrganizationManager.disableBat(ids);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(WebUtils.getTrace(e));
			res.put("code", 1);
			res.put("message", "删除机构信息异常");
		}
		return res;
	}
	
}
