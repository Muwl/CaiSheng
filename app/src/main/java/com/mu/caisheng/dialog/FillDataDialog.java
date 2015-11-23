package com.mu.caisheng.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.mu.caisheng.R;
import com.mu.caisheng.activity.PersonDataActivity;


/**
 * @author Mu
 * @date 2015-3-6
 * @description 普通提示对话框
 */
public class FillDataDialog extends Dialog implements
		View.OnClickListener {
	private Context context;
	private TextView ok;
	private TextView cancel;
	private TextView text;
	public FillDataDialog(Context context) {
		super(context, R.style.dialog2);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.context = context;
		setContentView(R.layout.custom_dialog);
		getWindow().setBackgroundDrawable(new BitmapDrawable());
		show();
		initView();

	}

	private void initView() {
		text = (TextView) findViewById(R.id.custom_dialog_text);
		ok = (TextView) findViewById(R.id.custom_dialog_ok);
		cancel = (TextView) findViewById(R.id.custom_dialog_cancel);
		text.setText("您有中奖纪录，请完善个人信息");
		ok.setOnClickListener(this);
		cancel.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.custom_dialog_ok:
			Intent intent=new Intent(context, PersonDataActivity.class);
			context.startActivity(intent);
			dismiss();
			break;
		case R.id.custom_dialog_cancel:

			dismiss();
			break;
		default:
			break;
		}

	}

}
