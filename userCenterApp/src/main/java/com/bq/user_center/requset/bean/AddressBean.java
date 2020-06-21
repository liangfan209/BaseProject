package com.bq.user_center.requset.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/20/020
 * 版权：
 */
public class AddressBean implements MultiItemEntity {

    private int type;
    private String name;
    private String phoneNumber;
    private String provinces;
    private String detailAddress;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProvinces() {
        return provinces;
    }

    public void setProvinces(String provinces) {
        this.provinces = provinces;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public AddressBean(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
