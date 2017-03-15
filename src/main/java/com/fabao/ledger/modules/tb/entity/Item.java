package com.fabao.ledger.modules.tb.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("item")
public class Item {
	public static final String NAME = "name";
	public static final String ENAME = "ename";
	public static final String CNAME = "cname";
	public static final String CONTENT = "content";
	public static final String TASKDOTYPE = "taskDoType";
	private String taskDoType;
	private String ename;
	private String cname;
	private String name;
	private String content;
	private Options options;
	
	
	
	public Item()
	{
	}
	public Item(String name, String content)
	{
		this.name = name;
		this.content = content;
	}
	@Override
	public String toString() {
		return "Item [name=" + name + ", content=" + content + "]";
	}

	public Options getOptions() {
		return options;
	}

	public void setOptions(Options options) {
		this.options = options;
	}

	public String getTaskDoType() {
		return taskDoType;
	}

	public void setTaskDoType(String taskDoType) {
		this.taskDoType = taskDoType;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
