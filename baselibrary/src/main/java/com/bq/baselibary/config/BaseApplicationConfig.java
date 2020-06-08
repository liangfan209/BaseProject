package com.bq.baselibary.config;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/5/28
 * 版权：
 */
public class BaseApplicationConfig {
    //base url
    public String baseUrl;
    //apiService
    public Class service;

    @Override
    public String toString() {
        return "BaseApplicationConfig{" +
                "baseUrl='" + baseUrl + '\'' +
                ", service=" + service +
                '}';
    }
}
