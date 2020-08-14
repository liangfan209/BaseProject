package com.bq.order.requset.bean;

import java.io.Serializable;

public class InvoiceInfo implements Serializable {
    private String name;
    private String phone;
    private String identification;
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public InvoiceInfo() {
    }

    public InvoiceInfo(String name, String phone, String identification) {
        this.name = name;
        this.phone = phone;
        this.identification = identification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }
}