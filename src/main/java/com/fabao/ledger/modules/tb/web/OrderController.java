package com.fabao.ledger.modules.tb.web;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;
import com.fabao.ledger.common.pojo.PoJoSet;
import com.fabao.ledger.common.utils.CommonField;
import com.fabao.ledger.common.utils.WebUtils;
import com.fabao.ledger.common.web.ViewExcel;
import com.fabao.ledger.modules.oracle.service.OracleManager;
import com.fabao.ledger.modules.sms.entity.SmsCityCode;
import com.fabao.ledger.modules.sms.entity.SmsMobileArea;
import com.fabao.ledger.modules.sms.entity.SmsProvinceCode;
import com.fabao.ledger.modules.sms.service.SmsCityCodeManager;
import com.fabao.ledger.modules.sms.service.SmsMobileAreaManager;
import com.fabao.ledger.modules.sms.service.SmsProvinceCodeManager;
import com.fabao.ledger.modules.sys.entity.SysSpecialty;
import com.fabao.ledger.modules.sys.entity.SysUser;
import com.fabao.ledger.modules.sys.service.SysDictManager;
import com.fabao.ledger.modules.sys.service.SysSpecialtyManager;
import com.fabao.ledger.modules.sys.service.SysUserManager;
import com.fabao.ledger.modules.tb.entity.FileList;
import com.fabao.ledger.modules.tb.entity.Item;
import com.fabao.ledger.modules.tb.entity.TaskTodo;
import com.fabao.ledger.modules.tb.entity.TaskXML;
import com.fabao.ledger.modules.tb.entity.Taskdesc;
import com.fabao.ledger.modules.tb.entity.TbClients;
import com.fabao.ledger.modules.tb.entity.TbOrders;
import com.fabao.ledger.modules.tb.entity.TbReexamine;
import com.fabao.ledger.modules.tb.entity.TbSupplement;
import com.fabao.ledger.modules.tb.service.TbClientsManager;
import com.fabao.ledger.modules.tb.service.TbOrdersManager;
import com.fabao.ledger.modules.tb.service.TbReexamineManager;
import com.fabao.ledger.modules.tb.service.TbSupplementManager;
import com.fabaoframework.modules.page.Page;
import com.fabaoframework.modules.page.PageRequest;
import com.fabaoframework.modules.web.BaseController;
import com.google.common.collect.Maps;
import com.shenlanse.sys.util.Util;
import com.tepper.www.TaskWebserviceImpl;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@Controller
@RequestMapping("/tb/order")
public class OrderController extends BaseController {
		private static final Logger log = Logger.getLogger(OrderController.class);
		@Autowired
		private TbOrdersManager tbOrdersManager;
		@Autowired
		private TbClientsManager tbClientsManager;
		@Autowired
		private TbSupplementManager tbSupplementManager;
		@Autowired
		private SysDictManager sysDictManager;
		@Autowired
		private SysUserManager sysUserManager;
		@Autowired
		private TaskWebserviceImpl taskWebservice;
		@Autowired
		private SysSpecialtyManager sysSpecialtyManager;

		@Autowired
		private SmsProvinceCodeManager provinceCodeManager;
		
		@Autowired
		private SmsCityCodeManager cityCodeManager;
		
		@Autowired
		private SmsMobileAreaManager mobileAreaManager;
		
		@Autowired
		private TbReexamineManager tbReexamineManager;
		
		/**
		 * 根据业务类型ID获取最上级ID
		 * @param id
		 */
		public String getOrderBusinessParentId(String id){
			SysSpecialty sysSpecialty = sysSpecialtyManager.getById(Long.valueOf(id));
			if(sysSpecialty.getNumCurrentLevel() == 1){
				return sysSpecialty.getId().toString();
			}else{
				do{
					sysSpecialty = sysSpecialtyManager.getById(Long.valueOf(sysSpecialty.getNumParentId()));
				}while(!sysSpecialty.getNumCurrentLevel().equals(1));
			}
			return sysSpecialty.getId().toString();
		}
		/**
		 * 督办插入任务
		 * @param tbOrders
		 */
		
