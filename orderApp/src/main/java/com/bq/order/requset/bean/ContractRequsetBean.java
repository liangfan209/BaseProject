package com.bq.order.requset.bean;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/8/3
 * 版权：
 */
public class ContractRequsetBean {
    private String name;
    private String phone;
    private String identification;
    private String email;
    private String autograph;

    public ContractRequsetBean(String name, String phone, String identification, String email, String autograph) {
        this.name = name;
        this.phone = phone;
        this.identification = identification;
        this.email = email;
        this.autograph = autograph;
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

    public String getIdentificatio() {
        return identification;
    }

    public void setIdentificatio(String identification) {
        this.identification = identification;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAutograph() {
        return autograph;
    }

    public void setAutograph(String autograph) {
        this.autograph = autograph;
    }
}
