package com.clkj.app;

import android.os.Handler;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.comm_config_lib.utils.CommSpUtils;
import com.clkj.app.welcome.WelcomeBannerAdapter;
import com.zhpan.bannerview.BannerViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 文件名：
 * 描述： 学校列表
 * 作者：梁帆
 * 时间：2020/7/29
 * 版权：
 */
//@Route(path = AppArouter.ORDER_WELCOME_ACTIVITY)
public class WelcomeActivity extends BaseActivity {
    @BindView(R.id.banner_advertising)
    BannerViewPager mBannerAdvertising;

    @Override
    protected int getContentViewLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected void attach() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        boolean welecom = CommSpUtils.getWelecom();
        if (welecom) {
            ARouter.getInstance().build(AppArouter.MAIN_ACTIVITY).navigation();
            new Handler().postDelayed(()->{finish();},1000);
        } else {
            ArrayList<Integer> list = new ArrayList<>();
            list.add(R.mipmap.icon_welcome1);
            list.add(R.mipmap.icon_welcome2);
            list.add(R.mipmap.icon_welcome3);
            list.add(R.mipmap.icon_welcome4);
            initAdvertisingBeanner(list);
        }


    }

    private void initAdvertisingBeanner(List<Integer> list) {
        mBannerAdvertising
                .setScrollDuration(600)
                .setLifecycleRegistry(getLifecycle())
                .setCanLoop(false)
                .setAdapter(new WelcomeBannerAdapter(this))
                .setOnPageClickListener(position -> {
                }).create();

        mBannerAdvertising.refreshData(list);
    }

}
