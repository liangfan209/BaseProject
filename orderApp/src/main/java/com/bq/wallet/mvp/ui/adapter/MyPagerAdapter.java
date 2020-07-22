package com.bq.wallet.mvp.ui.adapter;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments;
        private String[] mTitles;

        public MyPagerAdapter(FragmentManager fm,List<Fragment> list,String[] titleList) {
            super(fm);
            this.mFragments = list;
            this.mTitles = titleList;
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }