package com.clkj.order.mvp.ui.hodler;

import android.view.View;

import com.clkj.order.R;
import com.clkj.order.requset.bean.BannerData;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.zhpan.bannerview.BaseViewHolder;

import androidx.annotation.NonNull;

public class NewTypeViewHolder extends BaseViewHolder<BannerData> {

        private HolderInter mInter;
        public NewTypeViewHolder(@NonNull View itemView,HolderInter inter) {
            super(itemView);
            this.mInter = inter;
        }

        @Override
        public void bindData(BannerData data, int position, int pageSize) {
            //视频部分交给activity处理
//            View view = findView(R.id.image_view);
//            if (view instanceof ImageView) {
//                ((ImageView) view).setImageResource(resId);
//            }
            StandardGSYVideoPlayer player = findView(R.id.detail_player);
            player.getBackButton().setVisibility(View.GONE);
            this.mInter.createVideo(player);
        }

        public interface HolderInter{
            void createVideo(StandardGSYVideoPlayer palyer);
        }
}