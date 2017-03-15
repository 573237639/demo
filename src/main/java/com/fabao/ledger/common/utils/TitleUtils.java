package com.fabao.ledger.common.utils;

import com.fabaoframework.modules.config.Global;


public class TitleUtils {
	public static String getTitleName(){
		return Global.getConfig("productName");
	}
}