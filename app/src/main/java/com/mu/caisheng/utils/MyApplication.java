package com.mu.caisheng.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {

	private static final String TAG = MyApplication.class.getName();

	private List<Activity> list = new ArrayList<Activity>();

	private static MyApplication instance;

	public static Context applicationContext;


	public static MyApplication getInstance() {
		return instance;
	}

	public void addActivity(Activity activity) {
		list.add(activity);
	}

	// public boolean isFlag() {
	// return flag;
	// }
	//
	// public void setFlag(boolean flag) {
	// this.flag = flag;
	// }

	public List<Activity> getActivities() {
		return list;
	}

	public void exit() {
		for (Activity activity : list) {
			if (activity != null) {
				activity.finish();
			}
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		applicationContext = this;
		instance = this;

	}

	/**
	 * 判断当前应用程序处于前台还是后台
	 */
	public static boolean isApplicationBroughtToBackground() {
		ActivityManager am = (ActivityManager) instance
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasks = am.getRunningTasks(1);
		if (!tasks.isEmpty()) {
			ComponentName topActivity = tasks.get(0).topActivity;
			if (!topActivity.getPackageName().equals(instance.getPackageName())) {
				return true;
			}
		}
		return false;
	}
}


