package com.clkj.user_center.api;

import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.msgService.MessageEvent;
import com.bq.comm_config_lib.msgService.MessageInter;

import org.greenrobot.eventbus.EventBus;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/17
 * 版权：
 */
public class ComponentService {

    public static void logout(MessageInter messageInter){
        EventBus.getDefault().post(new MessageEvent(AppArouter.LOGOUT_SERVER,messageInter));
    }

}
