package com.bq.order.mvp.wallet.ui;

import android.graphics.Typeface;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.StringUtils;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.order.R;
import com.bq.order.R2;
import com.bq.order.api.WalletEventKey;
import com.bq.order.mvp.wallet.presenter.WalletPresenter;
import com.bq.order.pay.PayUtils;
import com.bq.order.pay.callback.IPayCallback;
import com.bq.order.requset.bean.WxBean;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.fan.baseuilibrary.view.DeletableEditText;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import skin.support.widget.SkinCompatRadioButton;
import skin.support.widget.SkinCompatRadioGroup;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/29/029
 * 版权：
 */
@Route(path = AppArouter.WALLET_RECHARGE_ACTIVITY)
public class RechargeActivity extends BaseActivity implements WalletIView {


    @BindView(R2.id.iv_title_left)
    ImageView mIvTitleLeft;
    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.tv_right)
    TextView mTvRight;
    @BindView(R2.id.view_line)
    View mViewLine;
    @BindView(R2.id.rlt_title)
    RelativeLayout mRltTitle;
    @BindView(R2.id.det_recharge)
    DeletableEditText mDetRecharge;
    @BindView(R2.id.tv_commit)
    TextView mTvCommit;
    @BindView(R2.id.rbt_wexin)
    SkinCompatRadioButton mRbtWexin;
    @BindView(R2.id.rg_pay)
    SkinCompatRadioGroup mRgPay;


    private WalletPresenter mWalletPresenter;

    @Override
    protected int getContentViewLayout() {
        return R.layout.wallet_activity_recharge;
    }

    @Override
    protected BasePresenter createPersenter() {
        mWalletPresenter = new WalletPresenter(this);
        return mWalletPresenter;
    }

    @Override
    protected void attach() {
        mTvTitle.setText("充值");
        mDetRecharge.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        initEditView();
    }

    private void initEditView() {
        mDetRecharge.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        mDetRecharge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mDetRecharge.setTextSize(s.length() > 0 ? 30 : 15);
                mDetRecharge.setTypeface(s.length() > 0 ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);


                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        mDetRecharge.setText(s.subSequence(0, 1));
                        mDetRecharge.setSelection(1);
                        return;
                    }
                }
                //如果第一为点，直接显示0.
                if (s.toString().startsWith(".")) {
                    mDetRecharge.setText("0.");
                    mDetRecharge.setSelection(2);
                    return;
                }
                //限制输入小数位数(2位)
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + 2 + 1);
                        mDetRecharge.setText(s);
                        mDetRecharge.setSelection(s.length());
                    }
                }
                if ("0".equals(s.toString()) || "0.".equals(s.toString()) || "0.0".equals(s.toString()) || "0.00".equals(s.toString())) {
                    mTvCommit.setEnabled(false);
                    return;
                }
                if(s.length() >0){
                    mTvCommit.setEnabled(true);
                }else{
                    mTvCommit.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        mWalletPresenter.unRegister();
    }

    @OnClick({R2.id.det_recharge, R2.id.tv_commit})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.det_recharge) {

        } else if (view.getId() == R.id.tv_commit) {
            String rechargeMoney = mDetRecharge.getText().toString();
            if (StringUtils.isEmpty(rechargeMoney)) {
                ToastUtils.showToast(this, "请输入充值金额");
                return;
            }
            Double moneyDouble = Double.valueOf(rechargeMoney);
            int moneyInt = (int) (moneyDouble * 100);
            String payType = "wechat";
            if(mRbtWexin.isChecked()){
                payType = "wechat";
            }else{
                payType = "alipay";
            }
            mWalletPresenter.recharge(moneyInt + "",payType);
        }
    }

    @Override
    public void rechargeView(WxBean.PayInfo info) {
        if(mRbtWexin.isChecked()){
            PayUtils.wexinPay(this, info, new IPayCallback() {
                @Override
                public void success() {
                    ToastUtils.showToastOk(RechargeActivity.this, "充值成功");
                    EventBus.getDefault().post(WalletEventKey.UPDATE_BALANCE);
                    new Handler().postDelayed(() -> {
                        finish();
                    }, 1000);
                }

                @Override
                public void failed() {
                }

                @Override
                public void cancel() {
                }
            });
        }else{
            PayUtils.zhifubaoPay(this, info.getPrepayid(), new IPayCallback() {
                @Override
                public void success() {
                    ToastUtils.showToastOk(RechargeActivity.this, "充值成功");
                    EventBus.getDefault().post(WalletEventKey.UPDATE_BALANCE);
                    new Handler().postDelayed(() -> {
                        finish();
                    }, 1000);
                }

                @Override
                public void failed() {

                }

                @Override
                public void cancel() {

                }
            });
        }

    }

}
