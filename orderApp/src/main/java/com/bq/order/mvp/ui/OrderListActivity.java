package com.bq.order.mvp.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.order.R;
import com.bq.order.R2;
import com.bq.order.mvp.order.ui.fragment.OrderListFragment;
import com.fan.baseuilibrary.view.NoAnimationViewPager;
import com.fan.baseuilibrary.view.flycotablayout.widget.SkinSlidingTabLayout;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import butterknife.BindView;

/**
 * 文件名：
 * 描述：订单列表
 * 作者：梁帆
 * 时间：2020/7/17
 * 版权：
 */

@Route(path = AppArouter.ORDER_LIST_ACTIVITY)
public class OrderListActivity extends BaseActivity {

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
    @BindView(R2.id.tablayout)
    SkinSlidingTabLayout mTablayout;
    @BindView(R2.id.viewpager)
    NoAnimationViewPager mViewpager;
    private String[] mTitles = {"全部订单", "待支付", "待收货", "已完成"};
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected int getContentViewLayout() {
        return R.layout.order_activity_order_list;
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected void attach() {
        mTvTitle.setText("订单列表");
        for (int i = 0; i < 4; i++) {
            mFragmentList.add(OrderListFragment.getInstance(i));
        }
        mTablayout.setViewPager(mViewpager,mTitles,this,mFragmentList);
    }
}
