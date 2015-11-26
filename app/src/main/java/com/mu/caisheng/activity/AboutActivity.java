package com.mu.caisheng.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mu.caisheng.R;
import com.mu.caisheng.utils.ToastUtils;
import com.umeng.socialize.bean.MultiStatus;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.utils.OauthHelper;

/**
 * Created by Mu on 2015/11/4.
 */
public class AboutActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;

    private ImageView back;

    private View qq;

    private View weixin;

    private View phone;

    private View sina;

    private View web;


    private View apply;

    private UMSocialService mController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        mController = UMServiceFactory
                .getUMSocialService("com.umeng.share");
        initView();


    }

    private void initView() {
        title = (TextView) findViewById(R.id.title_text);
        back = (ImageView) findViewById(R.id.title_back);
        qq = findViewById(R.id.about_qq);
        weixin = findViewById(R.id.about_weixin);
        phone = findViewById(R.id.about_phone);
        sina = findViewById(R.id.about_sina);
        web = findViewById(R.id.about_web);
        apply = findViewById(R.id.about_apply);

        title.setText("关于猜神到");
        back.setOnClickListener(this);
        back.setVisibility(View.VISIBLE);
        qq.setOnClickListener(this);
        weixin.setOnClickListener(this);
        phone.setOnClickListener(this);
        sina.setOnClickListener(this);
        web.setOnClickListener(this);
        apply.setOnClickListener(this);
        com.umeng.socialize.utils.Log.LOG = true;

    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.about_qq:
                break;
            case R.id.about_weixin:
                break;
            case R.id.about_phone:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + "010-62522870");
                intent.setData(data);
                startActivity(intent);
                break;
            case R.id.about_sina:
                guanzhuWeibo();

                break;
            case R.id.about_web:
                Intent intent2 = new Intent();
                intent2.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("http://101.200.192.147");
                intent2.setData(content_url);
                startActivity(intent2);
                break;
            case R.id.about_apply:
                Intent intent3 = new Intent();
                intent3.setAction("android.intent.action.VIEW");
                Uri content_url2= Uri.parse("http://101.200.192.147/merchant/login");
                intent3.setData(content_url2);
                startActivity(intent3);
                break;
        }
    }

    public void guanzhuWeibo() {

//关注功能需要授权，你可以先判断是否授权，如果没有授权则授权后关注；如果已经授权则直接关注。代码如下：
        if (!OauthHelper.isAuthenticated(AboutActivity.this, SHARE_MEDIA.SINA)) {
            //设置新浪SSO handler
            mController.getConfig().setSsoHandler(new SinaSsoHandler());
            mController.doOauthVerify(AboutActivity.this, SHARE_MEDIA.SINA,
                    new SocializeListeners.UMAuthListener() {
                        @Override
                        public void onStart(SHARE_MEDIA platform) {
                        }
                        @Override
                        public void onError(SocializeException e,
                                           SHARE_MEDIA platform) {
                        }
                        @Override
                        public void onComplete(Bundle value,
                                              SHARE_MEDIA platform) {
                            if (!TextUtils.isEmpty(value.getString("uid"))) {
                                follow();
                            }else {
                                // error deal
                            }
                        }
                        @Override
                        public void onCancel(SHARE_MEDIA platform) {
                        }
                    });
        }else {
            follow();
        }
    }

    private void follow() {
      //  mController.getConfig().addFollow(SHARE_MEDIA.SINA,"5750113827");
        mController.follow(this, SHARE_MEDIA.SINA, new SocializeListeners.MulStatusListener() {
            @Override
            public void onStart() {
               // ToastUtils.displayShortToast(AboutActivity.this,"----------------开始分析");
            }

            @Override
            public void onComplete(MultiStatus multiStatus, int st,
                                  SocializeEntity entity) {
                if (st == 200) {
                    // 关注成功
                    Toast.makeText(AboutActivity.this, "关注成功", Toast.LENGTH_SHORT).show();
                }else {
                   //ToastUtils.displayShortToast(AboutActivity.this,multiStatus.toString());
                }
            }
        },new String[] {"5750113827"});//此处为想要关注的微博的uid
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mController != null) {
            /** 使用SSO授权必须添加如下代码 */
            UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
                    requestCode);
            if (ssoHandler != null) {
                ssoHandler.authorizeCallBack(requestCode, resultCode, data);
            }
        }

    }
}

