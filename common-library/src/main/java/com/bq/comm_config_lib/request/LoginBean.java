package com.bq.comm_config_lib.request;

import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/8
 * 版权：
 */
public class LoginBean {

    /**
     * access_token : 4604ae29d195966f
     * expire_time : 1593656313
     * renew_flag : 7c28cb42ce477224
     */

    private String access_token;
    private String expire_time;
    private String renew_flag;
    private List<Integer> goods_ids;

    public List<Integer> getGoods_ids() {
        return goods_ids;
    }

    public void setGoods_ids(List<Integer> goods_ids) {
        this.goods_ids = goods_ids;
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
