package com.clkj.login;

import android.content.Context;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.StringUtils;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.utils.CommSpUtils;

/**
 * 文件名：
 * 描述： 页面的拦截器
 * 作者：梁帆
 * 时间：2020/5/29
 * 版权：
 */
@Interceptor(priority = 2)
public class ActivityInterceptor implements IInterceptor {
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        String token = CommSpUtils.getToken();
        String path = postcard.getPath();
        if (StringUtils.isEmpty(token)) {
            if (AppArouter.USER_CENTER_USER_INFO_ACTIVITY.equals(path) ||
                    AppArouter.USER_CENTER_ADDRESS_LIST.equals(path) ||
                    AppArouter.USER_CENTER_ADDRESS_SELECT.equals(path) ||
                    AppArouter.USER_CENTER_CETIFICATION_ACTIVITY.equals(path) || //实名认证
                    AppArouter.WALLET_MY_ACTIVITY.equals(path) || //我的钱包
                    AppArouter.USER_CENTER_ADDRESS_LIST.equals(path) || //地址管理
                    AppArouter.USER_CENTER_BANK_LIST.equals(path) ||  //银行卡管理
                    AppArouter.ORDER_LIST_ACTIVITY.equals(path) ||  //订单管理
                    AppArouter.ORDER_ORDER_COMMIT_ACTIVITY.equals(path) ||  //订单管理
                    AppArouter.ORDER_SCAN_ACTIVITY.equals(path) ||  //扫码
                    AppArouter.USER_CENTER_MESSAGE_ACTIVITY.equals(path) ||  //消息
                    AppArouter.LOGIN_MODIFY_ACTIVITY.equals(path)) { //修改密码
                Bundle optionsBundle = postcard.getOptionsBundle();
                ARouter.getInstance().build(AppArouter.LOGIN_ACTVITY)
                        .withBundle("mBundle", optionsBundle)
                        .withString("mPath", path).navigation();
                return;
            }
        }
        System.out.println("==============" + postcard.getPath());
        callback.onContinue(postcard);
    }

    @Override
    public void init(Context context) {

    }
}
