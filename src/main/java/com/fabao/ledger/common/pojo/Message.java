package com.fabao.ledger.common.pojo;

public class Message {
	
	public String code;
	public String message;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Message(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
}
