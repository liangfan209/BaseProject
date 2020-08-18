package com.bq.comm_config_lib.msgService;

import android.app.Activity;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/15
 * 版权：
 */
public class MessageEvent {
    public String key;
    public MessageInter eventInterface;
    public String orderId;
    public Activity activity;
    public String price;
    public String payType;


    public MessageEvent(String key, String orderId, Activity a,String price,String payType) {
        this.key = key;
        this.orderId = orderId;
        this.activity = a;
        this.price = price;
        this.payType = payType;
    }

    public MessageEvent(String key, MessageInter eventInter) {
        this.key = key;
        eventInterface = eventInter;
    }

}
