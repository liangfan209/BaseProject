package com.bq.wallet.requset.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/6
 * 版权：
 */
public class TransactionMonthListBean implements Serializable {

    private List<TransactionMonthInfo> statistics_list;

    public List<TransactionMonthInfo> getStatistics_list() {
        return statistics_list;
    }

    public void setStatistics_list(List<TransactionMonthInfo> statistics_list) {
        this.statistics_list = statistics_list;
    }
}
