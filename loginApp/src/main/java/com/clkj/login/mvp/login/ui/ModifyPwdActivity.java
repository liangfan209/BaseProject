package com.clkj.login.mvp.login.ui;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.StringUtils;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.comm_config_lib.utils.CommSpUtils;
import com.clkj.login.R;
import com.clkj.login.R2;
import com.clkj.login.api.bean.LoginConfigBean;
import com.clkj.login.mvp.login.presenter.LoginPresenter;
import com.bq.utilslib.AppUtils;
import com.bq.utilslib.Md5Utils;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.fan.baseuilibrary.view.EyeRelativeLayout;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/22
 * 版权：
 */
@Route(path = AppArouter.LOGIN_MODIFY_ACTIVITY)
public class ModifyPwdActivity extends BaseActivity implements LoginBaseIView {
    @BindView(R2.id.iv_title_left)
    ImageView mIvTitleLeft;
    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.tv_right)
    TextView mTvRight;
    @BindView(R2.id.rlt_title)
    RelativeLayout mRltTitle;

    @BindView(R2.id.eye_old_pwd)
    EyeRelativeLayout mEyeOldPwd;
    @BindView(R2.id.eye_pwd)
    EyeRelativeLayout mEyePwd;
    @BindView(R2.id.eye_re_pwd)
    EyeRelativeLayout mEyeRePwd;

    //    @BindView(R2.id.et_old_pwd)
//    DeletableEditText mEtOldPwd;
//    @BindView(R2.id.et_pwd)
//    DeletableEditText mEtPwd;
//    @BindView(R2.id.et_re_pwd)
//    DeletableEditText mEtRePwd;
    @BindView(R2.id.tv_modify)
    TextView mtvModify;

    private LoginPresenter mLoginPresenter;
    private LoginConfigBean loginConfig;

    @Override
    protected int getContentViewLayout() {
        return R.layout.login_activity_modify;
    }

    @Override
    protected BasePresenter createPersenter() {
        mLoginPresenter = new LoginPresenter(this, false);
        return mLoginPresenter;
    }

    @Override
    protected void attach() {
        String jsonStr = AppUtils.getAssetJson(this, "login_login_config.json");
        loginConfig = new Gson().fromJson(jsonStr, LoginConfigBean.class);
        mTvTitle.setText("修改密码");
        mEyeOldPwd.setHintText("请输入原密码");
        mEyePwd.setHintText("请设置新密码");
        mEyeRePwd.setHintText("请确认新密码");
        mEyeOldPwd.setEye(false);
        mEyePwd.setEye(false);
        mEyeRePwd.setEye(false);
    }


    @Override
    public void modifyPwdView() {
        ToastUtils.showToastOk(this, "修改成功");
        new Handler().postDelayed(() -> {
            finish();
            CommSpUtils.saveLoginInfo("");
//            ActivityUtils.finishAllActivities();
//            ARouter.getInstance().build(AppArouter.LOGIN_ACTVITY).navigation();
            EventBus.getDefault().post("go_logout");
        }, 1000);
    }

    @OnClick({R2.id.iv_title_left, R2.id.tv_modify})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.tv_modify) {
//            String oldPwd = mEtOldPwd.getText().toString();
//            String newPwd = mEtPwd.getText().toString();
//            String rePwd = mEtRePwd.getText().toString();
            String oldPwd = mEyeOldPwd.getText();
            String newPwd = mEyePwd.getText().toString();
            String rePwd = mEyeRePwd.getText().toString();


            if (StringUtils.isEmpty(oldPwd)) {
                ToastUtils.showToast(this, "请输入原密码");
                return;
            }
            if (StringUtils.isEmpty(newPwd)) {
                ToastUtils.showToast(this, "请输入新密码");
                return;
            }
            if (StringUtils.isEmpty(rePwd)) {
                ToastUtils.showToast(this, "请确认新密码");
                return;
            }
            if (!newPwd.equals(rePwd)) {
                ToastUtils.showToast(this, "确认密码不一致");
                return;
            }
            mLoginPresenter.modifypwd(Md5Utils.md5(oldPwd), Md5Utils.md5(newPwd));
        }
    }
}
