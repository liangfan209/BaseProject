package com.bq.user_center.mvp.bankcard.ui;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.StringUtils;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePersenter;
import com.bq.comm_config_lib.mvp.ui.BaseAcitivty;
import com.bq.user_center.R;
import com.bq.user_center.R2;
import com.bq.user_center.mvp.bankcard.presenter.BankCardPresenter;
import com.bq.user_center.requset.bean.BankCard;
import com.bq.utilslib.AccountValidatorUtil;
import com.bq.utilslib.EditFormatUtils;
import com.fan.baseuilibrary.utils.CountDownHelper;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.fan.baseuilibrary.view.DeletableEditText;

import androidx.appcompat.widget.AppCompatCheckBox;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/11
 * 版权：
 */
@Route(path = AppArouter.USER_CENTER_BANK_ADD_ACTIVITY)
public class AddBankActivity extends BaseAcitivty implements BankCardBaseIView {

    BankCardPresenter mBankCardPersenter;
    @BindView(R2.id.iv_title_left)
    ImageView mIvTitleLeft;
    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.tv_right)
    TextView mTvRight;
    @BindView(R2.id.et_bank)
    DeletableEditText mEtBank;
    @BindView(R2.id.et_name)
    DeletableEditText mEtName;
    @BindView(R2.id.et_id_card)
    DeletableEditText mEtIdCard;
    @BindView(R2.id.et_phone)
    DeletableEditText mEtPhone;
    @BindView(R2.id.et_vertical_code)
    EditText mEtVerticalCode;
    @BindView(R2.id.tv_get_verification_code)
    TextView mTvGetVerificationCode;
    @BindView(R2.id.rb_protocol)
    AppCompatCheckBox mRbProtocol;
    @BindView(R2.id.tv_comfirm_form)
    TextView mTvComfirmForm;

    CountDownHelper countDownHelper;//计时器

    @Override
    public void addBank(BankCard info) {
        //弹出吐司
        finish();
    }

    @Override
    protected int getContentViewLayout() {
        return R.layout.user_center_activity_add_bank;
    }

    @Override
    protected void attach() {
        mTvTitle.setText("添加银行卡");
        initView();
    }

    private void initView() {
        EditFormatUtils.bankCardNumAddSpace(mEtBank);
        EditFormatUtils.phoneNumAddSpace(mEtPhone);
        EditFormatUtils.idCardAddSpace(mEtIdCard);
        initVericalView();
    }

    /**
     * 初始化验证码
     */
    private void initVericalView() {
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

    @Override
    protected BasePersenter createPersenter() {
        mBankCardPersenter = new BankCardPresenter(this);
        return mBankCardPersenter;
    }


    @OnClick({R2.id.tv_get_verification_code, R2.id.tv_comfirm_form})
    public void onViewClicked(View view) {
        if(view.getId() == R.id.tv_get_verification_code){
            countDownHelper = new CountDownHelper(mTvGetVerificationCode, "获取验证码", "重新获取", 60, 1,2);
            countDownHelper.start();
        }else if(view.getId() == R.id.tv_comfirm_form){
            String cardNum = mEtBank.getText().toString();
            String idNum = mEtIdCard.getText().toString();
            String phoneNum = mEtPhone.getText().toString();
            String name = mEtName.getText().toString();
            if(StringUtils.isEmpty(cardNum)){
                ToastUtils.showToast(this,"银行卡号不能为空");
                return;
            }
            if(StringUtils.isEmpty(idNum)){
                ToastUtils.showToast(this,"身份证号不能为空");
                return;
            }
            if(StringUtils.isEmpty(name)){
                ToastUtils.showToast(this,"姓名不能为空");
                return;
            }
            if(StringUtils.isEmpty(phoneNum)){
                ToastUtils.showToast(this,"手机号不能为空");
                return;
            }
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
