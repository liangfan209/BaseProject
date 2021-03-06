package com.bq.comm_config_lib.mvp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bq.comm_config_lib.mvp.BaseIView;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.fan.baseuilibrary.view.MyRefreshLayout;
import com.fan.baseuilibrary.view.dialog.LoadingDialog;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import butterknife.ButterKnife;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/5/28
 * 版权：
 */
public abstract class BaseFragment extends Fragment implements BaseIView, LifecycleOwner {

    private LoadingDialog mLoadingDialog;
    public MyRefreshLayout mRefreshLayout;
    public View contentView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        contentView = inflater.inflate(getContentViewLayout(), null);
        mLoadingDialog = new LoadingDialog(this.getContext());
        ButterKnife.bind(this, contentView);
        BasePresenter presenter = createPersenter();
        if (presenter != null) {
            getLifecycle().addObserver(presenter);
        }
        attach();
        return contentView;
    }

    @Override
    public void showLoading() {
        if (mLoadingDialog != null)
            mLoadingDialog.show();
    }

    @Override
    public void onError(String msg) {
        if (this.getActivity() != null)
            ToastUtils.showToast(this.getActivity(), msg);
    }

    @Override
    public void onComplete() {
        if (mRefreshLayout != null) {
            mRefreshLayout.finishRefresh();
            mRefreshLayout.finishLoadmore();
        }
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    protected abstract BasePresenter createPersenter();

    protected abstract @LayoutRes
    int getContentViewLayout();

    protected abstract void attach();

}