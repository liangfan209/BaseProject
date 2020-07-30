package com.bq.order.mvp.ui.fragment;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseFragment;
import com.bq.order.R;
import com.bq.order.R2;
import com.fan.baseuilibrary.view.DeletableEditText;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/29
 * 版权：
 */
public class HomeFragment extends BaseFragment {
    @BindView(R2.id.tv_address_location)
    TextView mTvAddressLocation;
    @BindView(R2.id.det_search)
    DeletableEditText mDetSearch;
    @BindView(R2.id.rlt_search)
    RelativeLayout mRltSearch;
    @BindView(R2.id.flt_home_banner)
    FrameLayout mFltHomeBanner;
    @BindView(R2.id.tv_hot_school)
    TextView mTvHotSchool;
    @BindView(R2.id.rv_school)
    RecyclerView mRvSchool;
    @BindView(R2.id.rv_news)
    RecyclerView mRvNews;
    @BindView(R2.id.tv_hot_profession)
    TextView mTvHotProfession;
    @BindView(R2.id.rv_profession)
    RecyclerView mRvProfession;

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected int getContentViewLayout() {
        return R.layout.order_fragment_home;
    }

    @Override
    protected void attach() {
    }

    @OnClick({R2.id.rlt_search, R2.id.tv_hot_school, R2.id.tv_hot_profession})
    public void onViewClicked(View view) {
    }
}
