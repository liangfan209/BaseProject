package com.bq.order.requset.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/8/7
 * 版权：
 */
public class ProfessionListBean implements Serializable {
    private List<ProfessionInfo> data_list;

    public List<ProfessionInfo> getData_list() {
        return data_list;
    }

    public void setData_list(List<ProfessionInfo> data_list) {
        this.data_list = data_list;
    }
}
