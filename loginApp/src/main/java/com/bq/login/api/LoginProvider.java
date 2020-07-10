package com.bq.login.api;

import com.bq.comm_config_lib.annotation.Register;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.msgService.MessageBody;
import com.bq.comm_config_lib.msgService.MessageEvent;
import com.bq.comm_config_lib.request.RequestCallBackInter;
import com.bq.comm_config_lib.utils.CommSpUtils;
import com.bq.login.requset.LoginHttpReqeustImp;
import com.google.gson.Gson;

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
public class LoginProvider {

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void providerData(MessageEvent event) {
        if (AppArouter.LOGIN_SERVER.equals(event.key)) {
            event.eventInterface.callBack(new MessageBody(MessageBody.SUCCESS_CODE,"登录模块提供服务"));
        } else if(AppArouter.LOGOUT_SERVER.equals(event.key)){
            new LoginHttpReqeustImp().logout(new RequestCallBackInter() {
                @Override
                public void onSuccess(Object o) {
                    event.eventInterface.callBack(new MessageBody(MessageBody.SUCCESS_CODE,new Gson().toJson(o)));
                    CommSpUtils.clearLoginInfo();
                }
                @Override
                public void onError(String msg) {
                    event.eventInterface.callBack(new MessageBody(MessageBody.FAIL_CODE,msg));
                }
            });
        }
    }
}
