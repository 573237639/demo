package com.fabao.ledger.common.utils;

/**
 * 系统常量存放
 * @author fangzuo
 *
 */
public class ConstantUtils {
	/**
	 * 登录用户信息的session常量
	 */
	public final static String USER_SESSION="iuser";
	/**
	 * 登录用户左侧菜单seesion常量
	 */
	public final static String MENU_SESSION="MENU";
	
	public final static String ACT_SESSION="ACT";
	
	public final static String  NO_ACCOUNT="/sys/act/noauthor";
	
	/**
	 * 登录页面
	 */
	public final static String LOGIN_URL="/login";
	
	public final static String ERROR="error";
	
	public final static String SUCCESS="success";
	
	
	/**
	 * 基本常量定义
	 */
	public final static String ENCODING = "UTF-8";
	public final static String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public final static String SSO_SECRET_KEY = "h2wmABdfM7i3K80mAS";
	public final static String CUT_SYMBOL = "#";

	/**
	 * Cookie 设置常量
	 * maxage 介绍：
	 * -1 浏览器关闭时自动删除
	 *  0 立即删除
	 * 120 表示Cookie有效期2分钟(以秒为单位)
	 */
	public final static boolean COOKIE_SECURE = false;
	public final static boolean COOKIE_HTTPONLY = true;
	public final static int COOKIE_MAXAGE = -1;
	public final static String COOKIE_NAME = "uid";
	public final static String COOKIE_DOMAIN = "";
	public final static String COOKIE_PATH = "/";
	
	/**
	 * 数据权限:最大可见全部
	 */
	public final static Integer IDENTITY_TYPE_ALL=0;
	/**
	 * 数据权限:可见项目
	 */
	public final static Integer IDENTITY_TYPE_PROJ=1;
	/**
	 * 数据权限:可见案件
	 */
	public final static Integer IDENTITY_TYPE_CASE=2;
	/**
	 * 数据权限:可见任务
	 */
	public final static Integer IDENTITY_TYPE_TASK=3;

	/**
	 * 跟进中的任务(待开始和进行中)
	 */
	public final static Integer TASK_ING=0;
	/**
	 *有风险的任务 
	 */
	public final static Integer TASK_RISKY=1;
	
	/**
	 * 待验收的任务
	 */
	public final static Integer TASK_WAIT=2;
	/**
	 *案件 
	 */
	public final static Integer SFLOW_TYPE_CASEPROCESS=0;
	/**
	 * 任务
	 */
	public final static Integer SFLOW_TYPE_TASK=1;
	
	public static String getMethodName(String className) {  
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();  
        StackTraceElement e = stacktrace[2];  
        String methodName = e.getMethodName();  
        return className+"_"+methodName;
    }  
}
