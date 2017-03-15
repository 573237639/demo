package com.fabao.ledger.modules.tb.web;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fabao.ledger.common.utils.CommonField;
import com.fabaoframework.modules.web.BaseController;



@Controller
@RequestMapping("/tb/report")
public class ReportController extends BaseController {
	
	private static final Logger log = Logger.getLogger(ReportController.class);

	/**
	 * 查看话务报表
	 * @return
	 */
	@RequestMapping("/traffic")
	public String traffic(Model model){
		log.info("查看话务报表");
		model.addAttribute("GD_IVR_AGENT_COMPARE_HOUR", CommonField.GD_IVR_AGENT_COMPARE_HOUR);
		model.addAttribute("GD_IVR_COMPARE_HOUR", CommonField.GD_IVR_COMPARE_HOUR);
		model.addAttribute("GD_AGENT_COMPARE_HOUR", CommonField.GD_AGENT_COMPARE_HOUR);
		model.addAttribute("GD_ABANDONED", CommonField.GD_ABANDONED);
		return "modules/tb/report/traffic";
	}
	
	/**
	 * 查看人员管理报表
	 * @return
	 */	
	@RequestMapping("/usermanage")
	public String usermanage(Model model){
		log.info("查看人员管理报表");
		model.addAttribute("GD_AGENT_LOGIN", CommonField.GD_AGENT_LOGIN);
		model.addAttribute("GD_AGENT_LOGIN_DETAIL", CommonField.GD_AGENT_LOGIN_DETAIL);
		model.addAttribute("GD_AGENT_LOGIN_DAY", CommonField.GD_AGENT_LOGIN_DAY);
		return "modules/tb/report/usermanage";
	}
	
	/**
	 * 查看满意度报表
	 * @return
	 */	
	@RequestMapping("/satisfic")
	public String satisfic(Model model){
		log.info("查看满意度报表");
		model.addAttribute("GD_AGENT_SATISFACTION", CommonField.GD_AGENT_SATISFACTION);
		model.addAttribute("GD_CALL_SATISFACTION", CommonField.GD_CALL_SATISFACTION);
		return "modules/tb/report/satisfic";
	}
	
	/**
	 * 查看地市话务报表
	 * @return
	 */	
	@RequestMapping("/citytraffic")
	public String citytraffic(Model model){
		log.info("查看地市话务报表");
		model.addAttribute("GD_IVR_AGENT_COMPARE_CITY", CommonField.GD_IVR_AGENT_COMPARE_CITY);
		return "modules/tb/report/citytraffic";
	}
	
	/**
	 * 查看业务类话务报表
	 * @return
	 */	
	@RequestMapping("/businesstraffic")
	public String businesstraffic(Model model){
		log.info("查看业务类话务报表");
		model.addAttribute("GD_LAW_TYPE_CITY", CommonField.GD_LAW_TYPE_CITY);
		model.addAttribute("GD_LAW_TYPE_DAY", CommonField.GD_LAW_TYPE_DAY);
		model.addAttribute("GD_BUSINESS_LEVEL4", CommonField.GD_BUSINESS_LEVEL4);
		model.addAttribute("GD_BUSINESS_LEVEL4_DAY", CommonField.GD_BUSINESS_LEVEL4_DAY);
		return "modules/tb/report/businesstraffic";
	}

	
	
	
	
	

}