		public Map<String,Object> orderTaskXml(HttpServletRequest request,TbOrders tbOrders,TbClients tbClients) {
		Map<String,Object> res = Maps.newHashMap();
		SSOToken token = SSOHelper.getToken(request);
		String logId = token.getId().toString();
		tbOrders.setModifier(Integer.parseInt(logId));
		SysUser sysUser = sysUserManager.getById(token.getId());
		tbOrders.setModifierName(sysUser.getRealname());
		res.put("code", 0);
		try {
			XStream xstream = new XStream(new DomDriver());//不需要XPP3库开始使用Java6 
			Properties prop =  new  Properties();    
	        InputStream in = this.getClass().getResourceAsStream("/ordertaskxml.properties"); 
	        try {
				prop.load(in);
			} catch (IOException e) {
				log.error("读取配置文件异常"+e.getMessage());
				e.printStackTrace();
			}
			//完成时限
	        SimpleDateFormat sdfovertime = new SimpleDateFormat("yyyy-MM-dd");
	        TaskXML taskXML = new TaskXML();
	        //接收系统注册编号 20电话平台 30 网络平台 40 OA
	        taskXML.setSource(prop. getProperty("source").trim());
	        //接收系统任务id  确认 为工单ID
	        taskXML.setSourceid(tbOrders.getId().toString());
	        //任务名称  确认 工单标题
	        taskXML.setTasktitle(tbClients.getVacClientName()+"-"+tbClients.getVacClientNumber());
			//任务类型，参照督办系统代码对照表
			taskXML.setTasktype(tbOrders.getVacOrderTaskType().toString());
			//业务类型，参照督办系统代码对照表
			taskXML.setBusitype(getOrderBusinessParentId(tbOrders.getVacOrderBusinessType()));
			//紧急程度 10 普通 20 紧急
			taskXML.setUrgcode(tbOrders.getVacOrderType().toString());
			//督办状态 10普通任务 20 督办任务
			taskXML.setSupervisestate(prop. getProperty("supervisestate").trim());
			//任务所属行政区区划编码
			taskXML.setTaskareacode("");
			//完成时限 yyyy-mm-dd
			taskXML.setOvertime(sdfovertime.format(tbOrders.getDateOrderTerm()));
			//任务内容描述
			List<Item> listItem = new ArrayList<Item>();
				Taskdesc taskdesc = new Taskdesc();
					listItem.add(new Item("姓名",tbClients.getVacClientName()));
					listItem.add(new Item("手机号码",tbClients.getVacClientNumber()));
					listItem.add(new Item("内容",tbOrders.getVacOrderAccount()));
					taskdesc.setItem(listItem);
					
			taskXML.setTaskdesc(taskdesc);
			
			//答复对象
			TaskTodo taskTodo= new TaskTodo();
			Item item3 = new Item();
			item3.setTaskDoType("textarea");
			item3.setEname("replyContent");
			item3.setCname("答复内容");
			item3.setContent(tbOrders.getVacOrderSuggestion());
			taskTodo.setItem(item3);
			taskXML.setTaskTodo(taskTodo);
			
			//任务发起主体类型代码（10单位 11部门 20 人）
			taskXML.setFromunittype(prop. getProperty("fromunittype").trim());
			//任务发起主体ID
			taskXML.setFromunitid(prop. getProperty("fromunitid").trim());
			//任务发起主体名称
			taskXML.setFromunitname(prop. getProperty("fromunitname").trim());
			//任务发起经办人id
			taskXML.setFromuserid(tbOrders.getModifier().toString());
			//任务发起经办人名称
			taskXML.setFromusername(tbOrders.getModifierName());
			//任务接收者主体类型代码（10 单位 11部门 20 人）
			taskXML.setTounityype("");
			//任务接收者主体ID
			taskXML.setTounitid("");
			//任务接收者主体名称
			taskXML.setTounitname("");
			
			//附件列表
				FileList file = new FileList();
				Item item2 = new Item();
				item2.setName("文件名");
				item2.setContent(OracleManager.getFilNameByCallId(tbOrders.getVacOrderSerial(),tbOrders.getVacOrderAgentCode()));
				file.setItem(item2);
			taskXML.setFileList(file);
	       
			xstream.alias("xml", TaskXML.class);//为类名节点重命名
			xstream.alias("TaskTodo", TaskTodo.class);//为类名节点重命名
			xstream.autodetectAnnotations(true);
			String taskXml = xstream.toXML(taskXML);
			log.info("同步到督办接口insertTask方法数据:"+taskXml);
			String task = taskWebservice.insertTask(taskXml);
			//task 解析
			Document doc= DocumentHelper.parseText(task);
             Element root = doc.getRootElement();   
             Element code = root.element("code");
             Element error = root.element("err");
            if(code!=null&&code.getTextTrim().equals("1")){
            	res.put("message", "同步督办接口insertTask方法成功");
            }else{
            	String err = error.getTextTrim();
            	res.put("code", 1);
            	res.put("message", "同步督办接口insertTask方法失败,原因:"+err);
            }  
		}catch (DocumentException e) {
			log.info("同步督办接口异常:"+e.getMessage());
		}
		return res;
	}
		//工单查询主页
		@RequestMapping("/order-index")
		public String index() {
			return "modules/tb/order/order-index";
		}
		//工单查询form
		@RequestMapping("/order-query")
		public String orderForm() {
			return "modules/tb/order/order-query";
		}
		//工单列表
		@RequestMapping("/order-list")
		public String orderList(Model model) {
			PoJoSet.setModelData(sysDictManager, model);
			return "modules/tb/order/order-list";
		}
		//工单列表
		@ResponseBody
		@RequestMapping("/tbOrdersListDataGrid")
		public Map<String,Object> tbOrdersListDataGrid(HttpServletRequest request,@RequestParam("page") int page ,@RequestParam("rows") int rows,@Valid  TbClients tbClients,TbOrders tbOrders){
			//罗列查询条件
			SSOToken token = SSOHelper.getToken(request);
			String logId = token.getId().toString();
			if(tbOrders.getNumOrderStatus()==CommonField.ORDER_STATUS_AUDIT){
				tbOrders.setNumOrderUserId(Integer.parseInt(logId));
			}
			tbOrders.setTbClients(tbClients);
			try {
				return tbOrdersManager.tbOrdersList(page, rows,tbOrders);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(WebUtils.getTrace(e));
			}
			return null;
		}
		@RequestMapping(value="method=orderexcel")
		public ModelAndView orderexcel(HttpServletRequest request, HttpServletResponse response,@Valid  TbClients tbClients,TbOrders tbOrders){
					//业务类型查询
					if(null != tbOrders.getVacOrderBusinessType() && !tbOrders.getVacOrderBusinessType().equals("")){
						tbOrders.setVacOrderBusinessType(sysSpecialtyManager.getBusinessTypes(tbOrders.getVacOrderBusinessType()));
					}
					tbOrders.setTbClients(tbClients);
					tbOrders.setIsDeleted(false);
					Map<String, Object> model = new HashMap<String, Object>();   
			  		getModel(tbOrders,model); 
			        ViewExcel viewExcel = new ViewExcel();   
			        return new ModelAndView(viewExcel, model); 
		}
		public void getModel(TbOrders tbOrders,Map<String, Object> model){
		  		List<TbOrders> list = new ArrayList<TbOrders>(); 
				try {
					int total = 0;
					int start = 1;
					long l0 =  System.currentTimeMillis();
					do{
						long l1 =  System.currentTimeMillis();
						List<TbOrders> dtos = new ArrayList<TbOrders>();
						PageRequest<TbOrders> pr = new PageRequest<TbOrders>(tbOrders);
						pr.setPageNo(start);
						pr.setPageSize(500);
						pr.setSortColumns("gmt_modified DESC");
						Page<TbOrders> pages = tbOrdersManager.findByPageRequestAndEntity(pr);
						dtos = pages.getResult();
						total = pages.getTotalCount();
						if(dtos != null && dtos.size() > 0){
							for(TbOrders tb: dtos){
								tb.setModifierName(tbOrdersManager.getModifierName(tb.getModifier()));
								tb.setVacOrderBusinessName(tbOrdersManager.getBusinessNameByType(tb.getVacOrderBusinessType()));
								if(null != tb.getNumOrderReceiveProvinceId() && !tb.getNumOrderReceiveProvinceId().equals("")){
									tb.setVacOrderReceiveProvinceName(tbOrdersManager.getProvinceNameById(tb.getNumOrderReceiveProvinceId().toString()));
								}
								if(null != tb.getNumOrderReceiveCityId() && !tb.getNumOrderReceiveCityId().equals("")){
									tb.setVacOrderReceiveCityName(tbOrdersManager.getCityNameById(tb.getNumOrderReceiveCityId().toString()));
								}
								findNumberInfo(tb.getVacOrderNumber(),tb);
								list.add(tb);
							}
						}
						long l2 =  System.currentTimeMillis();
						logger.info("工单数据已经导出"+start+"个500条了,用时【"+(l2-l1)+"】毫秒，总数"+list.size()+"条，总耗时【"+(l2-l0)+"】毫秒");
						start ++;
					}while(list.size() != total);

				} catch (Exception e) {
					logger.error("工单对象设置值有误",e);
					e.printStackTrace();
				}  
				model.put("list", list);
				model.put("type", CommonField.ORDER_EXPORT_EXCEL_TYPE);
				model.put("statusList", sysDictManager.getListByType(CommonField.STATUS_TYPE));  
				model.put("genderList", sysDictManager.getListByType(CommonField.GENDER));        
				model.put("typeList", sysDictManager.getListByType(CommonField.CONSULTANT_TYPE)); 
				model.put("orderTypeList", sysDictManager.getListByType(CommonField.ORDER_TYPE));
				model.put("taskTypeList", sysDictManager.getListByType(CommonField.TASK_TYPE));
		}
		
