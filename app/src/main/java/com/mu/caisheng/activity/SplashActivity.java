package com.mu.caisheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mu.caisheng.R;
import com.mu.caisheng.utils.ShareDataTool;
import com.mu.caisheng.view.MyScrollLayout;
import com.mu.caisheng.view.OnViewChangeListener;

/**
 * Created by Mu on 2015/11/18.
 */
public class SplashActivity extends BaseActivity implements OnViewChangeListener {

    private MyScrollLayout mScrollLayout;

    private ImageView[] imgs;

    private int count;

    private int currentItem;


    private View ok;


    private RelativeLayout mainRLayout;


    private LinearLayout pointLLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
    }

    private void initView() {
        mScrollLayout= (MyScrollLayout) findViewById(R.id.splash_scrollayout);
        ok= (ImageView) findViewById(R.id.splash_ok);
        mainRLayout= (RelativeLayout) findViewById(R.id.splash);
        pointLLayout= (LinearLayout) findViewById(R.id.splash_llayout);
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        // 设置新任务按钮的大小和位置（模糊）
      //  RelativeLayout.LayoutParams laParams = (RelativeLayout.LayoutParams) ok.getLayoutParams();
//        laParams.width = (int) (0.48 * width);
//        laParams.height = (int) (0.09 * height);
//        laParams.setMargins((int) (0.26 * width), (int) (0.65 * height), 0, 0);
//        ok.setLayoutParams(laParams);
        ok.setOnClickListener(onClick);
        count = mScrollLayout.getChildCount();
        imgs = new ImageView[count];
        for (int i = 0; i < count; i++) {
            imgs[i] = (ImageView) pointLLayout.getChildAt(i);
            imgs[i].setEnabled(true);
            imgs[i].setTag(i);
        }
        currentItem = 0;
        imgs[currentItem].setEnabled(false);
        mScrollLayout.SetOnViewChangeListener(this);
    }
    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.splash_ok:
                    ShareDataTool.saveStart(SplashActivity.this, 1);
                    Intent intent = new Intent(
                            SplashActivity.this,
                            MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };

    @Override
    public void OnViewChange(int position) {
        setcurrentPoint(position);
    }

    private void setcurrentPoint(int position) {
        if (position < 0 || position > count - 1 || currentItem == position) {
            return;
        }
        imgs[currentItem].setEnabled(true);
        imgs[position].setEnabled(false);
        currentItem = position;
    }
}
