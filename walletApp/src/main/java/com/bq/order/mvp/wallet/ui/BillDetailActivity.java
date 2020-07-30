package com.bq.order.mvp.wallet.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.comm_config_lib.utils.Utils;
import com.bq.order.R;
import com.bq.order.R2;
import com.bq.order.mvp.wallet.presenter.WalletPresenter;
import com.bq.order.requset.bean.TransactionInfo;

import butterknife.BindView;
import skin.support.content.res.SkinCompatResources;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/29
 * 版权：
 */
@Route(path = AppArouter.WALLET_BILL_DETAIL_ACTIVITY)
public class BillDetailActivity extends BaseActivity implements WalletIView {


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
    @BindView(R2.id.iv_type1)
    ImageView mIvType1;
    @BindView(R2.id.iv_line1)
    ImageView mIvLine1;
    @BindView(R2.id.iv_line2)
    ImageView mIvLine2;
    @BindView(R2.id.iv_type2)
    ImageView mIvType2;
    @BindView(R2.id.iv_line3)
    ImageView mIvLine3;
    @BindView(R2.id.iv_line4)
    ImageView mIvLine4;
    @BindView(R2.id.iv_type3)
    ImageView mIvType3;
    @BindView(R2.id.tv1)
    TextView mTv1;
    @BindView(R2.id.tv2)
    TextView mTv2;
    @BindView(R2.id.tv3)
    TextView mTv3;


    private String payType;

    @Autowired
    TransactionInfo mTransactionInfo;
    private WalletPresenter mWalletPresenter;

    @Override
    protected int getContentViewLayout() {
        return R.layout.wallet_activity_bill_detail;
    }

    @Override
    protected BasePresenter createPersenter() {
        mWalletPresenter = new WalletPresenter(this);
        return mWalletPresenter;
    }

    @Override
    protected void attach() {
        ARouter.getInstance().inject(this);
        mTvTitle.setText("账单详情");
        if (mTransactionInfo != null) {
            mWalletPresenter.transactionDetail(mTransactionInfo.getId() + "");
        }
    }

    @Override
    public void transactionDetailView(TransactionInfo info) {
        mTransactionInfo = info;
        mTvTransactionType.setText("提现"+mTransactionInfo.getPay_type());
        mTvAmount.setText(Utils.getDouble2(mTransactionInfo.getAmount()));
        mTvStatus.setText(mTransactionInfo.getStatus() + "");
        mTvPayTpye.setText(mTransactionInfo.getPay_type());
        mTvTime.setText(mTransactionInfo.getCreate_time() + "");
        mTvOrderId.setText(mTransactionInfo.getNumber() + "");
        
        updateTransactionStatus();
    }

