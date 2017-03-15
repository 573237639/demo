package com.fabao.ledger.xpool.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class Logs {
	
	private static final String DEFAULT_LOG_FILE = "ledgerpool.log";
	
	private PrintWriter log;
	
	private String filename;
	
	public Logs(){
		this.filename = DEFAULT_LOG_FILE;
		try {
			log = new PrintWriter(new FileWriter(filename, true), true);
		} catch (IOException e) {
			System.err.println("无法打开日志文件:"+filename);
			log = new PrintWriter(System.out);
		}
	}
	
	public Logs(String filename){
		if(Strings.isNotBlank(filename)){
			this.filename = filename;
		}else{
			this.filename = DEFAULT_LOG_FILE;
		}
		
		try {
			log = new PrintWriter(new FileWriter(filename, true), true);
		} catch (IOException e) {
			System.err.println("无法打开日志文件:"+filename);
			log = new PrintWriter(System.out);
		}
	}
	
	/**
	 * 将文本信息写入日志文件
	 * @param msg
	 */
	public  void log(String msg){
		log.println(new Date()+":"+msg);
	}
	/**
	 * 将文本信息和异常写入文本文件
	 * @param e
	 * @param msg
	 */
	public void log(Throwable e,String msg){
		log.println(new Date()+":"+msg);
		e.printStackTrace(log);
	}
}
