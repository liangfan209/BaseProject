package com.bq.comm_config_lib.mvp;

import android.app.Activity;

import com.bq.comm_config_lib.mvp.ui.BaseAcitivty;
import com.bq.comm_config_lib.mvp.ui.BaseFragment;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/5/28
 * 版权：
 */
public interface BaseIView {
    void showLoading();
    void onError(String msg);
    void onComplete();

    default Activity getActivity() {
        if(this instanceof BaseAcitivty){
            return (Activity) this;
        }else if(this instanceof BaseFragment){
            return (Activity) this.getActivity();
        }else{
            new IllegalAccessException("该类必须继承 Activity,或者fragment");
        }
        return null;
    }
}