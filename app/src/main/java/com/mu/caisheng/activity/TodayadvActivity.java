package com.mu.caisheng.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
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
import com.mu.caisheng.adapter.TodayadvAdapter;
import com.mu.caisheng.model.GuessEntity;
import com.mu.caisheng.model.ReturnState;
import com.mu.caisheng.utils.Constant;
import com.mu.caisheng.utils.DensityUtil;
import com.mu.caisheng.utils.LogManager;
import com.mu.caisheng.utils.ShareDataTool;
import com.mu.caisheng.utils.TimeUtils;
import com.mu.caisheng.utils.ToastUtils;
import com.mu.caisheng.utils.ToosUtils;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mu on 2015/11/3.
 */
public class TodayadvActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;

    private TextView title_time;

    private ImageView back;

    private GridView gridView;

    private View torrowadv;

    private TodayadvAdapter adapter;

    private int width;

    private View pro;

    private List<GuessEntity> entities;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 100:
                    int position=msg.arg1;
                    addAttention(position);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todayadv);
        width = DensityUtil.getScreenWidth(this);
        entities=new ArrayList<>();
        initView();


    }

    @Override
    protected void onResume() {
        super.onResume();
        getTodayAdv();
    }

    private void initView() {
        title = (TextView) findViewById(R.id.title_text);
        title_time = (TextView) findViewById(R.id.title_time);
        back = (ImageView) findViewById(R.id.title_back);
        gridView = (GridView) findViewById(R.id.toadyadv_grid);
        torrowadv = (View) findViewById(R.id.toadyadv_tomoview);
        pro=findViewById(R.id.toadyadv_pro);


        title_time.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        title.setText("今日预告");
        title_time.setText(TimeUtils.getNowdate());
        torrowadv.setOnClickListener(this);
        adapter = new TodayadvAdapter(this, width,entities,handler,1);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(entities.get(position).products_url);
                intent.setData(content_url);
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
            case R.id.toadyadv_tomoview:
                Intent intent=new Intent(TodayadvActivity.this,TorrowActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 获取今天预告
     */
    private void getTodayAdv() {
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        RequestParams rp = new RequestParams();
        if (!ToosUtils.isStringEmpty(ShareDataTool.getToken(this))){
            rp.addBodyParameter("token",ShareDataTool.getToken(this));
        }
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH + "todayForecast", rp, new RequestCallBack<String>() {
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
                        Type type = new TypeToken<ArrayList<GuessEntity>>() {}.getType();
                        List<GuessEntity> list=gson.fromJson(state.result, type);
                        if (list==null){
                            list=new ArrayList<GuessEntity>();
                        }
                        entities.clear();
                        for (int i=0;i<list.size();i++){
                            entities.add(list.get(i));
                        }
                        adapter.notifyDataSetChanged();
                    }else if(Constant.RETURN_TOKENERROR.equals(state.msg)){
                        ToastUtils.displayShortToast(
                                TodayadvActivity.this, state.result);
                        ToosUtils.goLogin(TodayadvActivity.this);
                    } else {
                        ToastUtils.displayShortToast(
                                TodayadvActivity.this, state.result);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.displaySendFailureToast(TodayadvActivity.this);
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                pro.setVisibility(View.GONE);
                ToastUtils.displaySendFailureToast(TodayadvActivity.this);
            }
        });


    }


    /**
     * 关注取消关注
     */
    private void addAttention(final int position) {
        if (ToosUtils.isStringEmpty(ShareDataTool.getToken(this))){
            ToastUtils.displayShortToast(this,"请登录！");
            ToosUtils.goLogin(this);
            return;
        }
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        RequestParams rp = new RequestParams();
        rp.addBodyParameter("token",ShareDataTool.getToken(this));
        rp.addBodyParameter("id", entities.get(position).products_id);
        if (entities.get(position).favorite==1){
            rp.addBodyParameter("flag","0");
        }else{
            rp.addBodyParameter("flag","1");
        }

        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH + "favorite", rp, new RequestCallBack<String>() {
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
                        ToastUtils.displayShortToast(
                                TodayadvActivity.this, state.result);
                        if (entities.get(position).favorite==1){
                            entities.get(position).favorite=0;
                        }else{
                            entities.get(position).favorite=1;
                        }
                        adapter.notifyDataSetChanged();
                    } else if (Constant.RETURN_TOKENERROR.equals(state.msg)) {
                        ToastUtils.displayShortToast(
                                TodayadvActivity.this, state.result);
                        ToosUtils.goLogin(TodayadvActivity.this);
                    } else {
                        ToastUtils.displayShortToast(
                                TodayadvActivity.this, state.result);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.displaySendFailureToast(TodayadvActivity.this);
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                pro.setVisibility(View.GONE);
                ToastUtils.displayFailureToast(TodayadvActivity.this);
            }
        });
    }
}
