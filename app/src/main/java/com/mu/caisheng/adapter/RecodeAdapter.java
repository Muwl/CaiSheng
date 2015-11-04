package com.mu.caisheng.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mu.caisheng.R;

/**
 * Created by Mu on 2015/11/4.
 */
public class RecodeAdapter extends BaseAdapter {

    private Context context;

    public RecodeAdapter(Context context) {
        this.context = context;
    }


    @Override
    public int getCount() {
        return 5;
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
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.activity_recode_item, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.recode_item_image);
            holder.name = (TextView) convertView.findViewById(R.id.recode_item_name);
            holder.num = (TextView) convertView.findViewById(R.id.recode_item_num);
            holder.time = (TextView) convertView.findViewById(R.id.recode_item_time);
            holder.win = (TextView) convertView.findViewById(R.id.recode_item_win);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    class ViewHolder {
        public ImageView imageView;
        public TextView name;
        public TextView num;
        public TextView time;
        public TextView win;
    }
}
