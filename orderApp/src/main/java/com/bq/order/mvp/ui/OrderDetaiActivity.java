package com.bq.order.mvp.ui;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseAcitivty;
import com.bq.order.R;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/17
 * 版权：
 */
@Route(path = AppArouter.ORDER_ORDER_DETAIL_ACTIVITY)
public class OrderDetaiActivity extends BaseAcitivty {
    @Override
    protected int getContentViewLayout() {
        return R.layout.order_activity_order_detail;
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected void attach() {

    }
}
