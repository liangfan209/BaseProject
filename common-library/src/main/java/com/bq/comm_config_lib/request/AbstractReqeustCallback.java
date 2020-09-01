package com.bq.comm_config_lib.request;

import android.app.Activity;
import android.os.Handler;

import com.bq.comm_config_lib.mvp.BaseIView;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/16
 * 版权：
 */
public abstract class AbstractReqeustCallback<T> implements RequestCallBackInter<T>{

    BaseIView mIview;

    public AbstractReqeustCallback(BaseIView iview){
        this.mIview = iview;
    }

    @Override
    public void onError(String msg) {
        mIview.onError(msg);
            if(msg.contains("二维码") || msg.contains("已过期")){
            if(mIview instanceof Activity){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ((Activity) mIview).finish();
                    }
                }, 1000);
                return;
            }
        }
    }
    @Override
    public void onComplete() {
        mIview.onComplete();
    }
}
