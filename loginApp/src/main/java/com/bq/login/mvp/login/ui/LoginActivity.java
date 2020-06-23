package com.bq.login.mvp.login.ui;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePersenter;
import com.bq.comm_config_lib.mvp.ui.BaseAcitivty;
import com.bq.comm_config_lib.utils.CommSpUtils;
import com.bq.login.R;
import com.bq.login.R2;
import com.bq.login.api.bean.LoginConfigBean;
import com.bq.login.mvp.login.presenter.LoginPresenter;
import com.bq.login.requset.bean.LoginInfo;
import com.bq.utilslib.AccountValidatorUtil;
import com.bq.utilslib.EditFormatUtils;
import com.bq.utilslib.Md5Utils;
import com.fan.baseuilibrary.utils.CountDownHelper;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.fan.baseuilibrary.view.DeletableEditText;
import com.fan.baseuilibrary.view.captcha.Captcha;
import com.fan.baseuilibrary.view.dialog.CaptchaDialog;

import androidx.appcompat.widget.AppCompatCheckBox;
import butterknife.BindView;
import butterknife.OnClick;

@Route(path = AppArouter.LOGIN_ACTVITY)
public class LoginActivity extends BaseAcitivty implements LoginBaseIView {

    @BindView(R2.id.et_phone)
    DeletableEditText mEtPhone;
    @BindView(R2.id.et_pwd)
    DeletableEditText mEtPwd;
    @BindView(R2.id.tv_forget_pwd)
    TextView mTvForgetPwd;
    @BindView(R2.id.tv_login)
    TextView mTvLogin;
    @BindView(R2.id.iv_title_left)
    ImageView mIvTitleLeft;
    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.tv_input_verification)
    DeletableEditText mTvInputVerification;
    @BindView(R2.id.tv_get_verification_code)
    TextView mTvGetVerificationCode;
    @BindView(R2.id.rlt_verification)// 验证码选项
            RelativeLayout mRltVerification;
    @BindView(R2.id.tv_register)
    TextView mTvRegister;
    @BindView(R2.id.rlt_pwd_option) //密码操作、注册、忘记
            RelativeLayout mRltPwdOption;
    @BindView(R2.id.rlt_pwd) //密码选项
            RelativeLayout mRltPwd;

    @BindView(R2.id.cb_login_type)
    AppCompatCheckBox mCbLoginType;
    private LoginPresenter mLoginPresenter;
    private LoginConfigBean loginConfig;
    CountDownHelper countDownHelper;//计时器

    @Override
    protected int getContentViewLayout() {
        return R.layout.login_activity_login;
    }

    @Override
    protected BasePersenter createPersenter() {
        mLoginPresenter = new LoginPresenter(this,true);
        return mLoginPresenter;
    }

    @Override
    protected void attach() {
        mIvTitleLeft.setVisibility(View.GONE);
        mTvTitle.setText("登录");
        EditFormatUtils.phoneNumAddSpace(mEtPhone);
        initVerification();
    }

    /**
     * 初始化验证码
     */
    private void initVerification() {

        mCbLoginType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCbLoginType.setVisibility(View.VISIBLE);
                mCbLoginType.setText(isChecked ? "验证码登录" : "密码登录");
                if (isChecked) {
                    selectPwd();
                } else {
                    selectVerification();
                }
            }
        });
        mEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String phoneNum = s.toString().replaceAll(" ", "").trim();
                mTvGetVerificationCode.setEnabled(AccountValidatorUtil.isMobile(phoneNum) ? true : false);
                mTvLogin.setEnabled(AccountValidatorUtil.isMobile(phoneNum) ? true : false);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void updateView(LoginConfigBean config) {
        loginConfig = config;
        //密码登录
        if (loginConfig.getLoginType() == 1) {
            selectPwd();
            mCbLoginType.setVisibility(View.GONE);
        } else if (loginConfig.getLoginType() == 2) {
            selectVerification();
            mCbLoginType.setVisibility(View.GONE);
        } else if (loginConfig.getLoginType() == 3) {
            //默认第一次是密码选中
            selectPwd();
            mCbLoginType.setChecked(true);
            mCbLoginType.setVisibility(View.VISIBLE);
            mCbLoginType.setText("验证码登录");
        }
    }


    /**
     * 选中密码
     */
    private void selectPwd() {
        mRltPwdOption.setVisibility(View.VISIBLE);
        mRltPwd.setVisibility(View.VISIBLE);
        mRltVerification.setVisibility(View.GONE);

    }

    /**
     * 选中验证码
     */
    private void selectVerification() {
        mRltPwdOption.setVisibility(View.GONE);
        mRltPwd.setVisibility(View.GONE);
        mRltVerification.setVisibility(View.VISIBLE);
    }

    private void login() {
//        mEtPhone.setText("13260606900");
//        mEtPwd.setText("123456");
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
        ARouter.getInstance().build(AppArouter.LOGIN_SETTING_ACTIVITY).navigation();
    }


    @OnClick({R2.id.tv_forget_pwd, R2.id.tv_login, R2.id.tv_get_verification_code, R2.id.tv_register, R2.id.cb_login_type})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.tv_login) {
            if(loginConfig.isHasImageVirification()){
                showCaptcha();
            }else{
                login();
            }
        } else if (view.getId() == R.id.tv_forget_pwd) {
            ARouter.getInstance().build(AppArouter.FORGET_PWD_ACTIVITY).withInt("optionType"
                    , RegisterPwdActivity.FORGET_PWD).navigation();
        } else if (view.getId() == R.id.tv_register) {
            ARouter.getInstance().build(AppArouter.FORGET_PWD_ACTIVITY).withInt("optionType"
                    , RegisterPwdActivity.REGISTER).navigation();
        } else if (view.getId() == R.id.tv_get_verification_code) {
            countDownHelper = new CountDownHelper(mTvGetVerificationCode, "获取验证码", "重新获取", 60, 1,2);
            countDownHelper.start();
        } else if (view.getId() == R.id.cb_login_type) {

        }
    }

    private void showCaptcha(){
        final CaptchaDialog dialog = new CaptchaDialog(this, com.fan.baseuilibrary.R.layout.dialog_captcha);
        dialog.refreshImg();
        final Captcha captCha = dialog.getCaptCha();
        captCha.setCaptchaListener(new Captcha.CaptchaListener() {
            @Override
            public String onAccess(long time) {
                dialog.dissmiss();
                login();
                return "验证通过,耗时" + time + "毫秒";
            }

            @Override
            public String onFailed(int failedCount) {
                ToastUtils.showToast(LoginActivity.this,"验证失败");
                captCha.reset(true);
                return "验证失败,已失败" + failedCount + "次";
            }

            @Override
            public String onMaxFailed() {
                captCha.reset(true);
                return "验证失败,帐号已封锁";
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownHelper != null) {
            countDownHelper.stop();
        }
    }

}
