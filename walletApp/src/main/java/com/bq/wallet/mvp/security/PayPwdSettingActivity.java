package com.bq.wallet.mvp.security;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.comm_config_lib.utils.PwdAndNumberKeyboardView;
import com.bq.wallet.R;
import com.bq.wallet.R2;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.fan.baseuilibrary.view.CustomPopWindow;
import com.fan.baseuilibrary.view.dialog.CustomDialog;

import butterknife.BindView;
import skin.support.widget.SkinCompatCheckBox;

/**
 * 支付密码设置页面
 */
@Route(path = AppArouter.WALLET_SECURITY_SETTIGN_PWD_ACTIVITY)
public class PayPwdSettingActivity extends BaseActivity {


    @BindView(R2.id.iv_title_left)
    ImageView mIvTitleLeft;
    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.tv_right)
    TextView mTvRight;
    @BindView(R2.id.rlt_title)
    RelativeLayout mRltTitle;
    @BindView(R2.id.tv_haspwd)
    TextView mTvHaspwd;
    @BindView(R2.id.rlt_pay_pwd_setting)
    RelativeLayout mRltPayPwdSetting;
    @BindView(R2.id.rlt_modify_pwd)
    RelativeLayout mRltModifyPwd;
    @BindView(R2.id.switchView)
    SkinCompatCheckBox mSwitchView;
    private Dialog mDialog;
    CustomPopWindow customPopWindow;
    PwdAndNumberKeyboardView numberKeyboardView;

    private String hasPayPwd = "";
    private String isOpenPwd = "";
    private int errorCount = 0;

    private boolean showBoo = true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getContentViewLayout() {
        return R.layout.wallet_security_activity_pay_pwd_setting;
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected void attach() {

    }

//    @OnClick({R.id.iv_title_left, R.id.rlt_pay_pwd_setting, R.id.rlt_modify_pwd, R.id.switchView})
//    public void onViewClicked(View view) {
////        if (StringUtils.isEmpty(hasPayPwd)) {
////            Utils.showToast(this, "没有获取到支付状态");
////            return;
////        }
//
//        switch (view.getId()) {
//            case R.id.iv_title_left:
//                finish();
//                break;
//            case R.id.rlt_pay_pwd_setting:
//                if(!isShowToast()){
//                    return;
//                }
//                toVericationCodeActivity(1);
//                break;
//            case R.id.rlt_modify_pwd:
//                if(!isShowToast()){
//                    return;
//                }
//                if ("yes".equals(hasPayPwd)) {
//                    forgetDialog();
//                } else {
//                    showDialog();
//                }
//                break;
//            case R.id.switchView:
//                if(!isShowToast()){
//                    return;
//                }
//                if ("yes".equals(hasPayPwd)) {
//                    if ("yes".equals(isOpenPwd)) {
//                        numberKeyboardView =  new PwdAndNumberKeyboardView(this,
//                                new PwdAndNumberKeyboardView.NumberPwdAndKeyBoradCallBack() {
//                                    @Override
//                                    public void complete(String content) {
////                                        Utils.showLongToast(PayPwdSettingActivity.this, "输入完成");
//                                        setPayPwdSwitch("no", content);
//                                    }
//
//                                    @Override
//                                    public void forgetPwd() {
//                                        customPopWindow.dissmiss();
//                                        toVericationCodeActivity(2);
//                                    }
//                                });
//                        customPopWindow = numberKeyboardView.showBottomView();
//                        getUserInfo();
//                    } else {
//                        setPayPwdSwitch("yes", "");
//                    }
//
//                } else {
//                    showDialog();
//                    getUserInfo();
//                }
//
//                break;
//        }
//    }


    private void toVericationCodeActivity(int type) {
//        Intent intent = new Intent(this,VerificationCodeActivity.class);
//        intent.putExtra("type",type);
//        startActivity(intent);
    }

    private boolean isShowToast() {
        boolean boo = true;
        if (errorCount == 3) {
            boo = false;
            ToastUtils.showToast(this, "当前支付密码被锁定请联系客服");
            getUserInfo();
        }
        return boo;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserInfo();
    }

    private void getUserInfo() {
//        String access_token = new UserUtils(this).getUser().getAccess_token();
//        exeHttp(ApiServiceManager.getApi().getUserInfo("app.searchUserinfo", "app", "1",
//                access_token))
//                .subscribe(new BaseSubscriber<UserBean>(this) {
//                    @Override
//                    public void result(UserBean bean) {
//                        UserBean.ResultBean result = bean.getResult();
//                        //是否设置支付密码
//                        hasPayPwd = result.getSetType();
//                        isOpenPwd = result.getOpenType();
//                        errorCount = result.getErrorCount();
//
//                        if ("yes".equals(hasPayPwd)) {
//                            mtvHaspwd.setText("已设置");
//                            mRltPayPwdSetting.setEnabled(false);
//                            mtvHaspwd.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
//                        } else {
//                            mtvHaspwd.setText("未设置");
//                            mRltPayPwdSetting.setEnabled(true);
//                            if(showBoo){
//                                showBoo = false;
//                                showDialog();
//                            }
//
//                        }
//
//                        if ("yes".equals(isOpenPwd)) {
//                            mSwitchView.setChecked(true);
//                        } else {
//                            mSwitchView.setChecked(false);
//                        }
//                    }
//                });
    }

    //弹出对话框
    private void showDialog() {
        mDialog = new CustomDialog().showDialog(this,
                "小蜜提示", "您还未设置密码哦~", "取消", "去设置", new CustomDialog.ClickCallBack() {

                    @Override
                    public void ok() {
                        if (mDialog != null) {
                            mDialog.dismiss();
                            //去下一个页面设置密码
                            toVericationCodeActivity(1);
                        }
                    }

                    @Override
                    public void cacel() {
                        mDialog.dismiss();
                    }
                });
    }

    private void forgetDialog() {
        mDialog = new CustomDialog().showDialog(this,
                "小蜜提示", "您当前账号已设置支付密码，是否记得当前设置的支付密码？", "不记得", "记得", new CustomDialog.ClickCallBack() {
                    @Override
                    public void ok() {
                        if (mDialog != null) {
                            mDialog.dismiss();
                            //去下一个页面设置密码
//                            startActivity(new Intent(PayPwdSettingActivity.this, ForGotInputPayPwdActivity.class));
                        }
                    }

                    @Override
                    public void cacel() {
                        mDialog.dismiss();
                        toVericationCodeActivity(2);
                    }
                });
    }


    private void setPayPwdSwitch(String openType, String pwd) {
//        String access_token = new UserUtils(this).getUser().getAccess_token();
//        String phoneNumber = new UserUtils(this).getPhoneNumber();
//        pwd = BqUtils.md5(pwd);
//        exeHttpWithDialog(ApiServiceManager.getApi().setPayPwdSwitch("app.openType", "app", "1", access_token,
//                openType, pwd))
//                .subscribe(new BaseSubscriber<BaseBean<CheckPwdInfo>>(this, code -> {
//                    if (code != 10000) {
//                        //清除密码
//                        numberKeyboardView.clearAll();
//                    }
//                }) {
//                    @Override
//                    public void result(BaseBean<CheckPwdInfo> bean) {
//                        if(bean.getResult().getStatus() == 100){
//                            if (customPopWindow != null) {
//                                customPopWindow.dissmiss();
//                            }
//                            getUserInfo();
//                        }else{
//                            if(bean.getResult().getCount() == 3){
//                              Utils.showToast(PayPwdSettingActivity.this,"当前支付密码被锁定请联系客服");
//                            }else{
//                                Utils.showToast(PayPwdSettingActivity.this,"密码错误，当前还有" + (3 - bean.getResult().getCount()) +
//                                "次输入机会");
//                            }
//                            numberKeyboardView.clearAll();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        super.onError(e);
//                        if (customPopWindow != null) {
//                            customPopWindow.dissmiss();
//                        }
//                    }
//                });
    }


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }


}
