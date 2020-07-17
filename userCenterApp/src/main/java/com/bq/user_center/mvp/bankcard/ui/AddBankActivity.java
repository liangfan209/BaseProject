package com.bq.user_center.mvp.bankcard.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.baidu.ocr.ui.RecognizeService;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.blankj.utilcode.util.StringUtils;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseAcitivty;
import com.bq.user_center.R;
import com.bq.user_center.R2;
import com.bq.user_center.mvp.bankcard.presenter.BankCardPresenter;
import com.bq.user_center.requset.bean.BankCardInfo;
import com.bq.utilslib.AccountValidatorUtil;
import com.bq.utilslib.EditFormatUtils;
import com.fan.baseuilibrary.utils.CountDownHelper;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.fan.baseuilibrary.view.DeletableEditText;

import java.io.File;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import skin.support.widget.SkinCompatCheckBox;

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
    SkinCompatCheckBox mRbProtocol;
    @BindView(R2.id.tv_comfirm_form)
    TextView mTvComfirmForm;
    @BindView(R2.id.iv_scan)
    ImageView mIvScan;
    @BindView(R2.id.iv_scan1)
    ImageView mIvScan1;
    CountDownHelper countDownHelper;//计时器

    @Override
    public void addBankSuccess() {
        ToastUtils.showToastOk(this,"添加成功");
        new Handler().postDelayed(()->{
            finish();
        },1000);
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
    protected BasePresenter createPersenter() {
        mBankCardPersenter = new BankCardPresenter(this);
        return mBankCardPersenter;
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        mBankCardPersenter.unRegister();
    }

    @OnClick({R2.id.tv_get_verification_code, R2.id.tv_comfirm_form,R2.id.iv_scan,R2.id.iv_scan1})
    public void onViewClicked(View view) {
        if(view.getId() == R.id.tv_get_verification_code){
            countDownHelper = new CountDownHelper(mTvGetVerificationCode, "获取验证码", "重新获取", 60, 1,2);
            countDownHelper.start();
        }else if(view.getId() == R.id.tv_comfirm_form){
            String cardNum = mEtBank.getText().toString();
            String idNum = mEtIdCard.getText().toString();
            String phoneNum = mEtPhone.getText().toString();
            String name = mEtName.getText().toString();
            String code = mEtVerticalCode.getText().toString();
            if(StringUtils.isEmpty(cardNum)){
                ToastUtils.showToast(this,"请输入银行卡号");
                return;
            }

            if(StringUtils.isEmpty(name)){
                ToastUtils.showToast(this,"请输入开户姓名");
                return;
            }
            if(StringUtils.isEmpty(idNum)){
                ToastUtils.showToast(this,"请输入身份证号");
                return;
            }
            if(StringUtils.isEmpty(phoneNum)){
                ToastUtils.showToast(this,"请输入银行卡预留手机号");
                return;
            }
            if(StringUtils.isEmpty(code)){
                ToastUtils.showToast(this,"请输入短信验证码");
                return;
            }
            mBankCardPersenter.addBankCard(new BankCardInfo(name,cardNum,phoneNum,idNum,code));
        } else if (view.getId() == R.id.iv_scan || view.getId() == R.id.iv_scan1) {
            File file = new File(this.getFilesDir(), "bankcard.jpg");
            Intent intent = new Intent(this, CameraActivity.class);
            intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                    file.getAbsolutePath());
            intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                    CameraActivity.CONTENT_TYPE_BANK_CARD);
            startActivityForResult(intent, 111);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 111 &&  resultCode == Activity.RESULT_OK){
            File file = new File(this.getFilesDir(), "bankcard.jpg");
            RecognizeService.recBankCard(this, file.getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result) {
                            if(result.contains("erro")){
                                return;
                            }
                            mEtBank.setText(result);
                            mEtBank.setSelection(result.length());
                        }
                    });
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
