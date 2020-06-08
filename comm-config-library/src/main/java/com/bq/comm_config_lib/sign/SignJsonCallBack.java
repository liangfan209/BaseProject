package com.bq.comm_config_lib.sign;

import com.bq.netlibrary.http.JsonCallback;
import com.lzy.okgo.request.base.Request;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/8
 * 版权：
 */
public abstract class SignJsonCallBack<T> extends JsonCallback<T> {
    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart( RequsetUtils.signRequestParmas(request));
    }

}
