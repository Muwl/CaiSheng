package com.mu.caisheng.activity;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mu.caisheng.R;
import com.mu.caisheng.adapter.MainAdapter;
import com.mu.caisheng.view.HorizontalListView;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ImageView title_lef;

    private TextView title;

    private TextView title_rig;

    private ImageView main_image;

    private TextView time_text;

    private TextView name;

    private TextView price;

    private TextView freeNum;

    private TextView text1;

    private EditText input_price;

    private TextView guess;

    private TextView comNum;

    private HorizontalListView listView;

    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        title_lef = (ImageView) findViewById(R.id.title_lefmenu);
        title = (TextView) findViewById(R.id.title_text);
        title_rig = (TextView) findViewById(R.id.title_rignotice);
        main_image = (ImageView) findViewById(R.id.main_image);
        time_text = (TextView) findViewById(R.id.main_time);
        name = (TextView) findViewById(R.id.main_name);
        price = (TextView) findViewById(R.id.main_money);
        freeNum = (TextView) findViewById(R.id.main_freenum);
        text1 = (TextView) findViewById(R.id.main_text1);
        input_price = (EditText) findViewById(R.id.main_input_price);
        guess = (TextView) findViewById(R.id.main_guess);
        comNum = (TextView) findViewById(R.id.main_comnum);
        listView = (HorizontalListView) findViewById(R.id.main_list);

        title_lef.setOnClickListener(this);
        title_rig.setOnClickListener(this);
        guess.setOnClickListener(this);
        title.setText("财神到");
        text1.setText(Html.fromHtml("至<font color=\"#d02c06\">详情页</font>寻找<font color=\"#d02c06\">参考价区间</font>提高中奖率"));
        adapter = new MainAdapter(this);
        listView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_lefmenu:

                break;

            case R.id.title_rignotice:

                break;

            case R.id.main_guess:

                break;

        }

    }
}
