package com.fabao.ledger.common.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ModelAttribute;

import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;
import com.google.common.collect.Maps;

public class CommonController {
	
	@ModelAttribute
	public SSOToken getToken(HttpServletRequest request){
		SSOToken token = SSOHelper.getToken(request);
		return token;
	}
	
	
	public static final int CODE_SUCCESS=1;//处理成功
	public static final int CODE_FAIL=0;//处理失败

	public Map<String,Object> resMap(int code,String msg){
		Map<String,Object> res = Maps.newHashMap();
		res.put("code", code);
		res.put("msg", msg);
		
		return res;
	}
}
