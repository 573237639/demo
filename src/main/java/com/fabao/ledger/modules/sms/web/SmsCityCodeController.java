package com.fabao.ledger.modules.sms.web;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fabao.ledger.modules.sms.entity.SmsCityCode;
import com.fabao.ledger.modules.sms.service.SmsCityCodeManager;


/**
 * 
* @ClassName: SmsCityCodeController
* @Description: TODO(描述)
* @author lixf
* @date 2016年9月21日 下午5:28:38
*
 */
@Controller
@RequestMapping("/sms/city")
public class SmsCityCodeController {
	private static final Logger log = Logger.getLogger(SmsCityCodeController.class);
	@Autowired
	private SmsCityCodeManager cityCodeManager;
	
	
	@ResponseBody
	@RequestMapping("/findCityByProvId/{provId}")	
	public JSONArray findCityByProvId(@PathVariable("provId") String provId){
		log.info("根据省份id【"+provId+"】获取地市信息start....");
		List<SmsCityCode> cityCodes = cityCodeManager.getCityListByProvId(provId);
		JSONArray json = new JSONArray();
		if(cityCodes!=null){
			json = JSONArray.fromObject(cityCodes);
		}
		log.info("根据省份id【"+provId+"】获取地市信息end....");
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value="/findCityByRegion/{region}",method=RequestMethod.GET)	
	public JSONArray findCityByRegion(@PathVariable("region") String region){
		SmsCityCode cityCode = cityCodeManager.getCityByRegion(region);
		JSONArray json = new JSONArray();
		if(cityCode!=null){
			json = JSONArray.fromObject(cityCode);
		}
		
		return json;
	}
	
}
