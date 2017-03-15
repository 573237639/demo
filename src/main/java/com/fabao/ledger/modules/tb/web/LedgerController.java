package com.fabao.ledger.modules.tb.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;
import com.fabao.ledger.common.pojo.PoJoSet;
import com.fabao.ledger.common.utils.CommonField;
import com.fabao.ledger.common.utils.WebUtils;
import com.fabao.ledger.common.web.ViewExcel;
import com.fabao.ledger.modules.oracle.service.OracleManager;
import com.fabao.ledger.modules.sys.entity.SysSpecialty;
import com.fabao.ledger.modules.sys.service.SysDictManager;
import com.fabao.ledger.modules.sys.service.SysSpecialtyManager;
import com.fabao.ledger.modules.tb.entity.TbClients;
import com.fabao.ledger.modules.tb.entity.TbLedger;
import com.fabao.ledger.modules.tb.entity.TbOrders;
import com.fabao.ledger.modules.tb.entity.TbSupplement;
import com.fabao.ledger.modules.tb.service.TbClientsManager;
import com.fabao.ledger.modules.tb.service.TbLedgerManager;
import com.fabao.ledger.modules.tb.service.TbOrdersManager;
import com.fabao.ledger.modules.tb.service.TbSupplementManager;
import com.fabaoframework.modules.page.Page;
import com.fabaoframework.modules.page.PageRequest;
import com.fabaoframework.modules.web.BaseController;
import com.google.common.collect.Maps;




@Controller
@RequestMapping("/tb/ledger")
public class LedgerController extends BaseController {
	
	private static final Logger log = Logger.getLogger(LedgerController.class);
	
	@Autowired
	private TbLedgerManager tbLedgerManager;

	@Autowired
	private TbClientsManager tbClientsManager;
	
	@Autowired
	private TbOrdersManager tbOrdersManager;
	
	@Autowired
	private SysDictManager sysDictManager;
	
	
	@Autowired
	private SysSpecialtyManager sysSpecialtyManager;
	
	@Autowired
	private TbSupplementManager tbSupplementManager;
	

	@RequestMapping("/ledger-index")
	public String index(Model model){
		return "modules/tb/ledger/ledger-index";
	}
	
	@RequestMapping("/ledger-query")
	public String seachLedger(){
		return "modules/tb/ledger/ledger-query";
	}
	
