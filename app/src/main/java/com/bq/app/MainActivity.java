package com.bq.app;

import android.os.Build;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.base.R;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseAcitivty;
import com.bq.user_center.mvp.user.ui.UserFragment;
import com.fan.baseuilibrary.view.flycotablayout.TabEntity;
import com.fan.baseuilibrary.view.flycotablayout.widget.SkinCommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;

@Route(path = AppArouter.MAIN_ACTIVITY)
public class MainActivity extends BaseAcitivty {
    @BindView(R.id.tablayout)
    SkinCommonTabLayout mTablayout;


    private String[] tabs = new String[]{"tab1", "tab2", "个人中心"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private int[] mIconUnselectIds = {
            R.mipmap.icon_dark_home, R.mipmap.icon_dark_home,
            R.mipmap.icon_dark_home};
    private int[] mIconSelectIds = {
            R.mipmap.icon_bottom_home, R.mipmap.icon_bottom_home,
            R.mipmap.icon_bottom_home};
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private Fragment mUserFragment,skinFragment;

    @Override
    protected int getContentViewLayout() {
        return R.layout.activity_main;
    }
    @Override
    protected BasePresenter createPersenter() {
        return null;
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void attach() {
        for (String title : tabs) {
            mFragments.add(new UserFragment());
        }
        for (int i = 0; i < tabs.length; i++) {
            mTabEntities.add(new TabEntity(tabs[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
//        mTablayout.setTabData(mTabEntities, this, R.id.flt_content, mFragments);
        mTablayout.setTabData(mTabEntities);
        mTablayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                selectFragment(position);
            }
            @Override
            public void onTabReselect(int position) {
            }
        });
//        SkinCompatManager.getInstance().loadSkin("appskin-debug.apk", null, SkinCompatManager.SKIN_LOADER_STRATEGY_ASSETS);
    }

    /**
     * 选择相关的页面
     *
     * @param index
     */
    public void selectFragment(int index) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 1:
                if (skinFragment == null) {
                    skinFragment = new MainFragment();
                    transaction.add(R.id.flt_content, skinFragment, "flag" + index);
                }else{
                    transaction.show(skinFragment);
                }
                break;
            case 2:
                if (mUserFragment == null) {
                    mUserFragment = (Fragment) ARouter.getInstance().build(AppArouter.USER_CENTER_USER_FRAGMENT).navigation();
                    transaction.add(R.id.flt_content, mUserFragment, "flag" + index);
                }else{
                    transaction.show(mUserFragment);
                }
                break;

        }
        transaction.commit();

    }

    /**
     * 隐藏所有的fragment
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (mUserFragment != null) {
            transaction.hide(mUserFragment);
        }
        if(skinFragment != null){
            transaction.hide(skinFragment);
        }
    }


}
