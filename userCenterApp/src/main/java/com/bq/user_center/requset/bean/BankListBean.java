package com.bq.user_center.requset.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/11
 * 版权：
 */
public class BankListBean implements Serializable {
    private List<BankCardInfo> bankcard_list;
    public List<BankCardInfo> getBankcard_list() {
        return bankcard_list;
    }
    public void setBankcard_list(List<BankCardInfo> bankcard_list) {
        this.bankcard_list = bankcard_list;
    }
}
