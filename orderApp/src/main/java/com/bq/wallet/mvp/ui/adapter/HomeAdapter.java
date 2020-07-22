package com.bq.wallet.mvp.ui.adapter;

import android.view.View;

import com.bq.wallet.R;
import com.bq.wallet.mvp.ui.hodler.NetViewHolder;
import com.bq.wallet.mvp.ui.hodler.NewTypeViewHolder;
import com.bq.wallet.requset.bean.BannerData;
import com.zhpan.bannerview.BaseBannerAdapter;
import com.zhpan.bannerview.BaseViewHolder;

/**
 * <pre>
 *   Created by zhpan on 2020/4/6.
 *   Description:
 * </pre>
 */
public class HomeAdapter extends BaseBannerAdapter<BannerData, BaseViewHolder<BannerData>> {

    private NewTypeViewHolder.HolderInter mHolderInter;
    @Override
    protected void onBind(BaseViewHolder<BannerData> holder, BannerData data, int position, int pageSize) {
        holder.bindData(data, position, pageSize);
    }

    public HomeAdapter(NewTypeViewHolder.HolderInter inter) {
        this.mHolderInter = inter;
    }

    @Override
    public BaseViewHolder<BannerData> createViewHolder(View itemView, int viewType) {
        if (viewType == BannerData.TYPE_NEW) {
            return new NewTypeViewHolder(itemView, this.mHolderInter);
        }
        return new NetViewHolder(itemView);
    }

    @Override
    public int getViewType(int position) {
        return mList.get(position).getType();
    }

    @Override
    public int getLayoutId(int viewType) {
        if (viewType == BannerData.TYPE_NEW) {
            return R.layout.order_item_new_type;
        }
        return R.layout.order_item_banner;
    }
}
