package com.bq.order.mvp.ui;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.comm_config_lib.utils.BitImageUtils;
import com.bq.order.R;
import com.bq.order.R2;
import com.bq.order.mvp.order.ui.adapter.BannerAdapter;
import com.bq.order.mvp.order.ui.hodler.NewTypeViewHolder;
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
import java.util.List;

import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;
import butterknife.OnClick;
import cc.shinichi.library.bean.ImageInfo;
import skin.support.widget.SkinCompatRadioButton;
import skin.support.widget.SkinCompatRadioGroup;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/16
 * 版权：
 */
@Route(path = AppArouter.ORDER_PRODUCT_DETAIL_ACTIVITY)
public class ProductDetailActivity extends BaseActivity {

    @BindView(R2.id.iv_title_left)
    ImageView mIvTitleLeft;
    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.tv_right)
    TextView mTvRight;
    @BindView(R2.id.view_line)
    View mViewLine;
    @BindView(R2.id.rlt_title)
    RelativeLayout mRltTitle;
    @BindView(R2.id.banner_view)
    BannerViewPager mBannerView;
    @BindView(R2.id.tv_real_price)
    TextView mTvRealPrice;
    @BindView(R2.id.tv_initial_price)
    TextView mTvInitialPrice;
    @BindView(R2.id.tv_shipping_key)
    TextView mTvShippingKey;
    @BindView(R2.id.tv_shipping_value)
    TextView mTvShippingValue;
    @BindView(R2.id.tv_type_key)
    TextView mTvTypeKey;
    @BindView(R2.id.tv_type_value)
    TextView mTvTypeValue;
    @BindView(R2.id.rlt_type)
    RelativeLayout mRltType;
    @BindView(R2.id.key1)
    TextView mKey1;
    @BindView(R2.id.value1)
    TextView mValue1;
    @BindView(R2.id.key2)
    TextView mKey2;
    @BindView(R2.id.value2)
    TextView mValue2;
    @BindView(R2.id.tv_buy)
    TextView mTvBuy;


    @BindView(R2.id.tv_index)
    TextView mTvIndex;
    @BindView(R2.id.rb_video)
    SkinCompatRadioButton mRbVideo;
    @BindView(R2.id.rb_img)
    SkinCompatRadioButton mRbImg;
    @BindView(R2.id.rg_video)
    SkinCompatRadioGroup mRgVideo;
//    private BannerViewPager mViewPager;

    OrientationUtils orientationUtils;
    StandardGSYVideoPlayer detailPlayer;
    boolean isPause = true;
    boolean isPlay = true;


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
        mTvTitle.setText("商品详情");
        //创建viewhoder
        createViewHolder();
        mTvInitialPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    private void createViewHolder() {
//        mBannerView = findViewById(R2.id.banner_view);
        BannerAdapter homeAdapter = new BannerAdapter(new NewTypeViewHolder.HolderInter() {
            @Override
            public void createVideo(StandardGSYVideoPlayer palyer) {
                createVideoView(palyer);
            }
        });
        mRgVideo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rb_video){
                    mBannerView.setCurrentItem(0);
                }else if(checkedId == R.id.rb_img){
                    mBannerView.setCurrentItem(1);
                }
            }
        });
        mBannerView
                .setScrollDuration(600)
                .setLifecycleRegistry(getLifecycle())
                .setIndicatorStyle(IndicatorStyle.CIRCLE)
                .setIndicatorSlideMode(IndicatorSlideMode.WORM)
                .setInterval(3000)
                .setIndicatorGravity(IndicatorGravity.END)
                .setCanLoop(false)
                .setAdapter(homeAdapter)
                .setIndicatorView(setupIndicatorView())
                .setOnPageClickListener(new BannerViewPager.OnPageClickListener() {
                    @Override
                    public void onPageClick(int position) {
                        if(position != 0){
                            BitImageUtils.showBigImgPreview(ProductDetailActivity.this, position-1, getImgList(), new BitImageUtils.PageSelect() {
                                @Override
                                public void onPageSelected(int postion) {
                                    mBannerView.setCurrentItem(position+1);
                                }
                            });
                        }
                    }
                })
                .registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        if(position == 0){
                            mRbVideo.setChecked(true);
                            mRbImg.setChecked(false);
                            mTvIndex.setVisibility(View.GONE);
                        }else{
                            mRbVideo.setChecked(false);
                            mRbImg.setChecked(true);
                            mTvIndex.setVisibility(View.VISIBLE);
                            mTvIndex.setText(position+"/4");
                        }
                    }
                }).create();


        ArrayList<BannerData> dataList = new ArrayList<BannerData>();
        BannerData b1 = new BannerData("https://www.wanandroid.com/blogimgs/90c6cc12-742e-4c9f-b318-b912f163b8d0.png",
                BannerData.TYPE_NEW);
        BannerData b2 = new BannerData("http://img6.16fan.com/201510/11/005258wdngg6rv0tpn8z9z.jpg", 1);
        BannerData b3 = new BannerData("http://img6.16fan.com/201510/11/013553aj3kp9u6iuz6k9uj.jpg", 1);
        BannerData b4 = new BannerData("http://img6.16fan.com/201510/11/011753fnanichdca0wbhxc.jpg", 1);
        BannerData b5 = new BannerData("http://img6.16fan.com/201510/11/011819zbzbciir9ctn295o.jpg", 1);
        dataList.add(b1);
        dataList.add(b2);
        dataList.add(b3);
        dataList.add(b4);
        dataList.add(b5);
        mBannerView.refreshData(dataList);
    }

    private IIndicator setupIndicatorView() {
        FigureIndicatorView indicatorView = new FigureIndicatorView(this);
        indicatorView.setRadius(36);
        indicatorView.setTextSize(26);
        indicatorView.setBackgroundColor(Color.parseColor("#aaaaaaaa"));
        return indicatorView;
    }



    void createVideoView(StandardGSYVideoPlayer palyer) {
        detailPlayer = palyer;
        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, detailPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);
        GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
        gsyVideoOption
