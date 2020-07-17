package com.bq.order.mvp.ui;

import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.StringUtils;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseAcitivty;
import com.bq.order.R;
import com.bq.order.R2;
import com.bq.order.mvp.presenter.WalletPresenter;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.fan.baseuilibrary.view.DeletableEditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/29/029
 * 版权：
 */
@Route(path = AppArouter.WALLET_RECHARGE_ACTIVITY)
public class RechargeActivity extends BaseAcitivty implements WalletIView{


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
    }


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        mWalletPresenter.unRegister();
    }

    @OnClick({R2.id.det_recharge, R2.id.tv_commit})
    public void onViewClicked(View view) {
        if(view.getId() == R.id.det_recharge){

        }else if(view.getId() == R.id.tv_commit){
            String rechargeMoney = mDetRecharge.getText().toString();
            if(StringUtils.isEmpty(rechargeMoney)){
                ToastUtils.showToast(this,"请输入充值金额");
                return;
            }
            Double moneyDouble = Double.valueOf(rechargeMoney);
            int moneyInt = (int) (moneyDouble*100);
            mWalletPresenter.recharge(moneyInt+"");
        }
    }

    @Override
    public void rechargeView() {
        ToastUtils.showToastOk(this,"充值成功");
        new Handler().postDelayed(() -> {
            finish();
        },1000);
    }
}
