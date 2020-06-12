package com.bq.comm_config_lib.configration;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/2
 * 版权：
 */
public class AppArouter {


    //壳子
    public static final String MAIN = "/main";
    public static final String MAIN_ACTIVITY = "/main/MainActivity";

    //登录模块
    public static final String LOGIN = "/login";
    public static final String LOGIN_ACTVITY = LOGIN+"/LoginActivity";
    public static final String FORGET_PWD_ACTIVITY = LOGIN+"/ForgetPwdActivity";
    public static final String LOGIN_SERVER = LOGIN+"/LoginService";

    //用户中心
    public static final String USER_CENTER = "/userCenter";
//    public static final String USER_CENTER_ACTIVITY = USER_CENTER+"/UserInfoActivity";
    public static final String USER_CENTER_BANK_LIST = USER_CENTER+"/BankListActivity";
    public static final String USER_CENTER_USER_FRAGMENT = USER_CENTER+"/UserInfoFragment";


}
