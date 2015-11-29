package com.mu.caisheng.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.mu.caisheng.R;
import com.mu.caisheng.model.GuessEntity;
import com.mu.caisheng.utils.DensityUtil;
import com.mu.caisheng.utils.LogManager;
import com.mu.caisheng.utils.TimeUtils;
import com.mu.caisheng.utils.ToosUtils;

import java.util.List;

/**
 * Created by Mu on 2015/11/3.
 */
public class TodayadvAdapter extends BaseAdapter {

    private Context context;
    private int width;
    private List<GuessEntity> entities;
    private Handler handler;
    private BitmapUtils bitmapUtils;
    private int flag;

    public TodayadvAdapter(Context context, int width,List<GuessEntity> entities,Handler handler,int flag) {
        this.context = context;
        this.width = width;
        this.entities=entities;
        this.handler=handler;
        this.flag=flag;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.activity_toadyadv_item, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.toadyadv_item_image);
            holder.name = (TextView) convertView.findViewById(R.id.toadyadv_item_name);
            holder.price = (TextView) convertView.findViewById(R.id.toadyadv_item_price);
            holder.time = (TextView) convertView.findViewById(R.id.toadyadv_item_time);
            holder.checkBox = (ImageView) convertView.findViewById(R.id.toadyadv_item_atten);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.imageView.getLayoutParams();
        params.width = (width - DensityUtil.dip2px(context, 32)) / 2;
        params.height = (width - DensityUtil.dip2px(context, 32)) / 2;
        holder.imageView.setLayoutParams(params);

        bitmapUtils.display(holder.imageView, entities.get(position).products_image);
        holder.name.setText(entities.get(position).products_name);
        holder.price.setText("$" + entities.get(position).price);
        if (flag==1){
            holder.time.setText(TimeUtils.getHour(entities.get(position).events_date));
        }else{
            holder.time.setText(TimeUtils.getMin(entities.get(position).events_date));
        }


        if (entities.get(position).favorite==1){
            holder.checkBox.setImageResource(R.mipmap.atten_checked);
           // holder.checkBox.setChecked(true);
        }else{
            holder.checkBox.setImageResource(R.mipmap.atten_normal);
           // holder.checkBox.setChecked(false);
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message=new Message();
                message.what=100;
                message.arg1=position;
                handler.sendMessage(message);
            }
        });
        return convertView;
    }

    class ViewHolder {
        public ImageView imageView;
        public TextView name;
        public TextView price;
        public TextView time;
        public ImageView checkBox;

    }
}
