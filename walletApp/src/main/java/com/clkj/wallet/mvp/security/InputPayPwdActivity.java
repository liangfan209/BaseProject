package com.clkj.wallet.mvp.security;

import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.fan.baseuilibrary.view.CustomPopWindow;

public class InputPayPwdActivity extends BaseActivity {

//    NumberKeyboardView numberKeyboardView;
//    @BindView(R.id.iv_title_left)
//    ImageView mIvTitleLeft;
//    @BindView(R.id.public_toolbar_title)
//    TextView mPublicToolbarTitle;
//    @BindView(R.id.pay_pwd_view)
//    PayPwdView mPayPwdView;
//    @BindView(R.id.tv_complet)
//    TextView mTvComplet;

    CustomPopWindow customPopWindow;

    private int type = 1;
    private String oldPwd = "";
    private String smsCode = "";
    private String payPwd = "";


    @Override
    protected int getContentViewLayout() {
//        R.layout.activity_input_pay_pwd
        return 0;
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected void attach() {
//        type = getIntent().getExtras().getInt("type");
//        if (type == 1) {
//            smsCode = getIntent().getExtras().getString("smsCode");
//        } else {
//            oldPwd = getIntent().getExtras().getString("old_pwd");
//        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
//        numberKeyboardView = new NumberKeyboardView(this, new NumberKeyboardView.NumberKeyBoradCallBack() {
//            @Override
//            public void addNum(int num) {
//                mPayPwdView.addNumber(num);
//            }
//
//            @Override
//            public void delNum() {
//                mPayPwdView.delNumber();
//            }
//        });
//        customPopWindow = numberKeyboardView.showBottomView();
//        mPayPwdView.setCallBack(new PayPwdView.PayPwdViewInterface() {
//            @Override
//            public void showKeyboard() {
//                numberKeyboardView.showBottomView();
//            }
//
//            @Override
//            public void complet(String content) {
//            }
//
//            @Override
//            public void updateText(String content) {
//                if (content.length() == 6) {
//                    payPwd = content;
//                    mTvComplet.setEnabled(true);
//                    customPopWindow.dissmiss();
//                } else {
//                    mTvComplet.setEnabled(false);
//                }
//            }
//
//        });
    }

//    @OnClick({R.id.iv_title_left, R.id.tv_complet})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.iv_title_left:
//                finish();
//                break;
//            case R.id.tv_complet:
//                setPayPwd();
//
//                break;
//        }
//    }

    private void setPayPwd() {
//        String access_token = new UserUtils(this).getUser().getAccess_token();
//        String phoneNumber = new UserUtils(this).getPhoneNumber();
//        payPwd = BqUtils.md5(payPwd);
//
//        String sType = "set";
//        if (type == 1) {
//            sType = "set";
//        } else {
//            sType = "edit";
//        }
//
//        exeHttpWithDialog(ApiServiceManager.getApi().setPayPwd("app.savePayPassword", "app", "1", access_token,
//                phoneNumber, smsCode, payPwd, sType, oldPwd))
//                .subscribe(new BaseSubscriber<BaseBean>(this, code -> {
//                    if (code != 10000) {
//                        //清除密码
//                        mPayPwdView.clearAll();
//                        customPopWindow = numberKeyboardView.showBottomView();
//                    }
//                }) {
//                    @Override
//                    public void result(BaseBean bean) {
//                        setResult(11);
//                        Utils.showToastOk(InputPayPwdActivity.this, "设置成功");
//                        new Handler().postDelayed(() -> {
//                            finish();
//                        }, 1000);
//                    }
//                });
    }
}
