package com.fabao.ledger.xpool;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fabao.ledger.xpool.util.Logs;
import com.fabaoframework.modules.config.Global;

/**
 * 管理类DBConnectionManager 支持对一个或多个由属性文件定义的数据库连接池的访问.
 * 客户端程序可以使用getInstance()方法访问本类的唯一实例
 * @author haipeng
 *
 */
public class DBConnectionManager {
	
	private static DBConnectionManager instance;
	
	private static int clients = 0;
	
	private Driver drivers = null;
	
	private Logs log;
	
	private static final Logger loger = LoggerFactory.getLogger(DBConnectionManager.class);
	
	private DBConnectionPool pools = null;
	
	private static final String driverClassName = Global.getConfig("jdbc.oracle.driver");
	
	/**
	 * 构建私有函数,防止其他对象创建本类
	 */
	private DBConnectionManager(){
		init();
	}
	
	/**
	 * 读取属性文件完成初始化
	 */
	private void init(){
		log = new Logs("ledgerpool.log");
		loadDrivers();// 装载和注册所有的JDBC驱动程序
		createPools();// 根据指定属性创建连接池实例
	}
	
	/**
	 * 装载和注册JDBC驱动程序
	 * @param props 属性文件数据
	 */
	private void loadDrivers(){
		try {
			drivers = (Driver)Class.forName(driverClassName).newInstance();
			DriverManager.registerDriver(drivers);
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			//e.printStackTrace();
			log.log("无法注册JDBC驱动程序:"+driverClassName+",错误:"+e);
		}
	}
	
	/**
	 * 根据指定属性创建连接池实例
	 * @param props 连接池属性
	 */
	private void createPools(){
		String poolName = Global.getConfig("jdbc.oracle.poolname");
		String url = Global.getConfig("jdbc.oracle.url");
		String user = Global.getConfig("jdbc.oracle.username");
		String password = Global.getConfig("jdbc.oracle.password");
		String maxconn =  Global.getConfig("jdbc.oracle.maxconn");
		if(StringUtils.isBlank(maxconn)){
			maxconn = "10";
		}
		int max;
		
		try{
			max = Integer.parseInt(maxconn);
		}catch(Exception e){
			log.log("错误的最大连接数限制:"+maxconn+",连接池:"+poolName);
			max = 0;
		}
		pools = new DBConnectionPool(poolName,url,user,password,max,driverClassName);
		log.log("成功创建连接池:"+poolName);
	}
	
	/**
	 * 返回唯一实例,如果是第一次使用此方法,则创建此实例
	 * @return DBConnectionManager 唯一实例
	 */
	public static synchronized DBConnectionManager getInstance(){
		if(null==instance){
			instance = new DBConnectionManager();
		}
		clients++;
		
		return instance;
	}
	
	/**
	 * 获得一个可用的(空闲的)连接,
	 * 如果没有可用连接,且已有连接数小于最大连接数限制,
	 *  则创建并返回新连接
	 * @param name 在属性文件中定义的连接池名字
	 * @return Connection 可用连接或null
	 */
	public Connection getConnection(){
		loger.info("获取数据库的连接！");
		if(null!=pools){
			return pools.getConnection();
		}
		return null;
	}
	
	/**
	 * 获得一个可用连接.
	 * 若没有可用连接,且已有可用连接数小于最大连接数限制,
	 * 	则创建并返回新连接,
	 * 否则 在指定的时间内等待其他线程释放连接.
	 * @param time 以毫秒计的等待时间
	 * @return Connection 可用连接或null
	 */
	public Connection getConnection(long time){
		loger.info("获取数据库的连接！可等待时间="+time);
		if(null!=pools){
			return pools.getConnection(time);
		}
		return null;
	}
	
	/**
	 * 将连接对象返回给由名字指定的连接池
	 * @param name 
	 * 		在属性文件中定义的连接池名字
	 * @param con
	 * 		连接对象
	 */
	public void freeConection(Connection con){
		if(null!=pools){
			pools.freeConnection(con);
		}
	}
	
	/**
	 * 关闭所有连接,撤销驱动程序的注册
	 */
	public synchronized void release(){
		//等待直到最后一个客户端程序调用
		if(--clients!=0){
			return ;
		}
		
		if(null!=pools){
			pools.release();
		}
		
		try {
			DriverManager.deregisterDriver(drivers);
			log.log("撤销JDBC驱动程序"+drivers.getClass().getName()+"的注册!");
		} catch (SQLException e) {
			//e.printStackTrace();
			log.log(e,"无法撤销下列JDBC驱动程序的注册:"+drivers.getClass().getName());
		}
	}
}
