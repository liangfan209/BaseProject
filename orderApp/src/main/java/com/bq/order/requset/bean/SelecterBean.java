package com.bq.order.requset.bean;

import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/31
 * 版权：
 */
public class SelecterBean {

    private List<SelectInfo> data_list;
    public List<SelectInfo> getData_list() {
        return data_list;
    }
    public void setData_list(List<SelectInfo> data_list) {
        this.data_list = data_list;
    }

    public static class SelectInfo{
        private String id;
        private String name;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
}
