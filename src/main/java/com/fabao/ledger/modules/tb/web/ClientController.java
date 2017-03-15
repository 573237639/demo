package com.fabao.ledger.modules.tb.web;

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
import com.fabao.ledger.modules.sms.service.SmsCityCodeManager;
import com.fabao.ledger.modules.sms.service.SmsProvinceCodeManager;
import com.fabao.ledger.modules.sys.service.SysDictManager;
import com.fabao.ledger.modules.tb.entity.TbClients;
import com.fabao.ledger.modules.tb.service.TbClientsManager;
import com.fabaoframework.modules.page.Page;
import com.fabaoframework.modules.page.PageRequest;
import com.fabaoframework.modules.web.BaseController;
import com.google.common.collect.Maps;

@Controller
@RequestMapping("/tb/client")
public class ClientController extends BaseController {
	private static final Logger log = Logger.getLogger(ClientController.class);
	@Autowired
	private TbClientsManager tbClientsManager;
	@Autowired
	private SysDictManager sysDictManager;
	@Autowired
	private SmsProvinceCodeManager smsProvinceCodeManager;
	@Autowired
	private SmsCityCodeManager smsCityCodeManager;
	/**
	 * 客户列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/clientList")
	public String clientList(Model model){
		PoJoSet.setModelData(sysDictManager, model);
		return "modules/tb/client/client-list";
	}
	//根据省份ID获取省份名称
		public String getProvinceNameById(String pid) throws Exception{
			if(null == pid || pid.equals("")){
				return "";
			}
			return smsProvinceCodeManager.getById(Long.valueOf(pid)).getVacProvinceName();
		}
		//根据地市ID获取地市名称
		public String getCityNameById(String cid) throws Exception{
			if(null == cid || cid.equals("")){
			return "";
			}
			return smsCityCodeManager.getById(Long.valueOf(cid)).getVacCityName();
		}
	/**
	 * 客户列表数据获取
	 * @param page
	 * @param rows
	 * @param tbClients
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/clientListData")
	public Map<String,Object> clientListData(@RequestParam("page")int page ,@RequestParam("rows") int rows, TbClients tbClients){
		tbClients.setIsDeleted(tbClients.getIsDeleted() != null ? tbClients.getIsDeleted() : false);//默认查有效的
		PageRequest<TbClients> pr = new PageRequest<TbClients>(tbClients);
		pr.setPageNo(page);
		pr.setPageSize(rows);
		Page<TbClients> pages = tbClientsManager.findByPageRequest(pr);
		Map<String,Object> res = Maps.newHashMap();
		List<TbClients> tbList = pages.getResult();
		for(TbClients tb: tbList){
			try {
				if(tb.getNumClientProvinceId() != null ){
					tb.setVacClientProvinceName(getProvinceNameById(tb.getNumClientProvinceId().toString()));
				}
				if(tb.getNumClientCityId() != null){
					tb.setVacClientCityName(getCityNameById(tb.getNumClientCityId().toString()));
				}
			} catch (Exception e) {
				log.error("获取省份地市异常："+e.getMessage());
			}
		}
		res.put("rows", tbList);
		res.put("total", pages.getTotalCount());
		return res;
	}
	/** 
	 * 修改客户信息
	 * @param tbClient
	 */
	@ResponseBody
	@RequestMapping(value="/editClient/{id}",method=RequestMethod.POST)
	public Map<String,Object> editClient(TbClients tbClient){
		Map<String,Object> res = Maps.newHashMap();
		res.put("code", 0);
		try {
			if (null != tbClient) {
				PoJoSet.getTbClientsByWrite(tbClient);
			}
			tbClient.setIsDeleted(tbClient.getIsDeleted() != null ? tbClient.getIsDeleted() : false);
			tbClientsManager.update(tbClient);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(WebUtils.getTrace(e));
			res.put("code", 1);
			res.put("message", "修改客户信息异常");
		}
		 return res;
	}
	
	/** 
	 * 新增客户信息
	 * @param tbClient
	 */
	@ResponseBody
	@RequestMapping(value="/addClient",method=RequestMethod.POST)
	public Map<String,Object> addClient(TbClients tbClient){
		Map<String,Object> res = Maps.newHashMap();
		res.put("code", 0);
		try {
			if (null != tbClient) {
				PoJoSet.getTbClientsByWrite(tbClient);
			}
			tbClientsManager.save(tbClient);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(WebUtils.getTrace(e));
			res.put("code", 1);
			res.put("message", "保存客户信息异常");
		}
		 return res;
	}
	/**
	 * 客户删除 动作,逻辑删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/deleteClient")
	@ResponseBody
	public Map<String,Object> deleteClient(@RequestParam("id") String ids){
		Map<String,Object> res = Maps.newHashMap();
		res.put("code", 0);
		try {
			tbClientsManager.disableBat(ids);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(WebUtils.getTrace(e));
			res.put("code", 1);
			res.put("message", "删除客户信息异常");
		}
		return res;
	}
	
}
