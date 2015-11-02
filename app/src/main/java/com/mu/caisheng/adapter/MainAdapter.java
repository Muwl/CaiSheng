package com.mu.caisheng.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mu.caisheng.R;

/**
 * Created by Mu on 2015/11/2.
 */
public class MainAdapter extends BaseAdapter {

    private Context context;

    public MainAdapter(Context context) {
        this.context = context;
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.activity_main_item, null);
            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) convertView.findViewById(R.id.main_item_image);
            viewHolder.name = (TextView) convertView.findViewById(R.id.main_item_name);
            viewHolder.money = (TextView) convertView.findViewById(R.id.main_item_price);
            viewHolder.num = (TextView) convertView.findViewById(R.id.main_item_num);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    class ViewHolder {
        public ImageView image;
        public TextView name;
        public TextView money;
        public TextView num;
    }
}
