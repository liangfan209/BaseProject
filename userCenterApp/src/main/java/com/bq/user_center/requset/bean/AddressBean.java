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

    private int id;
    private int type;
    private String phoneNumber;
    private String name;
    private int sex;
    private String provinces;
    private String detailAddress;

    public AddressBean(int id,int type, String phoneNumber, String name, int sex, String provinces, String detailAddress) {
        this.id = id;
        this.type = type;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.sex = sex;
        this.provinces = provinces;
        this.detailAddress = detailAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

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
