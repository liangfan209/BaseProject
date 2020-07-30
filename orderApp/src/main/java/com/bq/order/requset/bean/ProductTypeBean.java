package com.bq.order.requset.bean;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/30
 * 版权：
 */
public class ProductTypeBean {

    private ProductTypeInfo info;

    public ProductTypeInfo getInfo() {
        return info;
    }

    public void setInfo(ProductTypeInfo info) {
        this.info = info;
    }

    public static class ProductTypeInfo{
        private int id;
        private String name;

        public ProductTypeInfo(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
