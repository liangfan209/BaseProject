package com.bq.login.requset;

import com.bq.comm_config_lib.http.SignJsonCallBack;
import com.bq.login.ApiLogin;
import com.bq.login.bean.LoginInfo;
import com.bq.netlibrary.NetManager;
import com.bq.netlibrary.http.BaseResponse;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/16
 * 版权：
 */
public class Reqeust {

    //login
    public void login(String name, String pwd) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiLogin.API_LOGIN);
        map.put("username",name);
        map.put("password",pwd);
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<LoginInfo>>(mIView) {
            @Override
            public void onSuccess(Response<BaseResponse<LoginInfo>> response) {
                super.onSuccess(response); mIView.loginView(response.body().result);
            }

        });
    }
}