	@RequestMapping("/ledger-list")
	public String ledgerList(Model model){
		  PoJoSet.setModelData(sysDictManager, model);
		  return "modules/tb/ledger/ledger-list";
	}
	
	
	@ResponseBody
	@RequestMapping("/tbLedgerListDataGrid")
	public Map<String,Object> ledgerListDataGrid(HttpServletRequest request,@RequestParam("page") int page ,@RequestParam("rows") int rows ,@Valid  TbClients tbClients,TbLedger tbLedger){
		if(tbLedger.getNumLedgerStatus()==CommonField.LEDGER_STATUS_FILL){
			SSOToken token = SSOHelper.getToken(request);
			String logId = token.getId().toString();
			tbLedger.setCreator(Integer.parseInt(logId));
		}
		tbLedger.setTbClients(tbClients);
		tbLedger.setIsDeleted(tbLedger.getIsDeleted() != null ? tbLedger.getIsDeleted() : false);//默认查有效的
		tbLedger.setNumLedgerStatus(tbLedger.getNumLedgerStatus() !=null ? tbLedger.getNumLedgerStatus() : CommonField.LEDGER_STATUS_SUCCESS);//默认查完成的
		try {
			return tbLedgerManager.getHistoryLedger(page, rows, tbLedger);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("获取台账集合异常："+WebUtils.getTrace(e));
		}
		return null;
	}
	
	
	@RequestMapping(value="method=ledgerexcel")
	public ModelAndView ledgerexcel(HttpServletRequest request, HttpServletResponse response,@Valid  TbClients tbClients,TbLedger tbLedger){
		//业务类型查询
		if(null != tbLedger.getVacLedgerBusinessType() && !tbLedger.getVacLedgerBusinessType().equals("")){
			tbLedger.setVacLedgerBusinessType(sysSpecialtyManager.getBusinessTypes(tbLedger.getVacLedgerBusinessType()));
		}
		tbLedger.setNumLedgerStatus(CommonField.LEDGER_STATUS_SUCCESS);
		tbLedger.setIsDeleted(false);
		tbLedger.setTbClients(tbClients);
		Map<String, Object> model = new HashMap<String, Object>();   
		getModel(tbLedger, model);
		ViewExcel viewExcel = new ViewExcel();   
		return new ModelAndView(viewExcel, model);
	}
	@RequestMapping(value="method=lnvalidledgerexcel")
	public ModelAndView lnvalidledgerexcel(HttpServletRequest request, HttpServletResponse response,@Valid  TbClients tbClients,TbLedger tbLedger){
		//业务类型查询
		if(null != tbLedger.getVacLedgerBusinessType() && !tbLedger.getVacLedgerBusinessType().equals("")){
			tbLedger.setVacLedgerBusinessType(sysSpecialtyManager.getBusinessTypes(tbLedger.getVacLedgerBusinessType()));
		}
		tbLedger.setNumLedgerStatus(CommonField.LEDGER_STATUS_SUCCESS);
		tbLedger.setIsDeleted(true);
		tbLedger.setTbClients(tbClients);
		Map<String, Object> model = new HashMap<String, Object>();   
		getModel(tbLedger, model);
		ViewExcel viewExcel = new ViewExcel();   
		return new ModelAndView(viewExcel, model);
	}
	
	
	public void getModel(TbLedger tbLedger,Map<String, Object> model) {
		List<TbLedger> list = new ArrayList<TbLedger>(); 
		//查询所有的业务类型
		List<SysSpecialty> sysSpecialtyList  = sysSpecialtyManager.getAll();
		//查询所有的工单（只查流水号）
		List<TbOrders> tbOrdersList = null;
		try {
			tbOrdersList = tbOrdersManager.getMapGroupBySerial();
		} catch (Exception e1) {
			logger.error("获取工单数据集合失败");
			e1.printStackTrace();
		}
		try {
			int total = 0;
			int start = 1;
			long l0 =  System.currentTimeMillis();
			do{
				long l1 = System.currentTimeMillis();
				List<TbLedger> dtos = new ArrayList<TbLedger>();
				PageRequest<TbLedger> pr = new PageRequest<TbLedger>(tbLedger);
				pr.setPageNo(start);
				pr.setPageSize(5000);
				pr.setSortColumns("gmt_modified DESC");
				Page<TbLedger> pages = tbLedgerManager.findByPageRequestAndEntity(pr);
				dtos = pages.getResult();
				total = pages.getTotalCount();
				if(null != dtos && dtos.size() > 0){
					for(TbLedger tb: dtos){
						getVacLedgerBusinessName(tb, sysSpecialtyList);//获取业务名称
						tb.setIsOrder(getOrderCountBySerial(tb.getVacLedgerSerial(), tbOrdersList));
						list.add(tb);
					}
				}
				long l2 =  System.currentTimeMillis();
				logger.info("有效台帐已经导出"+start+"个5000条了,用时【"+(l2-l1)+"】毫秒，总数"+list.size()+"条，总耗时【"+(l2-l0)+"】毫秒");
				start ++;
			}while(list.size() != total);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(WebUtils.getTrace(e));
		}  
		model.put("list", list);
		model.put("type", CommonField.LEDGER_EXPORT_EXCEL_TYPE);
		model.put("handleList", sysDictManager.getListByType(CommonField.HANDLE_TYPE));  
		model.put("genderList", sysDictManager.getListByType(CommonField.GENDER));        
		model.put("typeList", sysDictManager.getListByType(CommonField.CONSULTANT_TYPE));
		model.put("queueList", sysDictManager.getListByType(CommonField.PSKILL_QUEUE)); 

	}
	
	
	/**
	 * 获取业务类型所有父级节点名称
	 * @param tb
	 * @param list
	 */
	public void getVacLedgerBusinessName(TbLedger tb,List<SysSpecialty> list){
		if(null == tb.getVacLedgerBusinessType() || tb.getVacLedgerBusinessType().equals("")){
			logger.info("台帐id=【"+tb.getId()+"】没有业务类型");
			return ;
		}
		if(null == list || list.size() == 0){
			logger.info("无法获取业务类型所有数据");
			return ;
		}
		//获取当前台帐的
		SysSpecialty sysSpecialty = getSysSpecialtyById(tb.getVacLedgerBusinessType(), list);
		//设置业务名称
		tb.setVacLedgerBusinessName(sysSpecialty.getVacName());
		if(sysSpecialty.getNumCurrentLevel() == 1){
			tb.setVacLedgerBusinessName1(sysSpecialty.getVacName());
		}else{
			tb.setVacLedgerBusinessName2(null);
			tb.setVacLedgerBusinessName3(null);
			tb.setVacLedgerBusinessName4(null);
			do{
				if(sysSpecialty.getNumCurrentLevel() == 4){
					tb.setVacLedgerBusinessName4(sysSpecialty.getVacName());
				}else	if(sysSpecialty.getNumCurrentLevel() == 3){
					tb.setVacLedgerBusinessName3(sysSpecialty.getVacName());
				}else	if(sysSpecialty.getNumCurrentLevel() == 2){
					tb.setVacLedgerBusinessName2(sysSpecialty.getVacName());
					tb.setVacLedgerBusinessName1(getSysSpecialtyById(sysSpecialty.getNumParentId().toString(), list).getVacName());
				}
				sysSpecialty = getSysSpecialtyById(sysSpecialty.getNumParentId().toString(), list);
			}while(!sysSpecialty.getNumCurrentLevel().equals(1));
		}
	}
	
