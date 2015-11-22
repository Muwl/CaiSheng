package com.mu.caisheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.mu.caisheng.R;
import com.mu.caisheng.utils.ShareDataTool;

/**
 * Created by Mu on 2015/11/18.
 */
public class WelcomeActivity extends BaseActivity{

    private ImageView image;

    private Animation mFadeIn;

    private Animation mFadeInScale;

    private Animation mFadeOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        image= (ImageView) findViewById(R.id.welcome_image);

        new Thread(new Runnable() {

            @Override
            public void run() {

                    try {
                        Thread.sleep(2000);
                        go();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


            }
        }).start();


    }

    private void go() {
        if (ShareDataTool.getStart(WelcomeActivity.this) == 0) {
            Intent intent = new Intent(WelcomeActivity.this,
                    SplashActivity.class);
            startActivity(intent);

            WelcomeActivity.this.finish();
        } else {
            Intent intent = new Intent(WelcomeActivity.this,
                    MainActivity.class);
            startActivity(intent);
            WelcomeActivity.this.finish();
        }

    }
}
