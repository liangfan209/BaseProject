package com.bq.user_center.provider;

import com.bq.comm_config_lib.annotation.Register;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.msgService.MessageEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/9
 * 版权：
 */
@Register
public class UserCenterService {

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void providerData(MessageEvent event) {
        if(AppArouter.USER_CENTER_SERVICE.equals(event.key)){
            event.eventInterface.callBack("==>>  这个是用户中心提供的数据");
        }
    }
}
