package com.bq.order.mvp.ui.hodler;

import android.view.View;
import android.widget.ImageView;

import com.bq.order.R;
import com.bq.order.requset.bean.BannerData;
import com.bumptech.glide.Glide;
import com.zhpan.bannerview.BaseViewHolder;

import androidx.annotation.NonNull;

/**
 * <pre>
 *   Created by zhangpan on 2019-08-14.
 *   Description:
 * </pre>
 */
public class NetViewHolder extends BaseViewHolder<BannerData> {

    public NetViewHolder(@NonNull View itemView) {
        super(itemView);
        ImageView imageView = findView(R.id.banner_image);
//        imageView.setRoundCorner(BannerUtils.dp2px(0));
    }

    @Override
    public void bindData(BannerData data, int position, int pageSize) {
        ImageView imageView = findView(R.id.banner_image);
        Glide.with(imageView).load(data.getImagePath()).placeholder(R.mipmap.ic_launcher).into(imageView);
    }
}
