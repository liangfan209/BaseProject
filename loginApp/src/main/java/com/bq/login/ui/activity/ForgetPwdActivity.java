package com.bq.login.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.ui.BaseAcitivty;
import com.bq.comm_config_lib.utils.CheckUtils;
import com.bq.login.ApiLogin;
import com.bq.login.R;
import com.bq.login.R2;
import com.bq.login.bean.LoginInfo;
import com.bq.login.mvp.LoginIView;
import com.bq.login.mvp.LoginPresenter;
import com.fan.baseuilibrary.utils.Utils;
import com.fan.baseuilibrary.view.DeletableEditText;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = AppArouter.TEMPLTE_FORGET_PWD)
public class ForgetPwdActivity extends BaseAcitivty implements LoginIView {


    @BindView(R2.id.rlt_close_page)
    RelativeLayout mRltClosePage;
    @BindView(R2.id.et_phone)
    DeletableEditText mEtPhone;
    @BindView(R2.id.et_vertical_code)
    EditText mEtVerticalCode;
    @BindView(R2.id.tv_get_vertical_code)
    TextView mTvGetVerticalCode;
    @BindView(R2.id.et_new_pwd)
    DeletableEditText mEtNewPwd;
    @BindView(R2.id.et_repwd)
    DeletableEditText mEtRepwd;
    @BindView(R2.id.tv_comfirm_login)
    TextView mTvComfirmLogin;
    String phoneNumber = "";
    String newPwd = "";

    private LoginPresenter mLoginPresenter;
    @Override
    protected int getContentViewLayout() {
        return R.layout.login_activity_forget_pwd;
    }

    @Override
    protected void attach() {
        mLoginPresenter = new LoginPresenter(this);
    }

    @Override
    public void getVerticalCodeView() {
        Utils.showToast(this, "验证码获取成功");
    }

    @Override
    public void forgetPwdView() {
        mLoginPresenter.login(ApiLogin.API_LOGIN,phoneNumber,newPwd);
    }

    @Override
    public void loginView(LoginInfo info) {
        //登陆到主界面
        Utils.showToast(this,"登录成功");
    }

    @OnClick({R2.id.rlt_close_page, R2.id.tv_get_vertical_code, R2.id.tv_comfirm_login})
    public void onViewClicked(View view) {
        if(view.getId() == R.id.rlt_close_page){
            finish();
        }else if(view.getId() == R.id.tv_get_vertical_code){
            phoneNumber = mEtPhone.getText().toString().replaceAll(" ", "").trim();
            if(CheckUtils.checkPhoneNumber(this,phoneNumber)){
                mLoginPresenter.getVertificatCode(ApiLogin.API_GET_VERTIFICAT_CODE,"1",phoneNumber);
            }
        }else if(view.getId() == R.id.tv_comfirm_login){
            //忘记密码
            checkForm();
        }
    }

    private void checkForm() {
        newPwd = mEtNewPwd.getText().toString().trim();
        String rePwd = mEtRepwd.getText().toString().trim();
        String code = mEtVerticalCode.getText().toString().trim();
        String phoneNumber = mEtPhone.getText().toString().replaceAll(" ", "").trim();
        if (!CheckUtils.checkPhoneNumber(this,phoneNumber)) {
            return;
        }

        if(TextUtils.isEmpty(newPwd)){
            Utils.showToast(this, R.string.new_pwd_not_null);
            return;
        }

        if(TextUtils.isEmpty(rePwd)){
            Utils.showToast(this, R.string.confirm_pwd_not_null);
            return;
        }
        if(TextUtils.isEmpty(code)){
            Utils.showToast(this, R.string.vertication_code_not_null);
            return;
        }
        if (!newPwd.equals(rePwd)) {
            Utils.showToast(this, R.string.pwd_not_compaire);
            return;
        }
        newPwd = Utils.md5(newPwd);
        mLoginPresenter.forgetPwd(ApiLogin.API_FORGET_PWD,phoneNumber,newPwd,code);
    }
}
