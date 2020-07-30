package com.bq.order.mvp.ui;

import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.order.R;

/**
 * 文件名：
 * 描述： 学校详情
 * 作者：梁帆
 * 时间：2020/7/29
 * 版权：
 */

public class SchoolDetailActivity extends BaseActivity {
    @Override
    protected int getContentViewLayout() {
        return R.layout.order_activity_school_detail;
    }
    @Override
    protected BasePresenter createPersenter() {
        return null;
    }
    @Override
    protected void attach() {

    }
}