		//获取来电归属
		public void  findNumberInfo(String userNumber,TbOrders tbOrders){
			if (userNumber == null) {
				log.info("来电号码获取有误");
			} else {
				// 判断手机号码有效性，以1开头的手机至少输入前7位，或者已0开头的固话
				String eL = "[0-9]*"; // 数字
				Pattern p = Pattern.compile(eL);
				Matcher m1 = p.matcher(userNumber);
				if (!m1.matches()
						|| !(userNumber.startsWith("1") || userNumber
								.startsWith("0"))||userNumber.length() < 7 ) {
					log.info("来电号码格式有误");
				} else {
					if (userNumber.startsWith("1")) {
						SmsMobileArea mobileArea = mobileAreaManager.getMobileNumberInfo(userNumber);
						if (mobileArea != null) {
							SmsProvinceCode provinceCode = provinceCodeManager.getById(mobileArea.getNumProvinceId().longValue());
							SmsCityCode cityCode = cityCodeManager.getById(mobileArea.getNumCityId().longValue());
							tbOrders.setVacCallAreaProvinceName(provinceCode.getVacProvinceName());
							tbOrders.setVacCallAreaCityName(cityCode.getVacCityName());
							log.info("获取手机号码归属地信息成功");
						} else {
							log.info("获取手机号码归属地信息失败");
						}
					} else if (userNumber.startsWith("0")) {
						String region = getRegionByUserNumber(userNumber);
						SmsCityCode city = cityCodeManager.getCityByRegion(region);
						if (city != null) {
							SmsProvinceCode provinceCode = provinceCodeManager.getById(city.getNumProvinceId().longValue());
							tbOrders.setVacCallAreaProvinceName(provinceCode.getVacProvinceName());
							tbOrders.setVacCallAreaCityName(city.getVacCityName());
							log.info("获取固话号码归属地信息成功");
						} else {
							log.info("获取固话号码归属地信息失败");
						}
					}
				}
			}
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

		
		//待分配工单主页
		@RequestMapping("/order-assigned-index")
		public String assignedOrderindex() {
			return "modules/tb/order/order-assigned-index";
		}
		//待分配工单查询form
		@RequestMapping("/order-assigned-query")
		public String assignedOrderForm() {
			return "modules/tb/order/order-assigned-query";
		}
		//待分配工单页面
		@RequestMapping("/order-assigned-list")
		public String assignedOrderList(Model model) {
			PoJoSet.setModelData(sysDictManager, model);
			return "modules/tb/order/order-assigned-list";
		}
		//待处理工单主页
		@RequestMapping("/order-audit-index")
		public String auditTbOrderindex() {
			return "modules/tb/order/order-audit-index";
		}
		//待处理工单查询form
		@RequestMapping("/order-audit-query")
		public String auditOrderForm() {
			return "modules/tb/order/order-audit-query";
		}
		//待处理工单页面
		@RequestMapping("/order-audit-list")
		public String auditOrderList(Model model) {
			PoJoSet.setModelData(sysDictManager, model);
			return "modules/tb/order/order-audit-list";
		}

		//保存工单及客户信息
		@ResponseBody
		@RequestMapping(value="/saveOrder",method=RequestMethod.POST)	
		public Map<String,Object> saveOrder(HttpServletRequest request,TbOrders tbOrders,TbClients tbClients){
			Map<String,Object> res= Maps.newHashMap();
			res.put("code", 0);
			try {
				TbSupplement tbSupplement = new TbSupplement();
				tbSupplement.setVarSupplementType(CommonField.SUPPLEMENT_TYPE_ORDER);
				tbSupplement.setVacSupplementContent(tbOrders.getNoPassReason());
				tbSupplement.setNumLedgerOrderId(tbOrders.getId().intValue());
				tbSupplement.setNumSupplementStatus(CommonField.SUPPLEMENT_STATUS_AUDIT);
				if (tbOrders.getNumOrderStatus() == CommonField.ORDER_STATUS_NOPASS) {
					tbSupplementManager.addTbSupplement(tbSupplement);
				}
				if (tbOrders.getNumOrderStatus() == CommonField.ORDER_STATUS_SUCCESS) {
					res=orderTaskXml(request,tbOrders,tbClients);
				}
				tbOrdersManager.saveOrder(tbOrders);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(WebUtils.getTrace(e));
				res.put("code", 1);
				res.put("message", "工单信息保存失败");
			}
			return res;
		}
		//获取客户信息
		@ModelAttribute
		public void getTbClients(@RequestParam(value="tbClients.id",required=false) Long id, 
				Map<String, Object> map){
			if(id != null){
				//模拟从数据库中获取对象
				TbClients tbClients = tbClientsManager.getById(id);
				map.put("tbClients", tbClients);
			}
		}
		//获取工单
		@ModelAttribute
		public void getTbOrders(@RequestParam(value="tbOrders.id",required=false) Long id, 
				Map<String, Object> map){
	
			if(id != null){
				//模拟从数据库中获取对象
				TbOrders tbOrders = tbOrdersManager.getById(id);
				map.put("tbOrders", tbOrders);
			}
		}
		
	
		//删除工单
		@RequestMapping("/deleteOrder")
		@ResponseBody
		public Map<String,Object> deleteOrder(@RequestParam("id") String ids){
			Map<String,Object> res = Maps.newHashMap();
			res.put("code", 0);
			try {
				tbOrdersManager.disableBat(ids);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(WebUtils.getTrace(e));
				res.put("code", 1);
				res.put("message", "删除异常");
			}
			return res;
		}
	
		//修改工单详情页面
		@RequestMapping(value="/order-edit-index/{id}",method=RequestMethod.GET)	
		public String editIndex(@PathVariable("id") Long id,Model model){
			model.addAttribute("id", id);
			//根据id获取号码
			String number = tbOrdersManager.getById(id).getVacOrderNumber();
			
			//获取来电数
			Map<String, Object> paramOrder = new HashMap<String, Object>();
			paramOrder.put("vacOrderNumber", number);
			//历史工单数
			model.addAttribute("orderEditOrderNum", tbOrdersManager.findCountByPageRequestAndEntity(paramOrder));
			return "modules/tb/order/order-edit-index";
		}
		//修改工单详情数据
		@RequestMapping("/order-edit-detail/{id}")
		public String orderDetailEdit(HttpServletRequest request,@PathVariable("id") Long id,Model model){
			orderDetail(request, id, model);
			return "modules/tb/order/order-edit-detail";
		}
		//查看工单详情页面
		@RequestMapping(value="/order-view-index/{id}",method=RequestMethod.GET)	
		public String viewIndex(@PathVariable("id") Long id,Model model){
			//根据id获取号码
			String number = tbOrdersManager.getById(id).getVacOrderNumber();
			//获取来电数
			Map<String, Object> paramOrder = new HashMap<String, Object>();
			paramOrder.put("vacOrderNumber", number);
			//历史工单数
			model.addAttribute("orderviewOrderNum", tbOrdersManager.findCountByPageRequestAndEntity(paramOrder));
			return "modules/tb/order/order-view-index";
		}
		//查看审核不通过工单详情页面
		@RequestMapping(value="/order-nopass-index/{id}",method=RequestMethod.GET)	
		public String NopassIndex(@PathVariable("id") Long id,Model model){
			//根据id获取号码
				String number = tbOrdersManager.getById(id).getVacOrderNumber();
				//获取来电数
				Map<String, Object> paramOrder = new HashMap<String, Object>();
				paramOrder.put("vacOrderNumber", number);
				//历史工单数
				model.addAttribute("orderviewOrderNum", tbOrdersManager.findCountByPageRequestAndEntity(paramOrder));
				return "modules/tb/order/order-nopass-index";
		}
		//查看审核不通过工单详情数据
		@RequestMapping("/order-nopass-detail/{id}")
		public String orderNopassDetailView(HttpServletRequest request,@PathVariable("id") Long id,Model model){
			orderDetail(request,id, model);
			return "modules/tb/order/order-nopass-detail";
		}
		//工单审核不通过详情查看页面
		@RequestMapping("/order-nopass-order")
		public String historyOrderNopass(Model model){
			PoJoSet.setModelData(sysDictManager, model);
			return "modules/tb/order/order-nopass-order";
		}
		//查看工单详情数据
		@RequestMapping("/order-view-detail/{id}")
		public String orderDetailView(HttpServletRequest request,@PathVariable("id") Long id,Model model){
			orderDetail(request,id, model);
			return "modules/tb/order/order-view-detail";
		}
		private void orderDetail(HttpServletRequest request,Long id, Model model) {
			TbOrders tbOrders = tbOrdersManager.getById(id);
			tbOrders.setModifierName(tbOrdersManager.getModifierName(tbOrders.getModifier()));
			//获取初审不通过原因
			TbSupplement tbSupplement = new TbSupplement();
			tbSupplement.setNumLedgerOrderId(id.intValue());
			tbSupplement.setNumSupplementStatus(CommonField.SUPPLEMENT_STATUS_AUDIT);
			try {
				tbOrders.setNoPassReason(tbSupplementManager.getNoPassReason(tbSupplement));
			} catch (Exception e) {
				log.info("获取不通过原因异常");
			}
			TbClients tbClients = tbClientsManager.getById(tbOrders.getNumClientId().longValue());
			if(null != tbClients){
				PoJoSet.getTbClientsByRead(tbClients);
			}
			tbOrders.setDateOrderTerm(tbOrders.getDateOrderTerm());
			TbReexamine entityReexamine = new TbReexamine();
			entityReexamine.setNumReexamineSourceid(id.intValue());
			List<TbReexamine> listreexamine = tbReexamineManager.getByEntity(entityReexamine);
			if(listreexamine.size()==0 || listreexamine==null){
					tbOrders.setVacReexamineTaskstate(CommonField.REEXAMINE_TASKSTATE_ING);
			}
			if(listreexamine.size()>0&&listreexamine!=null){
			TbReexamine tbReexamine = listreexamine.get(0);
				tbOrders.setVacCompleteTime(tbReexamine.getDateCompleteTimeString());
				tbOrders.setVacReexamineOpinion(tbReexamine.getVacReexamineOpinion());
				if(tbReexamine.getNumReexamineTaskstate().toString().equals(CommonField.REEXAMINE_TASKSTATE_COMMON)){
					tbOrders.setVacReexamineTaskstate(CommonField.REEXAMINE_TASKSTATE_COMMONNAME);
					tbOrders.setVacSituation(CommonField.REEXAMINE_TASKSTATE_SITUATIONREADY);
				}
				if(tbReexamine.getNumReexamineTaskstate().toString().equals(CommonField.REEXAMINE_TASKSTATE_CANCLE)){
					tbOrders.setVacReexamineTaskstate(CommonField.REEXAMINE_TASKSTATE_CANCLENAME);
					tbOrders.setVacSituation(CommonField.REEXAMINE_TASKSTATE_SITUATIONCANCLE);
				}
			}
			tbOrders.setFileName(OracleManager.getFilNameByCallId(tbOrders.getVacOrderSerial(),tbOrders.getVacOrderAgentCode()));
			model.addAttribute("tbClients", tbClients);
			model.addAttribute("tbOrders", tbOrders);
			PoJoSet.setModelData(sysDictManager, model);
		}

		//工单详情查看页面//历史工单
		@RequestMapping("/order-view-order")
		public String historyOrderView(Model model){
			PoJoSet.setModelData(sysDictManager, model);
			return "modules/tb/order/order-view-order";
		}
		//工单详情编辑页面//历史工单
		@RequestMapping("/order-edit-order")
		public String historyOrderEdit(Model model){
			PoJoSet.setModelData(sysDictManager, model);
			return "modules/tb/order/order-edit-order";
		}
		@RequestMapping("/hisorder-datagrid")
		@ResponseBody
		public Map<String,Object> ledgereditorderDataGrid(@RequestParam("page") int page ,@RequestParam("rows") int rows,TbOrders tbOrders){
			tbOrders.setIsDeleted(false);
			try {
				return tbOrdersManager.tbOrdersList(page, rows,tbOrders);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			return null;
		}

		//追加记录页面
		@RequestMapping(value="record-add",method=RequestMethod.GET)
		public String addRecordView(){
			return "modules/tb/order/record-add";
		}
		//添加追加记录
		@ResponseBody
		@RequestMapping(value="/addTbSupplementData",method=RequestMethod.POST)
		public Map<String,Object> addTbSupplementData(HttpServletRequest request,@RequestParam("orderId") Integer orderId,@RequestParam("vacSupplementContent") String vacSupplementContent){
			Map<String,Object> res = Maps.newHashMap();
			res.put("code", 0);
			TbSupplement tbSupplement = new TbSupplement();
			tbSupplement.setVarSupplementType(CommonField.SUPPLEMENT_TYPE_ORDER);
			tbSupplement.setVacSupplementContent(vacSupplementContent);
			tbSupplement.setNumLedgerOrderId(orderId);
			try {
				tbSupplementManager.addTbSupplement(tbSupplement);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(WebUtils.getTrace(e));
				res.put("code", 1);
				res.put("message", "保存追加记录异常");
			}
			 return res;
		}

		//追加记录
		@RequestMapping("/recordDataGrid")
		@ResponseBody
		public Map<String,Object> recordViewGrid(@RequestParam("page") int page ,@RequestParam("rows") int rows ,TbOrders tbOrders){
			//根据台帐id查询对应的追加记录
			TbSupplement tbSupplement = new TbSupplement();
			tbSupplement.setNumLedgerOrderId(tbOrders.getId() !=null ? tbOrders.getId().intValue() : 0);
			tbSupplement.setVarSupplementType(CommonField.SUPPLEMENT_TYPE_ORDER);//从字典里面去 goto...
			try {
				return tbSupplementManager.getTbSupplementList(page, rows, tbSupplement);
			} catch (Exception e) {
				log.error("获取追加记录异常："+e.getMessage());
			}
			return null;
		}
		//工单详情编辑追加记录列表
		@RequestMapping(value="record-list",method=RequestMethod.GET)
		public String recordViewList(){
			return "modules/tb/order/record-list";
		}
		
		/**
		 * 用户列表数据获取
		 * @param page
		 * @param rows
		 * @return
		 */
		@RequestMapping("/userListData")
		@ResponseBody
		public Map<String,Object> userListData(@RequestParam("page") int page ,@RequestParam("rows") int rows, SysUser user){
			PageRequest<SysUser> pr = new PageRequest<SysUser>(user);
			pr.setPageNo(page);
			pr.setPageSize(rows);
			Page<SysUser> pages = sysUserManager.findByPageRequest(pr);
			Map<String,Object> res = Maps.newHashMap();
			res.put("rows", pages.getResult());
			res.put("total", pages.getTotalCount());
			return res;
		}
		
		@RequestMapping("/assignedUser")
		@ResponseBody
		public Map<String,Object> assignedUser(HttpServletRequest request,@RequestParam("oid") String ids,@RequestParam("uid") String uid){
			Map<String,Object> res = Maps.newHashMap();
			SSOToken token = SSOHelper.getToken(request);
			res.put("code", 0);
			try {
				Map<String,Object> param = new HashMap<String, Object>();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				param.put("oid", ids);
				param.put("uid", uid);
				param.put("aid", token.getId());
				param.put("allotTime",sdf.format(date));
				tbOrdersManager.assignedUser(param);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(WebUtils.getTrace(e));
				res.put("code", 1);
				res.put("message", "分配异常");
			}
			return res;
		}
		/**
		 * 定时器查询 弹框显示 工单列表数据获取 --
		 * @param page
		 * @param rows
		 * @return
		 */
		@RequestMapping("getAlertOrderList")
		@ResponseBody
		public Map<String,Object> getAlertOrderList(HttpServletRequest request){
			Map<String,Object> remap = Maps.newHashMap();
			TbOrders tbOrders = new TbOrders();
			SSOToken token = SSOHelper.getToken(request);
			String logId = token.getId().toString();
			List<TbOrders> list = null;
			tbOrders.setNumOrderStatus(CommonField.ORDER_STATUS_AUDIT);
			tbOrders.setCreator(Integer.parseInt(logId));
			try {
				list = tbOrdersManager.getAlertOrderList(tbOrders);
			} catch (Exception e) {
				remap.put("code", 1);
				e.printStackTrace();
			}
			if(null !=list && list.size() > 0){
				remap.put("code", 5);
			}else{
				remap.put("code", 2);
			}
			remap.put("getOrderList", list);
			return remap;
		}
}
