package com.mu.caisheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.mu.caisheng.R;
import com.mu.caisheng.adapter.InfoAdapter;
import com.mu.caisheng.model.GuessEntity;
import com.mu.caisheng.model.InfoEntity;
import com.mu.caisheng.model.ReturnState;
import com.mu.caisheng.utils.Constant;
import com.mu.caisheng.utils.LogManager;
import com.mu.caisheng.utils.ShareDataTool;
import com.mu.caisheng.utils.ToastUtils;
import com.mu.caisheng.utils.ToosUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mu on 2015/11/3.
 */
public class InfoActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;

    private ImageView back;

    private ListView listView;

    private InfoAdapter adapter;

    private View pro;

    private List<InfoEntity> entities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        entities=new ArrayList<>();
        initView();
    }

    private void initView() {
        title = (TextView) findViewById(R.id.title_text);
        back = (ImageView) findViewById(R.id.title_back);
        listView = (ListView) findViewById(R.id.info_list);
        pro=findViewById(R.id.info_pro);
        adapter = new InfoAdapter(this,entities);

        title.setText("通知中心");
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (entities.get(position).type==100){
                    Intent intent = new Intent(InfoActivity.this, WinActivity.class);
                    intent.putExtra("id",2);
                    startActivity(intent);
              }

            }
        });
        getInfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;

        }
    }

    /**
     * 获取列表
     */
    private void getInfo() {
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        RequestParams rp = new RequestParams();
        rp.addBodyParameter("token",ShareDataTool.getToken(this));
        LogManager.LogShow("token--------",ShareDataTool.getToken(this),LogManager.ERROR);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH + "notice", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                pro.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                pro.setVisibility(View.GONE);
                Gson gson = new Gson();
                try {
                    ReturnState state = gson.fromJson(arg0.result, ReturnState.class);
                    LogManager.LogShow("guessdata", state.result, LogManager.ERROR);
                    if (Constant.RETURN_OK.equals(state.msg)) {
                        if (ToosUtils.isStringEmpty(state.result)){
                            return;
                        }
                       Type type = new TypeToken<ArrayList<InfoEntity>>() {}.getType();
                        List<InfoEntity> list=gson.fromJson(state.result, type);
                        if (list==null){
                            list=new ArrayList<InfoEntity>();
                        }
                        entities.clear();
                        for (int i=0;i<list.size();i++){
                            entities.add(list.get(i));
                        }
                        adapter.notifyDataSetChanged();
                    }else if(Constant.RETURN_TOKENERROR.equals(state.msg)){
                        ToastUtils.displayShortToast(
                                InfoActivity.this, state.result);
                        ToosUtils.goLogin(InfoActivity.this);
                    } else {
                        ToastUtils.displayShortToast(
                                InfoActivity.this, state.result);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.displaySendFailureToast(InfoActivity.this);
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                pro.setVisibility(View.GONE);
                ToastUtils.displaySendFailureToast(InfoActivity.this);
            }
        });


    }
}
