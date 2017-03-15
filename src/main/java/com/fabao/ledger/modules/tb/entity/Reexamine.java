package com.fabao.ledger.modules.tb.entity;

import java.util.List;

public class Reexamine {

	public static final String SOURCE = "source";
	public static final String SOURCEID = "sourceid";
	public static final String TASKTYPE = "tasktype";
	public static final String TASKSTATE = "taskstate";
	public static final String TASKSTATENAME = "taskStateName";
	public static final String OPINION = "opinion";
	public static final String FROMUNITTYPE = "fromunittype";
	public static final String FROMUNITID = "fromunitid";
	public static final String TASKDESC="taskdesc";
	public static final String TASKTODO="TaskTodo";
	
	public static final String FILELIST="fileList";
	
	public static final String FROMUNITNAME = "fromunitname";
	public static final String FROMUSERID = "fromuserid";
	public static final String FROMUSERNAME = "fromusername";
	public static final String TOUNITYYPE = "tounityype";
	public static final String TOUNITID = "tounitid";
	public static final String TOUNITNAME = "tounitname";
	public static final String COMPLETETIME = "completetime";
	
	private String opinion;
	private String sourceid;
	private String source;
	private String tasktype;
	private String taskState;
	private String taskStateName;
	private List<Taskdesc> taskdesc;
	private List<TaskTodoResult> taskTodo;
	private String tounitname;
	private  List<FileList> fileList;
	private String completeTime;
	private String orderAuditTime;
	private String orderTitle;
	private String situation;
	private String trialTimeStart;
	private String trialTimeEnd;
	private String vacName;
	private String vacNumber;
	
	
	
	
	
	public String getVacName() {
		return vacName;
	}
	public void setVacName(String vacName) {
		this.vacName = vacName;
	}
	public String getVacNumber() {
		return vacNumber;
	}
	public void setVacNumber(String vacNumber) {
		this.vacNumber = vacNumber;
	}
	public String getTrialTimeStart() {
		return trialTimeStart;
	}
	public void setTrialTimeStart(String trialTimeStart) {
		this.trialTimeStart = trialTimeStart;
	}
	public String getTrialTimeEnd() {
		return trialTimeEnd;
	}
	public void setTrialTimeEnd(String trialTimeEnd) {
		this.trialTimeEnd = trialTimeEnd;
	}
	public String getSituation() {
		return situation;
	}
	public void setSituation(String situation) {
		this.situation = situation;
	}
	public String getOrderAuditTime() {
		return orderAuditTime;
	}
	public void setOrderAuditTime(String orderAuditTime) {
		this.orderAuditTime = orderAuditTime;
	}
	public String getOrderTitle() {
		return orderTitle;
	}
	public void setOrderTitle(String orderTitle) {
		this.orderTitle = orderTitle;
	}
	public String getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}
	public String getOpinion() {
		return opinion;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	public String getTaskState() {
		return taskState;
	}
	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}
	public String getTaskStateName() {
		return taskStateName;
	}
	public void setTaskStateName(String taskStateName) {
		this.taskStateName = taskStateName;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getSourceid() {
		return sourceid;
	}
	public void setSourceid(String sourceid) {
		this.sourceid = sourceid;
	}
	public String getTasktype() {
		return tasktype;
	}
	public void setTasktype(String tasktype) {
		this.tasktype = tasktype;
	}
	public List<Taskdesc> getTaskdesc() {
		return taskdesc;
	}
	public void setTaskdesc(List<Taskdesc> taskdesc) {
		this.taskdesc = taskdesc;
	}
	
	public List<TaskTodoResult> getTaskTodo() {
		return taskTodo;
	}
	public void setTaskTodo(List<TaskTodoResult> taskTodo) {
		this.taskTodo = taskTodo;
	}
	public String getTounitname() {
		return tounitname;
	}
	public void setTounitname(String tounitname) {
		this.tounitname = tounitname;
	}
	public List<FileList> getFileList() {
		return fileList;
	}
	public void setFileList(List<FileList> fileList) {
		this.fileList = fileList;
	}


}
