package com.bq.comm_config_lib.service.login;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.template.IProvider;
import com.bq.comm_config_lib.configration.AppArouter;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/9
 * 版权：
 */

@Route(path = AppArouter.LOGIN_SERVER)
public interface LoginService extends IProvider {
    String getLoginProvider();
}
