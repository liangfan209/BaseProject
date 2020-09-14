package com.clkj.order.requset.bean;

import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/31
 * 版权：
 */
public class DurationBean {

    private List<DurationInfo> data_list;
    public List<DurationInfo> getData_list() {
        return data_list;
    }
    public void setData_list(List<DurationInfo> data_list) {
        this.data_list = data_list;
    }

    public static class DurationInfo{
        private String key;
        private String value;



        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }
}
