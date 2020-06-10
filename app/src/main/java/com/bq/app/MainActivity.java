package com.bq.app;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bq.base.R;
import com.bq.comm_config_lib.ui.BaseAcitivty;
import com.bq.user_center.ui.UserFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;

public class MainActivity extends BaseAcitivty {

    @BindView(R.id.tablayout)
    TabLayout mTablayout;

    private String[] tabs = new String[]{"tab1", "tab2", "tab3"};
    private Fragment mUserFragment;

    @Override
    protected int getContentViewLayout() {
        return R.layout.activity_main;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void attach() {
        List<String> strings = Arrays.asList(tabs);
        for (String str : strings) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_tab, null);
            TextView tvName = view.findViewById(R.id.tv_name);
            tvName.setText(str);
            tvName.setCompoundDrawablesRelativeWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.tab_home_selector)
                    , null, null);
            mTablayout.addTab(mTablayout.newTab().setCustomView(view));
        }
        mTablayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                customView.findViewById(R.id.tv_name).setSelected(true);
                int position = tab.getPosition();
                selectFragment(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.tv_name).setSelected(false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
                if (mUserFragment == null) {
                    mUserFragment = new UserFragment();
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
    }


}
