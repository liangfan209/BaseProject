package com.bq.login.mvp.login.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.StringUtils;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.comm_config_lib.utils.CommSpUtils;
import com.bq.login.R;
import com.bq.login.R2;
import com.bq.login.api.bean.LoginConfigBean;
import com.bq.login.mvp.login.presenter.LoginPresenter;
import com.bq.login.requset.bean.LoginInfo;
import com.bq.utilslib.AccountValidatorUtil;
import com.bq.utilslib.AppUtils;
import com.bq.utilslib.EditFormatUtils;
import com.bq.utilslib.Md5Utils;
import com.fan.baseuilibrary.utils.CountDownHelper;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.fan.baseuilibrary.view.DeletableEditText;
import com.fan.baseuilibrary.view.captcha.Captcha;
import com.fan.baseuilibrary.view.dialog.CaptchaDialog;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.OnClick;
import skin.support.widget.SkinCompatCheckBox;

@Route(path = AppArouter.LOGIN_ACTVITY)
public class LoginActivity extends BaseActivity implements LoginBaseIView {

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
    @BindView(R2.id.tv_remark)
    TextView mTvRemark;

    @BindView(R2.id.rlt_pwd_option) //密码操作、注册、忘记
            RelativeLayout mRltPwdOption;
    @BindView(R2.id.rlt_pwd) //密码选项
            RelativeLayout mRltPwd;

    @BindView(R2.id.cb_eye) //密码选项
            SkinCompatCheckBox mCbEye;



    @BindView(R2.id.cb_login_type)
    SkinCompatCheckBox mCbLoginType;
    private LoginPresenter mLoginPresenter;
    private LoginConfigBean loginConfig;
    CountDownHelper countDownHelper;//计时器

    @Autowired
    String mPath;
    @Autowired
    Bundle mBundle;

    @Override
    protected int getContentViewLayout() {
        String jsonStr = AppUtils.getAssetJson(this,"login_login_config.json");
        loginConfig = new Gson().fromJson(jsonStr, LoginConfigBean.class);
        return R.layout.login_activity_login;
    }

    @Override
    protected BasePresenter createPersenter() {
        mLoginPresenter = new LoginPresenter(this, true);
        return mLoginPresenter;
    }

