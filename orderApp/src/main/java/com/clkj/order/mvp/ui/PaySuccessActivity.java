package com.clkj.order.mvp.ui;

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
import com.clkj.order.R;
import com.clkj.order.R2;
import com.clkj.order.api.OrderEventKey;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述： 支付成功
 * 作者：梁帆
 * 时间：2020/7/29
 * 版权：
 */
@Route(path = AppArouter.ORDER_PAY_SUCCESS_ACTIVITY)
public class PaySuccessActivity extends BaseActivity {


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
    @BindView(R2.id.tv_synpol)
    TextView mTvSynpol;
    @BindView(R2.id.tv_money)
    TextView mTvMoney;
    @BindView(R2.id.tv_signed)
    TextView mTvSigned;
    @BindView(R2.id.tv_order_detail)
    TextView mTvOrderDetail;

    @Autowired
    String mOrderId;
    @Autowired
    String mPrice;

    @Override
    protected int getContentViewLayout() {
        return R.layout.order_activity_pay_success;
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected void attach() {
        ARouter.getInstance().inject(this);
        mTvTitle.setText("支付成功");
        mTvMoney.setText(mPrice);
        EventBus.getDefault().post(OrderEventKey.UPDATE_ORDER_STATUS);
    }


    @OnClick({R2.id.tv_signed, R2.id.tv_order_detail})
    public void onViewClicked(View view) {
        if(view.getId() == R.id.tv_signed){
//            ARouter.getInstance().build(AppArouter.ORDER_SIGN_CONTRACT_ACTIVITY).navigation();
//            finish();
        }else if(view.getId() == R.id.tv_order_detail){
            ARouter.getInstance().build(AppArouter.ORDER_ORDER_DETAIL_ACTIVITY)
                    .withString("mOrderId",mOrderId).navigation();
            finish();
        }
    }
}
