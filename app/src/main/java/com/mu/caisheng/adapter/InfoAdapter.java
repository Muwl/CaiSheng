package com.mu.caisheng.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mu.caisheng.R;
import com.mu.caisheng.model.InfoEntity;
import com.mu.caisheng.utils.TimeUtils;
import com.mu.caisheng.utils.ToosUtils;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Mu on 2015/11/3.
 */
public class InfoAdapter extends BaseAdapter {

    private Context context;
    private List<InfoEntity> entities;

    public InfoAdapter(Context context,List<InfoEntity> entities) {
        this.context = context;
        this.entities=entities;
    }


    @Override
    public int getCount() {
        return entities.size();
    }

    @Override
    public Object getItem(int position) {
        return entities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
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
        holder.tip.setText(entities.get(position).title);
        holder.time.setText(TimeUtils.getNoticeTime(entities.get(position).time));
        holder.content.setText(entities.get(position).content);
        return convertView;
    }

    class ViewHolder {
        public TextView tip;
        public TextView time;
        public TextView content;

    }
}