	/**
	 * 根据id获取对象
	 * @param id
	 * @param list
	 * @return
	 */
	public SysSpecialty getSysSpecialtyById(String id,List<SysSpecialty> list){
		for(SysSpecialty s : list){
			if(id.equals(s.getId().toString())){
				return s;
			}
		}
		return new SysSpecialty();
	}
	
	
	/**
	 * 通过流水号获取工单数
	 * @param serial
	 * @param list
	 * @return
	 */
	public Long getOrderCountBySerial(String serial,List<TbOrders> list){
		if(null !=list && list.size() > 0){
			for(TbOrders tbOrder : list){
				if(null == tbOrder.getVacOrderSerial()){
					tbOrder.setVacOrderSerial("");
				}
				if(null == serial){
					return 0l;
				}else	if(serial.equals(tbOrder.getVacOrderSerial())){
					return 1l;
				}
			}
		}
		return 0l;
	}
	

	
	
	/**
	 * 批量设置
	 * @param ids
	 * @param isDeleted
	 * @return
	 */
	@RequestMapping("/deleteLedger")
	@ResponseBody
	public Map<String,Object> deleteLedger(@RequestParam("id") String ids,@RequestParam(value="isDeleted",required=false) Boolean isDeleted){
		Map<String,Object> res = Maps.newHashMap();
		res.put("code", 0);
		if(isDeleted !=null && isDeleted){
			 try {
				tbLedgerManager.enableBat(ids);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(WebUtils.getTrace(e));
				res.put("code", 1);
				res.put("message", "设置有效异常");
			}
		}else{
			 try {
				tbLedgerManager.disableBat(ids);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(WebUtils.getTrace(e));
				res.put("code", 1);
				res.put("message", "设置无效异常");
			}
		}
		return res;
	}

	//--------------------------以下是查看台账-------------------------
	
	@RequestMapping(value="/ledger-view-index/{id}",method=RequestMethod.GET)	
	public String viewIndex(@PathVariable("id") Long id,Model model){
		model.addAttribute("id", id);
		//根据id获取号码
		String number = tbLedgerManager.getById(id).getVacLedgerNumber();
		//获取来电数
		Map<String, Object> paramLedger = new HashMap<String, Object>();
		paramLedger.put("vacLedgerNumber", number);
		paramLedger.put("numLedgerStatus", CommonField.LEDGER_STATUS_SUCCESS);
		paramLedger.put("isDeleted", false);
		//历史来电数
		try {
			model.addAttribute("ledgerViewCallNum", tbLedgerManager.findCountByPageRequestAndEntity(paramLedger));
		} catch (Exception e) {
			log.error("获取历史来电数异常"+WebUtils.getTrace(e));
		}
		Map<String, Object> paramOrder = new HashMap<String, Object>();
		paramOrder.put("vacOrderNumber", number);
		//历史工单数
		model.addAttribute("ledgerViewOrderNum", tbOrdersManager.findCountByPageRequestAndEntity(paramOrder));
		return "modules/tb/ledger/ledger-view-index";
	}
	
