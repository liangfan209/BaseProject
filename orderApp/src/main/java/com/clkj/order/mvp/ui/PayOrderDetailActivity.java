package com.clkj.order.mvp.ui;

import android.view.View;
import android.widget.FrameLayout;
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
import com.clkj.order.requset.bean.OrderInfo;
import com.bq.utilslib.AppUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.fan.baseuilibrary.view.MyRefreshLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 文件名：
 * 描述：订单详情
 * 作者：梁帆
 * 时间：2020/7/17
 * 版权：
 */
@Route(path = AppArouter.ORDER_PAY_ORDER_DETAIL_ACTIVITY)
public class PayOrderDetailActivity extends BaseActivity implements OrderIview  , MyRefreshLayout.LayoutInterface<OrderInfo.PaymentListBean>{


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
    @BindView(R2.id.tv_top)
    TextView mTvTop;
    @BindView(R2.id.flt_content)
    FrameLayout mFltContent;

    @Autowired
    OrderInfo mOrderInfo;

    @Override
    protected int getContentViewLayout() {
        return R.layout.order_activity_pay_detail;
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected void attach() {
        ARouter.getInstance().inject(this);
        mTvTitle.setText("付款记录");
        mTvTop.setText("成交金额： ¥"+ AppUtils.getDouble2(mOrderInfo.getStrike_price())+"    已付款： ¥"+
                AppUtils.getDouble2(mOrderInfo.getActual_amount())+"     欠缴：¥"
        +AppUtils.getDouble2(mOrderInfo.getArrears()));


        mRefreshLayout = new MyRefreshLayout<OrderInfo.PaymentListBean>(this, this);
        mRefreshLayout.setRefresh(false, false);
        mFltContent.addView(mRefreshLayout);
//        mRefreshLayout.adapter.setOnItemClickListener((adapter, view, position) -> {
//        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public BaseQuickAdapter<OrderInfo.PaymentListBean, ? extends BaseViewHolder> createAdapter() {
        return new BaseQuickAdapter<OrderInfo.PaymentListBean, BaseViewHolder>(R.layout.order_pay_detail_item, new ArrayList<>()) {
            @Override
            protected void convert(@NotNull BaseViewHolder helper, OrderInfo.PaymentListBean info) {
                helper.setText(R.id.tv_pay_type,info.getPay_type());
                helper.setText(R.id.tv_pay_status,info.getStatus());
                helper.setText(R.id.tv_pay_price,  "¥"+AppUtils.getDouble2(info.getAmount()));
                helper.setText(R.id.tv_pay_num,"付款单号: "+info.getNumber());
                helper.setText(R.id.tv_pay_time,info.getCreate_time());
            }
        };
    }

    @Override
    public void loadData(int page, int pageSize) {
        mRefreshLayout.initRefreshBoo();
        mRefreshLayout.addData(mOrderInfo.getPayment_list());
    }
}
