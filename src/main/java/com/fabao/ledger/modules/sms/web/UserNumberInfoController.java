package com.fabao.ledger.modules.sms.web;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fabao.ledger.modules.sms.entity.SmsCityCode;
import com.fabao.ledger.modules.sms.entity.SmsMobileArea;
import com.fabao.ledger.modules.sms.entity.SmsOperatorCode;
import com.fabao.ledger.modules.sms.entity.SmsProvinceCode;
import com.fabao.ledger.modules.sms.service.SmsCityCodeManager;
import com.fabao.ledger.modules.sms.service.SmsMobileAreaManager;
import com.fabao.ledger.modules.sms.service.SmsOperatorCodeManager;
import com.fabao.ledger.modules.sms.service.SmsProvinceCodeManager;


/**
 * 
* @ClassName: UserNumberInfoController
* @Description: TODO(描述)
* @author lixf
* @date 2016年9月21日 下午5:26:06
*
 */
@Controller
@RequestMapping("/sms/numberInfo")
public class UserNumberInfoController {
	private static final Logger log = Logger.getLogger(UserNumberInfoController.class);
	@Autowired
	private SmsProvinceCodeManager provinceCodeManager;
	
	@Autowired
	private SmsCityCodeManager cityCodeManager;
	
	@Autowired
	private SmsMobileAreaManager mobileAreaManager;
	
	@Autowired
	private SmsOperatorCodeManager operatorCodeManager;
	
	
	@ResponseBody
	@RequestMapping(value="/findNumberInfo/{userNumber}",method=RequestMethod.GET)	
	public String findNumberInfo(@PathVariable("userNumber") String userNumber){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String result = "";
		if (userNumber == null) {
			resultMap.put("result", null);
			resultMap.put("error", "请输入手机号码");
			resultMap.put("success", false);
		} else {
			// 判断手机号码有效性，以1开头的手机至少输入前7位，或者已0开头的固话
			String eL = "[0-9]*"; // 数字
			Pattern p = Pattern.compile(eL);
			Matcher m1 = p.matcher(userNumber);
			if (!m1.matches()
					|| !(userNumber.startsWith("1") || userNumber
							.startsWith("0"))||userNumber.length() < 7 ) {
				resultMap.put("result", null);
				resultMap.put("error", "此查询号码无效");
				resultMap.put("success", false);
			} else {
				if (userNumber.startsWith("1")) {
					SmsMobileArea mobileArea = mobileAreaManager.getMobileNumberInfo(userNumber);
					if (mobileArea != null) {
						SmsOperatorCode operatorCode = operatorCodeManager.getById(mobileArea.getNumOperatorId().longValue());
						SmsProvinceCode provinceCode = provinceCodeManager.getById(mobileArea.getNumProvinceId().longValue());
						SmsCityCode cityCode = cityCodeManager.getById(mobileArea.getNumCityId().longValue());
								
						Map<String, String> info = new HashMap<String, String>();
						info.put("operatorid", String.valueOf(operatorCode.getId()));
						info.put("operatorname", operatorCode.getVacOperatorName());
						info.put("provinceid", String.valueOf(provinceCode.getId()));
						info.put("provincename", provinceCode.getVacProvinceName());
						info.put("cityid", String.valueOf(cityCode.getId()));
						info.put("cityname", cityCode.getVacCityName());
						resultMap.put("result", info);
						resultMap.put("error", null);
						resultMap.put("success", true);
					} else {
						resultMap.put("result", null);
						resultMap.put("error", null);
						resultMap.put("success", true);
					}
				} else if (userNumber.startsWith("0")) {
					String region = getRegionByUserNumber(userNumber);
					SmsCityCode city = cityCodeManager.getCityByRegion(region);
					if (city != null) {
						SmsProvinceCode provinceCode = provinceCodeManager.getById(city.getNumProvinceId().longValue());
						
						Map<String, String> info = new HashMap<String, String>();
						info.put("operatorid", null);
						info.put("operatorname", null);
						info.put("provinceid",
								String.valueOf(city.getNumProvinceId()));
						info.put("provincename", provinceCode.getVacProvinceName());
						info.put("cityid", String.valueOf(city.getNumCityInterId()));
						info.put("cityname", city.getVacCityName());
						resultMap.put("result", info);
						resultMap.put("error", null);
						resultMap.put("success", true);
					} else {
						resultMap.put("result", null);
						resultMap.put("error", null);
						resultMap.put("success", true);
					}
				}

			}

		}
		try {
			result = new ObjectMapper().writeValueAsString(resultMap);

		} catch (IOException e) {
			result = "{\"result\":null,\"error\":\"IO错误\",\"success\":false}";
		}
		return result;
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
	
}
