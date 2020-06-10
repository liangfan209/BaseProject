package com.bq.comm_config_lib.configration;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/2
 * 版权：
 */
public class AppArouter {


    //登录模块
    public static final String LOGIN = "/login";
    public static final String TEMPLTE_LOGIN = LOGIN+"/LoginActivity";
    public static final String TEMPLTE_FORGET_PWD = LOGIN+"/ForgetPwdActivity";

    public static final String TEMPLET_LOGIN_SERVER = LOGIN+"/LoginService";

    //用户模块
    public static final String USER = "/user";
    public static final String TEMPLETE_USER_INFO = USER+"/UserInfoActivity";

}