//                .setThumbImageView(imageView)
                .setIsTouchWiget(true)
//                .setRotateViewAuto(false)
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
        if (detailPlayer != null) {
            detailPlayer.getCurrentPlayer().onVideoPause();
            isPause = true;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (detailPlayer != null) {
            detailPlayer.getCurrentPlayer().onVideoResume(false);
            isPause = false;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (detailPlayer != null) {
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
        if (detailPlayer != null) {
            //如果旋转了就全屏
            if (isPlay && !isPause) {
                detailPlayer.onConfigurationChanged(this, newConfig, orientationUtils, true, true);
            }
        }
    }


    void showBuyPopwindow() {
        View view = getLayoutInflater().inflate(R.layout.order_popwindow_buy, null);
        RelativeLayout rltAdd = view.findViewById(R.id.rlt_add);
        RelativeLayout rltSubtract = view.findViewById(R.id.rlt_subtract);
        TextView tvCount = view.findViewById(R.id.tv_count);
        TextView tvInitPrice = view.findViewById(R.id.tv_initial_price);
        tvInitPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        rltAdd.setOnClickListener(v -> {
            int count = Integer.valueOf(tvCount.getText().toString());
            if (count < 10) {
                count++;
            }
            tvCount.setText(count + "");
        });
        rltSubtract.setOnClickListener(v -> {
            int count = Integer.valueOf(tvCount.getText().toString());
            if (count > 1) {
                count--;
            }
            tvCount.setText(count + "");
        });


        final CustomPopWindow mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .size(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                .setView(view)
                .enableBackgroundDark(true)
                .setAnimationStyle(R.style.public_dialog_inout_anim)
                .create()
                .showAtLocation(this.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        view.findViewById(R.id.tv_buy).setOnClickListener(v -> {
            ARouter.getInstance().build(AppArouter.ORDER_ORDER_COMMIT_ACTIVITY).navigation();
            mCustomPopWindow.dissmiss();
        });
    }


    @OnClick({R2.id.value2, R2.id.tv_buy})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.tv_buy) {
            showBuyPopwindow();
        }
    }


    List<ImageInfo> getImgList(){
        String[] images = {
                "http://img6.16fan.com/201510/11/005258wdngg6rv0tpn8z9z.jpg",
                "http://img6.16fan.com/201510/11/013553aj3kp9u6iuz6k9uj.jpg",
                "http://img6.16fan.com/201510/11/011753fnanichdca0wbhxc.jpg",
                "http://img6.16fan.com/201510/11/011819zbzbciir9ctn295o.jpg",
        };
        ImageInfo imageInfo;
        final List<ImageInfo> imageInfoList = new ArrayList<>();
        for (String image : images) {
            imageInfo = new ImageInfo();
            // 原图地址
            imageInfo.setOriginUrl(image);
            // 缩略图；实际使用中，根据需求传入缩略图路径。如果没有缩略图url，可以将两项设置为一样。
            imageInfo.setThumbnailUrl(image.concat("-400"));
            imageInfoList.add(imageInfo);
        }
        return imageInfoList;
    }
}
