package com.bq.app.api;

import com.bq.comm_config_lib.annotation.Register;
import com.bq.comm_config_lib.msgService.MessageBody;
import com.bq.comm_config_lib.msgService.MessageEvent;
import com.bq.utilslib.AppUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/22
 * 版权：
 */
@Register
public class LoginConfigProvider {

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getLoginConfig(MessageEvent event){
        if ("config/login".equals(event.key)) {
            String configStr = AppUtils.getAssetJson(AppUtils.getApp(), "login_login_config.json");
            event.eventInterface.callBack(new MessageBody(MessageBody.SUCCESS_CODE,configStr));
        }
    }
}
