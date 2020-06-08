package com.bq.netlibrary;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/3/003
 * 版权：
 */
public enum NetType {
    http(1),tcp(2);

    public int mCode;
    NetType(int code) {
        this.mCode = code;
    }
}
