package com.bq.order.mvp.order.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseFragment;
import com.bq.order.R;
import com.bq.order.R2;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.fan.baseuilibrary.view.MyRefreshLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/17
 * 版权：
 */

public class OrderListFragment extends BaseFragment implements MyRefreshLayout.LayoutInterface<String> {

    @BindView(R2.id.flt_content)
    FrameLayout mFltContent;
    private List<String> mlist = new ArrayList<>();
    private int mtype = 0;

    String[] typeStrs = new String[]{"全部订单","待支付","待收货，","已完成"};

    public static OrderListFragment getInstance(int type) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentViewLayout() {
        return R.layout.laoyut_frame_layout;
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected void attach() {
        mtype = (int) getArguments().get("type");
        mRefreshLayout = new MyRefreshLayout<String>(this.getContext(), this);
        mRefreshLayout.setRefresh(true, true);
        mFltContent.addView(mRefreshLayout);
    }

    @Override
    public BaseQuickAdapter<String, ? extends BaseViewHolder> createAdapter() {
        return new BaseQuickAdapter<String, BaseViewHolder>(R.layout.order_item_order,mlist) {
            @Override
            protected void convert(@NotNull BaseViewHolder helper, String s) {
                TextView tvType = helper.getView(R.id.tv_type);
                TextView tvHint = helper.getView(R.id.tv_hint);
                TextView tvPrimary = helper.getView(R.id.tv_primary);
                tvPrimary.setOnClickListener(v->{

                });
                tvHint.setOnClickListener(v->{

                });

                if(mtype != 0){
                    tvType.setText(typeStrs[mtype]);
                }
                tvHint.setVisibility(View.VISIBLE);
                tvPrimary.setVisibility(View.VISIBLE);
                if(mtype == 1){
                    tvHint.setText("取消");
                    tvPrimary.setText("支付");
                }else if(mtype == 2){
                    tvHint.setText("查看物流");
                    tvPrimary.setText("确认收货");
                }else if(mtype == 3){
                    tvPrimary.setText("评价");
                    tvHint.setVisibility(View.GONE);
                }
            }
        };
    }

    @Override
    public void loadData(int page, int pageSize) {
        mlist.add("1");
        mlist.add("1");
        mlist.add("1");
        mlist.add("1");
        mRefreshLayout.addData(mlist);
        new Handler().postDelayed(()->{
            onComplete();
        },1000);
    }
}
