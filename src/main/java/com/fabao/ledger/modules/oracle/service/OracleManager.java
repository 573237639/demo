package com.fabao.ledger.modules.oracle.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fabao.ledger.xpool.DBConnectionManager;
import com.fabaoframework.modules.config.Global;


public class OracleManager {
	
	private static final Logger log = LoggerFactory.getLogger(OracleManager.class);

	public static int queryCount(String id){
		String sql = Global.getConfig("liantong.sql.queryCount");
		// "SELECT COUNT(1) AS num  FROM gd_ledger"; 
		Connection conn = DBConnectionManager.getInstance().getConnection();
		int nums = 0;
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			 while (rs.next()) {
				 nums = rs.getInt(1);
			 }
			 System.err.println("-----------------" + nums);
			stmt.close();
		} catch (SQLException e) {
			log.error("获取数据报错！sql:"+sql+"||id:"+id,e);
		}finally{
			DBConnectionManager.getInstance().freeConection(conn);
		}
		return nums;
	}
	
	//根据ip查询分机号
	
	/**
	 * 查询录音文件路径
	 * @param callId
	 * @return
	 */
	public static String getFilNameByCallId(String callId,String agentId){
		String sql = Global.getConfig("liantong.sql.getFilNameByCallId");
		//String sql = "select agent.FILENAME from GD_AGENTRECORDLOG agent where agent.callid = ?  and agent.AGENTID = ?"; 
		log.info("根据流水号callid="+callId+"和agentId="+agentId+"查询录音文件路径,sql="+sql);
		Connection conn = DBConnectionManager.getInstance().getConnection();
		String fileName = "";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, callId);
			stmt.setString(2, agentId);
			ResultSet rs = stmt.executeQuery();
			 while (rs.next()) {
				 fileName = rs.getString(1);
			 }
			stmt.close();
		} catch (SQLException e) {
			log.error("获取数据报错！sql:"+sql+"||callid:"+callId,e);
		}finally{
			DBConnectionManager.getInstance().freeConection(conn);
		}
		log.info("根据流水号callid="+callId+"和agentId="+agentId+"查询录音文件路径,结果fileName="+fileName);
		return fileName;
	}
	
	/**
	 * 根据坐席id查询坐席类型
	 * @param agentid
	 * @return
	 */
	public static int getAgentTypeByAgentId(String agentid){
		String sql = Global.getConfig("liantong.sql.getAgentTypeByAgentId");
	//	String sql = "select agent_type from gd_user   where agent_id = ?"; 
		log.info("根据坐席id="+agentid+"查询坐席类型,sql="+sql);
		Connection conn = DBConnectionManager.getInstance().getConnection();
		int agentType = 0 ;
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, agentid);
			ResultSet rs = stmt.executeQuery();
			 while (rs.next()) {
				 agentType = rs.getInt(1);
			 }
			stmt.close();
		} catch (SQLException e) {
			log.error("获取数据报错！sql:"+sql+"||agentid:"+agentid,e);
		}finally{
			DBConnectionManager.getInstance().freeConection(conn);
		}
		log.info("根据坐席id="+agentid+"查询坐席类型结束,结果agentType="+agentType);
		return agentType;
	}
	
	/**
	 * 根据ip查询分机号
	 * @param ip
	 * @return
	 */
	public static String getAgentDnByIp(String ip){
		String sql = Global.getConfig("liantong.sql.getAgentDnByIp");
//		String sql = "select agent_dn from gd_ip2phone   where ip = ?"; 
		log.info("根据ip="+ip+"查询分机号,sql="+sql);
		Connection conn = DBConnectionManager.getInstance().getConnection();
		String agentDn = "" ;
//		ip="192.168.126.113";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, ip);
			ResultSet rs = stmt.executeQuery();
			 while (rs.next()) {
				 agentDn = rs.getString(1);
			 }
			stmt.close();
		} catch (SQLException e) {
			log.error("获取数据报错！sql:"+sql+"||ip:"+ip,e);
		}finally{
			DBConnectionManager.getInstance().freeConection(conn);
		}
		log.info("根据ip="+ip+"查询分机号结束,结果agentDn="+agentDn);
		return agentDn;
	}
	
	/**
	 * 根据mac查询分机号
	 * @param mac
	 * @return
	 */
	public static String getAgentDnByMac(String mac){
		String sql = Global.getConfig("liantong.sql.getAgentDnByMac");
	//	String sql = "select agent_dn from gd_ip2phone   where mac_addr = ?"; 
		log.info("根据mac=["+mac+"]查询分机号,sql=["+sql+"]");
		Connection conn = DBConnectionManager.getInstance().getConnection();
		String agentDn = "" ;
//		mac = "40-8D-5C-6A-41-81";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, mac);
			ResultSet rs = stmt.executeQuery();
			 while (rs.next()) {
				 agentDn = rs.getString(1);
			 }
			stmt.close();
		} catch (SQLException e) {
			log.error("获取数据报错！sql:"+sql+"||mac:"+mac,e);
		}finally{
			DBConnectionManager.getInstance().freeConection(conn);
		}
		log.info("根据mac=["+mac+"]查询分机号结束,结果agentDn=["+agentDn+"]");
		return agentDn;
	}
	
	
	
	
}
