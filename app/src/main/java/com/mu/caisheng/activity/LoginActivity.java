package com.mu.caisheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mu.caisheng.R;

/**
 * Created by Mu on 2015/11/3.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText name;

    private EditText code;

    private TextView getCode;

    private TextView ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        name = (EditText) findViewById(R.id.login_phone);
        code = (EditText) findViewById(R.id.login_code);
        getCode = (TextView) findViewById(R.id.login_getcode);
        ok = (TextView) findViewById(R.id.login_ok);

        getCode.setOnClickListener(this);
        ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_getcode:

                break;
            case R.id.login_ok:
                Intent intent = new Intent(LoginActivity.this, PersonDataActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
