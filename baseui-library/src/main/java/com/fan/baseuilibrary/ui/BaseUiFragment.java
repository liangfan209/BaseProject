package com.fan.baseuilibrary.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bq.baselibary.base.BaseFragment;
import com.bq.baselibary.mvp.IView;
import com.fan.baseuilibrary.view.dialog.LoadingDialog;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.ButterKnife;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/5/28
 * 版权：
 */
public abstract class BaseUiFragment extends BaseFragment implements IView {

    private LoadingDialog mLoadingDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(getContentViewLayout(),null);
        mLoadingDialog = new LoadingDialog(this.getContext());
        ButterKnife.bind(view);
        attach();
        return view;
    }

    protected abstract @LayoutRes int getContentViewLayout();
    protected abstract void attach();

}