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
	
	public static boolean isBig(String date){
		
		if(date.endsWith(".0")){
			try{
			    Date d=sdf.parse(date.substring(0, date.indexOf(".0")));
			    return d.after(nowD);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		return false;
	}
	
	public static void main(String[] args) {
		System.out.println(isBig("2010-01-13 16:46:28.0"));
	}
	
	
}
