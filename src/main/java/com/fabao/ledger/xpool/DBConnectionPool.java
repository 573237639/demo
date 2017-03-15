package com.fabao.ledger.xpool;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 数据库连接池 它能根据要求创建新连接,直到预定的最大连接数为止. 在返回给客户端之前,它能验证连接的有效性.
 * 
 */
public class DBConnectionPool {
	
	private static final Logger log = LoggerFactory.getLogger(DBConnectionPool.class);
	
	private int checkedOut;
	private int maxConn;
	private String name;
	private String password;
	private String url;
	private String user;
	
	private DruidDataSource dataSource;

	/**
	 * 创建新的连接池
	 * @param name 连接池的名字
	 * @param url  数据库JDBC URL
	 * @param user 数据库账号 或null
	 * @param password 数据库密码 或 null
	 * @param maxConn 此连接池允许建立的最大连接数
	 */
	public DBConnectionPool(String name, String url, String user,
			String password, int maxConn, String DriverClassName) {
		this.name = name;
		this.url = url;
		this.user = user;
		this.password = password;
		this.maxConn = maxConn;
		
		dataSource = new DruidDataSource();
		dataSource.setDriverClassName(DriverClassName);
		dataSource.setUsername(this.user);
		dataSource.setPassword(this.password);
		dataSource.setUrl(this.url); 
		dataSource.setInitialSize(5);
		dataSource.setMinIdle(1);
		dataSource.setMaxActive(this.maxConn); 
		// 启用监控统计功能 
		//dataSource.setFilters("stat");
		// for mysql  
		dataSource.setPoolPreparedStatements(false);
	}
	
	/**
	 * 从连接池中获取可用连接.
	 * 可以指定客户端程序能够等待的最长时间
	 * 
	 * 参见getConnection()方法
	 * 
	 * @param timeout 以毫秒计的等待时间限制
	 * @return
	 */
	public synchronized Connection getConnection(long timeout){
		try {
			return dataSource.getConnection(timeout);
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 从连接池中获取一个可用连接.
	 * 如果没有空闲的连接,且当前的连接数小于最大连接数限制,则创建新的连接.
	 * 如果原来登记的可用连接不在有效,则从向量中删除.
	 * 然后递归调用自己,以尝试新的连接.
	 * @return
	 */
	public synchronized Connection getConnection(){
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		}
		return null; 
	}
	
	/**
	 * 关闭所有的连接
	 */
	public synchronized void release(){
		dataSource.close();
	}
	
	/**
	 * 将不再使用的连接返回给连接池
	 * @param con 客户端程序释放的连接
	 */
	public synchronized void freeConnection(Connection con){
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//获取新连接
	private Connection newConnection(){
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
}
