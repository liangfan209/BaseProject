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
public class BannerListBean implements Serializable {

    private List<BannerInfo> data_list;
    public List<BannerInfo> getData_list() {
        return data_list;
    }
    public void setData_list(List<BannerInfo> data_list) {
        this.data_list = data_list;
    }


}
