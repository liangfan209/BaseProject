package com.clkj.user_center.requset.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/1
 * 版权：
 */
public class AddressInfo implements MultiItemEntity, Serializable , Comparable<AddressInfo> {
    private String id;
    private String contacts;
    private String gender;
    private String phone;
    private String city;
    private String address;

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }

    private int is_default;


    public AddressInfo(String contacts, String gender, String phone, String city, String address, int is_default) {
        this.contacts = contacts;
        this.gender = gender;
        this.phone = phone;
        this.city = city;
        this.address = address;
        this.is_default = is_default;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public int getItemType() {
        return is_default;
    }

    @Override
    public int compareTo(AddressInfo o) {
        return o.is_default - this.is_default;
    }
}
