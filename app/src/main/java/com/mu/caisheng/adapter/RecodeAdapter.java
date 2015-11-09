package com.mu.caisheng.adapter;

import android.app.job.JobInfo;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.mu.caisheng.R;
import com.mu.caisheng.model.RecodeGoodEntity;
import com.mu.caisheng.utils.TimeUtils;

import java.util.List;

/**
 * Created by Mu on 2015/11/4.
 */
public class RecodeAdapter extends BaseAdapter {

    private Context context;
    private List<RecodeGoodEntity> entities;
    private BitmapUtils bitmapUtils;

    public RecodeAdapter(Context context,List<RecodeGoodEntity> entities) {
        this.context = context;
        this.entities=entities;
        bitmapUtils=new BitmapUtils(context);
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
        bitmapUtils.display(holder.imageView,entities.get(position).products_image);
        holder.name.setText(entities.get(position).products_name);
        holder.num.setText("已有" + entities.get(position).salenum + "人出价");
        holder.time.setText(TimeUtils.getNoticeTime(entities.get(position).events_date));
        holder.win.setText(entities.get(position).report);
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
