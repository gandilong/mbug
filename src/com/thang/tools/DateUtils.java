package com.thang.tools;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {
	
	public final static String YYYY_MM="yyyy-MM"; 
	public final static String YYYY_MM_DD="yyyy-MM-dd"; 
	public final static String YYYY_MM_DD_HH_mm_ss="yyyy-MM-dd HH:mm:ss"; 
	public final static String YYYY_MM_DD_HH_mm_ss_SS="yyyy-MM-dd HH:mm:ss SS"; 
	public final static SimpleDateFormat sdf=new SimpleDateFormat();
	
	public final static Calendar car=Calendar.getInstance();
	
	static {//设置时区为本地默认时�?
		sdf.setTimeZone(TimeZone.getDefault());
		car.setTimeZone(TimeZone.getDefault());
	}
	
	/**
	 * ===============================================
	 *         常用方法返回类型为字符串
	 * ================================================
	 */
	
	
	/**
	 * 返回�?YYYY-MM 为格式的时间字符�?
	 * @return String
	 */
	public static String getSysmonth(){
		sdf.applyPattern(YYYY_MM);
		return sdf.format(new Date());
	}
	
	/**
	 * 返回�?YYYY-MM-DD 为格式的时间字符�?
	 * @return String
	 */
	public static String getSysdate(){
		sdf.applyPattern(YYYY_MM_DD);
		return sdf.format(new Date());
	}
	
	/**
	 * 返回�?YYYY-MM-DD HH:mm 为格式的时间字符�?
	 * @return String
	 */
    public static String getSystime(){
    	sdf.applyPattern(YYYY_MM_DD_HH_mm_ss);
		return sdf.format(new Date());
	}
    
    /**
     * 返回自定义格式的时间字符�?
     * @param format
     * @return String
     */
    public static String getFormatTime(String format){
    	sdf.applyPattern(format);
		return sdf.format(new Date());
	}
    
    /**
     * 返回当前�?
     * @return String
     */
    public static String getYear(){
    	sdf.applyPattern("yyyy");
		return sdf.format(new Date());
	}
    
   /**
    * 返回当前月份
    * @return String
    */
    public static String getMonth(){
    	sdf.applyPattern("MM");
		return sdf.format(new Date());
	}
    
    /**
     * 返回当天是当月的第几�?
     * @return String
     */
    public static String getDay(){
    	sdf.applyPattern("dd");
		return sdf.format(new Date());
	}
    
    /**
     * 返回当前小时
     * @return String
     */
    public static String getHour(){
    	sdf.applyPattern("HH");
		return sdf.format(new Date());
	}
    
   /**
    * 返回当前分钟
    * @return String
    */
    public static String getMinute(){
    	sdf.applyPattern("mm");
		return sdf.format(new Date());
	}
    /**
     * 返回当天为星期几，如：（星期�?��星期�?..�?
     * @return String
     */
    public static String getWeekDayCN(){
    	sdf.applyPattern("EEEE");
		return sdf.format(new Date());
    } 
    /**
     * 返回当天为星期几，如(1,2,3,4,5,6,7)
     * @return int
     */
    public static int getWeekDayNUM(){
        car.setTimeInMillis(System.currentTimeMillis());
        return (car.get(Calendar.DAY_OF_WEEK)-1)==0?7:(car.get(Calendar.DAY_OF_WEEK)-1);
    } 
    
    /**
     * 得到当前时间的指定格�?
     * @param format
     * @return
     */
    public static String getTime(String format){
    	return formatDate(new Date(),format);
    }
    
    
    /**
	 * ===============================================
	 *         常用方法返回类型为日期类�?
	 * ================================================
	 */
    
    /**
     * 得到上一年的日期
     * @return Date
     */
    public static Date getLastYeardate(){
    	car.setTimeInMillis(System.currentTimeMillis());
        return addYear(-1);
    }
    
    /**
     * 得到上个月的日期
     * @return Date
     */
    public static Date getLastMonthdate(){
    	car.setTimeInMillis(System.currentTimeMillis());
        return addMonth(-1);
    }
    
    /**
     * 得到昨天的日�?
     * @return Date
     */
    public static Date getLastDatedate(){
    	car.setTimeInMillis(System.currentTimeMillis());
        return addDay(-1);    	
    }
    
    
    
    /**
	 * ===============================================
	 *         工具方法
	 * ================================================
	 */
    
    public static Date addDay(int k){
    	car.add(Calendar.DAY_OF_MONTH,k);
    	return car.getTime();
    }
    
    public static Date addMonth(int k){
    	car.add(Calendar.MONTH,k);
    	return car.getTime();
    }
    
    public static Date addYear(int k){
    	car.add(Calendar.YEAR,k);
    	return car.getTime();
    }
    
    public static String formatDate(Date date,String formater){
    	 sdf.applyPattern(formater);
    	 return sdf.format(date);
    }
    
    public static Date parseDate(String value,String formater){
   	    Date date=null;
        sdf.applyPattern(formater);
   	    try {
		    date=sdf.parse(value);
	    } catch (ParseException e) {
		    e.printStackTrace();
	    }
	    return date;
   }
    
    public static void main(String[] args) {
		System.out.println(DateUtils.formatDate(DateUtils.getLastDatedate(),DateUtils.YYYY_MM_DD_HH_mm_ss));
		System.out.println(DateUtils.formatDate(getLastDatedate(),DateUtils.YYYY_MM_DD));
	}
    
   
}
