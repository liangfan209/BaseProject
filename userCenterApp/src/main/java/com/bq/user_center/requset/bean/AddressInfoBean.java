package com.bq.user_center.requset.bean;

import java.io.Serializable;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/20/020
 * 版权：
 */
public class AddressInfoBean implements Serializable {

    private AddressInfo address_info;


    public AddressInfo getAddress_info() {
        return address_info;
    }

    public void setAddress_info(AddressInfo address_info) {
        this.address_info = address_info;
    }
}
