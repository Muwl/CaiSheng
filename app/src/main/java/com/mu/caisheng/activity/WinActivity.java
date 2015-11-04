package com.mu.caisheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mu.caisheng.R;
import com.mu.caisheng.utils.DensityUtil;
import com.mu.caisheng.utils.ShareUtils;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.sso.UMSsoHandler;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);
        initView();
    }

    private void initView() {
        img = (View) findViewById(R.id.win_im);
        tip = (TextView) findViewById(win_tip);
        share = (TextView) findViewById(R.id.win_share);
        close = (TextView) findViewById(R.id.win_close);
        imageView = (ImageView) findViewById(R.id.win_image);
        name = (TextView) findViewById(R.id.win_name);

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

