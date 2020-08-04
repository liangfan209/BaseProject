package com.bq.order.requset.bean;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/8/3
 * 版权：
 */
public class OrderInfoDetailBean {

    /**
     * order_info : {"id":22,"number":0,"status":"order_launched","strike_price":916722,"create_time":"2020-08-03 23:32:58",
     * "last_payment_type":"","last_payment_time":"","last_payment_number":"","order_item_list":[{"sale_price":916722,
     * "total_price":916722,"quantity":1,"show_image":"","title":"这是一个商品072","school_name":"山东大学","major_name":"海洋工程",
     * "duration":"2.5年","school_city":"武汉","brand_name":"成教","production_name":"专升本","specification_value_list":[{"category
     * ":"课程","attribute":"vip课程"}]}]}
     */

    private OrderInfo order_info;

    public OrderInfo getOrder_info() {
        return order_info;
    }
    public void setOrder_info(OrderInfo order_info) {
        this.order_info = order_info;
    }

}
