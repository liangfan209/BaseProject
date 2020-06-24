package com.bq.login.mvp.login.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePersenter;
import com.bq.comm_config_lib.mvp.ui.BaseAcitivty;
import com.bq.login.R;
import com.bq.login.R2;
import com.bq.login.mvp.login.presenter.LoginPresenter;
import com.fan.baseuilibrary.view.DeletableEditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/22
 * 版权：
 */
@Route(path = AppArouter.LOGIN_SETTING_ACTIVITY)
public class SettingPwdActivity extends BaseAcitivty implements LoginBaseIView{
    @BindView(R2.id.iv_title_left)
    ImageView mIvTitleLeft;
    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.tv_right)
    TextView mTvRight;
    @BindView(R2.id.rlt_title)
    RelativeLayout mRltTitle;
    @BindView(R2.id.tv_jump)
    TextView mTvJump;
    @BindView(R2.id.et_pwd)
    DeletableEditText mEtPwd;
    @BindView(R2.id.et_re_pwd)
    DeletableEditText mEtRePwd;
    @BindView(R2.id.tv_setting)
    TextView mTvSetting;

    private LoginPresenter mLoginPresenter;

    @Override
    protected int getContentViewLayout() {
        return R.layout.login_activity_setting;
    }

    @Override
    protected BasePersenter createPersenter() {
        mLoginPresenter = new LoginPresenter(this,false);
        return mLoginPresenter;
    }

    @Override
    protected void attach() {
        mTvTitle.setText("设置密码");
    }


    @Override
    public void settingPwdView() {
        ARouter.getInstance().build(AppArouter.MAIN_ACTIVITY).navigation();
        finish();
    }

    @OnClick({R2.id.iv_title_left, R2.id.tv_jump, R2.id.tv_setting})
    public void onViewClicked(View view) {
        if(view.getId() == R.id.tv_jump){
            ARouter.getInstance().build(AppArouter.MAIN_ACTIVITY).navigation();
            finish();
        } else if(view.getId() == R.id.tv_setting){
            mLoginPresenter.setPwd("");
        }
    }
}
