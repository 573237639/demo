package com.fabao.ledger.modules.sys.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;
import com.fabao.ledger.common.utils.CommonField;
import com.fabao.ledger.modules.sys.util.DateTimeUtils;
import com.fabao.ledger.modules.tb.service.TbLedgerManager;
import com.fabao.ledger.modules.tb.service.TbOrdersManager;

@Controller
@RequestMapping("/sys/portal")
public class PortalController {
	private final static Logger log = Logger.getLogger(PortalController.class);
	@Autowired
	private TbLedgerManager tbLedgerManager;
	@Autowired
	private TbOrdersManager tbOrdersManager;
	
	@RequestMapping("index")
	public String index(HttpServletRequest request,Model model){
		SSOToken token = SSOHelper.getToken(request);
		Map<String, Object> param1 = new HashMap<String, Object>();
		param1.put("numLedgerStatus", CommonField.LEDGER_STATUS_FILL);
		param1.put("isDeleted", false);
		param1.put("creator", token.getId());
		Map<String, Object> param2 = new HashMap<String, Object>();
		param2.put("creator", token.getId());
		param2.put("isDeleted", false);
		param2.put("numOrderStatus", CommonField.ORDER_STATUS_AUDIT);
		param2.put("numOrderUserId", token.getId());
		//待填写台帐
		try {
			model.addAttribute("fill", tbLedgerManager.findCountByPageRequestAndEntity(param1));
		} catch (Exception e) {
			log.error("获待填写台账数异常"+e.getMessage());
		}
		//待审核工单
		model.addAttribute("audit", tbOrdersManager.findCountByPageRequestAndEntity(param2));
		//日有效台帐总生成量（登陆人）
		Map<String, Object> paramLedger = new HashMap<String, Object>();
		paramLedger.put("creator", token.getId());
		paramLedger.put("callTimeStart", DateTimeUtils.getTodayStart());
		paramLedger.put("callTimeEnd", DateTimeUtils.getTodayEnd());
		//历史来电数
		try {
			model.addAttribute("adminCount", tbLedgerManager.findCountByPageRequestAndEntity(paramLedger));
		} catch (Exception e) {
			log.error("获取登录人历史来电数异常"+e.getMessage());
		}
		//日有效台帐总生成量（系统）
		Map<String, Object> paramSystem = new HashMap<String, Object>();
		paramSystem.put("callTimeStart", DateTimeUtils.getTodayStart());
		paramSystem.put("callTimeEnd", DateTimeUtils.getTodayEnd());
		//历史来电数
		try {
			model.addAttribute("systemCount", tbLedgerManager.findCountByPageRequestAndEntity(paramSystem));
		} catch (Exception e) {
			log.error("获取系统历史来电数异常"+e.getMessage());
		}
		return "modules/sys/portal/index";
	}
	
	@RequestMapping("about")
	public String about(){
		return "modules/sys/portal/about";
	}
	
	@RequestMapping("about2")
	public String about2(){
		return "modules/sys/portal/about2";
	}
	
	@RequestMapping("link")
	public String link(){
		return "modules/sys/portal/link";
	}
	
	@RequestMapping("qun")
	public String qun(){
		return "modules/sys/portal/qun";
	}
	
	@RequestMapping("repair")
	public String repair(){
		return "modules/sys/portal/repair";
	}
	
	@RequestMapping("seq")
	public String seq(){
		return "modules/sys/portal/seq";
	}
}
