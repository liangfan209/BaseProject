package com.bq.comm_config_lib.utils;

import com.bq.comm_config_lib.configration.SpField;
import com.bq.comm_config_lib.msgService.bean.UserInfo;
import com.bq.comm_config_lib.request.LoginBean;
import com.bq.utilslib.SPUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

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

    public static void saveUserInfo(String userinfo){
        SPUtils.getInstance(SpField.AUTH_TOKEN).put(SpField.AUTH_USERINFO, userinfo);
    }


    public static UserInfo getUserInfo(){
        String userinfoStr = SPUtils.getInstance(SpField.AUTH_TOKEN).getString(SpField.AUTH_USERINFO);
        return new Gson().fromJson(userinfoStr,UserInfo.class);
    }



    public static String getToken() {
        LoginBean loginBean = getLoginBean();
        if (loginBean != null) {
            return loginBean.getAccess_token();
        } else {
            return "";
        }
    }

    public static List<Integer> getGoodsIds() {
        LoginBean loginBean = getLoginBean();
        if(loginBean == null){
            return new ArrayList<>();
        }
        List<Integer> goods_ids = loginBean.getGoods_ids();
        if(goods_ids == null){
            return new ArrayList<>();
        }
        return loginBean.getGoods_ids();
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


    public static boolean getWelecom() {
      return SPUtils.getInstance(SpField.AUTH_HAS_WELCOM).getBoolean(SpField.AUTH_HAS_WELCOM,false);
    }
    public static void saveWelecom(boolean b) {
        SPUtils.getInstance(SpField.AUTH_HAS_WELCOM).put(SpField.AUTH_HAS_WELCOM, b);
    }


    public static LoginBean getLoginBean() {
        String loginInfoStr = SPUtils.getInstance(SpField.AUTH_TOKEN).getString(SpField.AUTH_TOKEN);
        LoginBean loginBean = new Gson().fromJson(loginInfoStr, LoginBean.class);
        return loginBean;
    }

    //保持选择和定位地址
    public static void saveLocaltion(String city){
        SPUtils.getInstance(SpField.AUTH_TOKEN).put("city", city);
    }

    public static String getLocation(){
        return SPUtils.getInstance(SpField.AUTH_TOKEN).getString("city", "武汉市");
    }
}
