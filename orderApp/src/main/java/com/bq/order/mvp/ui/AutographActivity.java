package com.bq.order.mvp.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.order.R;
import com.bq.order.R2;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.fan.baseuilibrary.view.SignatureView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/8/6
 * 版权：
 */
@Route(path = AppArouter.ORDER_AUTOGRAPH_ACTIVITY)
public class AutographActivity extends BaseActivity {
    @BindView(R2.id.sign_view)
    SignatureView mSignView;
    @BindView(R2.id.tv_back)
    TextView mTvBack;
    @BindView(R2.id.tv_clear)
    TextView mTvClear;
    @BindView(R2.id.tv_commit)
    TextView mTvCommit;



    @Override
    protected int getContentViewLayout() {
        return R.layout.order_autograph_activity;
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void attach() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }


    @OnClick({R2.id.tv_back, R2.id.tv_clear, R2.id.tv_commit})
    public void onViewClicked(View view) {

        if(view.getId() == R.id.tv_back){
            finish();
        }else if(view.getId() == R.id.tv_clear){
            mSignView.clear();
        }else if(view.getId() == R.id.tv_commit){
            //图片保存到本地
            if (mSignView.getTouched()) {
                new RxPermissions(this)
                        .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    mSignView.save("/sdcard/sign.png", true, 10);
//                                    ToastUtils.showToast(AutographActivity.this,"图片保存成功");
                                    setResult(20);
                                    finish();
                                } else {
                                    ToastUtils.showToast(AutographActivity.this, "没有存储权限，请打开相关权限");
                                }
                            }
                        });
            } else {
                ToastUtils.showToast(this,"请先签名");
            }
        }
    }
}
