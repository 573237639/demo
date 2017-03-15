package com.fabao.ledger.modules.tb.web;


import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fabao.ledger.modules.tb.entity.TbQcBase;
import com.fabao.ledger.modules.tb.service.TbQcBaseManager;
import com.fabaoframework.modules.page.Page;
import com.fabaoframework.modules.page.PageRequest;
import com.fabaoframework.modules.web.BaseController;
import com.google.common.collect.Maps;

@Controller
@RequestMapping("/tb/qcbase")
public class QcbaseController extends BaseController {
	private static final Logger log = Logger.getLogger(QcbaseController.class);
	@Autowired
	private TbQcBaseManager tbQcBaseManager;
	/**
	 * 质检列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/qcbaseList")
	public String clientList(Model model){
		return "modules/tb/qcbase/qcbase-list";
	}
	/**
	 * 质检列表数据获取
	 * @param page
	 * @param rows
	 * @param tbQcPro
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/qcbaseListData")
	public Map<String,Object> qcproListData(@RequestParam("page")int page ,@RequestParam("rows") int rows, TbQcBase tbQcBase){
		tbQcBase.setIsDeleted(tbQcBase.getIsDeleted() != null ? tbQcBase.getIsDeleted() : false);//默认查有效的
		PageRequest<TbQcBase> pr = new PageRequest<TbQcBase>(tbQcBase);
		pr.setPageNo(page);
		pr.setPageSize(rows);
		Page<TbQcBase> pages = tbQcBaseManager.findByPageRequest(pr);
		Map<String,Object> res = Maps.newHashMap();
		res.put("rows", pages.getResult());
		res.put("total", pages.getTotalCount());
		return res;
	}
}
