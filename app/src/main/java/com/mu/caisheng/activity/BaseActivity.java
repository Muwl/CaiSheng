package com.mu.caisheng.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import com.mu.caisheng.utils.MyApplication;
import com.umeng.message.PushAgent;


/**
 * @author Mu
 * @date 2015-6-16
 * @description 基本Activity
 */
public class BaseActivity extends FragmentActivity {
	private static final int notifiId = 11;
	public static final String BATG = "BaseActivity";
	InputMethodManager imm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		MyApplication.getInstance().addActivity(this);
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		super.onCreate(savedInstanceState);
		PushAgent.getInstance(this).onAppStart();

	}

	public  void onrefush(){

	}

	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (imm != null && getCurrentFocus() != null
				&& getCurrentFocus().getWindowToken() != null) {
			imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

}
