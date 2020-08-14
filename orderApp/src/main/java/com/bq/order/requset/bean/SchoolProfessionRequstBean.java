package com.bq.order.requset.bean;

import java.io.Serializable;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/8/14
 * 版权：
 */
public class SchoolProfessionRequstBean implements Serializable {
    private String school_id;
    private String major_id;
    private String category;

    public SchoolProfessionRequstBean(String category) {
        this.category = category;
    }

    public SchoolProfessionRequstBean(String school_id, String category) {
        this.school_id = school_id;
        this.category = category;
    }

    public String getSchool_id() {
        return school_id;
    }

    public void setSchool_id(String school_id) {
        this.school_id = school_id;
    }

    public String getMajor_id() {
        return major_id;
    }

    public void setMajor_id(String major_id) {
        this.major_id = major_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
