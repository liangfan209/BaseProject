package com.bq.walletapp.mvp.ui;

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
import com.bq.walletapp.R;
import com.bq.walletapp.R2;
import com.bq.walletapp.api.bean.TransactionInfo;

import butterknife.BindView;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/29
 * 版权：
 */
@Route(path = AppArouter.WALLET_BILL_DETAIL_ACTIVITY)
public class BillDetailActivity extends BaseAcitivty {


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
    @BindView(R2.id.iv_type_bg)
    ImageView mIvTypeBg;
    @BindView(R2.id.tv_amount)
    TextView mTvAmount;
    @BindView(R2.id.tv_status)
    TextView mTvStatus;
    @BindView(R2.id.tv_pay_tpye)
    TextView mTvPayTpye;
    @BindView(R2.id.tv_time)
    TextView mTvTime;
    @BindView(R2.id.tv_orderId)
    TextView mTvOrderId;
    @BindView(R2.id.tv_transaction_type)
    TextView mTvTransactionType;

    private String payType;

    @Autowired
    TransactionInfo mTransactionInfo;

    @Override
    protected int getContentViewLayout() {
        return R.layout.wallet_activity_bill_detail;
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected void attach() {
        ARouter.getInstance().inject(this);
        mTvTitle.setText("账单详情");
        if(mTransactionInfo != null){
            mTvTransactionType.setText(mTransactionInfo.getPay_type()+"提现");
            mTvAmount.setText(Utils.getDouble2(mTransactionInfo.getAmount()));
            mTvStatus.setText(mTransactionInfo.getStatus()+"");
            mTvPayTpye.setText(mTransactionInfo.getPay_type());
            mTvTime.setText(mTransactionInfo.getCreate_time()+"");
            mTvOrderId.setText(mTransactionInfo.getNumber()+"");
        }
    }


}
