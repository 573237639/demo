package com.fabao.ledger.xpool.util;

public class Strings {

	public static boolean isNotBlank(String str){
		if(null==str){
			return false;
		}
		if(str.trim().equals("")){
			return false;
		}
		return true;
	}
	
	public static boolean isBlank(String str){
		return !isNotBlank(str);
	}
}
