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
    public int aType;
    public String phoneNumber;




    public MessageEvent(String key, String orderId, Activity a,String price,String payType,int aType) {
        this.key = key;
        this.orderId = orderId;
        this.activity = a;
        this.price = price;
        this.payType = payType;
        this.aType = aType;
    }

    public MessageEvent(String key, MessageInter eventInter) {
        this.key = key;
        eventInterface = eventInter;
    }
    public MessageEvent(String key, Activity a,MessageInter eventInter) {
        this.key = key;
        this.activity = a;
        this.eventInterface = eventInter;
    }

    public MessageEvent(String key, Activity activity, String phoneNumber) {
        this.key = key;
        this.activity = activity;
        this.phoneNumber = phoneNumber;
    }
    public MessageEvent(String key, Activity activity) {
        this.key = key;
        this.activity = activity;
    }
}
