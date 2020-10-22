package com.clkj.order.requset.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/8/7
 * 版权：
 */
public class EvaluationListBean implements Serializable {
    private List<EvaluationInfo> data_list;
    private StatisticsInfo statistics;
    private String head_url;
    private String nickname;
    private int total;
    private int total_page;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public String getHead_url() {
        return head_url;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public StatisticsInfo getStatistics() {
        return statistics;
    }

    public void setStatistics(StatisticsInfo statistics) {
        this.statistics = statistics;
    }

    public List<EvaluationInfo> getData_list() {
        return data_list;
    }

    public void setData_list(List<EvaluationInfo> data_list) {
        this.data_list = data_list;
    }
}
