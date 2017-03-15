package com.fabao.ledger.modules.tb.web;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;
import com.fabao.ledger.common.pojo.PoJoSet;
import com.fabao.ledger.common.utils.CommonField;
import com.fabao.ledger.common.utils.WebUtils;
import com.fabao.ledger.modules.oracle.service.OracleManager;
import com.fabao.ledger.modules.sms.entity.SmsCityCode;
import com.fabao.ledger.modules.sms.entity.SmsMobileArea;
import com.fabao.ledger.modules.sms.entity.SmsOperatorCode;
import com.fabao.ledger.modules.sms.entity.SmsProvinceCode;
import com.fabao.ledger.modules.sms.service.SmsCityCodeManager;
import com.fabao.ledger.modules.sms.service.SmsMobileAreaManager;
import com.fabao.ledger.modules.sms.service.SmsOperatorCodeManager;
import com.fabao.ledger.modules.sms.service.SmsProvinceCodeManager;
import com.fabao.ledger.modules.sys.entity.SysUser;
import com.fabao.ledger.modules.sys.service.SysDictManager;
import com.fabao.ledger.modules.sys.service.SysUserManager;
import com.fabao.ledger.modules.tb.entity.TbClients;
import com.fabao.ledger.modules.tb.entity.TbLedger;
import com.fabao.ledger.modules.tb.entity.TbOrders;
import com.fabao.ledger.modules.tb.entity.TbSupplement;
import com.fabao.ledger.modules.tb.service.TbClientsManager;
import com.fabao.ledger.modules.tb.service.TbLedgerManager;
import com.fabao.ledger.modules.tb.service.TbOrdersManager;
import com.fabao.ledger.modules.tb.service.TbSupplementManager;
import com.fabaoframework.modules.web.BaseController;
import com.google.common.collect.Maps;


@Controller
@RequestMapping("/tb/call")
public class CallController extends BaseController {
	
	private static final Logger log = Logger.getLogger(CallController.class);
	
	@Autowired
	private TbClientsManager tbClientsManager;
	
	@Autowired
	private TbLedgerManager tbLedgerManager;
	
	@Autowired
	private TbOrdersManager tbOrdersManager;
	
	@Autowired
	private SysDictManager sysDictManager;
	
	@Autowired
	private SmsProvinceCodeManager provinceCodeManager;
	
	@Autowired
	private SmsCityCodeManager cityCodeManager;
	
	@Autowired
	private SmsMobileAreaManager mobileAreaManager;
	
	@Autowired
	private SmsOperatorCodeManager operatorCodeManager;
	
	@Autowired
	private TbSupplementManager tbSupplementManager;
	
	@Autowired
	private SysUserManager sysUserManager; 
	
	@RequestMapping("/test")
	public String test(){
		return "modules/tb/call/test";
	}
	
	@RequestMapping("/win")
	public String win(Model model,@RequestParam("number") String number){
		//获取来电号码
		model.addAttribute("number", number);
		return "modules/tb/call/win";
	}
	
