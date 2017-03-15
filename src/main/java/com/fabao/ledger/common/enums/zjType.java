package com.fabao.ledger.common.enums;


public enum zjType {
	IDENTITY("vac_client_identity_code","身份证"),
	MILITARY("vac_client_military_code", "军官证"),
	PASSPORT("vac_client_passport_code","外国籍护照"),
	MOBILE("vac_client_eep_code","港澳台通行证");
	private String code;
	private String name;
	
	zjType(String code, String name){
		this.code = code;
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	public static String getJSONArray(){
		StringBuffer sb = new StringBuffer();		
		for(zjType e: zjType.values()){
			sb.append(",[").append(e.code).append(",\"").append(e.name).append("\"]");
		}
		return "["+sb.deleteCharAt(0).toString()+"]";
	}
	public static String getByCode(String code){
		for(zjType e: zjType.values()){
			if(e.getCode().equals(code))return e.name;
		}
		return null;
	}
}
