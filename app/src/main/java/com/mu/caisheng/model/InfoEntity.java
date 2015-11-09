package com.mu.caisheng.model;

import java.io.Serializable;

/**
 * Created by Mu on 2015/11/9.
 */
public class InfoEntity implements Serializable{

    public int id;
    public String title;
    public int time;
    public String content;
    public int type; // 100 中奖  101 关注商品提前推送
}
