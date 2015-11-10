package com.mu.caisheng.activity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
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
import com.mu.caisheng.adapter.RecodeAdapter;
import com.mu.caisheng.model.InfoEntity;
import com.mu.caisheng.model.RecodeEntity;
import com.mu.caisheng.model.RecodeGoodEntity;
import com.mu.caisheng.model.ReturnState;
import com.mu.caisheng.utils.Constant;
import com.mu.caisheng.utils.LogManager;
import com.mu.caisheng.utils.ShareDataTool;
import com.mu.caisheng.utils.ToastUtils;
import com.mu.caisheng.utils.ToosUtils;

import org.w3c.dom.Text;
import org.w3c.dom.ls.LSInput;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.mu.caisheng.R.id.end;
import static com.mu.caisheng.R.id.title_text;

/**
 * Created by Mu on 2015/11/4.
 */
public class RecodeActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;

    private ImageView back;

    private TextView num;

    private TextView prol;

    private TextView winBest;

    private ListView listView;

    private RecodeAdapter adapter;

    private View pro;

    private List<RecodeGoodEntity> entities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recode);
        entities = new ArrayList<>();
        initView();
        getInfo();
    }

    private void initView() {
        title = (TextView) findViewById(title_text);
        back = (ImageView) findViewById(R.id.title_back);
        num = (TextView) findViewById(R.id.recode_num);
        prol = (TextView) findViewById(R.id.recode_winprob);
        winBest = (TextView) findViewById(R.id.recode_winbest);
        listView = (ListView) findViewById(R.id.recode_list);
        pro = findViewById(R.id.recode_pro);

        title.setText("猜猜记录");
        back.setOnClickListener(this);
        back.setVisibility(View.VISIBLE);
        adapter = new RecodeAdapter(this, entities);
        listView.setAdapter(adapter);

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
        rp.addBodyParameter("token", ShareDataTool.getToken(this));
        LogManager.LogShow("token--------", ShareDataTool.getToken(this), LogManager.ERROR);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH + "record", rp, new RequestCallBack<String>() {
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
                        if (ToosUtils.isStringEmpty(state.result)) {
                            return;
                        }
                        RecodeEntity entity = gson.fromJson(state.result, RecodeEntity.class);
                        num.setText(entity.guessnum+"");
                        prol.setText(entity.win_rate);
                        winBest.setText(entity.win_best);
                        entities.clear();
                        if (entity.products_list == null) {
                            return;
                        }
                        for (int i = 0; i < entity.products_list.size(); i++) {
                            entities.add(entity.products_list.get(i));
                        }
                        adapter.notifyDataSetChanged();
                    } else if (Constant.RETURN_TOKENERROR.equals(state.msg)) {
                        ToastUtils.displayShortToast(
                                RecodeActivity.this, state.result);
                        ToosUtils.goLogin(RecodeActivity.this);
                    } else {
                        ToastUtils.displayShortToast(
                                RecodeActivity.this, state.result);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.displaySendFailureToast(RecodeActivity.this);
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                pro.setVisibility(View.GONE);
                ToastUtils.displaySendFailureToast(RecodeActivity.this);
            }
        });


    }
}
