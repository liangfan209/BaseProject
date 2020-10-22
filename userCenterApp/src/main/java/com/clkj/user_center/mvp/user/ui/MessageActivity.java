package com.clkj.user_center.mvp.user.ui;

import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.clkj.user_center.R;
import com.clkj.user_center.R2;
import com.fan.baseuilibrary.view.NoAnimationViewPager;
import com.fan.baseuilibrary.view.flycotablayout.widget.SkinSlidingTabLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述： 学校列表
 * 作者：梁帆
 * 时间：2020/7/29
 * 版权：
 */
@Route(path = AppArouter.USER_CENTER_MESSAGE_ACTIVITY)
public class MessageActivity extends BaseActivity {

    @BindView(R2.id.tablayout)
    SkinSlidingTabLayout mTablayout;
    @BindView(R2.id.iv_title_left)
    ImageView mIvTitleLeft;
    @BindView(R2.id.viewpager)
    NoAnimationViewPager mViewpager;

    private String[] mTitles = {"消息","公告"};
    private String[] typeStr ={"",""};
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected int getContentViewLayout() {
        return R.layout.user_center_activity_message;
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected void attach() {
        for (int i = 0; i < mTitles.length; i++) {
            mFragmentList.add(MessageListFragment.getInstance(i+1));
        }
        mTablayout.setViewPager(mViewpager,mTitles,this,mFragmentList);
        mTablayout.setMsgMargin(0,80,80);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void providerData(String key) {
        if(key.contains("unread_count,") ){
            int count =Integer.valueOf( key.split(",")[1]);
            if(count > 0){
                mTablayout.showMsg(0,count);
            }else{
                mTablayout.hideMsg(0);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @OnClick(R2.id.iv_title_left)
    public void onViewClicked() {
        finish();
    }
}
