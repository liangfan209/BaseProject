package com.bq.order.mvp.ui.fragment;

import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseFragment;
import com.bq.order.R;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/29
 * 版权：
 */
public class HomeBannerFragment extends BaseFragment {
    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected int getContentViewLayout() {
        return R.layout.order_fragment_home_banner;
    }

    @Override
    protected void attach() {

    }
}
