package com.bq.order.mvp.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.comm_config_lib.mvp.ui.BaseFragment;
import com.bq.comm_config_lib.utils.PayViewHelper;
import com.bq.comm_config_lib.utils.Utils;
import com.bq.order.R;
import com.bq.order.R2;
import com.bq.order.api.OrderEventKey;
import com.bq.order.mvp.presenter.OrderPresenter;
import com.bq.order.mvp.ui.OrderIview;
import com.bq.order.requset.bean.OrderInfo;
import com.bq.utilslib.AppUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.fan.baseuilibrary.view.MyRefreshLayout;
import com.fan.baseuilibrary.view.dialog.CustomDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import butterknife.BindView;
import skin.support.content.res.SkinCompatResources;

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
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void updateFragment(String info){
        searchInfo = info;
        mRefreshLayout.initRefreshBoo();
        loadData(1,10);
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
                helper.setText(R.id.tv_product,orderItemListBean.getCategory());
                helper.setText(R.id.tv_price, AppUtils.getDouble2(orderItemListBean.getSale_price()));
                helper.setText(R.id.tv_orgamnization, orderItemListBean.getAgent_name());

                helper.setText(R.id.tv_total_price,"总价： ¥"+AppUtils.getDouble2(orderItemListBean.getSale_price()*orderItemListBean.getQuantity()));
                tvTotalCount.setText("数量：x"+ orderItemListBean.getQuantity());
                String status = info.getStatus();

                tvType.setTextColor(SkinCompatResources.getColor(tvType.getContext(),R.color.ui_primary_color));
                tvHint.setVisibility(View.VISIBLE);
                tvPrimary.setVisibility(View.VISIBLE);

                tvPrimary.setOnClickListener(v->{
                    if(info.getDespatch_type().contains("物流")){
                        if(status.contains("order_launched")){
                            showPayView(info.getId()+"",AppUtils.getDouble2(orderItemListBean.getSale_price()*orderItemListBean.getQuantity()));
                            //待发货
                        }else if(status.contains("payment_finished")){
                            //待收货
                        }else if(status.contains("delivery_finished")){
                            //已完成
                        }else if(status.contains("order_finished")){

                        }
                    }else{
                        if(status.contains("order_launched")){
                            showPayView(info.getId()+"",AppUtils.getDouble2(orderItemListBean.getSale_price()*orderItemListBean.getQuantity()));
                            //待发货  待收货
                        }else if(status.contains("payment_finished") || status.contains("delivery_finished")){
                            ARouter.getInstance().build(AppArouter.ORDER_SIGN_CONTRACT_ACTIVITY)
                                    .withString("productId",info.getId()+"")
                                    .withSerializable("mInvoiceInfo",info.getInvoice_info())
                                    .withInt("sign",1)
                                    .withString("imgPath",info.getContract_background()).navigation();
                        }else if(status.contains("order_finished")){
                            ARouter.getInstance().build(AppArouter.ORDER_SIGN_CONTRACT_ACTIVITY)
                                    .withString("productId",info.getId()+"").navigation();
                        }
                    }
                });

                tvHint.setOnClickListener(v->{
                    if(status.contains("order_launched")){
                        //取消支付订单
                        cancelOrder(info.getId());
                        //待发货
                    }else if(status.contains("payment_finished")){
                        //待收货
                    }else if(status.contains("delivery_finished")){
                        //已完成
                    }else if(status.contains("order_finished")){

                    }
                });

                if(info.getDespatch_type().contains("教育")){
                    //待支付
                    if(status.contains("order_launched")){
                        tvHint.setText("取消");
                        tvPrimary.setText("支付");
                        tvType.setText("待支付");
                        //待发货
                    }else if(status.contains("payment_finished")){
                        tvHint.setVisibility(View.GONE);
                        tvPrimary.setVisibility(View.VISIBLE);
                        tvPrimary.setText("签署合同");
                        tvType.setText("已付款");
                        //待收货
                    }else if(status.contains("delivery_finished")){
                        tvHint.setVisibility(View.GONE);
                        tvPrimary.setVisibility(View.VISIBLE);
                        tvPrimary.setText("签署合同");
                        tvType.setText("已付款");
                        //已完成
                    }else if(status.contains("order_finished")){
                        tvHint.setVisibility(View.GONE);
                        tvPrimary.setVisibility(View.VISIBLE);
                        tvPrimary.setText("查看合同");
                        tvType.setText("已完成");
                        tvType.setTextColor(SkinCompatResources.getColor(tvType.getContext(),R.color.ui_txt_hint_color));
                    }else if(status.contains("order_closed")){
                        tvHint.setVisibility(View.GONE);
                        tvPrimary.setVisibility(View.GONE);
                        tvType.setText("已取消");
                        tvType.setTextColor(SkinCompatResources.getColor(tvType.getContext(),R.color.ui_txt_hint_color));
                    }
                }else{
                    if(status.contains("order_launched")){
                        tvHint.setText("取消");
                        tvPrimary.setText("支付");
                        tvType.setText("待支付");
                        //待发货
                    }else if(status.contains("payment_finished")){
                        tvHint.setVisibility(View.GONE);
                        tvPrimary.setVisibility(View.GONE);
                        tvType.setText("待发货");
                        //待收货
                    }else if(status.contains("delivery_finished")){
//                        tvHint.setText("查看物流");
                        tvHint.setVisibility(View.GONE);
                        tvPrimary.setText("确认收货");
                        tvType.setText("代收货");
                        //已完成
                    }else if(status.contains("order_finished")){
                        tvPrimary.setText("评价");
                        tvType.setText("已完成");
                        tvType.setTextColor(SkinCompatResources.getColor(tvType.getContext(),R.color.ui_txt_hint_color));
                        tvHint.setVisibility(View.GONE);
                        tvHint.setVisibility(View.GONE);
                        tvPrimary.setVisibility(View.GONE);
                    }else if(status.contains("order_closed")){
                        tvHint.setVisibility(View.GONE);
                        tvPrimary.setVisibility(View.GONE);
                        tvType.setText("已取消");
                        tvType.setTextColor(SkinCompatResources.getColor(tvType.getContext(),R.color.ui_txt_hint_color));
                    }
                }
                tvType.setText(info.getStatus_name());
            }
        };
    }

    private void cancelOrder(int id) {
        new CustomDialog().showDialogDialog(this.getContext(), "订单取消", "确定取消此订单吗？", new CustomDialog.ClickCallBack() {
            @Override
            public void ok() {
                mOrderPresenter.cancelOrder(id);
            }

            @Override
            public void cacel() {
            }
        });
    }

    @Override
    public void cancelOrderView() {
        updateFragment(searchInfo);
    }

    @Override
    public void loadData(int page, int pageSize) {
        mOrderPresenter.getOrderList(page,searchInfo);
    }

    @Override
    public void getOrderListView(List<OrderInfo> list) {
        mRefreshLayout.addData(list);
    }


    public void showPayView(String orderId,String price){
        PayViewHelper.getBanenceAndShow(((BaseActivity)getActivity()),orderId,price,3);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateAddress(String event) {
        if(OrderEventKey.UPDATE_ORDER_STATUS.equals(event)){
            updateFragment(searchInfo);
        }
    }
}
