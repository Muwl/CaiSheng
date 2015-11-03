package com.mu.caisheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mu.caisheng.R;
import com.mu.caisheng.adapter.TodayadvAdapter;
import com.mu.caisheng.utils.DensityUtil;

/**
 * Created by Mu on 2015/11/3.
 */
public class AttenActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;


    private ImageView back;

    private GridView gridView;

    private TodayadvAdapter adapter;

    private int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atten);
        width = DensityUtil.getScreenWidth(this);
        initView();

    }

    private void initView() {
        title = (TextView) findViewById(R.id.title_text);
        back = (ImageView) findViewById(R.id.title_back);
        gridView = (GridView) findViewById(R.id.atten_grid);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        title.setText("我的关注");
        adapter = new TodayadvAdapter(this, width);
        gridView.setAdapter(adapter);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
        }
    }
}
