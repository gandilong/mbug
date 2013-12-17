package com.thang.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

	private static Date nowD=null;
	private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
	static{
		  Calendar ca=Calendar.getInstance();
		  ca.add(Calendar.DAY_OF_MONTH,-15);
		  nowD=ca.getTime();
	}
	
	/**
	 * ÊÇ·ñ³¬¹ý15Ìì
	 * @param date
	 * @return
	 */
	public static boolean isBig(String date){
		
		if(null!=date&&date.trim().length()>0){
			try{
			    Date d=sdf.parse(date);
			    return d.before(nowD);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		return true;
	}
	
	public static void main(String[] args) {
		System.out.println(isBig("2013-12-01"));
		System.out.println(Math.random());
	}
	
	
}
