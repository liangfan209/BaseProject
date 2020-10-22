package com.clkj.login.mvp.login.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.msgService.MessageEvent;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.comm_config_lib.utils.CommSpUtils;
import com.bq.utilslib.AccountValidatorUtil;
import com.bq.utilslib.EditFormatUtils;
import com.clkj.login.R;
import com.clkj.login.R2;
import com.clkj.login.mvp.login.presenter.LoginPresenter;
import com.clkj.login.requset.bean.LoginInfo;
import com.fan.baseuilibrary.utils.CountDownHelper;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.fan.baseuilibrary.view.DeletableEditText;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = AppArouter.LOGIN_BIND_ACTIVITY)
public class BindPhoneActivity extends BaseActivity implements LoginBaseIView {

    @BindView(R2.id.et_phone)
    DeletableEditText mEtPhone;
    @BindView(R2.id.tv_login)
    TextView mTvLogin;
    @BindView(R2.id.iv_title_left)
    ImageView mIvTitleLeft;
    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.tv_input_verification)
    DeletableEditText mTvInputVerification;
//    @BindView(R2.id.tv_get_verification_code1)
    TextView mTvGetVerificationCode1;

    @Autowired
    String open_id;
    @Autowired
    String access_token;



    private LoginPresenter mLoginPresenter;
    CountDownHelper countDownHelper;//计时器


    @Override
    protected int getContentViewLayout() {
        return R.layout.login_activity_bind_phone;
    }

    @Override
    protected BasePresenter createPersenter() {
        mLoginPresenter = new LoginPresenter(this, true);
        return mLoginPresenter;
    }

    @Override
    protected void attach() {
        ARouter.getInstance().inject(this);
        mTvTitle.setText("绑定手机号");
        EditFormatUtils.phoneNumAddSpace(mEtPhone);

        mTvGetVerificationCode1 = findViewById(R.id.tv_get_verification_code1);
        mTvGetVerificationCode1.setOnClickListener(v->{
            String phoneNumber = mEtPhone.getText().toString().replaceAll(" ","");
            mLoginPresenter.getVertificatCode(phoneNumber,"bindwechat");
        });

    }



    @Override
    public void loginVertificationView(LoginInfo info) {
        //将token保存到本地sp中
        CommSpUtils.saveLoginInfo(new Gson().toJson(info));
        String phoneNumber = mEtPhone.getText().toString().replaceAll(" ", "").trim();
        EventBus.getDefault().post(new MessageEvent("create_jpush",this,phoneNumber));
        ARouter.getInstance().build(AppArouter.MAIN_ACTIVITY).navigation();
        finish();
    }



    @OnClick({R2.id.tv_login})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.tv_login) {
            String phoneNumber = mEtPhone.getText().toString().replaceAll(" ", "").trim();
            if (!AccountValidatorUtil.isMobile(phoneNumber)) {
                ToastUtils.showToast(this, "请输入正确的手机号码");
                return;
            }
            String verifyCode = mTvInputVerification.getText().toString().trim();
            if(StringUtils.isEmpty(verifyCode)){
                ToastUtils.showToast(this, "请输入验证码");
                return;
            }
            mLoginPresenter.wechatRegister(access_token,open_id,phoneNumber, DeviceUtils.getUniqueDeviceId(),verifyCode);

        }
    }

    @Override
    public void getVerticalCodeView() {
        countDownHelper = new CountDownHelper(mTvGetVerificationCode1, "获取验证码", "重新获取", 60, 1, 2);
        countDownHelper.start();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownHelper != null) {
            countDownHelper.stop();
        }
    }

}
