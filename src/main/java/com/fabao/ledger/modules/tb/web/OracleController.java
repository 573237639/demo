package com.fabao.ledger.modules.tb.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fabao.ledger.modules.oracle.service.OracleManager;
import com.fabaoframework.modules.web.BaseController;

@Controller
@RequestMapping("/tb/oracle")
public class OracleController extends BaseController {
	
	@RequestMapping("/queryCount")
	public String query(){
//		System.out.println(OracleManager.queryCount(""));
		System.err.println(OracleManager.getFilNameByCallId("201605010911262017",""));
		
		System.err.println(OracleManager.getAgentDnByIp("192.168.28.155"));
		
		System.err.println(OracleManager.getAgentDnByMac("C8-1F-66-3C-77-BD"));
		
		
		System.err.println(OracleManager.getAgentTypeByAgentId("2"));
		return "";
	}
	
	@RequestMapping("/cxftest")
	public String cxfTest(){
		
		System.out.println("进入cxf webservice 测试页面了");
//		System.out.println(taskWebserviceImpl.insertTask("youshenme wenti "));
		System.out.println("结束cxf webservice ");
		
		return "";
	}

}
