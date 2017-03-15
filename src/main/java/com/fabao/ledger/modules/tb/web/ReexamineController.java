package com.fabao.ledger.modules.tb.web;

import java.util.Map;




import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fabao.ledger.common.utils.WebUtils;
import com.fabao.ledger.modules.tb.entity.TbReexamine;
import com.fabao.ledger.modules.tb.service.TbReexamineManager;
import com.fabaoframework.modules.page.Page;
import com.fabaoframework.modules.page.PageRequest;
import com.fabaoframework.modules.web.BaseController;
import com.google.common.collect.Maps;

@Controller
@RequestMapping("/tb/reexamine")
public class ReexamineController extends BaseController {
		private static final Logger log = Logger.getLogger(ReexamineController.class);

		@Autowired
		private TbReexamineManager tbReexamineManager;
		
		@RequestMapping("/reexamine-list")
		public String orderReexamineList(){
			return "modules/tb/order/order-reexamine-list";
		}
		//工单列表
		@ResponseBody
		@RequestMapping("/tbReexamineListDataGrid")
		public Map<String,Object> tbReexamineListDataGrid(@RequestParam("page") int page ,@RequestParam("rows") int rows,TbReexamine tbReexamine){
			//罗列查询条件
			try {
			PageRequest<TbReexamine> pr = new PageRequest<TbReexamine>(tbReexamine);
			pr.setPageNo(page);
			pr.setPageSize(rows);
			Map<String,Object> res  = Maps.newHashMap();
			Page<TbReexamine> pages = tbReexamineManager.findByPageRequest(pr);
			res.put("rows", pages.getResult());
			res.put("total", pages.getTotalCount());
			return res;
			} catch (Exception e) {
				e.printStackTrace();
				log.error(WebUtils.getTrace(e));
			}
			return null;
		}

}
