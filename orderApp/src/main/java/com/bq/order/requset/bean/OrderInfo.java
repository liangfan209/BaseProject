package com.bq.order.requset.bean;

import com.blankj.utilcode.util.StringUtils;

import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/8/4
 * 版权：
 */
public class OrderInfo {
    /**
     * id : 22
     * number : 0
     * status : order_launched
     * strike_price : 916722
     * create_time : 2020-08-03 23:32:58
     * last_payment_type :
     * last_payment_time :
     * last_payment_number :
     * order_item_list : [{"sale_price":916722,"total_price":916722,"quantity":1,"show_image":"","title":"这是一个商品072",
     * "school_name":"山东大学","major_name":"海洋工程","duration":"2.5年","school_city":"武汉","brand_name":"成教",
     * "production_name":"专升本","specification_value_list":[{"category":"课程","attribute":"vip课程"}]}]
     */

    private int id;
    private String number;
    private String status;
    private int strike_price;
    private String create_time;
    private String last_payment_type;
    private String last_payment_time;
    private String last_payment_number;
    private List<OrderItemListBean> order_item_list;
    private String contract_background;
    private String despatch_type;
    private String status_name;

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public String getDespatch_type() {
        if(StringUtils.isEmpty(despatch_type)){
            return "教育合同";
        }
        if(despatch_type.equals("eduction_contract")){
            return "教育合同";
        }else if(despatch_type.equals("top_up_phone")){
            return "手机充值";
        }else if(despatch_type.equals("logistics")){
            return "物流交付";
        }
        return despatch_type;
    }

    public void setDespatch_type(String despatch_type) {
        this.despatch_type = despatch_type;
    }

    public String getContract_background() {
        return contract_background;
    }

    public void setContract_background(String contract_background) {
        this.contract_background = contract_background;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStrike_price() {
        return strike_price;
    }

    public void setStrike_price(int strike_price) {
        this.strike_price = strike_price;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getLast_payment_type() {
        return last_payment_type;
    }

    public void setLast_payment_type(String last_payment_type) {
        this.last_payment_type = last_payment_type;
    }

    public String getLast_payment_time() {
        return last_payment_time;
    }

    public void setLast_payment_time(String last_payment_time) {
        this.last_payment_time = last_payment_time;
    }

    public String getLast_payment_number() {
        return last_payment_number;
    }

    public void setLast_payment_number(String last_payment_number) {
        this.last_payment_number = last_payment_number;
    }

    public List<OrderItemListBean> getOrder_item_list() {
        return order_item_list;
    }

    public void setOrder_item_list(List<OrderItemListBean> order_item_list) {
        this.order_item_list = order_item_list;
    }

    public static class OrderItemListBean {
        /**
         * sale_price : 916722
         * total_price : 916722
         * quantity : 1
         * show_image :
         * title : 这是一个商品072
         * school_name : 山东大学
         * major_name : 海洋工程
         * duration : 2.5年
         * school_city : 武汉
         * brand_name : 成教
         * production_name : 专升本
         * specification_value_list : [{"category":"课程","attribute":"vip课程"}]
         */

        private int sale_price;
        private int total_price;
        private int quantity;
        private String show_image;
        private String title;
        private String school_name;
        private String major_name;
        private String duration;
        private String school_city;
        private String brand_name;
        private String production_name;
        private String remark;
        private String despatch_type;
        private List<SpecificationValueListBean> specification_value_list;

        public String getDespatch_type() {
            if(StringUtils.isEmpty(despatch_type)){
//                return "";
                return "教育合同";
            }
            if(despatch_type.equals("eduction_contract")){
                return "教育合同";
            }else if(despatch_type.equals("top_up_phone")){
                return "手机充值";
            }else if(despatch_type.equals("logistics")){
                return "物流交付";
            }
            return despatch_type;
        }

        public void setDespatch_type(String despatch_type) {
            this.despatch_type = despatch_type;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getSale_price() {
            return sale_price;
        }

        public void setSale_price(int sale_price) {
            this.sale_price = sale_price;
        }

        public int getTotal_price() {
            return total_price;
        }

        public void setTotal_price(int total_price) {
            this.total_price = total_price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getShow_image() {
            return show_image;
        }

        public void setShow_image(String show_image) {
            this.show_image = show_image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSchool_name() {
            return school_name;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public String getMajor_name() {
            return major_name;
        }

        public void setMajor_name(String major_name) {
            this.major_name = major_name;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getSchool_city() {
            return school_city;
        }

        public void setSchool_city(String school_city) {
            this.school_city = school_city;
        }

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
        }

        public String getProduction_name() {
            return production_name;
        }

        public void setProduction_name(String production_name) {
            this.production_name = production_name;
        }

        public List<SpecificationValueListBean> getSpecification_value_list() {
            return specification_value_list;
        }

        public void setSpecification_value_list(List<SpecificationValueListBean> specification_value_list) {
            this.specification_value_list = specification_value_list;
        }

        public static class SpecificationValueListBean {
            /**
             * category : 课程
             * attribute : vip课程
             */

            private String category;
            private String attribute;

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getAttribute() {
                return attribute;
            }

            public void setAttribute(String attribute) {
                this.attribute = attribute;
            }
        }
    }
}