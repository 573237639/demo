package com.fabao.ledger.common.pojo;

import java.util.List;

/**
 * EasyUI tree模型
 * 
 */
public class BusinessTypeTree implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6478930531823910127L;
	
	private String id;
	private String text;
	private String state = "open";
	private List<BusinessTypeTree> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}



	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<BusinessTypeTree> getChildren() {
		return children;
	}

	public void setChildren(List<BusinessTypeTree> children) {
		this.children = children;
	}


}