	@RequestMapping("/ledger-view-detail/{id}")
	public String ledgerDetailView(HttpServletRequest request,@PathVariable("id") Long id,Model model){
		
		TbLedger tbLedger = tbLedgerManager.getById(id);
		TbClients tbClients = tbClientsManager.getById(tbLedger.getNumClientId().longValue());
		if(null != tbClients){
			PoJoSet.getTbClientsByRead(tbClients);
		}
		tbLedger.setFileName(OracleManager.getFilNameByCallId(tbLedger.getVacLedgerSerial(),tbLedger.getVacLedgerAgentCode()));
		model.addAttribute("tbClients", tbClients);
		model.addAttribute("tbLedger", tbLedger);
		PoJoSet.setModelData(sysDictManager, model);
		
		return "modules/tb/ledger/ledger-view-detail";
	}
	
	
	@RequestMapping("/ledger-view-call")
	public String ledgerViewCall(Model model){
		return "modules/tb/ledger/ledger-view-call";
	}
	
	@RequestMapping("/ledger-view-order")
	public String ledgerViewOrder(Model model){
		PoJoSet.setModelData(sysDictManager, model);    
		return "modules/tb/ledger/ledger-view-order";
	}
	
	@RequestMapping("/ledgervieworder-datagrid")
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
	
	
	@RequestMapping("/ledger_view_no")
	public String ledgerViewNo(Model model){
		return "modules/tb/ledger/ledger-view-no";
	}
	
	@RequestMapping("/ledgerviewno-datagrid")
	@ResponseBody
	public Map<String,Object> ledgerViewNoDataGrid(@RequestParam("page") int page ,@RequestParam("rows") int rows,TbLedger tbLedger){
		tbLedger.setIsDeleted(true);
		tbLedger.setNumLedgerStatus(tbLedger.getNumLedgerStatus() !=null ? tbLedger.getNumLedgerStatus() : CommonField.LEDGER_STATUS_SUCCESS);//默认查完成的
		try {
			return tbLedgerManager.getHistoryLedger(page, rows, tbLedger);
		} catch (Exception e) {
			log.error("获取历史台账异常"+WebUtils.getTrace(e));
		}
		return null;
	}
	
	
	
