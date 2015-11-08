package com.mu.caisheng.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * TimeUtils
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-8-24
 */
public class TimeUtils {

	public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat(
			"yyyy-MM-dd");
	public static final SimpleDateFormat DATE_FORMAT_DATE2 = new SimpleDateFormat(
			"yyyy-M-d");

	public static Date getDateByStr(String dd) {

		Date date;
		try {
			date = DATE_FORMAT_DATE.parse(dd);
		} catch (java.text.ParseException e) {
			date = null;
			e.printStackTrace();
		}
		return date;
	}

	public static String getCurTime(int cur,int end){
		if (end<=cur){
			return "已结束";
		}
		int temp=end-cur;
		int min=temp/60;
		int mill=temp%60;
		String smin=null;
		String smill=null;
		if (min==0){
			smin=null;
		}else if(min>0 && min<=9){
			smin="0"+min;
		}else{
			smin=""+min;
		}
		if (mill==0){
			smill="00";
		}else if(mill>0 && mill<=9){
			smill="0"+mill;
		}else{
			smill=""+mill;
		}
		if (min==0){
			return  "剩余时间："+smill+"秒";
		}
		return  "剩余时间："+smin+":"+smill+"秒";



	}


	/**
	 * 获取当前时间的小�?
	 * 
	 * @return
	 */
	public static int getHours() {
		Calendar ca = Calendar.getInstance();
		return ca.get(Calendar.HOUR_OF_DAY);
	}

}