package com.bq.walletapp.requset.bean;

import java.io.Serializable;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/6
 * 版权：
 */
public class TransactionInfoBean implements Serializable {
    private TransactionInfo transaction_info;

    public TransactionInfo getTransaction_info() {
        return transaction_info;
    }

    public void setTransaction_info(TransactionInfo transaction_info) {
        this.transaction_info = transaction_info;
    }
}
