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
import com.baidu.ocr.sdk.model.IDCardResult;
import com.blankj.utilcode.util.StringUtils;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.comm_config_lib.request.SignJsonCallBack;
import com.bq.comm_config_lib.utils.ScanHelper;
import com.bq.comm_config_lib.utils.Utils;
import com.bq.netlibrary.NetManager;
import com.bq.netlibrary.http.BaseResponse;
import com.bq.user_center.R;
import com.bq.user_center.R2;
import com.bq.user_center.mvp.bankcard.presenter.BankCardPresenter;
import com.bq.user_center.requset.bean.BankCardInfo;
import com.bq.utilslib.AccountValidatorUtil;
import com.bq.utilslib.EditFormatUtils;
import com.fan.baseuilibrary.utils.CountDownHelper;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.fan.baseuilibrary.view.DeletableEditText;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.Map;

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
public class AddBankActivity extends BaseActivity implements BankCardBaseIView {

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
    private ScanHelper mScanHelper;

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

        //给scanHelper绑定生命周期
        mScanHelper = new ScanHelper(this);
        getLifecycle().addObserver(mScanHelper);

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
            showLoading();
            String phone = mEtPhone.getText().toString().replaceAll(" ","");
            Map<String,String> map = new HashMap<>();
            map.put("api", "customer.account.vcode.phone");
            map.put("number",phone);
            map.put("sms_type","bindcard");
            NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<Object>>(){
                @Override
                public void onSuccess(Response<BaseResponse<Object>> response) {
                    super.onSuccess(response);
                    AddBankActivity.this.onComplete();
                    countDownHelper = new CountDownHelper(mTvGetVerificationCode, "获取验证码", "重新获取", 60, 1,2);
                    countDownHelper.start();
                }
            });


        }else if(view.getId() == R.id.tv_comfirm_form){
            String cardNum = mEtBank.getText().toString().replaceAll(" ","");
            String idNum = mEtIdCard.getText().toString().replaceAll(" ","");
            String phoneNum = mEtPhone.getText().toString().replaceAll(" ","");
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
        } else if (view.getId() == R.id.iv_scan) {
            if (!Utils.isFastDoubleClick(mIvScan, 1000)) {
                mScanHelper.startScan(ScanHelper.BANK_CARD);
            }
        } else if(view.getId() == R.id.iv_scan1){
            if (!Utils.isFastDoubleClick(mIvScan1, 1000)) {
                mScanHelper.startScan(ScanHelper.ID_CARD_FRONT);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( resultCode == Activity.RESULT_OK){
            if(requestCode == 111){
                mScanHelper.paseData(data,ScanHelper.ID_CARD_FRONT,new ScanHelper.IdCardInter(){
                    @Override
                    public void idCardCallBack(IDCardResult result) {
                        String idNumber = result.getIdNumber().toString();
                        if(StringUtils.isEmpty(idNumber)){
                            ToastUtils.showToast(AddBankActivity.this,"识别失败");
                            return;
                        }

                        mEtIdCard.setText(result.getIdNumber().toString());
                        mEtIdCard.setClearDrawableVisible(false);
                    }

                    @Override
                    public void error(String msg) {
                        ToastUtils.showToast(AddBankActivity.this,msg);
                    }
                });
            }else if(requestCode == 112){
                mScanHelper.paseData(data,ScanHelper.BANK_CARD,new ScanHelper.IdCardInter(){
                    @Override
                    public void bankCard(String bankCardId) {
                        mEtBank.setText(bankCardId);
                        mEtBank.setClearDrawableVisible(false);
                    }
                    @Override
                    public void error(String msg) {
                        ToastUtils.showToast(AddBankActivity.this,msg);
                    }
                });
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
