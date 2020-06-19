package com.bq.login.mvp.login.ui;

import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.ui.BaseAcitivty;
import com.bq.comm_config_lib.utils.CommSpUtils;
import com.bq.login.R;
import com.bq.login.R2;
import com.bq.login.mvp.login.presenter.LoginPresenter;
import com.bq.login.requset.bean.LoginInfo;
import com.bq.utilslib.AccountValidatorUtil;
import com.bq.utilslib.EditFormatUtils;
import com.bq.utilslib.Md5Utils;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.fan.baseuilibrary.view.DeletableEditText;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = AppArouter.LOGIN_ACTVITY)
public class LoginActivity extends BaseAcitivty implements LoginIView {

    @BindView(R2.id.tv_welcom)
    TextView mTvWelcom;
    @BindView(R2.id.et_phone)
    DeletableEditText mEtPhone;
    @BindView(R2.id.et_pwd)
    DeletableEditText mEtPwd;
    @BindView(R2.id.tv_forget_pwd)
    TextView mTvForgetPwd;
    @BindView(R2.id.tv_login)
    TextView mTvLogin;
    @BindView(R2.id.tv_protocol1)
    TextView mTvProtocol1;
    @BindView(R2.id.tv_protocol2)
    TextView mTvProtocol2;

    private LoginPresenter mLoginPresenter;

    @Override
    protected int getContentViewLayout() {
        return R.layout.login_activity_login;
    }

    @Override
    protected void attach() {
        mLoginPresenter = new LoginPresenter(this);
        EditFormatUtils.phoneNumAddSpace(mEtPhone);
    }


    private void login() {
        mEtPhone.setText("13260606900");
        mEtPwd.setText("123456");
        String phoneNumber = mEtPhone.getText().toString().replaceAll(" ", "").trim();
        if (!AccountValidatorUtil.isMobile(phoneNumber)) {
            ToastUtils.showToast(this, "请输入正确的手机号码");
            return;
        }
        String pwd = mEtPwd.getText().toString().trim();
        pwd = Md5Utils.md5(pwd);
        mLoginPresenter.login(phoneNumber, pwd);
    }

    @Override
    public void loginView(LoginInfo info) {
        //将token保存到本地sp中
        CommSpUtils.saveToken(info.getAuth_token());
        //跳转到主页面中
        ARouter.getInstance().build(AppArouter.MAIN_ACTIVITY).navigation();
    }


    @OnClick({R2.id.tv_forget_pwd, R2.id.tv_login})
    public void onViewClicked(View view) {
        if(view.getId() == R.id.tv_login){
            login();
        }else if(view.getId() == R.id.tv_forget_pwd){
            ARouter.getInstance().build(AppArouter.FORGET_PWD_ACTIVITY).navigation();
        }
    }
}
