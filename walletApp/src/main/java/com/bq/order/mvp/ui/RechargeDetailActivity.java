package com.bq.order.mvp.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseAcitivty;
import com.bq.comm_config_lib.utils.Utils;
import com.bq.order.R;
import com.bq.order.R2;
import com.bq.order.mvp.presenter.WalletPresenter;
import com.bq.order.requset.bean.TransactionInfo;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/29/029
 * 版权：
 */
@Route(path = AppArouter.WALLET_TRANSACTION_DETAIL_ACTIVITY)
public class RechargeDetailActivity extends BaseAcitivty implements WalletIView{


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
    @BindView(R2.id.tv_type)
    TextView mTvType;
    @BindView(R2.id.tv_amount)
    TextView mTvAmount;
    @BindView(R2.id.tv_status)
    TextView mTvStatus;
    @BindView(R2.id.tv_pay_type)
    TextView mTvPayType;
    @BindView(R2.id.tv_remark)
    TextView mTvRemark;
    @BindView(R2.id.tv_time)
    TextView mTvTime;
    @BindView(R2.id.tv_orderId)
    TextView mTvOrderId;

    @Autowired
    TransactionInfo mTransactionInfo;

    private WalletPresenter mWalletPresenter;

    @Override
    protected int getContentViewLayout() {
        return R.layout.wallet_activity_transaction_detail;
    }

    @Override
    protected BasePresenter createPersenter() {
        mWalletPresenter = new WalletPresenter(this);
        return mWalletPresenter;
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        mWalletPresenter.unRegister();
    }

    @Override
    protected void attach() {
        ARouter.getInstance().inject(this);
        mTvTitle.setText("充值详情");

        if(mTransactionInfo != null){
            mWalletPresenter.transactionDetail(mTransactionInfo.getId()+"");

        }
    }

    @Override
    public void transactionDetailView(TransactionInfo info) {
        mTransactionInfo = info;
        mTvType.setText(mTransactionInfo.getPay_type()+"卡充值");
        mTvAmount.setText(Utils.getDouble2(mTransactionInfo.getAmount()));
        mTvStatus.setText(mTransactionInfo.getStatus()+"");

        mTvPayType.setText(mTransactionInfo.getPay_type()+"卡");
        mTvRemark.setText(mTransactionInfo.getRemark()+"");
        mTvTime.setText(mTransactionInfo.getCreate_time()+"");
        mTvOrderId.setText(mTransactionInfo.getNumber()+"");
    }

    @OnClick({R2.id.iv_title_left, R2.id.tv_title})
    public void onViewClicked(View view) {
    }

}
