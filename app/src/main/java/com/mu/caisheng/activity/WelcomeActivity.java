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

        mFadeIn = AnimationUtils.loadAnimation(this,
                R.anim.guide_welcome_fade_in);
        mFadeInScale = AnimationUtils.loadAnimation(this,
                R.anim.guide_welcome_fade_in_scale);
        mFadeOut = AnimationUtils.loadAnimation(this,
                R.anim.guide_welcome_fade_out);

        image.startAnimation(mFadeIn);
        mFadeIn.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                image.startAnimation(mFadeInScale);
            }
        });
        mFadeInScale.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
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
                // image.startAnimation(mFadeOut);

            }
            // }
        });

    }
}
