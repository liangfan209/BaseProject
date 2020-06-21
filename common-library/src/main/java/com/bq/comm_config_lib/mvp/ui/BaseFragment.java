package com.bq.comm_config_lib.mvp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bq.comm_config_lib.mvp.BaseIView;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.fan.baseuilibrary.view.MyRefreshLayout;
import com.fan.baseuilibrary.view.dialog.LoadingDialog;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/5/28
 * 版权：
 */
public abstract class BaseFragment extends Fragment implements BaseIView {

    private LoadingDialog mLoadingDialog;
    public MyRefreshLayout mRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(getContentViewLayout(),null);
        mLoadingDialog = new LoadingDialog(this.getContext());
        ButterKnife.bind(this,view);
        attach();
        return view;
    }

    @Override
    public void showLoading() {
        if (mLoadingDialog != null)
            mLoadingDialog.show();
    }

    @Override
    public void onError(String msg) {
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

    protected abstract @LayoutRes int getContentViewLayout();
    protected abstract void attach();

}