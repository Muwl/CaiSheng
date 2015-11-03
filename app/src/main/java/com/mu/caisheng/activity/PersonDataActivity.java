package com.mu.caisheng.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mu.caisheng.R;

import static com.mu.caisheng.R.id.data_phone;

/**
 * Created by Mu on 2015/11/3.
 */
public class PersonDataActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;

    private ImageView back;

    private TextView save;

    private EditText name;

    private EditText phone;

    private EditText address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        initView();

    }

    private void initView() {

        title = (TextView) findViewById(R.id.title_text);
        back = (ImageView) findViewById(R.id.title_back);
        save = (TextView) findViewById(R.id.title_save);
        name = (EditText) findViewById(R.id.data_name);
        phone = (EditText) findViewById(data_phone);
        address = (EditText) findViewById(R.id.data_address);

        title.setText("个人资料");
        back.setOnClickListener(this);
        back.setVisibility(View.VISIBLE);
        save.setOnClickListener(this);
        save.setVisibility(View.VISIBLE);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_save:
                finish();
                break;
        }

    }
}
