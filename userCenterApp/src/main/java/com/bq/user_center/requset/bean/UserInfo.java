package com.bq.user_center.requset.bean;

import java.io.Serializable;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/4/21
 * 版权：
 */
public class UserInfo implements Serializable{


    /**
     * customer_info : {"name":"","gender":"unknown","birthday":"","phone":"13333333333","email":"","wechat":"","qq":"",
     * "education":"other"}
     */

    private CustomerInfoBean customer_info;

    public CustomerInfoBean getCustomer_info() {
        return customer_info;
    }

    public void setCustomer_info(CustomerInfoBean customer_info) {
        this.customer_info = customer_info;
    }

    public static class CustomerInfoBean implements Serializable {
        /**
         * name :
         * gender : unknown
         * birthday :
         * phone : 13333333333
         * email :
         * wechat :
         * qq :
         * education : other
         */

        private String name;
        private String gender;
        private String birthday;
        private String phone;
        private String email;
        private String wechat;
        private String qq;
        private String education;

        public CustomerInfoBean(String nameType,String value) {
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
            }else if("education".equals(nameType)){
                this.education = value;
            }
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
            }
            return gender;
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
}
