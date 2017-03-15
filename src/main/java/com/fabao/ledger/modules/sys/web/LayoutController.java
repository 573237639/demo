package com.fabao.ledger.modules.sys.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sys/layout")
public class LayoutController {

	
	@RequestMapping("east")
	public String east(){
		return "modules/sys/layout/east";
	}
	
	@RequestMapping("south")
	public String south(){
		return "modules/sys/layout/south";
	}
	
	@RequestMapping("west")
	public String west(){
		return "modules/sys/layout/west";
	}
	
	@RequestMapping("north")
	public String north(){
		return "modules/sys/layout/north";
	}
	
	@RequestMapping("index")
	public String index(){
		return "modules/sys/layout/index";
	}
	
	

}
