package com.bq.app;

import android.os.Build;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.StringUtils;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.comm_config_lib.utils.CommSpUtils;
import com.bq.user_center.mvp.user.ui.UserFragment;
import com.bquan.app.R;
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
public class MainActivity extends BaseActivity {
    @BindView(R.id.tablayout)
    SkinCommonTabLayout mTablayout;

    @Autowired
    Bundle mBundle;


    private String[] tabs = new String[]{"学习", "发现", "我的"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private int[] mIconUnselectIds = {
            R.mipmap.icon_home_study_unselect, R.mipmap.icon_home_find_unselect,
            R.mipmap.icon_home_mine_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.icon_home_study_select, R.mipmap.icon_home_find_select,
            R.mipmap.icon_home_mine_select};
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private Fragment mUserFragment,skinFragment;
    private int currentIndex = 0;

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
        ARouter.getInstance().inject(this);
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
                if(position == 2){
                    //判断下token
                    String token = CommSpUtils.getToken();
                    if(StringUtils.isEmpty(token)){
                        //跳转到登录界面
                        Bundle bundle = new Bundle();
                        bundle.putInt("index",position);
                        ARouter.getInstance().build(AppArouter.LOGIN_ACTVITY)
                                .withString("mPath",AppArouter.MAIN_ACTIVITY)
                                .withBundle("mBundle",bundle).navigation();
                        mTablayout.setCurrentTab(currentIndex);
                        return;
                    }
                }else{
                    currentIndex = position;
                }
                selectFragment(position);
            }
            @Override
            public void onTabReselect(int position) {
            }
        });
        if(mBundle != null){
            int index = mBundle.getInt("index");
            mTablayout.setCurrentTab(index);
            selectFragment(index);
        }
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
