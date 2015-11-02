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
	 * 保存网关Id
	 * 
	 * @param context
	 * @return
	 */
	public static boolean SaveGateId(Context context, String gateId) {
		SharedPreferences sp = context.getSharedPreferences("sp",
				Context.MODE_PRIVATE);
		Editor e = sp.edit();
		e.putString("gateId", gateId);
		return e.commit();
	}

	/**
	 * 获取网关Id
	 * 
	 * @param context
	 * @return
	 */
	public static String getGateId(Context context) {

		return context.getSharedPreferences("sp", Context.MODE_PRIVATE)
				.getString("gateId", "");
	}

}
