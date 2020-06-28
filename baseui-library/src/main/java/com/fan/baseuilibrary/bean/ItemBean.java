package com.fan.baseuilibrary.bean;

import java.io.Serializable;

/**
 * Created by fanliang on 19/6/14.
 */

public class ItemBean implements Serializable{
    private int res;
    private String str;
    private String hot;

    public ItemBean(int res, String str, String hot) {
        this.res = res;
        this.str = str;
        this.hot = hot;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getHot() {
        return hot;
    }

    public void setHot(String hot) {
        this.hot = hot;
    }
}
