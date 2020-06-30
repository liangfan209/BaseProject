package com.bq.walletapp.mvp.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePersenter;
import com.bq.comm_config_lib.mvp.ui.BaseAcitivty;
import com.bq.walletapp.R;
import com.bq.walletapp.R2;

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

    @Override
    protected int getContentViewLayout() {
        return R.layout.wallet_activity_bill_detail;
    }

    @Override
    protected BasePersenter createPersenter() {
        return null;
    }

    @Override
    protected void attach() {
        mTvTitle.setText("账单详情");
    }

}
