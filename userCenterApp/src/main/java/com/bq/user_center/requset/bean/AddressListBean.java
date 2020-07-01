package com.bq.user_center.requset.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/20/020
 * 版权：
 */
public class AddressListBean implements Serializable {

    private List<AddressInfo> address_list;

    public List<AddressInfo> getAddress_list() {
        return address_list;
    }

    public void setAddress_list(List<AddressInfo> address_list) {
        this.address_list = address_list;
    }

}
