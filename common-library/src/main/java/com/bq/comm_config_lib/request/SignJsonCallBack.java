package com.bq.comm_config_lib.request;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ActivityUtils;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.utils.CommSpUtils;
import com.bq.netlibrary.http.BaseResponse;
import com.bq.netlibrary.http.JsonCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.net.ConnectException;
import java.net.UnknownHostException;

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

    public SignJsonCallBack() {
        this(null, null);
    }

    public SignJsonCallBack(RequestCallBackInter callBack) {
        this(callBack, null);
    }

    public SignJsonCallBack(RequestCallBackInter callBack, String url) {
        this.mRequestCallBack = callBack;
        this.mUrl = url;
    }


    @Override
    public void onStart(Request<T, ? extends Request> request) {
        //检查是否续签
        ReNewFlagHelper.renewFlag(request);
        super.onStart(RequsetUtils.signRequestParmas(request));
        if (mRequestCallBack != null)
            mRequestCallBack.onStart();
    }

    @Override
    public void onSuccess(Response<T> response) {
        T body = response.body();
        if (body instanceof BaseResponse) {
            if ("ok".equals(((BaseResponse) body).status)) {
                if (mRequestCallBack != null)
                    mRequestCallBack.onSuccess(((BaseResponse) body).result);
            } else {
                if(30008 ==((BaseResponse) body).code || 30007 ==((BaseResponse) body).code){
                    ActivityUtils.finishAllActivities();
                    CommSpUtils.saveLoginInfo("");
                    ARouter.getInstance().build(AppArouter.LOGIN_ACTVITY).navigation();
                }
                if (mRequestCallBack != null)
                    mRequestCallBack.onError(((BaseResponse) body).msg);
            }
        }
    }


    @Override
    public void onError(Response<T> response) {
        super.onError(response);
        Throwable ex = response.getException();
        if (ex instanceof ConnectException) {
            if (mRequestCallBack != null)
                mRequestCallBack.onError("网络连接异常");
        }else if(ex instanceof UnknownHostException){
            if (mRequestCallBack != null)
                mRequestCallBack.onError("无法解析域名"+Api.BASE_API);
        }
    }


    @Override
    public void onFinish() {
        super.onFinish();
        if (mRequestCallBack != null)
            mRequestCallBack.onComplete();
    }
}
