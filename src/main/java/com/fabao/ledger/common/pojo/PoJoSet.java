package com.fabao.ledger.common.pojo;


import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;

import com.fabao.ledger.common.enums.zjType;
import com.fabao.ledger.common.utils.CommonField;
import com.fabao.ledger.modules.sys.entity.SysDict;
import com.fabao.ledger.modules.sys.service.SysDictManager;
import com.fabao.ledger.modules.tb.entity.TbClients;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.sf.json.JSONArray;

public class PoJoSet {
	
	private static final Logger logger = Logger.getLogger(PoJoSet.class);
			public static  void getTbClientsByRead(TbClients tbClients) {
				if (null != tbClients.getVacClientIdentityCode()
						&& !tbClients.getVacClientIdentityCode().equals("")) {
					tbClients.setZjCode(tbClients.getVacClientIdentityCode());
					tbClients.setZjType(zjType.IDENTITY.getCode());
				} else if (null != tbClients.getVacClientMilitaryCode()
						&& !tbClients.getVacClientMilitaryCode().equals("")) {
					tbClients.setZjCode(tbClients.getVacClientMilitaryCode());
					tbClients.setZjType(zjType.MILITARY.getCode());
				} else if (null != tbClients.getVacClientPassportCode()
						&& !tbClients.getVacClientPassportCode().equals("")) {
					tbClients.setZjCode(tbClients.getVacClientPassportCode());
					tbClients.setZjType(zjType.PASSPORT.getCode());
				} else if (null != tbClients.getVacClientEepCode() && !tbClients.getVacClientEepCode().equals("")) {
					tbClients.setZjCode(tbClients.getVacClientEepCode());
					tbClients.setZjType(zjType.MOBILE.getCode());
				}
			}
			
			public static void getTbClientsByWrite(TbClients tbClients) {
				if (null != tbClients.getZjType() && tbClients.getZjType().equals(zjType.IDENTITY.getCode())) {//身份证
					tbClients.setVacClientIdentityCode(tbClients.getZjCode());
				} else if (null != tbClients.getZjType() && tbClients.getZjType().equals(zjType.MILITARY.getCode())) {//军官证
					tbClients.setVacClientMilitaryCode(tbClients.getZjCode());
				} else if (null != tbClients.getZjType() && tbClients.getZjType().equals(zjType.PASSPORT.getCode())) {//外国籍护照
					tbClients.setVacClientPassportCode(tbClients.getZjCode());
				} else if (null != tbClients.getZjType() && tbClients.getZjType().equals(zjType.MOBILE.getCode())) {//港澳台通行证
					tbClients.setVacClientEepCode(tbClients.getZjCode());
				}
			}
			
			public static void setModelData(SysDictManager sysDictManager,Model model){
				//一次性查询所有,避免多次与数据库交互
				logger.info("获取所有字典数据,start..."); 
				List<SysDict> list = sysDictManager.getAll();
				logger.info("获取所有字典数据,end..."); 
				model.addAttribute("handleList", JSONArray.fromObject(getListBy(list,CommonField.HANDLE_TYPE)));  
				model.addAttribute("genderList", JSONArray.fromObject(getListBy(list,CommonField.GENDER)));        
				model.addAttribute("typeList", JSONArray.fromObject(getListBy(list,CommonField.CONSULTANT_TYPE))); 
				model.addAttribute("papersTypeList", JSONArray.fromObject(getListBy(list,CommonField.PAPERS_TYPE)));
				model.addAttribute("queueList", JSONArray.fromObject(getListBy(list,CommonField.PSKILL_QUEUE)));
				//order
				model.addAttribute("orderTypeList", JSONArray.fromObject(getListBy(list,CommonField.ORDER_TYPE)));  
				model.addAttribute("statusTypeList", JSONArray.fromObject(getListBy(list,CommonField.STATUS_TYPE)));        
				model.addAttribute("taskTypeList", JSONArray.fromObject(getListBy(list,CommonField.TASK_TYPE))); 
				model.addAttribute("orderDateList", JSONArray.fromObject(getListBy(list,CommonField.ORDER_DATE)));
				model.addAttribute("businessTypeList", JSONArray.fromObject(getListBy(list,CommonField.BUSINESS_TYPE)));
				//client
				model.addAttribute("sourceList", JSONArray.fromObject(getListBy(list,CommonField.SOURCE_TYPE)));
				//qccategory
				model.addAttribute("qccateTypeList", JSONArray.fromObject(getListBy(list,CommonField.QCCATE_TYPE)));
				//qcpro
				model.addAttribute("enabledDisabledList", JSONArray.fromObject(getListBy(list,CommonField.ENABLED_DISABLED)));
			}
			
