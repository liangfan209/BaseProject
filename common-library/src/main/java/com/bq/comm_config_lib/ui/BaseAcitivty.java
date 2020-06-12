package com.bq.comm_config_lib.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bq.comm_config_lib.R;
import com.bq.comm_config_lib.mvp.IView;
import com.fan.baseuilibrary.view.dialog.LoadingDialog;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/5/28
 * 版权：
 */
public abstract class BaseAcitivty extends AppCompatActivity implements IView {

    private LoadingDialog mLoadingDialog;
    ImageView mIvTitleLeft;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewLayout());
        mLoadingDialog = new LoadingDialog(this);
        ButterKnife.bind(this);
        View backIv = findViewById(R.id.iv_title_left);
        if (backIv != null) {
            backIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        attach();

    }


    protected abstract @LayoutRes
    int getContentViewLayout();

    protected abstract void attach();

}