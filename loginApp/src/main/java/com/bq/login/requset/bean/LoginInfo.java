package com.bq.login.requset.bean;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/5/28
 * 版权：
 */
public class LoginInfo {


    /**
     * access_token : 4604ae29d195966f
     * expire_time : 1593656313
     * renew_flag : 7c28cb42ce477224
     */

    private String access_token;
    private String expire_time;
    private String renew_flag;
    private int is_password;

    public int getIs_password() {
        return is_password;
    }

    public void setIs_password(int is_password) {
        this.is_password = is_password;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(String expire_time) {
        this.expire_time = expire_time;
    }

    public String getRenew_flag() {
        return renew_flag;
    }

    public void setRenew_flag(String renew_flag) {
        this.renew_flag = renew_flag;
    }
}
