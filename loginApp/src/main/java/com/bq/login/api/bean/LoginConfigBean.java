package com.bq.login.api.bean;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/22
 * 版权：
 */
public class LoginConfigBean {

    /**
     * isImgInvalidate : false
     * loginType : pwd
     */
    private int loginType;

    public boolean isHasImageVirification() {
        return hasImageVirification;
    }

    public void setHasImageVirification(boolean hasImageVirification) {
        this.hasImageVirification = hasImageVirification;
    }

    private boolean hasImageVirification;

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }


}
