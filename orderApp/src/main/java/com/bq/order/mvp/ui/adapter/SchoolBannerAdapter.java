package com.bq.order.mvp.ui.adapter;

import android.view.View;

import com.bq.order.R;
import com.bq.order.mvp.ui.hodler.SchoolViewHolder;
import com.bq.order.requset.bean.SchoolInfo;
import com.zhpan.bannerview.BaseBannerAdapter;
import com.zhpan.bannerview.BaseViewHolder;

import java.util.List;

/**
 * <pre>
 *   Created by zhpan on 2020/4/6.
 *   Description:
 * </pre>
 */
public class SchoolBannerAdapter extends BaseBannerAdapter<List<SchoolInfo>, BaseViewHolder<List<SchoolInfo>>> {

    @Override
    protected void onBind(BaseViewHolder<List<SchoolInfo>> holder, List<SchoolInfo> data, int position, int pageSize) {
        holder.bindData(data, position, pageSize);
    }

    @Override
    public BaseViewHolder<List<SchoolInfo>> createViewHolder(View itemView, int viewType) {
        return new SchoolViewHolder(itemView);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.order_item_school_banner;
    }
}

