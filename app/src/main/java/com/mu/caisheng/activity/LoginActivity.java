package com.mu.caisheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mu.caisheng.R;
import com.mu.caisheng.utils.LogManager;
import com.mu.caisheng.utils.ToastUtils;
import com.mu.caisheng.utils.ToosUtils;

import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Mu on 2015/11/3.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText name;

    private EditText code;

    private TextView getCode;

    private TextView ok;

    private View pro;

    private TimeCount time;

    String sname;

    String scode;

    // 填写从短信SDK应用后台注册得到的APPKEY
    private static String APPKEY = "2218a1b45fc4";

    // 填写从短信SDK应用后台注册得到的APPSECRET
    private static String APPSECRET = "586b9657479716a00b16d03d84630ad4";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 111:
                    pro.setVisibility(View.GONE);
                    ToastUtils.displayShortToast(LoginActivity.this, "发送成功！");
                    time.start();
                    break;
                case 112:
                    pro.setVisibility(View.GONE);
                    ToastUtils.displayShortToast(LoginActivity.this, "验证成功！");
                    //前段短信验证成功 调用后台
                    break;

                case -888:
                    pro.setVisibility(View.GONE);
                    String s = (String) msg.obj;
                    ToastUtils.displayShortToast(LoginActivity.this, s);
                    break;
                case -999:
                    pro.setVisibility(View.GONE);
                    ToastUtils.displayShortToast(LoginActivity.this, "操作失败！");
                    break;
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initSDK();
        time = new TimeCount(60000, 1000);
    }

    private void initSDK() {
        // 初始化短信SDK
        SMSSDK.initSDK(this, APPKEY, APPSECRET, false);
        EventHandler eh = new EventHandler() {

            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        LogManager.LogShow("提交-----", data.toString(), LogManager.ERROR);
                        handler.sendEmptyMessage(112);
                        //提交验证码成功
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        handler.sendEmptyMessage(111);
                        LogManager.LogShow("获取-----", data.toString(), LogManager.ERROR);
                        //获取验证码成功
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        LogManager.LogShow("国家-----", data.toString(), LogManager.ERROR);
                        //返回支持发送验证码的国家列表
                    }

                } else {
                    try {
                        Throwable throwable = (Throwable) data;
                        throwable.printStackTrace();
                        JSONObject object = new JSONObject(throwable.getMessage());
                        String des = object.optString("detail");//错误描述
                        int status = object.optInt("status");//错误代码
                        if (status > 0 && !TextUtils.isEmpty(des)) {
                            Message message = new Message();
                            message.what = -888;
                            message.obj = des;
                            handler.sendMessage(message);
//                            ToastUtils.displayShortToast(LoginActivity.this, des);
//                            return;
                        } else {
                            handler.sendEmptyMessage(-999);
                        }
                    } catch (Exception e) {
                        //do something
                        handler.sendEmptyMessage(-999);
                    }
                }
            }
        };
        // 注册回调监听接口
        SMSSDK.registerEventHandler(eh);
    }

    private void initView() {
        name = (EditText) findViewById(R.id.login_phone);
        code = (EditText) findViewById(R.id.login_code);
        getCode = (TextView) findViewById(R.id.login_getcode);
        ok = (TextView) findViewById(R.id.login_ok);
        pro = findViewById(R.id.login_pro);
        getCode.setOnClickListener(this);
        ok.setOnClickListener(this);
    }

    /* 定义一个倒计时的内部类 */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            getCode.setText("获取验证码");
            getCode.setClickable(true);
            getCode.setEnabled(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            getCode.setClickable(false);
            getCode.setEnabled(false);
            getCode.setText(millisUntilFinished / 1000 + "S");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_getcode:
                if (ToosUtils.isTextEmpty(name)) {
                    ToastUtils.displayShortToast(LoginActivity.this, "手机号不能为空！");
                    return;
                }
                if (!ToosUtils.MatchPhone(ToosUtils.getTextContent(name))) {
                    ToastUtils.displayShortToast(this, "输入的手机号格式错误！");
                    return;
                }
                pro.setVisibility(View.VISIBLE);
                sname = ToosUtils.getTextContent(name);
                SMSSDK.getVerificationCode("86", ToosUtils.getTextContent(name));
//                .getSupportedCountries();
                break;
            case R.id.login_ok:
                if (ToosUtils.isStringEmpty(sname)) {
                    ToastUtils.displayShortToast(LoginActivity.this, "请先获取短信验证码!");
                    return;
                }
                if (ToosUtils.isTextEmpty(code) || !sname.equals(ToosUtils.getTextContent(name))) {
                    ToastUtils.displayShortToast(LoginActivity.this, "请输入正确的验证码!");
                    return;
                }
                pro.setVisibility(View.VISIBLE);
                scode = ToosUtils.getTextContent(code);
                SMSSDK.submitVerificationCode("86", sname, scode);
//
//                Intent intent = new Intent(LoginActivity.this, PersonDataActivity.class);
//                startActivity(intent);
//                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        // 销毁回调监听接口
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }
}
