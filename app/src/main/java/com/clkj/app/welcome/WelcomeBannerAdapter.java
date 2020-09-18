package com.clkj.app.welcome;

import android.app.Activity;
import android.view.View;

import com.clkj.app.R;
import com.zhpan.bannerview.BaseBannerAdapter;
import com.zhpan.bannerview.BaseViewHolder;

/**
 * <pre>
 *   Created by zhpan on 2020/4/6.
 *   Description:
 * </pre>
 */
public class WelcomeBannerAdapter extends BaseBannerAdapter<Integer, BaseViewHolder<Integer>> {


    private Activity mActivity;
    public WelcomeBannerAdapter(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    protected void onBind(BaseViewHolder<Integer> holder, Integer data, int position, int pageSize) {
        holder.bindData(data, position, pageSize);
    }

    @Override
    public BaseViewHolder<Integer> createViewHolder(View itemView, int viewType) {
        return new WelcomeHolder(itemView,mActivity);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_welcome_banner;
    }
}
