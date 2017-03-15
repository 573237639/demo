package com.fabao.ledger.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class WebUtils {
	
	/**
	 * 获取域名
	 * @return
	 */
	public static String getDomain(HttpServletRequest request){
		StringBuffer url = request.getRequestURL();  
		return url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();  
	}
	
	
	/**
	 * 获取请求参数返回key1:value1;key2:value2
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String geAllParamters(HttpServletRequest request){
		Enumeration<String> paramNames = request.getParameterNames();
		StringBuilder sb = new StringBuilder();
		while(paramNames.hasMoreElements()){
			String paramName = paramNames.nextElement();
			String[] values = request.getParameterValues(paramName);
			sb.append(paramName).append(":");
			for(String val:values){
				sb.append(val).append(",");
			}
		}
		return sb.toString();
	}
	
	 public static String getTrace(Throwable t) {
	        StringWriter stringWriter= new StringWriter();
	        PrintWriter writer= new PrintWriter(stringWriter);
	        t.printStackTrace(writer);
	        StringBuffer buffer= stringWriter.getBuffer();
	        return buffer.toString();
	    }
}