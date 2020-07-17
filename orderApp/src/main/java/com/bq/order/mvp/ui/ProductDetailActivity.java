package com.bq.order.mvp.ui;

import android.content.res.Configuration;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseAcitivty;
import com.bq.order.R;
import com.bq.order.mvp.ui.adapter.HomeAdapter;
import com.bq.order.mvp.ui.hodler.NewTypeViewHolder;
import com.bq.order.requset.bean.BannerData;
import com.fan.baseuilibrary.view.CustomPopWindow;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.IndicatorGravity;
import com.zhpan.indicator.base.IIndicator;
import com.zhpan.indicator.enums.IndicatorSlideMode;
import com.zhpan.indicator.enums.IndicatorStyle;

import java.util.ArrayList;

import androidx.viewpager2.widget.ViewPager2;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/16
 * 版权：
 */
@Route(path = AppArouter.ORDER_PRODUCT_DETAIL_ACTIVITY)
public class ProductDetailActivity extends BaseAcitivty {

    private BannerViewPager mViewPager;
    @Override
    protected int getContentViewLayout() {
        return R.layout.order_activity_product_detail;
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected void attach() {
        //创建viewhoder
        createViewHolder();
        findViewById(R.id.tv_buy).setOnClickListener(v->{
            showBuyPopwindow();
        });
    }

    private void createViewHolder() {
        mViewPager = findViewById(R.id.banner_view);
        HomeAdapter homeAdapter = new HomeAdapter(new NewTypeViewHolder.HolderInter() {
            @Override
            public void createVideo(StandardGSYVideoPlayer palyer) {
                createVideoView(palyer);
            }
        });
        mViewPager
                .setScrollDuration(600)
                .setLifecycleRegistry(getLifecycle())
                .setIndicatorStyle(IndicatorStyle.CIRCLE)
                .setIndicatorSlideMode(IndicatorSlideMode.WORM)
                .setInterval(3000)
                .setIndicatorGravity(IndicatorGravity.END)
                .setCanLoop(false)
                .setAdapter(homeAdapter)
                .setIndicatorView(setupIndicatorView())
                .registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                    }
                }).create();

        ArrayList<BannerData> dataList = new ArrayList<BannerData>();
        BannerData b1 = new BannerData("https://www.wanandroid.com/blogimgs/90c6cc12-742e-4c9f-b318-b912f163b8d0.png",BannerData.TYPE_NEW);
        BannerData b2 = new BannerData("https://www.wanandroid.com/blogimgs/90c6cc12-742e-4c9f-b318-b912f163b8d0.png",1);
        BannerData b3 = new BannerData("https://www.wanandroid.com/blogimgs/90c6cc12-742e-4c9f-b318-b912f163b8d0.png",1);
        dataList.add(b1);
        dataList.add(b2);
        dataList.add(b3);
        mViewPager.refreshData(dataList);
    }

    private IIndicator setupIndicatorView() {
        FigureIndicatorView indicatorView = new FigureIndicatorView(this);
        indicatorView.setRadius(36);
        indicatorView.setTextSize(26);
        indicatorView.setBackgroundColor(Color.parseColor("#aaaaaaaa"));
        return indicatorView;
    }


    OrientationUtils orientationUtils;
    StandardGSYVideoPlayer detailPlayer;
    boolean isPause = true;
    boolean isPlay = true;

    void createVideoView(StandardGSYVideoPlayer palyer){
        detailPlayer = palyer;
        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, detailPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);
        GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
        gsyVideoOption
//                .setThumbImageView(imageView)
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setAutoFullWithSize(true)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setUrl("http://mvvideo2.meitudata.com/576bc2fc91ef22121.mp4")
                .setCacheWithPlay(false)
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        //开始播放了才能旋转和全屏
                        orientationUtils.setEnable(true);
                        isPlay = true;
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        if (orientationUtils != null) {
                            orientationUtils.backToProtVideo();
                        }
                    }
                }).setLockClickListener(new LockClickListener() {
            @Override
            public void onClick(View view, boolean lock) {
                if (orientationUtils != null) {
                    //配合下方的onConfigurationChanged
                    orientationUtils.setEnable(!lock);
                }
            }
        }).build(detailPlayer);

        detailPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                orientationUtils.resolveByClick();

                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                detailPlayer.startWindowFullscreen(ProductDetailActivity.this, true, true);
            }
        });
    }



    @Override
    public void onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if(detailPlayer != null){
            detailPlayer.getCurrentPlayer().onVideoPause();
            isPause = true;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(detailPlayer != null) {
            detailPlayer.getCurrentPlayer().onVideoResume(false);
            isPause = false;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(detailPlayer != null) {
            if (isPlay) {
                detailPlayer.getCurrentPlayer().release();
            }
            if (orientationUtils != null)
                orientationUtils.releaseListener();
        }

    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(detailPlayer != null){
            //如果旋转了就全屏
            if (isPlay && !isPause) {
                detailPlayer.onConfigurationChanged(this, newConfig, orientationUtils, true, true);
            }
        }
    }


    void showBuyPopwindow(){
        View view = getLayoutInflater().inflate(R.layout.order_popwindow_buy,null);
        CustomPopWindow mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .size(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                .setView(view)
                .enableBackgroundDark(true)
                .setAnimationStyle(R.style.public_dialog_inout_anim)
                .create()
                .showAtLocation(this.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

}
