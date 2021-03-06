package com.bq.comm_config_lib.msgService.bean;

import java.io.Serializable;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/4/21
 * 版权：
 */
public class UserInfo implements Serializable{


    private String name;
    private String gender;
    private String birthday;
    private String phone;
    private String email;
    private String wechat;
    private String qq;
    private String education;
    private String head_url;
    private String nick;
    private Integer is_certify;

    public Integer getIs_certify() {
        return is_certify;
    }

    public void setIs_certify(Integer is_certify) {
        this.is_certify = is_certify;
    }

    public String getHead_url() {
        return head_url;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public UserInfo(String nameType, String value) {
        if("name".equals(nameType)){
            this.name = value;
        }else if("gender".equals(nameType)){
            this.gender = value;
        }else if("birthday".equals(nameType)){
            this.birthday = value;
        }else if("phone".equals(nameType)){
            this.phone = value;
        }else if("email".equals(nameType)){
            this.email = value;
        }else if("wechat".equals(nameType)){
            this.wechat = value;
        }else if("qq".equals(nameType)){
            this.qq = value;
        }else if("nick".equals(nameType)){
            this.nick = value;
        }else if("head_url".equals(nameType)){
            this.head_url = value;
        }
//            else if("education".equals(nameType)){
//                this.education = value;
//            }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        if("unknown".equals(gender)){
            return "未知";
        }else if("woman".equals(gender)){
            return "女";
        }else{
            return "男";
        }
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEducation() {
        if("other".equals(education)){
            return "未知";
        }
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }
}
