package com.bq.order.mvp.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseFragment;
import com.bq.comm_config_lib.utils.Utils;
import com.bq.order.R;
import com.bq.order.R2;
import com.bq.order.mvp.presenter.OrderPresenter;
import com.bq.order.mvp.ui.OrderIview;
import com.bq.order.requset.bean.OrderInfo;
import com.bq.utilslib.AppUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.fan.baseuilibrary.view.MyRefreshLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import butterknife.BindView;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/17
 * 版权：
 */

public class OrderListFragment extends BaseFragment implements MyRefreshLayout.LayoutInterface<OrderInfo> , OrderIview {

    @BindView(R2.id.flt_content)
    FrameLayout mFltContent;
    private List<OrderInfo> mlist = new ArrayList<>();
    private OrderPresenter mOrderPresenter;
    private String searchInfo = "";

    String[] typeStrs = new String[]{"待支付","待发货","待收货","已完成"};

    public static OrderListFragment getInstance(String info) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("searchInfo", info);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentViewLayout() {
        return R.layout.laoyut_frame_layout;
    }

    @Override
    protected BasePresenter createPersenter() {
        return mOrderPresenter = new OrderPresenter(this);
    }

    @Override
    protected void attach() {
        searchInfo =  getArguments().getString("searchInfo");
        mRefreshLayout = new MyRefreshLayout<String>(this.getContext(), this);
        mRefreshLayout.setRefresh(true, true);
        mFltContent.addView(mRefreshLayout);
        mRefreshLayout.adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                OrderInfo info = (OrderInfo) adapter.getData().get(position);
                ARouter.getInstance().build(AppArouter.ORDER_ORDER_DETAIL_ACTIVITY)
                        .withString("mOrderId",info.getId()+"").navigation();
            }
        });
    }

    @Override
    public BaseQuickAdapter<OrderInfo, ? extends BaseViewHolder> createAdapter() {
        return new BaseQuickAdapter<OrderInfo, BaseViewHolder>(R.layout.order_item_order,mlist) {
            @Override
            protected void convert(@NotNull BaseViewHolder helper, OrderInfo info) {
                TextView tvType = helper.getView(R.id.tv_type);

                TextView tvTotalCount = helper.getView(R.id.tv_total_acount);
                helper.getView(R.id.tv_bottom_right).setVisibility(View.GONE);

                TextView tvHint = helper.getView(R.id.tv_hint);
                TextView tvPrimary = helper.getView(R.id.tv_primary);

                OrderInfo.OrderItemListBean orderItemListBean = info.getOrder_item_list().get(0);
                ImageView iv = helper.getView(R.id.iv_item);
                Glide.with(iv).load(orderItemListBean.getShow_image())
                        .apply(Utils.getRequestOptionRadus(iv.getContext(),0)).into(iv);
                helper.setText(R.id.tv_product_title,orderItemListBean.getTitle());
                helper.setText(R.id.tv_school,orderItemListBean.getSchool_name());
                helper.setText(R.id.tv_profession,orderItemListBean.getMajor_name());
                helper.setText(R.id.tv_duration,orderItemListBean.getDuration());
                helper.setText(R.id.tv_address,orderItemListBean.getSchool_city());
                helper.setText(R.id.tv_brand,orderItemListBean.getBrand_name());
                helper.setText(R.id.tv_product,orderItemListBean.getProduction_name());
                helper.setText(R.id.tv_price, AppUtils.getDouble2(orderItemListBean.getSale_price()));

                helper.setText(R.id.tv_total_price,"总价： ¥"+AppUtils.getDouble2(orderItemListBean.getSale_price()*orderItemListBean.getQuantity()));
                tvTotalCount.setText("数量：x"+ orderItemListBean.getQuantity());
                String status = info.getStatus();

                tvHint.setVisibility(View.VISIBLE);
                tvPrimary.setVisibility(View.VISIBLE);

                tvPrimary.setOnClickListener(v->{
                    if(status.contains("order_launched")){
                        //待发货
                    }else if(status.contains("payment_finished")){
                        //待收货
                    }else if(status.contains("delivery_finished")){
                        //已完成
                    }else if(status.contains("order_finished")){

                    }
                });


                //待支付
                if(status.contains("order_launched")){
                    tvHint.setText("取消");
                    tvPrimary.setText("支付");
                    tvType.setText("待支付");

                    //待发货
                }else if(status.contains("payment_finished")){
                    tvHint.setVisibility(View.GONE);
                    tvPrimary.setVisibility(View.GONE);

                    //待收货
                }else if(status.contains("delivery_finished")){
                    tvHint.setText("查看物流");
                    tvPrimary.setText("确认收货");
                    tvHint.setVisibility(View.GONE);
                    tvPrimary.setVisibility(View.GONE);
                    //已完成
                }else if(status.contains("order_finished")){
                    tvPrimary.setText("评价");
                    tvHint.setVisibility(View.GONE);
                    tvHint.setVisibility(View.GONE);
                    tvPrimary.setVisibility(View.GONE);
                }
            }
        };
    }

    @Override
    public void loadData(int page, int pageSize) {
        mOrderPresenter.getOrderList(page,searchInfo);
    }

    @Override
    public void getOrderListView(List<OrderInfo> list) {
        mRefreshLayout.addData(list);
    }
}
