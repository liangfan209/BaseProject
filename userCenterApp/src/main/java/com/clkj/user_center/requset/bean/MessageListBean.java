package com.clkj.user_center.requset.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/30
 * 版权：
 */
public class MessageListBean implements Serializable {
    private List<MessageInfo> data_list;

    public List<MessageInfo> getData_list() {
        return data_list;
    }

    public void setData_list(List<MessageInfo> data_list) {
        this.data_list = data_list;
    }
}
