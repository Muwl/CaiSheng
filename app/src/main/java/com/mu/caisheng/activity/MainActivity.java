package com.mu.caisheng.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.mu.caisheng.R;
import com.mu.caisheng.adapter.MainAdapter;
import com.mu.caisheng.dialog.CustomeDialog;
import com.mu.caisheng.model.GuessEntity;
import com.mu.caisheng.model.PersonDataEntity;
import com.mu.caisheng.model.ReturnState;
import com.mu.caisheng.utils.Constant;
import com.mu.caisheng.utils.DensityUtil;
import com.mu.caisheng.utils.LogManager;
import com.mu.caisheng.utils.ShareDataTool;
import com.mu.caisheng.utils.ShareUtils;
import com.mu.caisheng.utils.TimeUtils;
import com.mu.caisheng.utils.ToastUtils;
import com.mu.caisheng.utils.ToosUtils;
import com.mu.caisheng.view.HorizontalListView;
import com.umeng.message.ALIAS_TYPE;
import com.umeng.message.PushAgent;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.sso.UMSsoHandler;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.Inflater;

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

    private View tit;

    private PopupWindow menuWindow;

    PushAgent mPushAgent;

    private View pro;

    private GuessEntity entity;

    private BitmapUtils bitmapUtils;

    private List<GuessEntity> hotEntities;

    private Timer timer;

    private TimerTask task;

    private UMSocialService mController;

    private View titleRoot;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 40:
                    mController = ShareUtils.Share(MainActivity.this, "我在【猜神到】参与了免费赢得了"+entity.products_name+"大奖。快来一起加入全球最火热的电商猜价游戏吧，赢取每整点时段的时尚大奖，从此当上大猜神，迎娶白富美，走上人生的巅峰！我在猜神到，等你来战！！！APP下载地址：http://101.200.192.147/guess.apk", entity.products_image,"http://101.200.192.147/guess.apk");
                    break;
                case 222:
                    if (entity != null) {
                        entity.now = entity.now + 1;
                        time_text.setText(TimeUtils.getCurTime(entity.now, entity.events_date + 3600));
                        if (entity.now < entity.events_date + 3600) {
                            guess.setClickable(true);
                            guess.setEnabled(true);
                        } else {
                            if (task != null) {
                                task.cancel();
                            }
                            if (timer != null) {
                                timer.cancel();
                            }
                            guess.setEnabled(false);
                            guess.setClickable(false);

                        }
                    }
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPushAgent = PushAgent.getInstance(this);
        mPushAgent.enable();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bitmapUtils = new BitmapUtils(this);
        hotEntities = new ArrayList<>();
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getGuess();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mController != null) {
            /** 使用SSO授权必须添加如下代码 */
            UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
                    requestCode);
            if (ssoHandler != null) {
                ssoHandler.authorizeCallBack(requestCode, resultCode, data);
            }
        }

    }

    private void initView() {
        title_lef = (ImageView) findViewById(R.id.title_lefmenu);
        title = (TextView) findViewById(R.id.title_text);
        tit = findViewById(R.id.main_title);
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
        pro = findViewById(R.id.main_pro);
        titleRoot = tit.findViewById(R.id.title_root);

        title_lef.setVisibility(View.VISIBLE);
        title_rig.setVisibility(View.VISIBLE);
        titleRoot.setBackgroundResource(R.color.title_root);
        title_lef.setOnClickListener(this);
        title_rig.setOnClickListener(this);
        guess.setOnClickListener(this);
        title.setText("猜神到");
        text1.setText(Html.fromHtml("至<font color=\"#f7dc1d\">详情页</font>寻找<font color=\"#f7dc1d\">参考价区间</font>提高中奖率"));
        adapter = new MainAdapter(this, hotEntities);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(hotEntities.get(position).products_url);
                intent.setData(content_url);
                startActivity(intent);
            }
        });
        setPricePoint(input_price);
    }


    public static void setPricePoint(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_lefmenu:
                if (menuWindow == null || menuWindow.isShowing() == false) {
                    menu_press();
                } else {
                    menuWindow.dismiss();

                }
                break;

            case R.id.title_rignotice:
                Intent intent = new Intent(MainActivity.this, TodayadvActivity.class);
                startActivity(intent);

                break;

            case R.id.main_guess:
                subGuess();
//                Intent intent4 = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(intent4);
                break;

            case R.id.menu_data:
                menuWindow.dismiss();
                if (ToosUtils.isStringEmpty(ShareDataTool.getToken(this))) {
                    ToastUtils.displayShortToast(this, "请登录");
                    ToosUtils.goLogin(MainActivity.this);
                    return;
                }
                Intent intent1 = new Intent(MainActivity.this, PersonDataActivity.class);
                intent1.putExtra("flag", 1);
                startActivity(intent1);
                break;
            case R.id.menu_atten:
                menuWindow.dismiss();
                if (ToosUtils.isStringEmpty(ShareDataTool.getToken(this))) {
                    ToastUtils.displayShortToast(this, "请登录");
                    ToosUtils.goLogin(MainActivity.this);
                    return;
                }
                Intent intent2 = new Intent(MainActivity.this, AttenActivity.class);
                startActivity(intent2);

                break;
            case R.id.menu_info:
                menuWindow.dismiss();
                if (ToosUtils.isStringEmpty(ShareDataTool.getToken(this))) {
                    ToastUtils.displayShortToast(this, "请登录");
                    ToosUtils.goLogin(MainActivity.this);
                    return;
                }
                Intent intent3 = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(intent3);

                break;
            case R.id.menu_guess:
                menuWindow.dismiss();
                if (ToosUtils.isStringEmpty(ShareDataTool.getToken(this))) {
                    ToastUtils.displayShortToast(this, "请登录");
                    ToosUtils.goLogin(MainActivity.this);
                    return;
                }
                Intent intent5 = new Intent(MainActivity.this, RecodeActivity.class);
                startActivity(intent5);
                menuWindow.dismiss();
                menuWindow.dismiss();
                break;
            case R.id.menu_about:
                menuWindow.dismiss();
                Intent intent6 = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent6);

                break;

        }

    }


    /**
     * 弹出menu菜单
     */
    public void menu_press() {
        // if (!menu_display) {
        // 获取LayoutInflater实例
        LayoutInflater inflater = getLayoutInflater();
        // 这里的main布局是在inflate中加入的哦，以前都是直接this.setContentView()的吧？呵呵
        // 该方法返回的是一个View的对象，是布局中的根
        View layout = inflater.inflate(R.layout.activity_menu, null);
        View data = layout.findViewById(R.id.menu_data);
        View atten = layout.findViewById(R.id.menu_atten);
        View info = layout.findViewById(R.id.menu_info);
        View recode = layout.findViewById(R.id.menu_guess);
        View about = layout.findViewById(R.id.menu_about);
        data.setOnClickListener(this);
        atten.setOnClickListener(this);
        info.setOnClickListener(this);
        recode.setOnClickListener(this);
        about.setOnClickListener(this);
        int screenWidth = getWindowManager().getDefaultDisplay()
                .getWidth();
        // 下面我们要考虑了，我怎样将我的layout加入到PopupWindow中呢？？？很简单
        menuWindow = new PopupWindow(layout, screenWidth / 2 - 30,
                RelativeLayout.LayoutParams.WRAP_CONTENT); // 后两个参数是width和height
        // menuWindow.showAsDropDown(layout); //设置弹出效果
        // menuWindow.showAsDropDown(null, 0, layout.getHeight());
        // 设置如下四条信息，当点击其他区域使其隐藏，要在show之前配置
        menuWindow.setBackgroundDrawable(new BitmapDrawable());
        menuWindow.setFocusable(true);
        menuWindow.setOutsideTouchable(true);
        menuWindow.update();
        menuWindow.showAtLocation(tit, Gravity.TOP | Gravity.LEFT,
                DensityUtil.dip2px(this, 0),
                DensityUtil.dip2px(this, 74)); // 设置layout在PopupWindow中显示的位置
        // 如何获取我们main中的控件呢？也很简单
        // }
    }

    /**
     * 获取猜的商品
     */
    private void getGuess() {
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        RequestParams rp = new RequestParams();
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH + "getGuessGoods", rp, new RequestCallBack<String>() {
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
                        if (ToosUtils.isStringEmpty(state.result) && "[]".equals(state.result)) {
                            return;
                        }
                        entity = gson.fromJson(state.result, GuessEntity.class);
                        if (entity == null) {
                            return;
                        }
                        getHotGoods(entity.products_id);
                        LogManager.LogShow("====", entity.toString(), LogManager.ERROR);
                        bitmapUtils.display(main_image, entity.products_image);
                        main_image.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (ToosUtils.isStringEmpty(entity.products_url)){
                                    return;
                                }
                                try{
                                    Intent intent = new Intent();
                                    intent.setAction("android.intent.action.VIEW");
                                    Uri content_url = Uri.parse(entity.products_url);
                                    intent.setData(content_url);
                                    startActivity(intent);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }



                            }
                        });
                        if (timer == null && task == null) {
                            timer = new Timer();
                            task = new TimerTask() {
                                @Override
                                public void run() {
                                    Message msg = Message.obtain();
                                    msg.what = 222;
                                    handler.sendMessage(msg);
                                }
                            };
                            timer.schedule(task, 0, 1000);
                        }
                        name.setText(entity.products_name);
                        price.setText("$" + entity.price);
                        freeNum.setText(entity.free_num + "份");
                        comNum.setText(Html.fromHtml("已有<font color=\"#f7dc1d\">" + entity.bidnum + "</font>人出价"));
                        time_text.setText(TimeUtils.getCurTime(entity.now, entity.events_date + 3600));
                    } else {
                        ToastUtils.displayShortToast(
                                MainActivity.this, state.result);
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                pro.setVisibility(View.GONE);
                ToastUtils.displaySendFailureToast(MainActivity.this);
            }
        });


    }

    /**
     * 获取热卖商品
     */
    private void getHotGoods(String id) {
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        RequestParams rp = new RequestParams();
        rp.addBodyParameter("id",id);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH + "getHotGoods", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                Gson gson = new Gson();
                try {
                    ReturnState state = gson.fromJson(arg0.result, ReturnState.class);
                    LogManager.LogShow("guessdata", state.result, LogManager.ERROR);
                    if (Constant.RETURN_OK.equals(state.msg)) {
                        if (ToosUtils.isStringEmpty(state.result)) {
                            return;
                        }
                        Type type = new TypeToken<ArrayList<GuessEntity>>() {
                        }.getType();
                        List<GuessEntity> list = gson.fromJson(state.result, type);
                        if (list == null) {
                            list = new ArrayList<GuessEntity>();
                        }
                        hotEntities.clear();
                        for (int i = 0; i < list.size(); i++) {
                            hotEntities.add(list.get(i));
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        ToastUtils.displayShortToast(
                                MainActivity.this, state.result);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.displaySendFailureToast(MainActivity.this);
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                ToastUtils.displaySendFailureToast(MainActivity.this);
            }
        });


    }


    /**
     * 提交猜价
     */
    private void subGuess() {
        if (ToosUtils.isTextEmpty(input_price)) {
            ToastUtils.displayShortToast(this, "请填写猜价的价格！");
            return;
        }
        if (ToosUtils.isStringEmpty(ShareDataTool.getToken(this))) {
            ToastUtils.displayShortToast(this, "请登录！");
            ToosUtils.goLogin(MainActivity.this);
            return;
        }
        if (entity == null) {
            ToastUtils.displayShortToast(this, "暂无猜价物品！");
            return;
        }
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        RequestParams rp = new RequestParams();
        rp.addBodyParameter("token", ShareDataTool.getToken(this));
        rp.addBodyParameter("id", entity.products_id);
        rp.addBodyParameter("price", ToosUtils.getTextContent(input_price));

        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH + "guessPrice", rp, new RequestCallBack<String>() {
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
                                MainActivity.this, state.result);
                        entity.bidnum=entity.bidnum+1;
                        comNum.setText(Html.fromHtml("已有<font color=\"#f7dc1d\">" + entity.bidnum + "</font>人出价"));
                        input_price.setText("");
                        CustomeDialog dialog = new CustomeDialog(MainActivity.this, handler);
                    } else if (Constant.RETURN_TOKENERROR.equals(state.msg)) {
                        ToastUtils.displayShortToast(
                                MainActivity.this, state.result);
                        ToosUtils.goLogin(MainActivity.this);
                    } else {
                        ToastUtils.displayShortToast(
                                MainActivity.this, state.result);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.displaySendFailureToast(MainActivity.this);
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                pro.setVisibility(View.GONE);
                ToastUtils.displaySendFailureToast(MainActivity.this);
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}
