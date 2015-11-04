package com.mu.caisheng.activity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mu.caisheng.R;
import com.mu.caisheng.adapter.RecodeAdapter;

import org.w3c.dom.Text;
import org.w3c.dom.ls.LSInput;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recode);
        initView();
    }

    private void initView() {
        title = (TextView) findViewById(title_text);
        back = (ImageView) findViewById(R.id.title_back);
        num = (TextView) findViewById(R.id.recode_num);
        prol = (TextView) findViewById(R.id.recode_winprob);
        winBest = (TextView) findViewById(R.id.recode_winbest);
        listView = (ListView) findViewById(R.id.recode_list);

        title.setText("猜猜记录");
        back.setOnClickListener(this);
        back.setVisibility(View.VISIBLE);
        adapter = new RecodeAdapter(this);
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
}
