package com.bq.order.requset.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/8/3
 * 版权：
 */
public class SpecificationList implements Serializable {
    /**
     * sale_price : 231233
     * stock : 11
     * show_image : /resource/1/image_1595406243.image
     * specification_value_list : [{"category":"颜色","attribute":"红色"}]
     */

    private int id;
    private int sale_price;
    private int stock;
    private String show_image;
    private List<SpecificationValueList> specification_value_list;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSale_price() {
        return sale_price;
    }

    public void setSale_price(int sale_price) {
        this.sale_price = sale_price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getShow_image() {
        return show_image;
    }

    public void setShow_image(String show_image) {
        this.show_image = show_image;
    }

    public List<SpecificationValueList> getSpecification_value_list() {
        return specification_value_list;
    }

    public void setSpecification_value_list(List<SpecificationValueList> specification_value_list) {
        this.specification_value_list = specification_value_list;
    }

    public static class SpecificationValueList implements Serializable{
        /**
         * category : 颜色
         * attribute : 红色
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
