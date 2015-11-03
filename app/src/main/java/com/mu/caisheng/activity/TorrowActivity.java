package com.mu.caisheng.activity;

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
public class TorrowActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;

    private TextView title_time;

    private ImageView back;

    private GridView gridView;

    private View torrowadv;

    private TodayadvAdapter adapter;

    private int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todayadv);
        width = DensityUtil.getScreenWidth(this);
        initView();

    }

    private void initView() {
        title = (TextView) findViewById(R.id.title_text);
        title_time = (TextView) findViewById(R.id.title_time);
        back = (ImageView) findViewById(R.id.title_back);
        gridView = (GridView) findViewById(R.id.toadyadv_grid);
        torrowadv = (View) findViewById(R.id.toadyadv_tomoview);

        title_time.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        title.setText("明日预告");
        torrowadv.setVisibility(View.GONE);
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
