package com.bq.order.requset.bean;

import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/8/6
 * 版权：
 */
public class ContactListBean {

    private List<String> contract_list;
    private List<String> contract_img_list;

    public List<String> getContract_img_list() {
        return contract_img_list;
    }

    public void setContract_img_list(List<String> contract_img_list) {
        this.contract_img_list = contract_img_list;
    }

    public List<String> getContract_list() {
        return contract_list;
    }

    public void setContract_list(List<String> contract_list) {
        this.contract_list = contract_list;
    }
}
