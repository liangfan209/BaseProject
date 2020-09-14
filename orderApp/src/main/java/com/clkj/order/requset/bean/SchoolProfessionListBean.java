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
public class SchoolProfessionListBean implements Serializable {
    private List<SchoolProfessionInfo> data_list;

    public List<SchoolProfessionInfo> getData_list() {
        return data_list;
    }

    public void setData_list(List<SchoolProfessionInfo> data_list) {
        this.data_list = data_list;
    }
}
