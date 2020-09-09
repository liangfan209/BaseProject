package com.bq.order.requset.bean;

import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/8/3
 * 版权：
 */
public class OrderRequsetBean {
    private Integer strike_price;
    private Integer address_id;
    private List<GoodsList> goods_list;
    private InvoiceInfo invoice_info;
    private int deposit;


    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public void setAddress_id(Integer address_id) {
        this.address_id = address_id;
    }

    public InvoiceInfo getInvoice_info() {
        return invoice_info;
    }

    public void setInvoice_info(InvoiceInfo invoice_info) {
        this.invoice_info = invoice_info;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStrike_price(Integer strike_price) {
        this.strike_price = strike_price;
    }



    private String status;

    public OrderRequsetBean(String status) {
        this.status = status;
    }

    public OrderRequsetBean(int strike_price, List<GoodsList> goods_list) {
        this.strike_price = strike_price;
        this.goods_list = goods_list;
    }


    public OrderRequsetBean(int strike_price, int address_id, List<GoodsList> goods_list) {
        this.strike_price = strike_price;
        this.address_id = address_id;
        this.goods_list = goods_list;
    }

    public int getStrike_price() {
        return strike_price;
    }

    public void setStrike_price(int strike_price) {
        this.strike_price = strike_price;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public List<GoodsList> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsList> goods_list) {
        this.goods_list = goods_list;
    }

    public static class GoodsList{
        private int quantity;
        private String specification_id;

        public GoodsList(int quantity, String specification_id) {
            this.quantity = quantity;
            this.specification_id = specification_id;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getSpecification_id() {
            return specification_id;
        }

        public void setSpecification_id(String specification_id) {
            this.specification_id = specification_id;
        }
    }
}
