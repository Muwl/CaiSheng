package com.mu.caisheng.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


/**
 * @author Mu
 * @date 2015-8-5下午9:55:20
 * @description SharePrefence 工具�?
 */
public class ShareDataTool {

	/**
	 * 保存登陆信息
	 * 
	 * @param context
	 * @return
	 */
	public static boolean saveLoginInfo(Context context, String token,int state,String phone) {
		SharedPreferences sp = context.getSharedPreferences("sp",
				Context.MODE_PRIVATE);
		Editor e = sp.edit();
		e.putString("token", token);
		e.putString("phone", phone);
		e.putInt("state", state);
		return e.commit();
	}



	public static boolean saveState(Context context, int state) {
		SharedPreferences sp = context.getSharedPreferences("sp",
				Context.MODE_PRIVATE);
		Editor e = sp.edit();
		e.putInt("state", state);
		return e.commit();
	}

	/**
	 * 获取token
	 *
	 * @param context
	 * @return
	 */
	public static String getToken(Context context) {

		return context.getSharedPreferences("sp", Context.MODE_PRIVATE)
				.getString("token", "");
	}

	/**
	 * 获取电话
	 *
	 * @param context
	 * @return
	 */
	public static String getPhone(Context context) {

		return context.getSharedPreferences("sp", Context.MODE_PRIVATE)
				.getString("phone", "");
	}

	/**
	 * 获取state  0 没维护个人信息 1维护了个人信息
	 *
	 * @param context
	 * @return
	 */
	public static int getState(Context context) {
		return context.getSharedPreferences("sp", Context.MODE_PRIVATE)
				.getInt("state", 0);
	}

	/**
	 * 保存是否为第一次启动
	 *
	 * @param flag
	 *            0第一次启动 1 以后启动
	 * @return
	 */
	public static boolean saveStart(Context context, int flag) {
		SharedPreferences sp = context.getSharedPreferences("start",
				Context.MODE_PRIVATE);
		Editor e = sp.edit();
		e.putInt("flag", flag);
		return e.commit();
	}

	/**
	 * 获取是否为第一次启动
	 *
	 * @param context
	 * @return
	 */
	public static int getStart(Context context) {
		return context.getSharedPreferences("start", Context.MODE_PRIVATE)
				.getInt("flag", 0);
	}


}
