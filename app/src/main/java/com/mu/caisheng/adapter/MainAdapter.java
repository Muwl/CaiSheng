package com.mu.caisheng.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.mu.caisheng.R;
import com.mu.caisheng.model.GuessEntity;

import java.util.List;

/**
 * Created by Mu on 2015/11/2.
 */
public class MainAdapter extends BaseAdapter {

    private Context context;
    private List<GuessEntity> entities;
    private BitmapUtils bitmapUtils;

    public MainAdapter(Context context,List<GuessEntity> entities) {
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
        bitmapUtils.display(viewHolder.image,entities.get(position).products_image);
        viewHolder.name.setText(entities.get(position).products_name);
        viewHolder.money.setText("现价:" + entities.get(position).price);
        viewHolder.num.setText("销量:"+entities.get(position).salenum);

        return convertView;
    }

    class ViewHolder {
        public ImageView image;
        public TextView name;
        public TextView money;
        public TextView num;
    }
}
