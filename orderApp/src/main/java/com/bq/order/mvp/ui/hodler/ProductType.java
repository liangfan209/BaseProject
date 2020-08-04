package com.bq.order.mvp.ui.hodler;

import java.io.Serializable;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/8/3
 * 版权：
 */
public class ProductType implements Serializable {
    public String searchInfo;
    public int type;

    public ProductType(String searchInfo, int type) {
        this.searchInfo = searchInfo;
        this.type = type;
    }
}
