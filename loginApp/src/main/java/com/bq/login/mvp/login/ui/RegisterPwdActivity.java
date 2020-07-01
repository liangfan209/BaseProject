package com.bq.login.mvp.login.ui;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePersenter;
import com.bq.comm_config_lib.mvp.ui.BaseAcitivty;
import com.bq.login.R;
import com.bq.login.R2;
import com.bq.login.mvp.login.presenter.LoginPresenter;
import com.bq.login.requset.bean.LoginInfo;
import com.bq.utilslib.AccountValidatorUtil;
import com.bq.utilslib.EditFormatUtils;
import com.bq.utilslib.Md5Utils;
import com.fan.baseuilibrary.utils.CountDownHelper;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.fan.baseuilibrary.view.DeletableEditText;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = AppArouter.FORGET_PWD_ACTIVITY)
public class RegisterPwdActivity extends BaseAcitivty implements LoginBaseIView {

    public static final int REGISTER = 1;
    public static final int FORGET_PWD = 2;


    @BindView(R2.id.et_phone)
    DeletableEditText mEtPhone;
    @BindView(R2.id.et_vertical_code)
    EditText mEtVerticalCode;
    @BindView(R2.id.tv_get_verification_code)
    TextView mTvGetVerticalCode;
    @BindView(R2.id.et_new_pwd)
    DeletableEditText mEtNewPwd;
    @BindView(R2.id.et_repwd)
    DeletableEditText mEtRepwd;
    @BindView(R2.id.tv_comfirm_form)
    TextView mTvComfirmLogin;
    String phoneNumber = "";
    String newPwd = "";
    @BindView(R2.id.iv_title_left)
    ImageView mIvTitleLeft;
    @BindView(R2.id.tv_title)
    TextView mTvTitle;

    @Autowired(required = true)
    int optionType;
    private LoginPresenter mLoginPresenter;
    CountDownHelper countDownHelper;

    @Override
    protected int getContentViewLayout() {
        return R.layout.login_activity_forget_pwd;
    }

    @Override
    protected BasePersenter createPersenter() {
        mLoginPresenter = new LoginPresenter(this);
        return mLoginPresenter;
    }

    @Override
    protected void attach() {
        ARouter.getInstance().inject(this);
        initView();
        mTvTitle.setText(optionType == FORGET_PWD ? "忘记密码" : "注册");
        mTvComfirmLogin.setText(optionType == FORGET_PWD ? "完成" : "注册");
    }

    private void initView() {
        EditFormatUtils.phoneNumAddSpace(mEtPhone);
        mEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String phoneNum = s.toString().replaceAll(" ", "").trim();
                mTvGetVerticalCode.setEnabled(AccountValidatorUtil.isMobile(phoneNum) ? true : false);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    @Override
    public void getVerticalCodeView() {
        ToastUtils.showToast(this, "验证码获取成功");
    }


    @Override
    public void loginView(LoginInfo info) {
        //登陆到主界面
        ToastUtils.showToast(this, "登录成功");
    }

    @Override
    public void registerView() {
        ToastUtils.showToastOk(this,"注册成功");
        new Handler().postDelayed(()->{
            finish();
        },1000);
    }

    @Override
    public void forgetPwdView() {
        ToastUtils.showToastOk(this,"修改成功");
        new Handler().postDelayed(()->{
            finish();
        },1000);
    }


    /**
     * 提交表单
     */
    private void CommitForm() {
        newPwd = mEtNewPwd.getText().toString().trim();
        String rePwd = mEtRepwd.getText().toString().trim();
        String code = mEtVerticalCode.getText().toString().trim();
        String phoneNumber = mEtPhone.getText().toString().replaceAll(" ", "").trim();
        if(!mLoginPresenter.checkRegisterFrom(phoneNumber,newPwd,rePwd,code)){
            return;
        }
        newPwd = Md5Utils.md5(newPwd);
        if(optionType == REGISTER){
            mLoginPresenter.register(phoneNumber, newPwd, code);
        }else if(optionType == FORGET_PWD){
            mLoginPresenter.forgetPwd(phoneNumber, newPwd, code);
        }
    }

    @OnClick({R2.id.tv_get_verification_code, R2.id.tv_comfirm_form, R2.id.iv_title_left})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.tv_get_verification_code) {
//            phoneNumber = mEtPhone.getText().toString().replaceAll(" ", "").trim();
//            if (CheckUtils.checkPhoneNumber(this, phoneNumber)) {
//                mLoginPresenter.getVertificatCode("1", phoneNumber);
//            }
            countDownHelper = new CountDownHelper(mTvGetVerticalCode, "获取验证码", "重新获取", 60, 1,2);
            countDownHelper.start();
        } else if (view.getId() == R.id.tv_comfirm_form) {
            //忘记密码
            CommitForm();
        } else if (view.getId() == R.id.iv_title_left) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownHelper != null) {
            countDownHelper.stop();
        }
    }

}