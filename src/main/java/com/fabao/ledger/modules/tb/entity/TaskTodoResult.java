package com.fabao.ledger.modules.tb.entity;


import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("TaskTodo")
public class TaskTodoResult {
	
	public static final String ITEM = "item";
	public static final String TASKDOTYPE = "taskDoType";
	public static final String ENAME = "ename";
	public static final String CNAME = "cname";
	public static final String CONTENT = "content";
	public static final String OPTIONS = "options";
	public static final String RESULT = "result";
	private String taskDoType;
	private String ename;
	private String cname;
	private String content;
	private Options options;
	private String result;
	
	public Options getOptions() {
		return options;
	}
	public void setOptions(Options options) {
		this.options = options;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
