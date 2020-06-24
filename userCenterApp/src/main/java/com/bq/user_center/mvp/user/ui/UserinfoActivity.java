package com.bq.user_center.mvp.user.ui;

import com.bq.comm_config_lib.mvp.BasePersenter;
import com.bq.comm_config_lib.mvp.ui.BaseAcitivty;
import com.bq.user_center.R;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/24
 * 版权：
 */
public class UserinfoActivity extends BaseAcitivty {
    @Override
    protected int getContentViewLayout() {
        return R.layout.user_center_activity_userinfo;
    }
    @Override
    protected BasePersenter createPersenter() {
        return null;
    }
    @Override
    protected void attach() {

    }
}
