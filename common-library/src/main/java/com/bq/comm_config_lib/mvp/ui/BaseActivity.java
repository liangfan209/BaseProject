package com.bq.comm_config_lib.mvp.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bq.comm_config_lib.R;
import com.bq.comm_config_lib.mvp.BaseIView;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.utils.MyStatusBarUtil;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.fan.baseuilibrary.view.MyRefreshLayout;
import com.fan.baseuilibrary.view.dialog.LoadingDialog;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.SkinAppCompatDelegateImpl;
import androidx.lifecycle.LifecycleOwner;
import butterknife.ButterKnife;
import skin.support.widget.SkinCompatSupportable;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/5/28
 * 版权：
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseIView, LifecycleOwner, SkinCompatSupportable {

    private LoadingDialog mLoadingDialog;
    public MyRefreshLayout mRefreshLayout;
    ImageView mIvTitleLeft;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewLayout());
        MyStatusBarUtil.setStatusBarTranslucent(this, R.color.transparent, true);
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
        BasePresenter presenter = createPersenter();
        if(presenter != null){
            getLifecycle().addObserver(presenter);
        }
        attach();
//        SkinCompatManager.getInstance().loadSkin("myskin.skin", null, SkinCompatManager.SKIN_LOADER_STRATEGY_ASSETS);
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

    @NonNull
    @Override
    public AppCompatDelegate getDelegate() {
        return SkinAppCompatDelegateImpl.get(this, this);
    }

    @Override
    public void applySkin() {
//        if (tv != null)
//            tv.setTextColor(SkinCompatResources.getColor(this, R.color.colorPrimary));
    }

    protected abstract @LayoutRes int getContentViewLayout();
    protected abstract BasePresenter createPersenter();
    protected abstract void attach();
}