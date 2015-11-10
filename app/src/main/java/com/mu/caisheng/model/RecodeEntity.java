package com.mu.caisheng.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/11/9.
 */
public class RecodeEntity implements Serializable{

    public int guessnum;//次数

    public String win_rate;//中奖率

    public String win_best;//最佳成绩

    public List<RecodeGoodEntity> products_list;//列表数据


}
