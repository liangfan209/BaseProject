package com.bq.comm_config_lib.utils;

import com.bq.comm_config_lib.configration.SpField;
import com.bq.comm_config_lib.request.LoginBean;
import com.bq.utilslib.SPUtils;
import com.google.gson.Gson;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/11
 * 版权：
 */
public class CommSpUtils {

    public static void clearLoginInfo() {
        SPUtils.getInstance(SpField.AUTH_TOKEN).put(SpField.AUTH_TOKEN, "");
    }

    public static void saveLoginInfo(String loginInfo) {
        SPUtils.getInstance(SpField.AUTH_TOKEN).put(SpField.AUTH_TOKEN, loginInfo);
    }

    public static String getToken() {
        LoginBean loginBean = getLoginBean();
        if (loginBean != null) {
            return loginBean.getAccess_token();
        } else {
            return "";
        }
    }

    public static String getRenewFlag() {
        LoginBean loginBean = getLoginBean();
        if (loginBean != null)
            return loginBean.getRenew_flag();
        return "";
    }

    public static String getExpireTime() {
        LoginBean loginBean = getLoginBean();
        if (loginBean != null)
            return loginBean.getExpire_time();
        return "";
    }

    public static LoginBean getLoginBean() {
        String loginInfoStr = SPUtils.getInstance(SpField.AUTH_TOKEN).getString(SpField.AUTH_TOKEN);
        LoginBean loginBean = new Gson().fromJson(loginInfoStr, LoginBean.class);
        return loginBean;
    }

    //获取定位地址
    public static String getLocation(){
        return "武汉";
    }
}