    private void updateTransactionStatus() {
        if("付款成功".equals(mTransactionInfo.getStatus())){
            mIvType1.setBackgroundDrawable(SkinCompatResources.getDrawable(this,R.mipmap.ui_checkbox_select));
            mIvType2.setBackgroundDrawable(SkinCompatResources.getDrawable(this,R.mipmap.ui_checkbox_more));
            mIvType3.setBackgroundDrawable(SkinCompatResources.getDrawable(this,R.mipmap.ui_checkbox_more));
            mIvLine1.setBackgroundColor(SkinCompatResources.getColor(this,R.color.ui_primary_color));
            mIvLine2.setBackgroundColor(SkinCompatResources.getColor(this,R.color.ui_txt_hint_color));
            mIvLine3.setBackgroundColor(SkinCompatResources.getColor(this,R.color.ui_txt_hint_color));
            mIvLine4.setBackgroundColor(SkinCompatResources.getColor(this,R.color.ui_txt_hint_color));

            mTv1.setTextColor(SkinCompatResources.getColor(this,R.color.ui_primary_color));
            mTv2.setTextColor(SkinCompatResources.getColor(this,R.color.ui_txt_hint_color));
            mTv3.setTextColor(SkinCompatResources.getColor(this,R.color.ui_txt_hint_color));

        }else if("交易处理中".equals(mTransactionInfo.getStatus())){
            mIvType1.setBackgroundDrawable(SkinCompatResources.getDrawable(this,R.mipmap.ui_checkbox_select));
            mIvType2.setBackgroundDrawable(SkinCompatResources.getDrawable(this,R.mipmap.ui_checkbox_select));
            mIvType3.setBackgroundDrawable(SkinCompatResources.getDrawable(this,R.mipmap.ui_checkbox_more));

            mIvLine1.setBackgroundColor(SkinCompatResources.getColor(this,R.color.ui_primary_color));
            mIvLine2.setBackgroundColor(SkinCompatResources.getColor(this,R.color.ui_primary_color));
            mIvLine3.setBackgroundColor(SkinCompatResources.getColor(this,R.color.ui_primary_color));
            mIvLine4.setBackgroundColor(SkinCompatResources.getColor(this,R.color.ui_txt_hint_color));

            mTv1.setTextColor(SkinCompatResources.getColor(this,R.color.ui_primary_color));
            mTv2.setTextColor(SkinCompatResources.getColor(this,R.color.ui_primary_color));
            mTv3.setTextColor(SkinCompatResources.getColor(this,R.color.ui_txt_hint_color));
        }else if("到账成功".equals(mTransactionInfo.getStatus())){
            mIvType1.setBackgroundDrawable(SkinCompatResources.getDrawable(this,R.mipmap.ui_checkbox_select));
            mIvType2.setBackgroundDrawable(SkinCompatResources.getDrawable(this,R.mipmap.ui_checkbox_select));
            mIvType3.setBackgroundDrawable(SkinCompatResources.getDrawable(this,R.mipmap.ui_checkbox_select));

            mIvLine1.setBackgroundColor(SkinCompatResources.getColor(this,R.color.ui_primary_color));
            mIvLine2.setBackgroundColor(SkinCompatResources.getColor(this,R.color.ui_primary_color));
            mIvLine3.setBackgroundColor(SkinCompatResources.getColor(this,R.color.ui_primary_color));
            mIvLine4.setBackgroundColor(SkinCompatResources.getColor(this,R.color.ui_primary_color));

            mTv1.setTextColor(SkinCompatResources.getColor(this,R.color.ui_primary_color));
            mTv2.setTextColor(SkinCompatResources.getColor(this,R.color.ui_primary_color));
            mTv3.setTextColor(SkinCompatResources.getColor(this,R.color.ui_primary_color));
            mTv3.setText(mTransactionInfo.getStatus());
        }else if("交易失败".equals(mTransactionInfo.getStatus())){
            mIvType1.setBackgroundDrawable(SkinCompatResources.getDrawable(this,R.mipmap.ui_checkbox_more));
            mIvType2.setBackgroundDrawable(SkinCompatResources.getDrawable(this,R.mipmap.ui_checkbox_more));
            mIvType3.setBackgroundDrawable(SkinCompatResources.getDrawable(this,R.mipmap.ui_checkbox_more));

            mIvLine1.setBackgroundColor(SkinCompatResources.getColor(this,R.color.ui_txt_hint_color));
            mIvLine2.setBackgroundColor(SkinCompatResources.getColor(this,R.color.ui_txt_hint_color));
            mIvLine3.setBackgroundColor(SkinCompatResources.getColor(this,R.color.ui_txt_hint_color));
            mIvLine4.setBackgroundColor(SkinCompatResources.getColor(this,R.color.ui_txt_hint_color));

            mTv1.setTextColor(SkinCompatResources.getColor(this,R.color.ui_txt_hint_color));
            mTv2.setTextColor(SkinCompatResources.getColor(this,R.color.ui_txt_hint_color));
            mTv3.setTextColor(SkinCompatResources.getColor(this,R.color.ui_txt_hint_color));
            mTv3.setText(mTransactionInfo.getStatus());
        }
    }


}
