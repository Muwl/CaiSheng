package com.mu.caisheng.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mu.caisheng.R;

import org.w3c.dom.Text;

/**
 * Created by Mu on 2015/11/3.
 */
public class InfoAdapter extends BaseAdapter {

    private Context context;

    public InfoAdapter(Context context) {
        this.context = context;
    }


    @Override
    public int getCount() {
        return 8;
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
            convertView = View.inflate(context, R.layout.activity_info_item, null);
            holder.tip = (TextView) convertView.findViewById(R.id.info_item_tip);
            holder.time = (TextView) convertView.findViewById(R.id.info_item_time);
            holder.content = (TextView) convertView.findViewById(R.id.info_item_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    class ViewHolder {
        public TextView tip;
        public TextView time;
        public TextView content;

    }
}