			public static List<Map<String, Object>> getListBy(List<SysDict> list,String type){
				logger.info("获取字典数据【"+type+"】,start..."); 
				List<Map<String, Object>> papersTypes = Lists.newArrayList();
				for(SysDict s:list){
					if(s.getType().equals(type)){
						Map<String, Object> m = Maps.newHashMap();
						m.put("label", s.getLabel());
						m.put("value", s.getValue());
						papersTypes.add(m);
					}
				}
				logger.info("获取字典数据【"+type+"】,end"); 
				return papersTypes;
			}
			
			
			
			
//			public static void setModelDataByLedger(SysDictManager sysDictManager,Model model) {
//				  model.addAttribute("handleList", JSONArray.fromObject(sysDictManager.getListByType(CommonField.HANDLE_TYPE)));  
//			      model.addAttribute("genderList", JSONArray.fromObject(sysDictManager.getListByType(CommonField.GENDER)));        
//			      model.addAttribute("typeList", JSONArray.fromObject(sysDictManager.getListByType(CommonField.CONSULTANT_TYPE))); 
//			      model.addAttribute("papersTypeList", JSONArray.fromObject(sysDictManager.getListByType(CommonField.PAPERS_TYPE)));
//			      model.addAttribute("queueList", JSONArray.fromObject(sysDictManager.getListByType(CommonField.PSKILL_QUEUE)));
//			}
//			public static void setModelDataByOrder(SysDictManager sysDictManager,Model model) {
//			      model.addAttribute("typeList", JSONArray.fromObject(sysDictManager.getListByType(CommonField.CONSULTANT_TYPE))); 
//			      model.addAttribute("genderList", JSONArray.fromObject(sysDictManager.getListByType(CommonField.GENDER)));
//			      model.addAttribute("papersTypeList", JSONArray.fromObject(sysDictManager.getListByType(CommonField.PAPERS_TYPE)));
			
//				  model.addAttribute("orderTypeList", JSONArray.fromObject(sysDictManager.getListByType(CommonField.ORDER_TYPE)));  
//			      model.addAttribute("statusTypeList", JSONArray.fromObject(sysDictManager.getListByType(CommonField.STATUS_TYPE)));        
//			      model.addAttribute("taskTypeList", JSONArray.fromObject(sysDictManager.getListByType(CommonField.TASK_TYPE))); 
//			      model.addAttribute("orderDateList", JSONArray.fromObject(sysDictManager.getListByType(CommonField.ORDER_DATE)));
//			      model.addAttribute("businessTypeList", JSONArray.fromObject(sysDictManager.getListByType(CommonField.BUSINESS_TYPE)));
//			}
			
//			public static void setModelDataByClient(SysDictManager sysDictManager,Model model) {
//				  model.addAttribute("handleList", JSONArray.fromObject(sysDictManager.getListByType(CommonField.HANDLE_TYPE)));  
//			      model.addAttribute("genderList", JSONArray.fromObject(sysDictManager.getListByType(CommonField.GENDER)));        
//			      model.addAttribute("typeList", JSONArray.fromObject(sysDictManager.getListByType(CommonField.CONSULTANT_TYPE))); 
//			      model.addAttribute("papersTypeList", JSONArray.fromObject(sysDictManager.getListByType(CommonField.PAPERS_TYPE)));
//			      model.addAttribute("queueList", JSONArray.fromObject(sysDictManager.getListByType(CommonField.PSKILL_QUEUE)));
//			      model.addAttribute("sourceList", JSONArray.fromObject(sysDictManager.getListByType(CommonField.CLIENT_SOURCE)));
//			}
			
			
}
