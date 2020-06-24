package com.bq.comm_config_lib.mvp.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bq.comm_config_lib.R;
import com.bq.comm_config_lib.mvp.BaseIView;
import com.bq.comm_config_lib.mvp.BasePersenter;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.fan.baseuilibrary.view.MyRefreshLayout;
import com.fan.baseuilibrary.view.dialog.LoadingDialog;
import com.wind.me.xskinloader.SkinInflaterFactory;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import butterknife.ButterKnife;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/5/28
 * 版权：
 */
public abstract class BaseAcitivty extends AppCompatActivity implements BaseIView, LifecycleOwner {

    private LoadingDialog mLoadingDialog;
    public MyRefreshLayout mRefreshLayout;
    ImageView mIvTitleLeft;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SkinInflaterFactory.setFactory(this);
        super.onCreate(savedInstanceState);
        setContentView(getContentViewLayout());
        mLoadingDialog = new LoadingDialog(this);
        ButterKnife.bind(this);
        View backIv = findViewById(R.id.iv_title_left);
        if (backIv != null) {
            backIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        BasePersenter presenter = createPersenter();
        if(presenter != null){
            getLifecycle().addObserver(presenter);
        }
        attach();

    }

    @Override
    public void showLoading() {
        if (mLoadingDialog != null)
            mLoadingDialog.show();
    }

    @Override
    public void onError(String msg) {
        ToastUtils.showToast(this, msg);
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
    protected abstract BasePersenter createPersenter();
    protected abstract void attach();
}