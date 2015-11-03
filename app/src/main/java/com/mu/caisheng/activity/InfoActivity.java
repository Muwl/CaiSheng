package com.mu.caisheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mu.caisheng.R;
import com.mu.caisheng.adapter.InfoAdapter;

/**
 * Created by Mu on 2015/11/3.
 */
public class InfoActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;

    private ImageView back;

    private ListView listView;

    private InfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        initView();
    }

    private void initView() {
        title = (TextView) findViewById(R.id.title_text);
        back = (ImageView) findViewById(R.id.title_back);
        listView = (ListView) findViewById(R.id.info_list);
        adapter = new InfoAdapter(this);

        title.setText("通知中心");
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(InfoActivity.this, WinActivity.class);
                startActivity(intent);
            }
        });
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
