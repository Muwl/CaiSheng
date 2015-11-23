package com.mu.caisheng.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Application;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.View;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.mu.caisheng.activity.BaseActivity;
import com.mu.caisheng.activity.PersonDataActivity;
import com.mu.caisheng.dialog.CustomeDialog;
import com.mu.caisheng.dialog.FillDataDialog;
import com.mu.caisheng.model.ReturnState;
import com.mu.caisheng.model.WinEntity;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {

	private static final String TAG = MyApplication.class.getName();

	private List<Activity> list = new ArrayList<Activity>();

	private static MyApplication instance;

	public static Context applicationContext;

	private boolean flag = true;

	private Dialog dialog;

	private String curAcName="";


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
		onRefush();

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

	private void onRefush(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true){

					if ((!isApplicationBroughtToBackground()) && !ToosUtils.isStringEmpty(ShareDataTool.getToken(applicationContext)) && (ShareDataTool.getState(applicationContext)==0)) {
						getState();
					}

					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}

			}
		}).start();
	}

	private void getState(){
		if (ToosUtils.isStringEmpty(ShareDataTool.getToken(this))){
			return;
		}
		if (ShareDataTool.getState(this)==1){
			return;
		}
		HttpUtils utils = new HttpUtils();
		utils.configTimeout(20000);
		RequestParams rp = new RequestParams();
		rp.addBodyParameter("token", ShareDataTool.getToken(this));
		LogManager.LogShow("token--------", ShareDataTool.getToken(this), LogManager.ERROR);
		utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH + "isfilldata", rp, new RequestCallBack<String>() {
			@Override
			public void onStart() {
				super.onStart();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {

				Gson gson = new Gson();
				try {
					ReturnState state = gson.fromJson(arg0.result, ReturnState.class);
					LogManager.LogShow("guessdata", state.result, LogManager.ERROR);
					if (Constant.RETURN_OK.equals(state.msg)) {
						if (ToosUtils.isStringEmpty(state.result)){
							return;
						}
						ActivityManager am = (ActivityManager) instance
								.getSystemService(Context.ACTIVITY_SERVICE);
						List<RunningTaskInfo> tasks = am.getRunningTasks(1);
						if (!tasks.isEmpty()) {
							ComponentName topActivity = tasks.get(0).topActivity;
							LogManager.LogShow("token--------", topActivity.getClassName()+"好了。。。。。", LogManager.ERROR);
							for (int i = 0; i < list.size(); i++) {
								if (list.get(i) != null
										&& !list.get(i).getClass()
										.equals(PersonDataActivity.class) && list.get(i).getClass().getName().equals(topActivity.getClassName())){
									LogManager.LogShow("token--------", list.get(i).getClass()+"好了。。。。。", LogManager.ERROR);
									LogManager.LogShow("token--------", "好了。。。。。", LogManager.ERROR);

									if (dialog==null || !dialog.isShowing() || !list.get(i).getClass().getName().equals(curAcName)){
										curAcName=list.get(i).getClass().getName();
										dialog=new FillDataDialog(list.get(i));
									}
								}
							}

						}



					}else if(Constant.RETURN_TOKENERROR.equals(state.msg)){

					} else {

					}

				} catch (Exception e) {
					e.printStackTrace();

				}
			}

			@Override
			public void onFailure(HttpException e, String s) {

			}
		});



	}
}


