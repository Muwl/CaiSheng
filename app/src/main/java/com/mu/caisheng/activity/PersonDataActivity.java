package com.mu.caisheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.mu.caisheng.R;
import com.mu.caisheng.model.LoginEntity;
import com.mu.caisheng.model.PersonDataEntity;
import com.mu.caisheng.model.ReturnState;
import com.mu.caisheng.utils.Constant;
import com.mu.caisheng.utils.LogManager;
import com.mu.caisheng.utils.ShareDataTool;
import com.mu.caisheng.utils.ToastUtils;
import com.mu.caisheng.utils.ToosUtils;

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

    private View pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        initView();
        getInfo();

    }

    private void initView() {
        title = (TextView) findViewById(R.id.title_text);
        back = (ImageView) findViewById(R.id.title_back);
        save = (TextView) findViewById(R.id.title_save);
        name = (EditText) findViewById(R.id.data_name);
        phone = (EditText) findViewById(data_phone);
        address = (EditText) findViewById(R.id.data_address);
        pro = findViewById(R.id.data_pro);
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
                saveInfo();
                break;
        }

    }


    private void getInfo() {

        if (ToosUtils.isStringEmpty(ShareDataTool.getToken(this))) {
            return;
        }
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        RequestParams rp = new RequestParams();
        rp.addBodyParameter("token", ShareDataTool.getToken(this));
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH + "login", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                pro.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                pro.setVisibility(View.GONE);
                Gson gson = new Gson();
                try {
                    ReturnState state = gson.fromJson(arg0.result, ReturnState.class);
                    LogManager.LogShow("personData", state.result, LogManager.ERROR);
                    if (Constant.RETURN_OK.equals(state.msg)) {
                        PersonDataEntity entity = gson.fromJson(state.result, PersonDataEntity.class);
                        if (entity == null) {
                            return;
                        }
                        if (!ToosUtils.isStringEmpty(entity.name)) {
                            name.setText(entity.name);
                        }
                        if (!ToosUtils.isStringEmpty(entity.phone)) {
                            phone.setText(entity.phone);
                        }
                        if (!ToosUtils.isStringEmpty(entity.address)) {
                            address.setText(entity.address);
                        }

                    } else {
                        ToastUtils.displayShortToast(
                                PersonDataActivity.this, state.result);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.displaySendFailureToast(PersonDataActivity.this);
                }


            }

            @Override
            public void onFailure(HttpException e, String s) {
                pro.setVisibility(View.GONE);
                ToastUtils.displaySendFailureToast(PersonDataActivity.this);
            }
        });


    }

    private void saveInfo() {
        if (ToosUtils.isStringEmpty(ShareDataTool.getToken(this))) {
            return;
        }
        if (ToosUtils.isTextEmpty(name)) {
            ToastUtils.displayShortToast(this, "姓名不能为空");
            return;
        }

        if (ToosUtils.isTextEmpty(phone)) {
            ToastUtils.displayShortToast(this, "手机不能为空");
            return;
        }

        if (ToosUtils.isTextEmpty(address)) {
            ToastUtils.displayShortToast(this, "地址不能为空");
            return;
        }
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        RequestParams rp = new RequestParams();
        rp.addBodyParameter("token", ShareDataTool.getToken(this));
        rp.addBodyParameter("name", ToosUtils.getTextContent(name));
        rp.addBodyParameter("phone", ToosUtils.getTextContent(phone));
        rp.addBodyParameter("address", ToosUtils.getTextContent(address));
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH + "updatePersonDate", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                pro.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                pro.setVisibility(View.GONE);
                Gson gson = new Gson();
                try {
                    ReturnState state = gson.fromJson(arg0.result, ReturnState.class);
                    LogManager.LogShow("personData", state.result, LogManager.ERROR);
                    if (Constant.RETURN_OK.equals(state.msg)) {
                        ToastUtils.displayShortToast(
                                PersonDataActivity.this, state.result);
                    } else {
                        ToastUtils.displayShortToast(
                                PersonDataActivity.this, state.result);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.displaySendFailureToast(PersonDataActivity.this);
                }


            }

            @Override
            public void onFailure(HttpException e, String s) {
                pro.setVisibility(View.GONE);
                ToastUtils.displaySendFailureToast(PersonDataActivity.this);
            }
        });


    }


}
