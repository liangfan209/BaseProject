package com.bq.login.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.ui.BaseAcitivty;
import com.bq.login.ApiLogin;
import com.bq.login.R;
import com.bq.login.R2;
import com.bq.login.bean.LoginInfo;
import com.bq.login.mvp.LoginIView;
import com.bq.login.mvp.LoginPresenter;
import com.bq.utilslib.rsa.AccountValidatorUtil;
import com.bq.utilslib.rsa.EditFormatUtils;
import com.fan.baseuilibrary.utils.Utils;
import com.fan.baseuilibrary.view.DeletableEditText;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = AppArouter.TEMPLTE_LOGIN)
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
        String phoneNumber = mEtPhone.getText().toString().replaceAll(" ", "").trim();
        if (!AccountValidatorUtil.isMobile(phoneNumber)) {
            Utils.showToast(this, "请输入正确的手机号码");
            return;
        }
        String pwd = mEtPwd.getText().toString().trim();
        pwd = Utils.md5(pwd);
        mLoginPresenter.login(ApiLogin.API_LOGIN, phoneNumber, pwd);
    }

    @Override
    public void loginView(LoginInfo info) {
        Utils.showToastOk(this, "登陆成功");
    }


    @OnClick({R2.id.tv_forget_pwd, R2.id.tv_login})
    public void onViewClicked(View view) {
        if(view.getId() == R.id.tv_login){
            login();
        }else if(view.getId() == R.id.tv_forget_pwd){
            ARouter.getInstance().build(AppArouter.TEMPLTE_FORGET_PWD).navigation();
        }
    }
}
