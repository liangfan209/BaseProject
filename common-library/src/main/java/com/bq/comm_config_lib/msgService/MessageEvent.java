package com.bq.comm_config_lib.msgService;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/15
 * 版权：
 */
public class MessageEvent {
    public  String key;
    public  EventInter eventInterface;

    public MessageEvent(String key, EventInter eventInter) {
        this.key = key;
        eventInterface = eventInter;
    }
}
