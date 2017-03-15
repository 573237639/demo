package com.fabao.ledger.modules.sms.web;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fabao.ledger.modules.sms.entity.SmsProvinceCode;
import com.fabao.ledger.modules.sms.service.SmsProvinceCodeManager;


/**
 * 
* @ClassName: SmsProvinceCodeController
* @Description: TODO(描述)
* @author lixf
* @date 2016年9月21日 下午5:24:27
*
 */
@Controller
@RequestMapping("/sms/province")
public class SmsProvinceCodeController {
	private static final Logger log = Logger.getLogger(SmsProvinceCodeController.class);
	@Autowired
	private SmsProvinceCodeManager provinceCodeManager;
	
	
	@RequestMapping("/findAllProvince")
	@ResponseBody
	public JSONArray findAllProvince(){
		log.info("获取所有省份信息start....");
		List<SmsProvinceCode> provinceCodes = provinceCodeManager.getProvinceList();
		JSONArray Json = JSONArray.fromObject(provinceCodes);
		log.info("获取所有省份信息end....");
		return Json;
	}
	
}