	@RequestMapping("/call-index")
	public String index(Model model,HttpServletRequest request,@RequestParam("number") String mobile,@RequestParam("callid") String callid,@RequestParam("pSkill") String pSkill){
		//----------------客户信息
		//根据mobile获取客户信息
		TbClients tbClients = null;
		try {
			tbClients = tbClientsManager.getTbClientsByMobile(mobile);
		} catch (Exception e) {
			log.error("获取客户信息异常"+WebUtils.getTrace(e));
		} 
		if(tbClients.getId() == null){
			try {
				tbClients.setVacClientNumber(mobile);
				tbClientsManager.save(tbClients);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(WebUtils.getTrace(e));
			}
		}
		//来电获取相关信息
		
		//通过流水号判断,如果已存在
		TbLedger tbLedger = new TbLedger();
		//为了保证一通电话一个台账
		//1、流水号：
		tbLedger.setVacLedgerSerial(callid);
		//2.来电号码
		tbLedger.setVacLedgerNumber(mobile);
		//3.来电归属
		findNumberInfo(mobile, tbLedger);
		//4.来电队列
		if(pSkill.equals("0")){
			tbLedger.setNumLedgerCallQueue(CommonField.PSKILL_QUEUE_0);
		}else{
			tbLedger.setNumLedgerCallQueue(pSkill);
		}
		//获取用户对象
		SSOToken token = SSOHelper.getToken(request);
		SysUser sysUser = sysUserManager.getById(token.getId());
		//5.话务员工号
		tbLedger.setVacLedgerAgentCode(sysUser.getHwNum());
		//6.话务员姓名
		tbLedger.setVacLedgerAgentName(sysUser.getRealname());
		tbLedger.setNumClientId(tbClients.getId().intValue());
		tbLedger.setNumLedgerStatus(CommonField.LEDGER_STATUS_FILL);//第一次来电状态为待填写
		//1.通过number来获取历史台帐信息
		TbLedger historytbLedger = null;
		try {
			historytbLedger = tbLedgerManager.getTbLedgerByNumber(mobile);
		} catch (Exception e1) {
			log.error("获取台账信息异常"+WebUtils.getTrace(e1));
		}
		if(historytbLedger != null){
			//获取一个事发地址
			tbLedger.setNumLedgerIncidentPorvinceId(historytbLedger.getNumLedgerIncidentPorvinceId());
			tbLedger.setNumLedgerIncidentCityId(historytbLedger.getNumLedgerIncidentCityId());
			tbLedger.setVacLedgerIncidentAddress(historytbLedger.getVacLedgerIncidentAddress());
			//获取一个法律类型
			tbLedger.setNumSpecialtyId(historytbLedger.getNumSpecialtyId());
		}
		//置空业务类型 客户自述 处理意见 处理情况
		tbLedger.setVacLedgerBusinessType(null);
		tbLedger.setVacLedgerClientAccount(null);
		tbLedger.setVacLedgerLawyerSuggestion(null);
		//初始化处理情况
		tbLedger.setNumLedgerHandle(1);//默认办结归档
		tbLedgerManager.save(tbLedger);
		model.addAttribute("ledgerId", tbLedger.getId());
		return "modules/tb/call/call-index";
	}
	
