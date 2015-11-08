package com.mu.caisheng.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/11/8.猜的物品
 */
public class GuessEntity implements Serializable{
    public String products_id;//商品id
    public String products_name;//商品名称
    public float price;//商品价格
    public int free_num;//免费数量
    public int bidnum;//多少人出价
    public int salenum;//销量
    public String products_url;//链接
    public String products_image;//商品图片
    public int events_date;//竞价开始时间
    public int now;//现在的时间
    public int favorite;//关注 0未关注 1关注

    @Override
    public String toString() {
        return "GuessEntity{" +
                "products_id='" + products_id + '\'' +
                ", products_name='" + products_name + '\'' +
                ", price=" + price +
                ", free_num=" + free_num +
                ", bidnum=" + bidnum +
                ", salenum=" + salenum +
                ", products_url='" + products_url + '\'' +
                ", products_image='" + products_image + '\'' +
                ", events_date=" + events_date +
                ", now=" + now +
                ", favorite=" + favorite +
                '}';
    }
}
