package com.mu.caisheng.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mu.caisheng.R;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
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
    }

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
                break;
            case R.id.about_sina:
                break;
            case R.id.about_web:
                break;
            case R.id.about_apply:
                break;
        }
    }
}

