package com.fan.baseuilibrary.ui;

import android.os.Bundle;

import com.bq.baselibary.base.BaseActivity;
import com.bq.baselibary.mvp.IView;
import com.fan.baseuilibrary.view.dialog.LoadingDialog;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import butterknife.ButterKnife;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/5/28
 * 版权：
 */
public abstract class BaseUiAcitivty extends BaseActivity implements IView {

    private LoadingDialog mLoadingDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewLayout());
        mLoadingDialog = new LoadingDialog(this);
        ButterKnife.bind(this);
        attach();
    }


    protected abstract @LayoutRes int getContentViewLayout();
    protected abstract void attach();

}