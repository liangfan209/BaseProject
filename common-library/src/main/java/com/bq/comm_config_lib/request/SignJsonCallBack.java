package com.bq.comm_config_lib.request;

import com.bq.netlibrary.http.BaseResponse;
import com.bq.netlibrary.http.JsonCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.net.ConnectException;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/8
 * 版权：
 */
public class SignJsonCallBack<T> extends JsonCallback<T> {
    private String mUrl;
    private RequestCallBackInter mRequestCallBack;

    public SignJsonCallBack(RequestCallBackInter callBack) {
        this(callBack, null);
    }

    public SignJsonCallBack(RequestCallBackInter callBack, String url) {
        this.mRequestCallBack = callBack;
        this.mUrl = url;
    }


    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(RequsetUtils.signRequestParmas(request));
        mRequestCallBack.onStart();
    }

    @Override
    public void onSuccess(Response<T> response) {
        T body = response.body();
        if (body instanceof BaseResponse) {
            if (10000 != ((BaseResponse) body).code) {
                mRequestCallBack.onError(((BaseResponse) body).msg);
            }
        }
    }


    @Override
    public void onError(Response<T> response) {
        super.onError(response);
        Throwable ex = response.getException();
        if(ex instanceof ConnectException){
            mRequestCallBack.onError("网络连接异常");
        }
        //异常部分统一处理
    }


    @Override
    public void onFinish() {
        super.onFinish();
        mRequestCallBack.onComplete();
    }
}
