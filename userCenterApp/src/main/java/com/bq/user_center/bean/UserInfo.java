package com.bq.user_center.bean;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/4/21
 * 版权：
 */
public class UserInfo {

    /**
     * user_balance : 8546.31
     * user_cardno : 420821199111160751
     * user_id : 321568461
     * user_name : 13918383354
     * user_age : 28
     * user_phone : 13918383354
     * user_gender : M
     * user_nick : 超级玛丽
     * user_head_img : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1587471328925&di
     * =d6aaf77b3131d8af617b054d52bb7e9c&imgtype=0&src=http%3A%2F%2Fdik.img.kttpdq
     * .com%2Fpic%2F160%2F111422%2F6474bdadb88aea64_1680x1050.jpg
     */

    private double user_balance;
    private String user_cardno;
    private String user_id;
    private String user_name;
    private int user_age;
    private String user_phone;
    private String user_gender;
    private String user_nick;
    private String user_head_img;
    private String user_binding;
    private int user_sex;
    private int is_pay_password;
    private String service_wx;

    public String getService_wx() {
        return service_wx;
    }

    public void setService_wx(String service_wx) {
        this.service_wx = service_wx;
    }

    public int getIs_pay_password() {
        return is_pay_password;
    }

    public void setIs_pay_password(int is_pay_password) {
        this.is_pay_password = is_pay_password;
    }

    public int getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(int user_sex) {
        this.user_sex = user_sex;
    }

    public String getUser_binding() {
        return user_binding;
    }

    public void setUser_binding(String user_binding) {
        this.user_binding = user_binding;
    }

    public double getUser_balance() {
        return user_balance;
    }

    public void setUser_balance(double user_balance) {
        this.user_balance = user_balance;
    }

    public String getUser_cardno() {
        return user_cardno;
    }

    public void setUser_cardno(String user_cardno) {
        this.user_cardno = user_cardno;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getUser_age() {
        return user_age;
    }

    public void setUser_age(int user_age) {
        this.user_age = user_age;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public String getUser_nick() {
        return user_nick;
    }

    public void setUser_nick(String user_nick) {
        this.user_nick = user_nick;
    }

    public String getUser_head_img() {
        return user_head_img;
    }

    public void setUser_head_img(String user_head_img) {
        this.user_head_img = user_head_img;
    }
}
