package com.bq.login.provider;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.service.login.LoginService;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/9
 * 版权：
 */
@Route(path = AppArouter.LOGIN_SERVER)
public class LoginServiceImp implements LoginService {
    @Override
    public String getLoginInfo() {
        return "hello login";
    }

    @Override
    public void init(Context context) {

    }
}
