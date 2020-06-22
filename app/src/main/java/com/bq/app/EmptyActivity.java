package com.bq.app;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.base.R;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePersenter;
import com.bq.comm_config_lib.mvp.ui.BaseAcitivty;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/11
 * 版权：
 */
public class EmptyActivity extends BaseAcitivty {
    @Override
    protected int getContentViewLayout() {
        return R.layout.activity_empty;
    }

    @Override
    protected BasePersenter createPersenter() {
        return null;
    }
    @Override
    protected void attach() {
        //跳转到登录界面
        ARouter.getInstance().build(AppArouter.LOGIN_ACTVITY).navigation();
        finish();
    }
}