    @Override
    protected void attach() {
        mTvTitle.setText("登录");
        EditFormatUtils.phoneNumAddSpace(mEtPhone);
        initVerification();
        ARouter.getInstance().inject(this);
        updateView();
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
//                mTvLogin.setEnabled(AccountValidatorUtil.isMobile(phoneNum) ? true : false);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void updateView() {


        //密码登录
        if (loginConfig.getLoginType() == 1) {
            selectPwd();
            mCbLoginType.setVisibility(View.GONE);
            //验证码登陆
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

        //模板2
        if(loginConfig.getTemplet() == 2){
            mCbEye.setVisibility(View.VISIBLE);
            mCbLoginType.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,
                    getResources().getDrawable(R.mipmap.icon_login_next),null);
        }else{
            mCbEye.setVisibility(View.GONE);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mEtPwd.getLayoutParams();
            layoutParams.setMargins(0,0, AppUtils.dp2px(this,15),0);
            mCbEye.setLayoutParams(layoutParams);
            mCbLoginType.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,
                    null,null);
        }
        if(loginConfig.getTemplet() == 2 && loginConfig.getLoginType() == 2){
            mTvRemark.setText("无需注册，可直接登录");
        }else{
            mTvRemark.setText("");
        }
    }


    /**
     * 选中密码
     */
    private void selectPwd() {
        mRltPwdOption.setVisibility(View.VISIBLE);
        mRltPwd.setVisibility(View.VISIBLE);
        mRltVerification.setVisibility(View.GONE);
        mTvRemark.setText("");
    }

    /**
     * 选中验证码
     */
    private void selectVerification() {
        mRltPwdOption.setVisibility(View.GONE);
        mRltPwd.setVisibility(View.GONE);
        mRltVerification.setVisibility(View.VISIBLE);
        //如果是模板2那么，描述文字显示出来
        if(loginConfig.getTemplet() == 2){
            mTvRemark.setText("无需注册，可直接登录");
        }
    }

    private void login() {
//        mEtPhone.setText("13260606900");
//        mEtPwd.setText("123456");
        String phoneNumber = mEtPhone.getText().toString().replaceAll(" ", "").trim();
        String pwd = mEtPwd.getText().toString().trim();
        pwd = Md5Utils.md5(pwd);

        if(mCbLoginType.isChecked() || mCbLoginType.getVisibility() == View.GONE){
            mLoginPresenter.login(phoneNumber, pwd);
        }else{
            String code = mTvInputVerification.getText().toString();
            mLoginPresenter.loginVertificationCode(phoneNumber,code);
//            ToastUtils.showToast(this,"验证码登陆正在开发中...");

        }

    }

    @Override
    public void loginVertificationView(LoginInfo info) {
        //将token保存到本地sp中
        CommSpUtils.saveLoginInfo(new Gson().toJson(info));
        if(info.getIs_password() == 0){
            ARouter.getInstance().build(AppArouter.LOGIN_SETTING_ACTIVITY).navigation();
        }else{
            //跳转到主页面中
            if (StringUtils.isEmpty(mPath)) {
                ARouter.getInstance().build(AppArouter.MAIN_ACTIVITY).navigation();
            }else if(mPath.equals("-1")){
            } else {
                ARouter.getInstance().build(mPath)
                        .withBundle("mBundle", mBundle).navigation();
            }
        }

        finish();
    }

    @Override
    public void loginView(LoginInfo info) {
        //将token保存到本地sp中
        CommSpUtils.saveLoginInfo(new Gson().toJson(info));
        //跳转到主页面中
        if (StringUtils.isEmpty(mPath)) {
            ARouter.getInstance().build(AppArouter.MAIN_ACTIVITY).navigation();
        } else if(mPath.equals("-1")){
        }else{
            ARouter.getInstance().build(mPath)
                    .withBundle("mBundle", mBundle).navigation();
        }
        finish();
    }


    @OnClick({R2.id.tv_forget_pwd, R2.id.tv_login, R2.id.tv_get_verification_code, R2.id.tv_register, R2.id.cb_login_type,
            R2.id.cb_eye})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.tv_login) {
            if (loginConfig.isHasImageVirification()) {
                showCaptcha();
            } else {
                login();
            }
        } else if (view.getId() == R.id.tv_forget_pwd) {
            ARouter.getInstance().build(AppArouter.FORGET_PWD_ACTIVITY).withInt("optionType"
                    , RegisterPwdActivity.FORGET_PWD).navigation();
        } else if (view.getId() == R.id.tv_register) {
            ARouter.getInstance().build(AppArouter.FORGET_PWD_ACTIVITY).withInt("optionType"
                    , RegisterPwdActivity.REGISTER).navigation();
        } else if (view.getId() == R.id.tv_get_verification_code) {
            //获取登录的验证码
            String phoneNumber = mEtPhone.getText().toString().replaceAll(" ","");
            mLoginPresenter.getVertificatCode(phoneNumber,"login");
        } else if (view.getId() == R.id.cb_login_type) {
        } else if (view.getId() == R.id.cb_eye) {
            boolean checked = mCbEye.isChecked();
            mEtPwd.setInputType(checked ? 128 : 129);
            if(!mEtPwd.hasFocus()){
                mEtPwd.setClearDrawableVisible(false);
            }
        }
    }

    @Override
    public void getVerticalCodeView() {
        countDownHelper = new CountDownHelper(mTvGetVerificationCode, "获取验证码", "重新获取", 60, 1, 2);
        countDownHelper.start();
    }

    private void showCaptcha() {
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
                ToastUtils.showToast(LoginActivity.this, "验证失败");
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