	@RequestMapping(value="/historyCallDataGrid",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> historyCallDataGrid(@RequestParam("page") int page ,@RequestParam("rows") int rows ,TbLedger tbLedger){
		tbLedger.setIsDeleted(tbLedger.getIsDeleted() != null ? tbLedger.getIsDeleted() : false);//默认查有效的
		tbLedger.setNumLedgerStatus(tbLedger.getNumLedgerStatus() !=null ? tbLedger.getNumLedgerStatus() : CommonField.LEDGER_STATUS_SUCCESS);//默认查完成的
		try {
			return tbLedgerManager.getHistoryLedger(page, rows, tbLedger);
		} catch (Exception e) {
			log.error("获取台账集合异常"+WebUtils.getTrace(e));
		}
		return null;
	}
	
	//--------------------------以下是追加-------------------------
	
	@RequestMapping(value="record-add",method=RequestMethod.GET)
	public String addRecordView(){
		return "modules/tb/ledger/record-add";
	}
	
	@RequestMapping(value="record-list",method=RequestMethod.GET)
	public String recordList(){
		return "modules/tb/ledger/record-list";
	}
	
	@RequestMapping(value="/addTbSupplementData",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addTbSupplementData(@RequestParam("ledgerId") Integer ledgerId,@RequestParam("vacSupplementContent") String vacSupplementContent){
		Map<String,Object> res = Maps.newHashMap();
		res.put("code", 0);
		TbSupplement tbSupplement = new TbSupplement();
		tbSupplement.setVarSupplementType(CommonField.SUPPLEMENT_TYPE_LEDGER);
		tbSupplement.setNumLedgerOrderId(ledgerId);
		tbSupplement.setVacSupplementContent(vacSupplementContent);
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
	
	@RequestMapping("/recordDataGrid")
	@ResponseBody
	public Map<String,Object> recordListDataGrid(@RequestParam("page") int page ,@RequestParam("rows") int rows ,TbLedger tbLedger){
		//根据台帐id查询对应的追加记录
		TbSupplement tbSupplement = new TbSupplement();
		tbSupplement.setNumLedgerOrderId(tbLedger.getId() !=null ? tbLedger.getId().intValue() : 0);
		tbSupplement.setVarSupplementType(CommonField.SUPPLEMENT_TYPE_LEDGER);//从字典里面去 goto...
		try {
			return tbSupplementManager.getTbSupplementList(page, rows, tbSupplement);
		} catch (Exception e) {
			log.error("获取追加记录异常"+WebUtils.getTrace(e));
		}
		return null;
	}
	
	//--------------------------以下是修改台账-------------------------
	
	@RequestMapping(value="/ledger-edit-index/{id}",method=RequestMethod.GET)	
	public String editIndex(@PathVariable("id") Long id,Model model){
		model.addAttribute("id", id);
		//根据id获取号码
		TbLedger tbLedger = tbLedgerManager.getById(id);
		//获取来电数
		Map<String, Object> paramLedger = new HashMap<String, Object>();
		paramLedger.put("vacLedgerNumber", tbLedger.getVacLedgerNumber());
		paramLedger.put("numLedgerStatus", CommonField.LEDGER_STATUS_SUCCESS);
		paramLedger.put("isDeleted", false);
		//历史来电数
		try {
			model.addAttribute("ledgerEditCallNum", tbLedgerManager.findCountByPageRequestAndEntity(paramLedger));
		} catch (Exception e) {
			log.error("获取历史来电数异常"+WebUtils.getTrace(e));
		}
		Map<String, Object> paramOrder = new HashMap<String, Object>();
		paramOrder.put("vacOrderNumber", tbLedger.getVacLedgerNumber());
		//历史工单数
		model.addAttribute("ledgerEditOrderNum", tbOrdersManager.findCountByPageRequestAndEntity(paramOrder));
		model.addAttribute("status", tbLedger.getNumLedgerStatus());
		return "modules/tb/ledger/ledger-edit-index";
	}
	
	@RequestMapping("/ledger-edit-detail/{id}")
	public String ledgerDetailEdit(HttpServletRequest request,@PathVariable("id") Long id,Model model){
		TbLedger tbLedger = tbLedgerManager.getById(id);
		TbClients tbClients = tbClientsManager.getById(tbLedger.getNumClientId().longValue());
		if(null != tbClients){
			PoJoSet.getTbClientsByRead(tbClients);
		}
		tbLedger.setFileName(OracleManager.getFilNameByCallId(tbLedger.getVacLedgerSerial(),tbLedger.getVacLedgerAgentCode()));
		model.addAttribute("tbClients", tbClients);
		model.addAttribute("tbLedger", tbLedger);
		PoJoSet.setModelData(sysDictManager, model);
		return "modules/tb/ledger/ledger-edit-detail";
	}
	
	@RequestMapping("/ledger-edit-call")
	public String historyCallEdit(Model model){
		return "modules/tb/ledger/ledger-edit-call";
	}
	
	@RequestMapping("/ledger-edit-order")
	public String ledgerEditOrder(Model model){
		PoJoSet.setModelData(sysDictManager, model);
		return "modules/tb/ledger/ledger-edit-order";
	}
	
	@RequestMapping("/ledgereditorder-datagrid")
	@ResponseBody
	public Map<String,Object> ledgereditorderDataGrid(@RequestParam("page") int page ,@RequestParam("rows") int rows,TbOrders tbOrders){
		tbOrders.setIsDeleted(false);
		try {
			return tbOrdersManager.tbOrdersList(page, rows,tbOrders);
		} catch (Exception e) {
			log.error("获取工单集合异常"+WebUtils.getTrace(e));
		}
		return null;
	}
	
	
	@RequestMapping("/ledger-edit-no")
	public String ledgerEditNo(){
		return "modules/tb/ledger/ledger-edit-no";
	}
	
	@RequestMapping("/ledgereditno-datagrid")
	@ResponseBody
	public Map<String,Object> ledgerEditNoDataGrid(@RequestParam("page") int page ,@RequestParam("rows") int rows,TbLedger tbLedger){
		tbLedger.setIsDeleted(true);
		tbLedger.setNumLedgerStatus(tbLedger.getNumLedgerStatus() !=null ? tbLedger.getNumLedgerStatus() : CommonField.LEDGER_STATUS_SUCCESS);//默认查完成的
		try {
			return tbLedgerManager.getHistoryLedger(page, rows, tbLedger);
		} catch (Exception e) {
			log.error("获取台账集合异常"+WebUtils.getTrace(e));
		}
		return null;
	}	
	
	
	@ResponseBody
	@RequestMapping(value="/saveLedger",method=RequestMethod.POST)	
	public Map<String,Object> saveLedger(@Valid  TbClients tbClients,TbLedger tbLedger){
		Map<String,Object> res = Maps.newHashMap();
		res.put("code", 0);
		tbLedger.setNumLedgerStatus(CommonField.LEDGER_STATUS_SUCCESS);
		 try {
			 tbLedgerManager.saveLedger(tbClients, tbLedger);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(WebUtils.getTrace(e));
			res.put("code", 1);
			res.put("message", "保存台账信息异常");
		}
		 return res;
	}


	//--------------------------以下是modelAttribute-------------------------
	
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
			TbLedger tbLedger = tbLedgerManager.getById(id);
			map.put("tbLedger", tbLedger);
		}
	}
	
	
	//---------------------------------------------以下内容是涉及查看工单部分--------------------------------------
	
	@RequestMapping("/orderlist-index/{serial}")
	public String orderlistIndex(@PathVariable("serial") String serial,Model model){
		model.addAttribute("serial", serial);
		return "modules/tb/ledger/orderlist-index";
	}
	
	@RequestMapping("/orderview-index/{serial}")
	public String orderviewIndex(@PathVariable("serial") String serial,Model model){
		//根据serial查询唯一的工单id
		TbOrders tbOrders = new TbOrders();
		tbOrders.setVacOrderSerial(serial);
		List<TbOrders> list = tbOrdersManager.getByEntity(tbOrders);
		Long id= 1l;
		if(null != list && list.size() > 0){
			id= list.get(0).getId();
		}
		return "redirect:/tb/order/order-view-index/"+id; 
	}
	
	@RequestMapping("/orderlist-query")
	public String seachOrder(){
		return "modules/tb/ledger/orderlist-query";
	}
	
	
	@RequestMapping(value="/orderlist-index/orderListDatagrid/{serial}",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> orderListDatagrid(@RequestParam("page") int page ,@RequestParam("rows") int rows ,@PathVariable("serial") String serial,TbOrders tbOrders){
		tbOrders.setVacOrderSerial(serial);
		tbOrders.setIsDeleted(false);
		try {
			return tbOrdersManager.tbOrdersList(page, rows, tbOrders);
		} catch (Exception e) {
			log.error("获取工单集合异常"+WebUtils.getTrace(e));
		}
		return null;
	}
	
	//---------------------------------------------以下内容是涉及生成工单部分--------------------------------------
	

		
	@RequestMapping("/orderadd-index/{id}")
	public String orderaddIndex(@PathVariable("id") Long id,Model model){
		model.addAttribute("id", id);
		//根据id获取号码
		String number = tbLedgerManager.getById(id).getVacLedgerNumber();
		Map<String, Object> paramOrder = new HashMap<String, Object>();
		paramOrder.put("vacOrderNumber", number);
		//历史工单数
		model.addAttribute("orderaddOrderNum", tbOrdersManager.findCountByPageRequestAndEntity(paramOrder));
		return "modules/tb/ledger/orderadd-index";
	}
	
	@RequestMapping("/orderadd-detail/{id}")
	public String orderaddDetail(HttpServletRequest request,@PathVariable("id") Long id,Model model){
		//根据台帐id查询工单信息和客户信息
		TbLedger tbLedger = tbLedgerManager.getById(id);
		TbClients tbClients = tbClientsManager.getById(tbLedger.getNumClientId().longValue());
		if(null != tbClients){
			PoJoSet.getTbClientsByRead(tbClients);
		}
		TbOrders tbOrders = new TbOrders();
		tbOrders.setVacOrderSerial(tbLedger.getVacLedgerSerial());//流水号
		tbOrders.setVacOrderNumber(tbLedger.getVacLedgerNumber());//来电号码
		tbOrders.setVacOrderAgentCode(tbLedger.getVacLedgerAgentCode());//制单员工号
		tbOrders.setVacOrderAgentName(tbLedger.getVacLedgerAgentName());//制单员姓名
		//业务类型
		tbOrders.setVacOrderBusinessType(tbLedger.getVacLedgerBusinessType());
		//客户自述
		tbOrders.setVacOrderAccount(tbLedger.getVacLedgerClientAccount());
		//处理意见
		tbOrders.setVacOrderSuggestion(tbLedger.getVacLedgerLawyerSuggestion());
		//联系地址
		tbOrders.setNumOrderContactProvinceId(tbClients.getNumClientProvinceId());
		tbOrders.setNumOrderContactCityId(tbClients.getNumClientCityId());
		tbOrders.setVacOrderContactAddress(tbClients.getVacClientAddress());
		//常住地址
		//事发地址
		tbOrders.setNumOrderIncidentProvinceId(tbLedger.getNumLedgerIncidentPorvinceId());
		tbOrders.setNumOrderIncidentCityId(tbLedger.getNumLedgerIncidentCityId());
		tbOrders.setVacOrderIncidentAddress(tbLedger.getVacLedgerIncidentAddress());
		//录音文件
		tbOrders.setFileName(OracleManager.getFilNameByCallId(tbLedger.getVacLedgerSerial(),tbLedger.getVacLedgerAgentCode()));
		model.addAttribute("tbClients", tbClients);
		model.addAttribute("tbOrders", tbOrders);
		PoJoSet.setModelData(sysDictManager, model);
		return "modules/tb/ledger/orderadd-detail";
	}
	
	@RequestMapping("/orderadd-order")
	public String orderaddList(Model model){
		PoJoSet.setModelData(sysDictManager, model);
		return "modules/tb/ledger/orderadd-order";
	}
	
	@ResponseBody
	@RequestMapping(value="/orderadd-datagrid")
	public Map<String,Object> orderaddDatagrid(@RequestParam("page") int page ,@RequestParam("rows") int rows ,TbOrders tbOrders){
		//根据number查询所有的工单数据
		tbOrders.setIsDeleted(false);
		try {
			return tbOrdersManager.tbOrdersList(page, rows, tbOrders);
		} catch (Exception e) {
			log.error("获取工单集合异常"+WebUtils.getTrace(e));
		}
		return null;
	}


	
	@ResponseBody
	@RequestMapping(value="/orderaddSave",method=RequestMethod.POST)	
	public Map<String,Object> saveOrderadd(@Valid TbClients tbClients,TbOrders tbOrders){
		tbOrders.setTbClients(tbClients);
		tbClients.setId(tbOrders.getNumClientId().longValue());
		Map<String,Object> res = Maps.newHashMap();
		res.put("code", 0);
		try {
			tbOrdersManager.saveLedgerOrder(tbClients, tbOrders);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(WebUtils.getTrace(e));
			res.put("code", 1);
			res.put("message", "工单信息保存失败");
		}
		return res;
	}
	
	
	//-------------------以下是无效来电台帐------------------------------
	
	
	@RequestMapping("/lnvalid-ledger-list")
	public String lnvalidLedgerList(Model model){
		PoJoSet.setModelData(sysDictManager, model);
		return "modules/tb/ledger/lnvalid-ledger-list";
	}
	
	@RequestMapping("/fill-ledger-list")
	public String fillLedgerList(){
		return "modules/tb/ledger/fill-ledger-list";
	}
	
	
//------------------------------以下是应急新增台账-------------------------------
	@RequestMapping("contingency-ledger")
	public String contingencyLedger(){
		return "modules/tb/ledger/contingency-ledger";
	}
	

	
//-------------------------------------以下是测试一个页面展示所有数据
	@RequestMapping("/call-detail/{id}")
	public String callDetail(HttpServletRequest request,@PathVariable("id") Long id,Model model){
		TbLedger tbLedger = tbLedgerManager.getById(id);
		TbClients tbClients = tbClientsManager.getById(tbLedger.getNumClientId().longValue());
		if(null != tbClients){
			PoJoSet.getTbClientsByRead(tbClients);
		}
		tbLedger.setFileName(OracleManager.getFilNameByCallId(tbLedger.getVacLedgerSerial(),tbLedger.getVacLedgerAgentCode()));
		model.addAttribute("tbClients", tbClients);
		model.addAttribute("tbLedger", tbLedger);
		PoJoSet.setModelData(sysDictManager, model);
		return "modules/tb/ledger/call-detail";
	}
	
	
}
