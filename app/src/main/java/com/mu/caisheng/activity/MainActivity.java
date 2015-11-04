package com.mu.caisheng.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mu.caisheng.R;
import com.mu.caisheng.adapter.MainAdapter;
import com.mu.caisheng.utils.DensityUtil;
import com.mu.caisheng.view.HorizontalListView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
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

        title_lef.setVisibility(View.VISIBLE);
        title_rig.setVisibility(View.VISIBLE);
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
                Intent intent4 = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent4);

                break;

            case R.id.menu_data:
                Intent intent1 = new Intent(MainActivity.this, PersonDataActivity.class);
                startActivity(intent1);
                menuWindow.dismiss();
                break;
            case R.id.menu_atten:
                Intent intent2 = new Intent(MainActivity.this, AttenActivity.class);
                startActivity(intent2);
                menuWindow.dismiss();
                break;
            case R.id.menu_info:
                Intent intent3 = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(intent3);
                menuWindow.dismiss();
                break;
            case R.id.menu_guess:
                Intent intent5 = new Intent(MainActivity.this, RecodeActivity.class);
                startActivity(intent5);

                menuWindow.dismiss();
                break;
            case R.id.menu_about:
                Intent intent6 = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent6);
                menuWindow.dismiss();
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
}
