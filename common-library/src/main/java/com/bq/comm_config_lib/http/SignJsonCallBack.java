package com.bq.comm_config_lib.http;

import com.bq.comm_config_lib.mvp.IView;
import com.bq.comm_config_lib.ui.BaseAcitivty;
import com.bq.comm_config_lib.ui.BaseFragment;
import com.bq.netlibrary.http.BaseResponse;
import com.bq.netlibrary.http.JsonCallback;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/8
 * 版权：
 */
public abstract class SignJsonCallBack<T> extends JsonCallback<T> {
    private IView mIvew;
    private String mUrl;
    private SmartRefreshLayout mSmartRefreshLayout;

    public SignJsonCallBack(IView iView) {
        this(iView,null);
    }

    public SignJsonCallBack(IView iView,SmartRefreshLayout layout) {
        this(iView,layout,null);
    }

    public SignJsonCallBack(IView iView,SmartRefreshLayout layout,String url) {
        this.mIvew = iView;
        this.mSmartRefreshLayout = layout;
        this.mUrl = url;
    }


    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(RequsetUtils.signRequestParmas(request));
    }

    @Override
    public void onSuccess(Response<T> response) {
        T body = response.body();
        if (body instanceof BaseResponse) {
            if (10000 != ((BaseResponse) body).code && mIvew != null) {
                if (mIvew instanceof BaseAcitivty) {
                    ToastUtils.showToast((BaseAcitivty) mIvew, ((BaseResponse) body).msg);
                } else if (mIvew instanceof BaseFragment) {
                    ToastUtils.showToast(((BaseFragment) mIvew).getActivity(), ((BaseResponse) body).msg);
                }
            }
        }
    }

    @Override
    public void onError(Response<T> response) {
        super.onError(response);
        //异常部分统一处理
    }

    @Override
    public void onFinish() {
        super.onFinish();
        if (mSmartRefreshLayout != null) {
            mSmartRefreshLayout.finishRefresh();
            mSmartRefreshLayout.finishLoadmore();
        }
    }
}
