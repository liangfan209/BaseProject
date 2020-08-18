package com.bquan.app;

import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.user_center.mvp.user.ui.UserFragment;
import com.fan.baseuilibrary.view.flycotablayout.TabEntity;
import com.fan.baseuilibrary.view.flycotablayout.widget.SkinCommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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


    private String[] tabs = new String[]{"学习", "我的"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private int[] mIconUnselectIds = {
            R.mipmap.icon_home_study_unselect,
            R.mipmap.icon_home_mine_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.icon_home_study_select,
            R.mipmap.icon_home_mine_select};
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private Fragment mUserFragment,skinFragment,mHomeFragment;
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
        EventBus.getDefault().register(this);

        //绿色皮肤
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
//                if(position == 2){
//                    //判断下token
//                    String token = CommSpUtils.getToken();
//                    if(StringUtils.isEmpty(token)){
//                        //跳转到登录界面
//                        Bundle bundle = new Bundle();
//                        bundle.putInt("index",position);
//                        ARouter.getInstance().build(AppArouter.LOGIN_ACTVITY)
//                                .withString("mPath",AppArouter.MAIN_ACTIVITY)
//                                .withBundle("mBundle",bundle).navigation();
//                        mTablayout.setCurrentTab(currentIndex);
//                        return;
//                    }
//                }else{
//                    currentIndex = position;
//                }
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
        selectFragment(0);
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
            case 0:
                if (mHomeFragment == null) {
                    mHomeFragment = (Fragment) ARouter.getInstance().build(AppArouter.ORDER_HOME_FRAGMENT).navigation();
                    transaction.add(R.id.flt_content, mHomeFragment, "flag" + index);
                }else{
                    transaction.show(mHomeFragment);
                }
                break;
            case 1:
//                if (skinFragment == null) {
////                    skinFragment = new MainFragment();
////                    transaction.add(R.id.flt_content, skinFragment, "flag" + index);
////                }else{
////                    transaction.show(skinFragment);
////                }
////                break;
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
        if(mHomeFragment != null){
            transaction.hide(mHomeFragment);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void providerData(String key) {
        if(key == "go_home"){
            mTablayout.setCurrentTab(0);
            selectFragment(0);
            selectFragment(0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private long mPressedTime;
    @Override
    public void onBackPressed() {
        //获取第一次按键时间
        long mNowTime = System.currentTimeMillis();
        //比较两次按键时间差
        if ((mNowTime - mPressedTime) > 2000) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
//            Utils.showToast(this,getString(R.string.exit_app));
            mPressedTime = mNowTime;
        } else {
            super.onBackPressed();
            //退出应用
            System.exit(0);
        }
    }

}
