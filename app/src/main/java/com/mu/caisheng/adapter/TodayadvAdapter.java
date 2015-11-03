package com.mu.caisheng.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mu.caisheng.R;
import com.mu.caisheng.utils.DensityUtil;

/**
 * Created by Mu on 2015/11/3.
 */
public class TodayadvAdapter extends BaseAdapter {

    private Context context;
    private int width;

    public TodayadvAdapter(Context context, int width) {
        this.context = context;
        this.width = width;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.activity_toadyadv_item, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.toadyadv_item_image);
            holder.name = (TextView) convertView.findViewById(R.id.toadyadv_item_name);
            holder.price = (TextView) convertView.findViewById(R.id.toadyadv_item_price);
            holder.time = (TextView) convertView.findViewById(R.id.toadyadv_item_time);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.toadyadv_item_atten);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.imageView.getLayoutParams();
        params.width = (width - DensityUtil.dip2px(context, 32)) / 2;
        params.height = (width - DensityUtil.dip2px(context, 32)) / 2;
        holder.imageView.setLayoutParams(params);
        return convertView;
    }

    class ViewHolder {
        public ImageView imageView;
        public TextView name;
        public TextView price;
        public TextView time;
        public CheckBox checkBox;

    }
}
