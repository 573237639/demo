/**
 * 
 */
package com.fabao.ledger.modules.sys.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间工具类
 *
 */
public class DateTimeUtils {
	
	/**
	 * yyyy-MM-dd
	 */
	public final static SimpleDateFormat SDF_DATE = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * yyyy-MM-dd hh:mi:ss
	 */
	public final static SimpleDateFormat SDF_DATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * MM月-dd日
	 */
	public final static SimpleDateFormat SDF_MONTH_DAY_1 = new SimpleDateFormat("MM月-dd日");
	/**
	 * MM月dd日
	 */
	public final static SimpleDateFormat SDF_MONTH_DAY_2 = new SimpleDateFormat("MM月dd日");
	/**
	 * yyyy年MM月
	 */
	public final static SimpleDateFormat SDF_MONTH_DAY_3 = new SimpleDateFormat("yyyy年MM月");
	
	/**
	 * dd
	 */
	public final static SimpleDateFormat SDF_DATE_DAY = new SimpleDateFormat("dd");
	
	
	public final static int FEILD_DATE = Calendar.DATE;
	public final static int FEILD_MONTH = Calendar.MONTH;
	public final static int FEILD_YEAR = Calendar.YEAR;
	public final static int FEILD_HOUR = Calendar.HOUR;
	public final static int FEILD_SECOND = Calendar.SECOND;
	public final static int FEILD_MINUTE = Calendar.MINUTE;

	/**
	 * 格式化日期格式 yyyy-mm-dd,参数为空时取当前日期
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date)
	{
		if(date == null){ date = new Date();}
		return SDF_DATE.format(date);
	}
	
	/**
	 * 格式化日期格式 dd,参数为空时取当前日期
	 * @param date
	 * @return
	 */
	public static String formatDateDay(Date date)
	{
		if(date == null){ date = new Date();}
		return SDF_DATE_DAY.format(date);
	}
	
	/**
	 * 格式化时间格式 yyyy-mm-dd hh:mi:ss,参数为空时取当前日期
	 * @param date
	 * @return
	 */
	public static String formatDateTime(Date date)
	{
		if(date == null)  {date = new Date();}
		return SDF_DATETIME.format(date);
	}
	
	/**
	 * 格式或自定义日期格式
	 * @param date  为空时取当前日期
	 * @param format
	 * @return
	 */
	public static String formatAnyDate(Date date,String format)
	{
		if(date == null) {date = new Date();}
		SimpleDateFormat SDF = new SimpleDateFormat(format);
		return SDF.format(date);
	}
	
	/**
	 * 格式或自定义日期格式
	 * @param date  为空时取当前日期
	 * @param format
	 * @return
	 */
	public static String formatAnyDate(Date date,SimpleDateFormat format)
	{
		if(date == null) date = new Date();
		return format.format(date);
	}
	
	/**
	 * 将格式为 yyyy-mm-dd的字符串转化成日期  
	 * @param date
	 * @return
	 */
	public static Date parseDate(String date)
	{
		try {
			return SDF_DATE.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 将格式为 yyyy-mm-dd hh:mi:ss的字符串转化成时间
	 * @param date
	 * @return
	 */
	public static Date parseDateTime(String date)
	{
		try {
			return SDF_DATETIME.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 将自定义格式的字符串转化成时间
	 * @param date
	 * @return
	 */
	public static Date parseAnyDate(String date,String format)
	{
		
		SimpleDateFormat SDF = new SimpleDateFormat(format);
		try {
			return SDF.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 将自定义格式的字符串转化成时间
	 * @param date
	 * @return
	 */
	public static Date parseAnyDate(String date,SimpleDateFormat format)
	{
		
		
		try {
			return format.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取下一天
	 * @param date 为空时区当前时间
	 * @return
	 */
	public static Date getNextDate(Date date)
	{
		if(date == null) {date = new Date();}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		return cal.getTime();
	}
	
	/**
	 * 获取上一天
	 * @param date 为空时区当前时间
	 * @return
	 */
	public static Date getYesteday(Date date)
	{
		if(date == null) {date = new Date();}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}
	
	/**
	 * 获取上一天
	 * 获取任何指定Field的计算日期
	 * @param date 为空时区当前时间
	 * @param field
	 * @param any
	 * @return
	 */
	public static Date getAnyYesteDate(Date date,int field,int any)
	{
		if(date == null) {date = new Date();}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);
		cal.add(field, any);
		return cal.getTime();
	}
	
	/**
	 * 获取任何指定Field的计算日期
	 * @param date 为空时区当前时间
	 * @param field
	 * @param any
	 * @return
	 */
	public static Date getAnyDate(Date date,int field,int any)
	{
		if(date == null) {date = new Date();}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, any);
		return cal.getTime();
	}
	
	/**
	 * 获取日期的字段值
	 * @param date 为空时区当前时间
	 * @param field
	 * @return
	 */
	public static int getDateFeildValue(Date date,int field)
	{
		if(date == null) {date = new Date();}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int value= cal.get(field);
		if(field == Calendar.MONTH) value = value+1;
		return value;
	}
	
	/**
	 * 获取上个月的第一天
	 * @return
	 */
	public static Date getLastMonthFirstDay()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}
	
	/**
	 * 获取上个月的最后一天
	 * @return
	 */
	public static Date getLastMonthLastDay()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}
	
	
	/**
	 * 获取上个周的第一天
	 * @return
	 */
	public static Date getLastWeekFirstDay()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.WEEK_OF_MONTH, -1);
		calendar.set(Calendar.DAY_OF_WEEK, 2);
		return calendar.getTime();
	}
	
	/**
	 * 获取上个周的最后一天
	 * @return
	 */
	public static Date getLastWeekLastDay()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK, 2);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar.getTime();
	}
	
	
	public static Date get3MinuteDay(){
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.add(Calendar.MINUTE, -3);  //设置为前三分钟
		return calendar.getTime(); 
	}
	
	
	public static Date get5MinuteDay(){
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.add(Calendar.MINUTE, -5);  //设置为前五分钟
		return calendar.getTime(); 
	}
	
	public static Date get10MinuteDay(){
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.add(Calendar.MINUTE, -10);  //设置为前五分钟
		return calendar.getTime(); 
	}
	
	public static String getTodayStart(){
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date d = calendar.getTime();
		return SDF_DATETIME.format(d);
	}
	
	
	public static String getTodayEnd() {
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		Date d = calendar.getTime();
		return SDF_DATETIME.format(d);
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}

}
