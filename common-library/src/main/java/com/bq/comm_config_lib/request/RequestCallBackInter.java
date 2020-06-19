package com.bq.comm_config_lib.request;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/16
 * 版权：
 */
public interface RequestCallBackInter<T> {
    default void onStart(){};
    void onSuccess(T t);
    void onError(String msg);
    default void onComplete(){};
}