	/**
	 * 
	 * @param request
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/call-detail")
	public String detail(Model model,HttpServletRequest request,@RequestParam("number") String mobile,@RequestParam("callid") String callid,@RequestParam("pSkill") String pSkill){
		log.info("号码【"+mobile+"】，通话id【"+callid+"】,技能组为【"+pSkill+"】，获取弹框信息开始");
		log.info("根据mobile【"+mobile+"】获取客户信息start");
		//根据mobile获取客户信息
		TbClients tbClients = null;
		try {
			tbClients = tbClientsManager.getTbClientsByMobile(mobile);
		} catch (Exception e) {
			log.error("获取客户信息异常"+WebUtils.getTrace(e));
		} 
		if(tbClients.getId() == null){
			try {
				tbClients.setVacClientNumber(mobile);
				tbClientsManager.save(tbClients);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(WebUtils.getTrace(e));
			}
		}
		log.info("根据mobile【"+mobile+"】获取客户信息end");
		//来电获取相关信息
		log.info("新建台账信息srart");
		//通过流水号判断,如果已存在
		TbLedger tbLedger = new TbLedger();
		//为了保证一通电话一个台账
		//1、流水号：
		tbLedger.setVacLedgerSerial(callid);
		//2.来电号码
		tbLedger.setVacLedgerNumber(mobile);
		//3.来电归属
		findNumberInfo(mobile, tbLedger);
		//4.来电队列
		if(pSkill.equals("0") || pSkill.equals("undefined")){
			tbLedger.setNumLedgerCallQueue(CommonField.PSKILL_QUEUE_0);
		}else{
			tbLedger.setNumLedgerCallQueue(pSkill);
		}
		//获取用户对象
		SSOToken token = SSOHelper.getToken(request);
		SysUser sysUser = sysUserManager.getById(token.getId());
		//5.话务员工号
		tbLedger.setVacLedgerAgentCode(sysUser.getHwNum());
		//6.话务员姓名
		tbLedger.setVacLedgerAgentName(sysUser.getRealname());
		tbLedger.setNumClientId(tbClients.getId().intValue());
		tbLedger.setNumLedgerStatus(CommonField.LEDGER_STATUS_FILL);//第一次来电状态为待填写
		//1.通过number来获取历史台帐信息
		log.info("通过number【"+mobile+"】来获取历史台帐信息srart");
		TbLedger historytbLedger = null;
		try {
			historytbLedger = tbLedgerManager.getTbLedgerByNumber(mobile);
		} catch (Exception e1) {
			log.error("获取台账信息异常"+WebUtils.getTrace(e1));
		}
		log.info("通过number【"+mobile+"】来获取历史台帐信息end");
		if(historytbLedger != null){
			//获取一个事发地址
			tbLedger.setNumLedgerIncidentPorvinceId(historytbLedger.getNumLedgerIncidentPorvinceId());
			tbLedger.setNumLedgerIncidentCityId(historytbLedger.getNumLedgerIncidentCityId());
			tbLedger.setVacLedgerIncidentAddress(historytbLedger.getVacLedgerIncidentAddress());
			//获取一个法律类型
			tbLedger.setNumSpecialtyId(historytbLedger.getNumSpecialtyId());
		}
		//置空业务类型 客户自述 处理意见 处理情况
		tbLedger.setVacLedgerBusinessType(null);
		tbLedger.setVacLedgerClientAccount(null);
		tbLedger.setVacLedgerLawyerSuggestion(null);
		//初始化处理情况
		tbLedger.setNumLedgerHandle(1);//默认办结归档
		tbLedgerManager.save(tbLedger);
		log.info("新建台账信息end");
		model.addAttribute("tbClients", tbClients);
		model.addAttribute("tbLedger", tbLedger);
//		model.addAttribute("t", t);//来源，0是来电 1是手动应急
		//一次性查询所有,避免多次与数据库交互
		PoJoSet.setModelData(sysDictManager, model);
		log.info("号码【"+mobile+"】，通话id【"+callid+"】,技能组为【"+pSkill+"】，获取弹框信息结束");
		return "modules/tb/call/call-detail";
	}
	
	@RequestMapping(value="/new-ledger/{number}",method=RequestMethod.GET)
	public String newLedger(HttpServletRequest request,@PathVariable("number") String mobile,Model model){
		//根据mobile获取客户信息
		TbClients tbClients = null;
		try {
			tbClients = tbClientsManager.getTbClientsByMobile(mobile);
		} catch (Exception e) {
			log.error("获取客户信息异常"+WebUtils.getTrace(e));
		} 
		if(tbClients.getId() == null){
			try {
				tbClients.setVacClientNumber(mobile);
				tbClientsManager.save(tbClients);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(WebUtils.getTrace(e));
			}
		}
		//来电获取相关信息
		//通过流水号判断,如果已存在
		TbLedger tbLedger = new TbLedger();
		//为了保证一通电话一个台账
		//1、流水号：
		tbLedger.setVacLedgerSerial("2017010123456789");
		//2.来电号码
		tbLedger.setVacLedgerNumber(mobile);
		//3.来电归属
		findNumberInfo(mobile, tbLedger);
		//4.来电队列
		tbLedger.setNumLedgerCallQueue("应急新增");
		//获取用户对象
		SSOToken token = SSOHelper.getToken(request);
		SysUser sysUser = sysUserManager.getById(token.getId());
		//5.话务员工号
		tbLedger.setVacLedgerAgentCode(sysUser.getHwNum());
		//6.话务员姓名
		tbLedger.setVacLedgerAgentName(sysUser.getRealname());
		tbLedger.setNumClientId(tbClients.getId().intValue());
		tbLedger.setNumLedgerStatus(CommonField.LEDGER_STATUS_FILL);//第一次来电状态为待填写
		//1.通过number来获取历史台帐信息
		TbLedger historytbLedger = null;
		try {
			historytbLedger = tbLedgerManager.getTbLedgerByNumber(mobile);
		} catch (Exception e1) {
			log.error("获取台账信息异常"+WebUtils.getTrace(e1));
		}
		if(historytbLedger != null){
			//获取一个事发地址
			tbLedger.setNumLedgerIncidentPorvinceId(historytbLedger.getNumLedgerIncidentPorvinceId());
			tbLedger.setNumLedgerIncidentCityId(historytbLedger.getNumLedgerIncidentCityId());
			tbLedger.setVacLedgerIncidentAddress(historytbLedger.getVacLedgerIncidentAddress());
			//获取一个法律类型
			tbLedger.setNumSpecialtyId(historytbLedger.getNumSpecialtyId());
		}
		//置空业务类型 客户自述 处理意见 处理情况
		tbLedger.setVacLedgerBusinessType(null);
		tbLedger.setVacLedgerClientAccount(null);
		tbLedger.setVacLedgerLawyerSuggestion(null);
		//初始化处理情况
		tbLedger.setNumLedgerHandle(1);//默认办结归档
		tbLedgerManager.save(tbLedger);

		model.addAttribute("id", tbLedger.getId());
		model.addAttribute("status", tbLedger.getNumLedgerStatus());
		return "modules/tb/ledger/ledger-edit-index";
	}
	

	@RequestMapping("call-query")
	public String ledgerCallDetail(@RequestParam("ledgerId") Long tbLedgerId,Model model){
		
		TbLedger tbLedger = new TbLedger();
		tbLedger = tbLedgerManager.getById(tbLedgerId);
		TbClients tbClients = null;
		try {
			tbClients = tbClientsManager.getTbClientsByMobile(tbLedger.getVacLedgerNumber());
		} catch (Exception e) {
			log.error("获取客户信息异常"+WebUtils.getTrace(e));
		} 
		model.addAttribute("tbClients", tbClients);
		model.addAttribute("tbLedger", tbLedger);
		PoJoSet.setModelData(sysDictManager, model);
		return "modules/tb/call/call-query";
	}

	@RequestMapping("call-ledgerlist")
	public String historyCall(Model model){
		return "modules/tb/call/call-ledgerlist";
	}

	@RequestMapping("/callLedger-datagrid")
	@ResponseBody
	public Map<String,Object> historyCallDataGrid(@RequestParam("page") int page ,@RequestParam("rows") int rows,TbLedger tbLedger){
		try {
			return tbLedgerManager.getHistoryLedger(page, rows, tbLedger);
		} catch (Exception e) {
			log.error("获取历史来电异常"+WebUtils.getTrace(e));
		}
		return null;
	}
	
	@RequestMapping("call-order/{id}")
	public String callOrder(HttpServletRequest request,@PathVariable("id") Long id,Model model){
		//根据台帐id查询工单信息和客户信息
		TbLedger tbLedger = tbLedgerManager.getById(id);
		TbClients tbClients = tbClientsManager.getById(tbLedger.getNumClientId().longValue());
		if(null != tbClients){
			PoJoSet.getTbClientsByRead(tbClients);
		}
		TbOrders tbOrders = new TbOrders();
		tbOrders.setVacOrderSerial(tbLedger.getVacLedgerSerial());//流水号
		tbOrders.setVacOrderNumber(tbLedger.getVacLedgerNumber());//来电号码
		SSOToken token = SSOHelper.getToken(request);
		SysUser sysUser = sysUserManager.getById(token.getId());
		if(null != sysUser){
			tbOrders.setVacOrderAgentCode(sysUser.getHwNum());//制单员工号
			tbOrders.setVacOrderAgentName(sysUser.getRealname());//制单员姓名
		}
		//业务类型
		tbOrders.setVacOrderBusinessType(tbLedger.getVacLedgerBusinessType());
		//常住地址
		tbOrders.setNumOrderContactProvinceId(tbClients.getNumClientProvinceId());
		tbOrders.setNumOrderContactCityId(tbClients.getNumClientCityId());
		tbOrders.setVacOrderContactAddress(tbClients.getVacClientAddress());
		//联系地址(同常住)
		//事发地址
		tbOrders.setNumOrderIncidentProvinceId(tbLedger.getNumLedgerIncidentPorvinceId());
		tbOrders.setNumOrderIncidentCityId(tbLedger.getNumLedgerIncidentCityId());
		tbOrders.setVacOrderIncidentAddress(tbLedger.getVacLedgerIncidentAddress());
		//客户自述
		tbOrders.setVacOrderAccount(tbLedger.getVacLedgerClientAccount());
		//处理意见
		tbOrders.setVacOrderSuggestion(tbLedger.getVacLedgerLawyerSuggestion());
		//录音文件
		tbOrders.setFileName(OracleManager.getFilNameByCallId(tbLedger.getVacLedgerSerial(),tbLedger.getVacLedgerAgentCode()));
		
		model.addAttribute("tbClients", tbClients);
		model.addAttribute("tbOrders", tbOrders);
		PoJoSet.setModelData(sysDictManager, model);
		return "modules/tb/call/call-order";
	}
	
	
	@RequestMapping("call-orderlist")
	public String callOrderList(Model model){
		PoJoSet.setModelData(sysDictManager, model);
		return "modules/tb/call/call-orderlist";
	}

	@RequestMapping("/callorder-datagrid")
	@ResponseBody
	public Map<String,Object> callOrderDataGrid(@RequestParam("page") int page ,@RequestParam("rows") int rows,TbOrders tbOrders){
		tbOrders.setIsDeleted(false);
		try {
			return tbOrdersManager.tbOrdersList(page, rows,tbOrders);	
		} catch (Exception e) {
			log.error("获取工单集合异常"+WebUtils.getTrace(e));
		}
		return null;
	}
	
	@RequestMapping(value="/saveCallOrder",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> saveCallOrder(TbOrders tbOrders){
		Map<String,Object> res = Maps.newHashMap();
		res.put("code", 0);
		try {
			tbOrdersManager.saveCallOrder(tbOrders);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(WebUtils.getTrace(e));
			res.put("code", 1);
			res.put("message", "工单信息保存失败");
		}
		return res;
	}

	@RequestMapping(value="/saveCallLedgerByFail")
	@ResponseBody
	public Map<String,Object> saveCallLedgerByFail(TbLedger tbLedger,TbClients tbClients){
		Map<String,Object> res = Maps.newHashMap();
		res.put("code", 0);
		PoJoSet.getTbClientsByWrite(tbClients);
		tbClients.setVacClientNumber(tbLedger.getVacLedgerNumber());//将号码赋值
		try {
			tbClientsManager.update(tbClients);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(WebUtils.getTrace(e));
			res.put("code", 1);
			res.put("message", "客户信息保存错误");
		}
		try {
			tbLedgerManager.update(tbLedger);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(WebUtils.getTrace(e));
			res.put("code", 1);
			res.put("message", "台帐时间保存错误");
		}
		
		return res;
	}
	
	
	@RequestMapping(value="/saveCallLedger/{ledgerStatus}",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> saveCallLedger(@PathVariable("ledgerStatus") int ledgerStatus, @Valid  TbClients tbClients,TbLedger tbLedger){
		
		//做个判断，tbLedger中的状态是成功还是待编辑
		if(ledgerStatus == CommonField.LEDGER_STATUS_FILL && tbLedger.getNumLedgerStatus() != CommonField.LEDGER_STATUS_SUCCESS){
			//判断点击的按钮，是取消还是保存
			tbLedger.setNumLedgerStatus(CommonField.LEDGER_STATUS_FILL);
		}else{
			tbLedger.setNumLedgerStatus(CommonField.LEDGER_STATUS_SUCCESS);
		}
		//改变相应的状态
		Map<String,Object> res = Maps.newHashMap();
		res.put("code", 0);
		PoJoSet.getTbClientsByWrite(tbClients);
		tbClients.setVacClientNumber(tbLedger.getVacLedgerNumber());//将号码赋值
		try {
			tbClientsManager.saveOrUpdate(tbClients);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(WebUtils.getTrace(e));
			res.put("code", 1);
			res.put("message", "客户信息保存错误");
		}
		//开始保存台帐信息
		tbLedger.setNumClientId(tbClients.getId().intValue());
		findNumberInfo(tbLedger.getVacLedgerNumber(), tbLedger);
		try {
			tbLedgerManager.saveOrUpdate(tbLedger);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(WebUtils.getTrace(e));
			res.put("code", 1);
			res.put("message", "台帐信息保存错误");
		}
		return res;
	}


	
	@ModelAttribute
	public void getTbClients(HttpServletRequest request,@RequestParam(value="tbClients.id",required=false) Long id, 
			Map<String, Object> map){
		if(id != null){
			TbClients tbClients = tbClientsManager.getById(id);
			map.put("tbClients", tbClients);
		}
	}
	
	@ModelAttribute
	public void getTbLedger(HttpServletRequest request,@RequestParam(value="tbLedger.id",required=false) Long id, 
			Map<String, Object> map){
		if(id != null){
			//模拟从数据库中获取对象
			TbLedger tbLedger = tbLedgerManager.getById(id);
			map.put("tbLedger", tbLedger);
		}
	}
	
	public String  findNumberInfo(String userNumber,TbLedger tbLedger){
		log.info("来电号码【"+userNumber+"】获取来电归属start");
		String message = null;
		if (userNumber == null) {
			log.info("来电号码获取有误");
			message = "来电号码获取有误";
			return message;
		} else {
			// 判断手机号码有效性，以1开头的手机至少输入前7位，或者已0开头的固话
			String eL = "[0-9]*"; // 数字
			Pattern p = Pattern.compile(eL);
			Matcher m1 = p.matcher(userNumber);
			if (!m1.matches()
					|| !(userNumber.startsWith("1") || userNumber
							.startsWith("0"))||userNumber.length() < 7 ) {
				log.info("来电号码格式有误");
				message ="来电号码格式有误";
				return message;
			} else {
				if (userNumber.startsWith("1")) {
					SmsMobileArea mobileArea = mobileAreaManager.getMobileNumberInfo(userNumber);
					if (mobileArea != null) {
						SmsOperatorCode operatorCode = operatorCodeManager.getById(mobileArea.getNumOperatorId().longValue());
						SmsProvinceCode provinceCode = provinceCodeManager.getById(mobileArea.getNumProvinceId().longValue());
						SmsCityCode cityCode = cityCodeManager.getById(mobileArea.getNumCityId().longValue());
						tbLedger.setNumLedgerProvinceId(null== operatorCode.getId()?null:operatorCode.getId().intValue());
						tbLedger.setNumLedgerCityId(null==cityCode.getId()?null:cityCode.getId().intValue());
						tbLedger.setVacLedgerProvinceName(provinceCode.getVacProvinceName());
						tbLedger.setVacLedgerCityName(cityCode.getVacCityName());
						tbLedger.setNumLedgerOperatorId(null== operatorCode.getId()?null:operatorCode.getId().intValue());
						tbLedger.setVacLedgerOperatorName(operatorCode.getVacOperatorName());
						log.info("获取手机号码归属地信息成功");
						message = "获取手机号码归属地信息成功";
					} else {
						log.info("获取手机号码归属地信息失败");
						message = "获取手机号码归属地信息失败";
						return message;
					}
				} else if (userNumber.startsWith("0")) {
					String region = getRegionByUserNumber(userNumber);
					SmsCityCode city = cityCodeManager.getCityByRegion(region);
					if (city != null) {
						SmsProvinceCode provinceCode = provinceCodeManager.getById(city.getNumProvinceId().longValue());
						tbLedger.setNumLedgerProvinceId(null==city.getNumProvinceId()?null:city.getNumProvinceId().intValue());
						tbLedger.setNumLedgerCityId(null==city.getNumCityInterId()?null:city.getNumCityInterId().intValue());
						tbLedger.setVacLedgerProvinceName(provinceCode.getVacProvinceName());
						tbLedger.setVacLedgerCityName(city.getVacCityName());
						log.info("获取固话号码归属地信息成功");
						message = "获取固话号码归属地信息成功";
					} else {
						log.info("获取固话号码归属地信息失败");
						message = "获取固话号码归属地信息失败";
						return message;
					}
				}
			}
		}
		log.info("来电号码【"+userNumber+"】获取来电归属end");
		return message;
	}
	
	private String getRegionByUserNumber(String phoneNumber){
		String regionCode = "";
		if ("1".equals(phoneNumber.substring(1, 2))
				|| "2".equals(phoneNumber.substring(1, 2))) {
			regionCode = phoneNumber.substring(0, 3);
            if("024".equals(regionCode)) {
                String regionCode4 = phoneNumber.substring(0, 4);
                if("0244".equals(regionCode4) ||"0245".equals(regionCode4) ||"0247".equals(regionCode4)) {
                    regionCode = regionCode4;
                }
            }
		} else {
			regionCode = phoneNumber.substring(0, 4);
            if("0731".equals(regionCode)) {
                regionCode = phoneNumber.substring(0, 5);
            }
		}
		return regionCode;
	}

//----------------------------以下是查看工单 列表------------------------------------------
	
	
	@RequestMapping("/callorderlist-index/{serial}")
	public String orderlistIndex(@PathVariable("serial") String serial,Model model){
		model.addAttribute("serial", serial);
		return "modules/tb/call/callorderlist-index";
	}

	@RequestMapping("/callorderlist-query")
	public String seachOrder(){
		return "modules/tb/call/callorderlist-query";
	}
	
	@RequestMapping("/callorderlist-list")
	public String orderList(Model model){
		PoJoSet.setModelData(sysDictManager, model);
		return "modules/tb/call/callorderlist-list";
	}
	@RequestMapping(value="/callorderListDatagrid/{serial}",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> orderListDatagrid(@RequestParam("page") int page ,@RequestParam("rows") int rows ,@PathVariable("serial") String serial,TbOrders tbOrders){
		tbOrders.setVacOrderSerial(serial);
		tbOrders.setIsDeleted(false);
		try {
			return tbOrdersManager.tbOrdersList(page, rows,  tbOrders);
		} catch (Exception e) {
			log.error("获取工单集合异常"+WebUtils.getTrace(e));
		}
		return null;
	}
//------------------------------以下是查看工单  详情---------------------------------------	
	
	//查看工单详情页面
	@RequestMapping(value="/callorderview-index/{id}",method=RequestMethod.GET)	
	public String viewIndex(@PathVariable("id") String id,Model model){
		model.addAttribute("id", id);
		return "modules/tb/call/callorderview-index";
	}
	//查看工单详情数据
	@RequestMapping("/callorderview-detail/{id}")
	public String orderDetailView(@PathVariable("id") Long id,Model model){
		TbOrders tbOrders = tbOrdersManager.getById(id);
		//获取初审不通过原因
		TbSupplement tbSupplement = new TbSupplement();
		tbSupplement.setNumLedgerOrderId(id.intValue());
		tbSupplement.setNumSupplementStatus(CommonField.SUPPLEMENT_STATUS_AUDIT);
		try {
			tbOrders.setNoPassReason(tbSupplementManager.getNoPassReason(tbSupplement));
		} catch (Exception e) {
			log.error("获取初审不通过原因异常"+WebUtils.getTrace(e));
		}
		TbClients tbClients = tbClientsManager.getById(tbOrders.getNumClientId().longValue());
		if(null != tbClients){
			PoJoSet.getTbClientsByRead(tbClients);
		}
		model.addAttribute("tbClients", tbClients);
		model.addAttribute("tbOrders", tbOrders);
		PoJoSet.setModelData(sysDictManager, model);
		return "modules/tb/call/callorderview-detail";
	}

	//工单详情查看页面——历史工单
	@RequestMapping("/callorderview-order")
	public String historyOrderView(Model model){
		PoJoSet.setModelData(sysDictManager, model);
		return "modules/tb/call/callorderview-order";
	}
	
	
	//根据Id获取历史工单
	@RequestMapping(value="/callorderview-datagrid/{id}",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> historyOrdersDataGrid(@RequestParam("page") int page ,@RequestParam("rows") int rows ,@PathVariable("id") Long id){
		TbOrders tbOrders = new TbOrders();
		tbOrders.setId(id);
		tbOrders.setIsDeleted(false);		
		try {
			return tbOrdersManager.tbOrdersList(page, rows,tbOrders);
		} catch (Exception e) {
			log.error("获取工单集合异常"+WebUtils.getTrace(e));
		}
		return null;
	}
	
	
	
	
	

}
