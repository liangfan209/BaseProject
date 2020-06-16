package com.bq.login.requset;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/16
 * 版权：
 */
public interface ReqeustCallBack<T> {
    void onscucess();
    void onError();
    void onComplete();
}
