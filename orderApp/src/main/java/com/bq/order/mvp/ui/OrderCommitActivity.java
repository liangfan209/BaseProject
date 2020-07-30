package com.bq.order.mvp.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.comm_config_lib.utils.PayView;
import com.bq.order.R;
import com.bq.order.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述： 提交订单
 * 作者：梁帆
 * 时间：2020/7/17
 * 版权：
 */
@Route(path = AppArouter.ORDER_ORDER_COMMIT_ACTIVITY)
public class OrderCommitActivity extends BaseActivity {
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
    @BindView(R2.id.iv_item)
    ImageView mIvItem;
    @BindView(R2.id.tv_remark)
    TextView mTvRemark;
    @BindView(R2.id.tv_buy)
    TextView mTvBuy;

    @Override
    protected int getContentViewLayout() {
        return R.layout.order_activity_commit_order;
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected void attach() {
        mTvTitle.setText("提交订单");
    }


    @OnClick({R2.id.tv_remark, R2.id.tv_buy})
    public void onViewClicked(View view) {
        if(view.getId() == R.id.tv_buy){
            //发送一个消息
            new PayView().showBottomView(this,"123","1","22");
        }
    }
}
