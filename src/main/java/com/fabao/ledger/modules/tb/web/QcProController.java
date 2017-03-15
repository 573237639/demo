package com.fabao.ledger.modules.tb.web;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fabao.ledger.common.pojo.PoJoSet;
import com.fabao.ledger.common.utils.WebUtils;
import com.fabao.ledger.modules.sys.entity.SysSpecialty;
import com.fabao.ledger.modules.sys.entity.SysUser;
import com.fabao.ledger.modules.sys.service.SysDictManager;
import com.fabao.ledger.modules.tb.entity.TbClients;
import com.fabao.ledger.modules.tb.entity.TbQcCategory;
import com.fabao.ledger.modules.tb.entity.TbQcPro;
import com.fabao.ledger.modules.tb.service.TbQcCategoryManager;
import com.fabao.ledger.modules.tb.service.TbQcProManager;
import com.fabaoframework.modules.page.Page;
import com.fabaoframework.modules.page.PageRequest;
import com.fabaoframework.modules.web.BaseController;
import com.google.common.collect.Maps;

@Controller
@RequestMapping("/tb/qcpro")
public class QcProController extends BaseController {
	private static final Logger log = Logger.getLogger(QcProController.class);
	@Autowired
	private TbQcProManager tbQcProManager;
	@Autowired
	private TbQcCategoryManager tbQcCategoryManager;
	@Autowired
	private SysDictManager sysDictManager;
	/**
	 * 质检项目列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/qcproList")
	public String qcproList(Model model){
		 PoJoSet.setModelData(sysDictManager, model);
		return "modules/tb/qcpro/qcpro-list";
	}
	/**
	 * 质检项目列表数据获取
	 * @param page
	 * @param rows
	 * @param tbQcPro
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/qcproListData")
	public Map<String,Object> qcproListData(@RequestParam("page")int page ,@RequestParam("rows") int rows, TbQcPro tbQcPros){
		PageRequest<TbQcPro> pr = new PageRequest<TbQcPro>(tbQcPros);
		pr.setPageNo(page);
		pr.setPageSize(rows);
		Page<TbQcPro> pages = tbQcProManager.findByPageRequest(pr);
		Map<String,Object> res = Maps.newHashMap();
		List<TbQcPro> tbList = pages.getResult();
		for(TbQcPro tb : tbList){
				tb.setVacCategoryName(getQccategoryName(tb.getNumCategoryId()));
		}
		res.put("rows", tbList);
		res.put("total", pages.getTotalCount());
		return res;
	}
	//获取质检分类名称
	public String getQccategoryName(Integer cateId){
		if(null == cateId || cateId.equals("")){
			log.info("获取cateId="+cateId+"的分类名称");
			return "";
		}
		TbQcCategory tbQcCategory = tbQcCategoryManager.getById(cateId.longValue()); 
		if(null != tbQcCategory && !tbQcCategory.equals("")){
			return tbQcCategory.getVarCategoryName();
		}
		return null;
	}
	/** 
	 * 修改质检项目信息
	 * @param tbQcPro
	 */
	@ResponseBody
	@RequestMapping(value="/editQcPro/{id}",method=RequestMethod.POST)
	public Map<String,Object> editQcPro(TbQcPro tbQcPro){
		Map<String,Object> res = Maps.newHashMap();
		res.put("code", 0);
		try {
			if (null != tbQcPro) {
//				PoJoSet.getTbClientsByWrite(tbClient);
			}
			tbQcPro.setIsDeleted(tbQcPro.getIsDeleted() != null ? tbQcPro.getIsDeleted() : false);
			tbQcProManager.update(tbQcPro);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(WebUtils.getTrace(e));
			res.put("code", 1);
			res.put("message", "修改质检项目信息异常");
		}
		 return res;
	}
	
	/** 
	 * 新增质检项目信息
	 * @param tbQcPro
	 */
	@ResponseBody
	@RequestMapping(value="/addQcPro",method=RequestMethod.POST)
	public Map<String,Object> addQcPro(TbQcPro tbQcPro){
		Map<String,Object> res = Maps.newHashMap();
		res.put("code", 0);
		try {
			if (null != tbQcPro) {
//				PoJoSet.getTbClientsByWrite(tbClient);
			}
			tbQcProManager.save(tbQcPro);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(WebUtils.getTrace(e));
			res.put("code", 1);
			res.put("message", "保存质检项目异常");
		}
		 return res;
	}
	/**
	 * 质检项目删除 动作,逻辑删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/deleteQcPro")
	@ResponseBody
	public Map<String,Object> deleteQcPro(@RequestParam("id") String ids){
		Map<String,Object> res = Maps.newHashMap();
		res.put("code", 0);
		try {
			tbQcProManager.disableBat(ids);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(WebUtils.getTrace(e));
			res.put("code", 1);
			res.put("message", "删除质检项目信息异常");
		}
		return res;
	}
}
