package com.fabao.ledger.common.utils;
public class CommonField {
	
	//台账状态：待填写 1，已完成 2
	public static final Integer LEDGER_STATUS_FILL = 1;
	public static final Integer LEDGER_STATUS_SUCCESS = 2;
	//工单状态：待审核0，审核通过1,审核不通过2,待分配3,已分配
	public static final Integer ORDER_STATUS_AUDIT = 0;
	public static final Integer ORDER_STATUS_SUCCESS = 1;
	public static final Integer ORDER_STATUS_NOPASS = 2;
	public static final Integer ORDER_STATUS_ASSIGNED = 3;
	public static final String REEXAMINE_TASKSTATE_ING="工单复审中";//正常状态

	//区分追加记录的类别（台账、工单）
	public static final String  SUPPLEMENT_TYPE_LEDGER ="0"; 
	public static final String  SUPPLEMENT_TYPE_ORDER ="1"; 
	//工单类型
	public static final Integer ORDER_TYPE_COMMON = 10;
	public static final Integer ORDER_TYPE_URGENT = 20;
	//工单期限
	public static final String ORDER_TYPE_DAY = "三个工作日";
	public static final String ORDER_TYPE_HOUR = "三小时";
	//区分追加记录的属性（审核信息、留言信息）
	public static final Integer  SUPPLEMENT_STATUS_AUDIT =0; 
	public static final Integer  SUPPLEMENT_STATUS_RECORD =1; 
	
	//台帐导出
	public static final Integer  LEDGER_EXPORT_EXCEL_TYPE = 0;
	//工单导出
	public static final Integer  ORDER_EXPORT_EXCEL_TYPE = 1;
	public static final String REEXAMINE_TASKSTATE_COMMON="50";//正常状态
	public static final String REEXAMINE_TASKSTATE_CANCLE="80";//终止
	public static final String REEXAMINE_TASKSTATE_COMMONNAME="正常状态";//正常状态
	public static final String REEXAMINE_TASKSTATE_CANCLENAME="终止";//终止
	public static final String REEXAMINE_TASKSTATE_SITUATIONREADY="已完成";//正常状态
	public static final String REEXAMINE_TASKSTATE_SITUATIONCANCLE="已终止";//终止
	public static final String GENDER="gender";//性别
	public static final String CONSULTANT_TYPE="consultant_type";//
	public static final String HANDLE_TYPE="handle_type";//
	public static final String PAPERS_TYPE= "papers_type";
	public static final String ORDER_TYPE= "order_type";
	public static final String ORDER_DATE= "order_date";
	public static final String TASK_TYPE= "task_type";
	public static final String STATUS_TYPE= "status_type";
	public static final String BUSINESS_TYPE= "business_type";
	public static final String PSKILL_QUEUE = "pskill_queue";
	public static final String SOURCE_TYPE = "client_source";
	public static final String PSKILL_QUEUE_0 = "话务员工号-外呼";
	public static final String ORG_LAW_TYPE = "org_law_type";//机构类型
	public static final String QCCATE_TYPE = "qccate_type";//机构类型
	public static final String ENABLED_DISABLED = "enabled_disabled";//机构类型
	
	
	//报表路径
	//1、广东12348法律服务综合日报
	public static final String GD_IVR_AGENT_COMPARE_HOUR = "http://portal.b114.com.cn/pentaho/content/reporting/reportviewer/report.html?solution=BST+Reports&path=%2F12348%2Fgd&name=GD_IVR_AGENT_COMPARE_HOUR.prpt";
	//2、广东12348法律服务IVR接通率报表
	public static final String GD_IVR_COMPARE_HOUR = "http://portal.b114.com.cn/pentaho/content/reporting/reportviewer/report.html?solution=BST+Reports&path=%2F12348%2Fgd&name=GD_IVR_COMPARE_HOUR.prpt";
	//3、广东12348法律服务人工接通率报表
	public static final String GD_AGENT_COMPARE_HOUR = "http://portal.b114.com.cn/pentaho/content/reporting/reportviewer/report.html?solution=BST+Reports&path=%2F12348%2Fgd&name=GD_AGENT_COMPARE_HOUR.prpt";
	//4、广东12348主动放弃报表
	public static final String GD_ABANDONED = "http://portal.b114.com.cn/pentaho/content/reporting/reportviewer/report.html?solution=BST+Reports&path=%2F12348%2Fgd&name=GD_ABANDONED.prpt";
		  //5、广东12348坐席工作量统计表
	public static final String GD_AGENT_LOGIN = "http://portal.b114.com.cn/pentaho/content/reporting/reportviewer/report.html?solution=BST+Reports&path=%2F12348%2Fgd&name=GD_AGENT_LOGIN.prpt";
		  //6、广东12348坐席登录明细
	public static final String GD_AGENT_LOGIN_DETAIL = "http://portal.b114.com.cn/pentaho/content/reporting/reportviewer/report.html?solution=BST+Reports&path=%2F12348%2Fgd&name=GD_AGENT_LOGIN_DETAIL.prpt";
		  //7、广东12348坐席登录日报表
	public static final String GD_AGENT_LOGIN_DAY = "http://portal.b114.com.cn/pentaho/content/reporting/reportviewer/report.html?solution=BST+Reports&path=%2F12348%2Fgd&name=GD_AGENT_LOGIN_DAY.prpt";
		  //8、广东12348坐席满意度统计表
	public static final String GD_AGENT_SATISFACTION = "http://portal.b114.com.cn/pentaho/content/reporting/reportviewer/report.html?solution=BST+Reports&path=%2F12348%2Fgd&name=GD_AGENT_SATISFACTION.prpt";
		  //9、广东12348法律服务满意度报表
	public static final String GD_CALL_SATISFACTION = "http://portal.b114.com.cn/pentaho/content/reporting/reportviewer/report.html?solution=BST+Reports&path=%2F12348%2Fgd&name=GD_CALL_SATISFACTION.prpt";
		  //10、广东12348各地市接通率日报表
	public static final String GD_IVR_AGENT_COMPARE_CITY = "http://portal.b114.com.cn/pentaho/content/reporting/reportviewer/report.html?solution=BST+Reports&path=%2F12348%2Fgd&name=GD_IVR_AGENT_COMPARE_CITY.prpt";
		//11、广东12348各地市业务类话务综合日报表
	public static final String GD_LAW_TYPE_CITY = "http://portal.b114.com.cn/pentaho/content/reporting/reportviewer/report.html?solution=BST+Reports&path=%2F12348%2Fgd&name=GD_LAW_TYPE_CITY.prpt";
		//12、广东12348地市业务类话务综合报表
	public static final String GD_LAW_TYPE_DAY = "http://portal.b114.com.cn/pentaho/content/reporting/reportviewer/report.html?solution=BST+Reports&path=%2F12348%2Fgd&name=GD_LAW_TYPE_DAY.prpt";
	//广东12348各地市业务类型统计报表
	public static final String  GD_BUSINESS_LEVEL4 = "http://portal.b114.com.cn/pentaho/content/reporting/reportviewer/report.html?solution=BST+Reports&path=%2F12348%2Fgd&name=GD_BUSINESS_LEVEL4.prpt";
	//广东12348业务类型统计报表
	public static final String  GD_BUSINESS_LEVEL4_DAY = "http://portal.b114.com.cn/pentaho/content/reporting/reportviewer/report.html?solution=BST+Reports&path=%2F12348%2Fgd&name=GD_BUSINESS_LEVEL4_DAY.prpt";
	
	
}
