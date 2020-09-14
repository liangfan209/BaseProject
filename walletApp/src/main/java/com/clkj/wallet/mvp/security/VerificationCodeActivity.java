package com.clkj.wallet.mvp.security;

import android.os.Bundle;

import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;

public class VerificationCodeActivity extends BaseActivity {

//    @BindView(R.id.iv_close_page)
//    ImageView mIvClosePage;
//    @BindView(R.id.tv_get_verify_Code)
//    TextView mTvGetVerifyCode;
//    @BindView(R.id.et_vertify_code)
//    VerifyEditText mEtVertifyCode;
//    @BindView(R.id.tv_complet)
//    TextView mTvComplet;
//    @BindView(R.id.tv_phone_number)
//    TextView mTvPhoneNumber;
//    @BindView(R.id.tv_msg)
//    TextView mTvMsg;


    private int type = 1;
    private String smsCode;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewLayout() {
//        return R.layout.activity_verification_code;
        return 0;
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected void attach() {
//        String phoneNumber = new UserUtils(this).getPhoneNumber();
//        type = getIntent().getExtras().getInt("type");
//        if(type == 1){
//            mTvMsg.setText("设置支付密码");
//        }else{
//            mTvMsg.setText("修改支付密码");
//        }
//        mTvPhoneNumber.setText("中国(+86)"+phoneNumber);
//        initView();
//        KeyboardUtils.hideSoftInput(this);
    }

    private void initView() {
//        mEtVertifyCode.setInputCompleteListener(new VerifyEditText.inputCompleteListener() {
//            @Override
//            public void inputComplete(VerifyEditText et, String content) {
//
//            }
//
//            @Override
//            public void update(VerifyEditText et, String content) {
//                int length = content.trim().length();
//                if (length == 6) {
//                    mTvComplet.setEnabled(true);
//                    KeyboardUtils.hideSoftInput(VerificationCodeActivity.this);
//                    smsCode = content;
//                } else {
//                    mTvComplet.setEnabled(false);
//                }
//            }
//        });

    }



    void getViticalCode() {
        sendSMS();
    }

    public void sendSMS() {
//        String access_token = new UserUtils(this).getUser().getAccess_token();
//        String phoneNumber = new UserUtils(this).getPhoneNumber();
//        exeHttpWithDialog(ApiServiceManager.getApi().getVerificationCode("app.sendMessage", "app", "1", access_token,
//                phoneNumber))
//                .subscribe(new BaseSubscriber<BaseBean>(this) {
//                    @Override
//                    public void result(BaseBean bean) {
//                        CountDownHelper countDownHelper = new CountDownHelper(mTvGetVerifyCode, "获取验证码", "重新获取", 60, 1, 2);
//                        countDownHelper.start();
//                        new Handler().postDelayed(()->{
//                            mEtVertifyCode.setFirstForcus();
//                        },200);
//                    }
//                });
    }

    private void checkSmsCode() {
//        String access_token = new UserUtils(this).getUser().getAccess_token();
//        String phoneNumber = new UserUtils(this).getPhoneNumber();
//        exeHttpWithDialog(ApiServiceManager.getApi().checkSmsCode("app.valMessage", "app", "1", access_token,
//                phoneNumber, smsCode))
//                .subscribe(new BaseSubscriber<BaseBean>(this) {
//                    @Override
//                    public void result(BaseBean bean) {
//                        Intent intent = new Intent(VerificationCode1Activity.this, InputPayPwdActivity.class);
//                        intent.putExtra("smsCode",smsCode);
//                        intent.putExtra("type",1);
//                        startActivityForResult(intent, 1);
//                    }
//                });
    }

//    @OnClick({R.id.iv_close_page, R.id.tv_get_verify_Code, R.id.tv_complet})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.iv_close_page:
//                finish();
//                break;
//            case R.id.tv_get_verify_Code:
//                getViticalCode();
//                break;
//            case R.id.tv_complet:
//                checkSmsCode();
//                break;
//        }
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == 11) {
//            finish();
//        }
//    }
}
