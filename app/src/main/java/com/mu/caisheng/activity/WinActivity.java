package com.mu.caisheng.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.mu.caisheng.R;
import com.mu.caisheng.model.InfoEntity;
import com.mu.caisheng.model.ReturnState;
import com.mu.caisheng.model.WinEntity;
import com.mu.caisheng.utils.Constant;
import com.mu.caisheng.utils.DensityUtil;
import com.mu.caisheng.utils.LogManager;
import com.mu.caisheng.utils.ShareDataTool;
import com.mu.caisheng.utils.ShareUtils;
import com.mu.caisheng.utils.ToastUtils;
import com.mu.caisheng.utils.ToosUtils;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.sso.UMSsoHandler;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.mu.caisheng.R.id.end;
import static com.mu.caisheng.R.id.image;
import static com.mu.caisheng.R.id.win_tip;

/**
 * Created by Mu on 2015/11/3.
 */
public class WinActivity extends BaseActivity implements View.OnClickListener {

    private View img;

    private TextView tip;

    private TextView share;

    private TextView close;

    private ImageView imageView;

    private TextView name;

    private UMSocialService mController;

    private View pro;

    private WinEntity entity;

    private String id;

    private BitmapUtils bitmapUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);
        id=getIntent().getStringExtra("id");
        bitmapUtils=new BitmapUtils(this);
        initView();
        getWin();

        LogManager.LogShow("----",id+"---",LogManager.ERROR);
    }

    private void initView() {
        img = (View) findViewById(R.id.win_im);
        tip = (TextView) findViewById(win_tip);
        share = (TextView) findViewById(R.id.win_share);
        close = (TextView) findViewById(R.id.win_close);
        imageView = (ImageView) findViewById(R.id.win_image);
        name = (TextView) findViewById(R.id.win_name);
        pro=findViewById(R.id.win_pro);

        int width = DensityUtil.getScreenWidth(this);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) img.getLayoutParams();
        params.width = width - DensityUtil.dip2px(this, 40);
        params.height = (int) ((width - DensityUtil.dip2px(this, 40)) * 0.7325);
        img.setLayoutParams(params);

        share.setOnClickListener(this);
        close.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.win_share:
                mController = ShareUtils.Share(WinActivity.this, "测试一下", "", null);
                break;
            case R.id.win_close:
                finish();
                break;
        }
    }



    /**
     * 获取获奖信息
     */
    private void getWin() {
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        RequestParams rp = new RequestParams();
        rp.addBodyParameter("token", ShareDataTool.getToken(this));
        rp.addBodyParameter("id", id+"");
        LogManager.LogShow("token--------", ShareDataTool.getToken(this), LogManager.ERROR);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH + "win", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                pro.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                pro.setVisibility(View.GONE);
                Gson gson = new Gson();
                try {
                    ReturnState state = gson.fromJson(arg0.result, ReturnState.class);
                    LogManager.LogShow("guessdata", state.result, LogManager.ERROR);
                    if (Constant.RETURN_OK.equals(state.msg)) {
                        if (ToosUtils.isStringEmpty(state.result)){
                            return;
                        }
                        entity=gson.fromJson(state.result,WinEntity.class);
                        if (entity==null){
                            return;
                        }
                        tip.setText(entity.content);
                        bitmapUtils.display(imageView, entity.products_image);
                        name.setText(Html.fromHtml(entity.products_name + "<br> 价格：<font color=\"#ffbf25\">$" + entity.price + "</font>"));

                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent.setAction("android.intent.action.VIEW");
                                Uri content_url = Uri.parse(entity.products_url);
                                intent.setData(content_url);
                                startActivity(intent);
                            }
                        });

                    }else if(Constant.RETURN_TOKENERROR.equals(state.msg)){
                        ToastUtils.displayShortToast(
                                WinActivity.this, state.result);
                        ToosUtils.goLogin(WinActivity.this);
                    } else {
                        ToastUtils.displayShortToast(
                                WinActivity.this, state.result);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.displaySendFailureToast(WinActivity.this);
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                pro.setVisibility(View.GONE);
                ToastUtils.displaySendFailureToast(WinActivity.this);
            }
        });


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

