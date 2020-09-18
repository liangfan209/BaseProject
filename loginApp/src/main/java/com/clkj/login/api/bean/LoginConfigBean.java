package com.clkj.login.api.bean;

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
     * templet:2
     */
    private int loginType;
    private boolean hasImageVirification;
    private int templet;

    public int getTemplet() {
        return templet;
    }

    public void setTemplet(int templet) {
        this.templet = templet;
    }

    public boolean isHasImageVirification() {
        return hasImageVirification;
    }

    public void setHasImageVirification(boolean hasImageVirification) {
        this.hasImageVirification = hasImageVirification;
    }


    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }


}
