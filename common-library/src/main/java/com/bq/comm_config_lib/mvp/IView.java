package com.bq.comm_config_lib.mvp;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/5/28
 * 版权：
 */
public interface IView {
    void showLoading();
    void onError(String msg);
    void onComplete();
}