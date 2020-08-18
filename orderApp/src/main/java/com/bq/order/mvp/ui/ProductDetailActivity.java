package com.bq.order.mvp.ui;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.StringUtils;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.comm_config_lib.utils.BitImageUtils;
import com.bq.comm_config_lib.utils.CommSpUtils;
import com.bq.comm_config_lib.utils.Utils;
import com.bq.order.R;
import com.bq.order.R2;
import com.bq.order.mvp.presenter.ProductPresenter;
import com.bq.order.mvp.ui.adapter.BannerAdapter;
import com.bq.order.mvp.ui.hodler.NewTypeViewHolder;
import com.bq.order.requset.bean.BannerData;
import com.bq.order.requset.bean.ProductInfo;
import com.bq.order.requset.bean.SpecificationList;
import com.bq.utilslib.AppUtils;
import com.bq.utilslib.BitmapUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.fan.baseuilibrary.view.CustomPopWindow;
import com.fan.baseuilibrary.view.FlowRadioGroup;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
public class ProductDetailActivity extends BaseActivity implements ProductIview {

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
    @BindView(R2.id.value1)
    TextView mValue1;
    @BindView(R2.id.key2)
    TextView mKey2;
    @BindView(R2.id.value2)
    TextView mValue2;
    @BindView(R2.id.tv_buy)
    TextView mTvBuy;


//    @BindView(R2.id.iv_product_mark)
//    ImageView mIvProductMark;


    @BindView(R2.id.tv_month_quantity)
    TextView mTvMonthQuantity;
    @BindView(R2.id.tv_product_title)
    TextView mTvProductTitle;
    @BindView(R2.id.tv_product_remark)
    TextView mTvProductRemark;

    @BindView(R2.id.llt_imgs)
    LinearLayout mLltImgs;


    @BindView(R2.id.tv_index)
    TextView mTvIndex;
    @BindView(R2.id.rb_video)
    SkinCompatRadioButton mRbVideo;
    @BindView(R2.id.rb_img)
    SkinCompatRadioButton mRbImg;
    @BindView(R2.id.rg_video)
    SkinCompatRadioGroup mRgVideo;
//    private BannerViewPager mViewPager;


    @Autowired
    String mProductId;
    private ProductInfo mProductInfo;

    OrientationUtils orientationUtils;
    StandardGSYVideoPlayer detailPlayer;
    boolean isPause = true;
    boolean isPlay = true;
    private ProductPresenter mProductPresenter;


    @Override
    protected int getContentViewLayout() {
        return R.layout.order_activity_product_detail;
    }

    @Override
    protected BasePresenter createPersenter() {
        return mProductPresenter = new ProductPresenter(this);
    }

    @Override
    public void getProductDetailView(ProductInfo info) {
        this.mProductInfo = info;
        createViewHolder();
    }

    @Override
    protected void attach() {
        ARouter.getInstance().inject(this);
        mTvTitle.setText("助学详情");
        //创建viewhoder
        mTvInitialPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        mProductPresenter.getProductDetail(mProductId);
    }

    private void initView() {
        mValue1.setText(mProductInfo.getBrand_name());
        mValue2.setText(mProductInfo.getProduction_name());
        String despatch_type = mProductInfo.getDespatch_type();
        mTvShippingValue.setText(mProductInfo.getDespatch_type());
        mTvMonthQuantity.setText("月销" + mProductInfo.getMonth_quantity());
        mTvProductTitle.setText(mProductInfo.getTitle());
        mTvRealPrice.setText(AppUtils.getDouble2(mProductInfo.getMin_price()));
        mTvProductRemark.setText(mProductInfo.getDescription());

        if( mProductInfo.getSpecification_list().size() > 0){
            List<SpecificationList.SpecificationValueList> specification_value_list =
                    mProductInfo.getSpecification_list().get(0).getSpecification_value_list();
            StringBuilder sb = new StringBuilder();
            for (int i1 = 0; i1 < specification_value_list.size(); i1++) {
                sb.append(specification_value_list.get(i1).getAttribute());
                if (i1 != specification_value_list.size() - 1) {
                    sb.append("/");
                }
            }
            mTvTypeValue.setText(sb.toString());
        }
        List<String> detail = mProductInfo.getDetail();
        for (int i = 0; i < detail.size(); i++) {
            ImageView iv = new ImageView(this);
            Utils.showImage(mProductInfo.getDetail().get(i),iv);
            mLltImgs.addView(iv);
        }
    }

    private void createViewHolder() {
        initView();
        BannerAdapter homeAdapter = new BannerAdapter(new NewTypeViewHolder.HolderInter() {
            @Override
            public void createVideo(StandardGSYVideoPlayer palyer) {
                createVideoView(palyer);
            }
        });
        mRgVideo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(!StringUtils.isEmpty(mProductInfo.getVideo_display())){
                    if (checkedId == R.id.rb_video) {
                        mBannerView.setCurrentItem(0);
                    } else if (checkedId == R.id.rb_img) {
                        mBannerView.setCurrentItem(1);
                    }
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
                        if(!StringUtils.isEmpty(mProductInfo.getVideo_display())){
                            if (position != 0) {
                                BitImageUtils.showBigImgPreview(ProductDetailActivity.this, position - 1, getImgList(),
                                        new BitImageUtils.PageSelect() {
                                            @Override
                                            public void onPageSelected(int postion) {
                                                mBannerView.setCurrentItem(position + 1);
                                            }
                                        });
                            }
                        }else{
                            BitImageUtils.showBigImgPreview(ProductDetailActivity.this, position, getImgList(),
                                    new BitImageUtils.PageSelect() {
                                        @Override
                                        public void onPageSelected(int postion) {
                                            mBannerView.setCurrentItem(position);
                                        }
                                    });
                        }
                    }
                })
                .registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        if (position == 0 && !StringUtils.isEmpty(mProductInfo.getVideo_display())) {
                            mRbVideo.setChecked(true);
                            mRbImg.setChecked(false);
                            mTvIndex.setVisibility(View.GONE);
                        } else {
                            mRbVideo.setChecked(false);
                            mRbImg.setChecked(true);
                            mTvIndex.setVisibility(View.VISIBLE);
                            if(!StringUtils.isEmpty(mProductInfo.getVideo_display())){
                                mTvIndex.setText(position + "/" + mProductInfo.getSlideshow().size());
                            }else{
                                mRbVideo.setVisibility(View.GONE);
                                mTvIndex.setText((position+1) + "/" + mProductInfo.getSlideshow().size());
                            }
                        }
                    }
                }).create();


        ArrayList<BannerData> dataList = new ArrayList<BannerData>();


        if(StringUtils.isEmpty(mProductInfo.getVideo_display())){
            mRgVideo.setVisibility(View.GONE);
        }else{
            mRgVideo.setVisibility(View.VISIBLE);
            BannerData b1 = new BannerData("https://www.wanandroid.com/blogimgs/90c6cc12-742e-4c9f-b318-b912f163b8d0.png",
            BannerData.TYPE_NEW);
            dataList.add(b1);

        }


        List<String> slideshow = mProductInfo.getSlideshow();
        for (int i = 0; i < slideshow.size(); i++) {
            dataList.add(new BannerData(slideshow.get(i), 1));
        }
        mBannerView.refreshData(dataList);
    }

    private IIndicator setupIndicatorView() {
        FigureIndicatorView indicatorView = new FigureIndicatorView(this);
        indicatorView.setRadius(36);
        indicatorView.setTextSize(26);
        indicatorView.setBackgroundColor(Color.parseColor("#aaaaaaaa"));
        return indicatorView;
    }


    Handler mHandler;
    void createVideoView(StandardGSYVideoPlayer palyer) {
        detailPlayer = palyer;
        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, detailPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);
        GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
        final ImageView imageView = new ImageView(this);

        mHandler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                imageView.setImageBitmap((Bitmap) msg.obj);
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bm = Utils.getNetVideoBitmap(mProductInfo.getVideo_display());
                Message m = new Message();
                m.obj = bm;
                mHandler.sendMessage(m);
            }
        }).start();
        gsyVideoOption
                .setThumbImageView(imageView)
                .setIsTouchWiget(true)
//                .setRotateViewAuto(false)
                .setLockLand(false)
                .setAutoFullWithSize(true)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setUrl(mProductInfo.getVideo_display())
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
        if(mHandler != null){
            mHandler.removeCallbacksAndMessages(null);
        }
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


    private int mStock = 0;
    int position = 0;

    void showBuyPopwindow() {
        String token = CommSpUtils.getToken();
        if(StringUtils.isEmpty(token)){
            ARouter.getInstance().build(AppArouter.LOGIN_ACTVITY).withString("mPath","-1").navigation();
            return;
        }

        View view = getLayoutInflater().inflate(R.layout.order_popwindow_buy, null);
        final TextView tvBuySelcter = view.findViewById(R.id.tv_buy_select);
        final TextView tvRealPrice = view.findViewById(R.id.tv_real_price);
        final TextView tvBuyStock = view.findViewById(R.id.tv_buy_stock);
        final ImageView ivIcon = view.findViewById(R.id.iv_item);
        final TextView tvBuy = view.findViewById(R.id.tv_buy);

        RelativeLayout rltAdd = view.findViewById(R.id.rlt_add);
        RelativeLayout rltSubtract = view.findViewById(R.id.rlt_subtract);
        TextView tvCount = view.findViewById(R.id.tv_count);

        tvRealPrice.setText(AppUtils.getDouble2(mProductInfo.getMin_price()));

        FlowRadioGroup fg = view.findViewById(R.id.frg);
        FlowRadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,
                RadioGroup.LayoutParams.WRAP_CONTENT);
        int marginInt = AppUtils.dp2px(this, 7);
        params.setMargins(marginInt, marginInt, marginInt, 0);
        final List<SpecificationList> specification_list = mProductInfo.getSpecification_list();

        if(specification_list.size() == 0){
            tvBuy.setEnabled(false);
        }
        for (int i = 0; i < specification_list.size(); i++) {
            final RadioButton rb = (RadioButton) LayoutInflater.from(this).inflate(R.layout.order_layout_radiobutton, null);
            List<SpecificationList.SpecificationValueList> specificationValueList =
                    specification_list.get(i).getSpecification_value_list();
            Glide.with(this).asBitmap().apply(Utils.getRequestOptionRadus(rb.getContext(), 3))
                    .load(specification_list.get(i).getShow_image())// Api.BASE_API + specification_list.get(i).getShow_image())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            resource = BitmapUtils.scaleBitmap(resource, AppUtils.dp2px(rb.getContext(), 40),
                                    AppUtils.dp2px(rb.getContext(), 40));
                            rb.setCompoundDrawablesWithIntrinsicBounds(new BitmapDrawable(resource), null, null, null);
                        }
                    });
//   rb.setCompoundDrawablesWithIntrinsicBounds(null,null,new BitmapDrawable(resource),null);
            StringBuilder sb = new StringBuilder();

            for (int i1 = 0; i1 < specificationValueList.size(); i1++) {
                sb.append(specificationValueList.get(i1).getAttribute());
                if (i1 != (specificationValueList.size()-1)) {
                    sb.append("/");
                }
            }
            rb.setText(sb.toString());
            if(!StringUtils.isEmpty(mTvTypeValue.getText().toString())){
                if(mTvTypeValue.getText().toString().equals(sb.toString())){
                    tvBuySelcter.setText("已选择：“"+sb.toString()+"”");
                    mStock = specification_list.get(i).getStock();
                    tvBuyStock.setText("库存 "+mStock+" 件");
                    Utils.showImage(specification_list.get(i).getShow_image(),ivIcon);
                    mProductInfo.setAttrubute(sb.toString());
                    mProductInfo.setImgPath(specification_list.get(i).getShow_image());
                    mProductInfo.setRealPrice(specification_list.get(i).getSale_price());
                    position = i;
                    mProductInfo.setSelectPosition(position);
                    tvBuy.setEnabled(mStock <= 0?false:true);

                }
            }
            fg.addView(rb, params);
        }
        if( ((RadioButton)fg.getChildAt(position)) != null){
            ((RadioButton)fg.getChildAt(position)).setChecked(true);
        }


        fg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbt = group.findViewById(checkedId);
                position = group.indexOfChild(rbt);
                //根据当前选择的内容，
                SpecificationList specificationList = specification_list.get(position);
                //得到库存
                mStock = specificationList.getStock();
                tvBuyStock.setText("库存 "+mStock+" 件");
                tvBuy.setEnabled(mStock <= 0?false:true);
                tvRealPrice.setText(AppUtils.getDouble2(specificationList.getSale_price()));
                mTvTypeValue.setText(rbt.getText().toString());
                tvBuySelcter.setText("已选择：“"+rbt.getText().toString()+"”");
                tvCount.setText("1");
                tvBuy.setEnabled(mStock <= 0?false:true);
                mProductInfo.setAttrubute(specificationList.getShow_image());
                mProductInfo.setAttrubute(rbt.getText().toString());
                mProductInfo.setRealPrice(specificationList.getSale_price());
                mProductInfo.setSelectPosition(position);
                mProductInfo.setImgPath(specificationList.getShow_image());

                Utils.showImage(specificationList.getShow_image(),ivIcon);

            }
        });


        TextView tvInitPrice = view.findViewById(R.id.tv_initial_price);
        tvInitPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        rltAdd.setOnClickListener(v -> {
            int count = Integer.valueOf(tvCount.getText().toString());
            if (count < mStock) {
                count++;
                tvBuy.setEnabled(true);
            }else{
                //将按钮变为不可点击
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
        tvBuy.setOnClickListener(v -> {
            int count = Integer.valueOf(tvCount.getText().toString());
            mProductInfo.setCount(count);
            ARouter.getInstance().build(AppArouter.ORDER_ORDER_COMMIT_ACTIVITY)
                    .withSerializable("mProductInfo",mProductInfo).navigation();
//                    .withSerializable("mProductInfo",mProductInfo).navigation();
            mCustomPopWindow.dissmiss();


        });
    }


    @OnClick({R2.id.value2, R2.id.tv_buy, R2.id.rlt_type,R2.id.tv_save,R2.id.tv_server})
    public void onViewClicked(View view) {


        if (view.getId() == R.id.tv_buy) {
            if(!Utils.isFastDoubleClick(mTvBuy,1000)){
                showBuyPopwindow();
            }
        } else if (view.getId() == R.id.rlt_type) {
            if(!Utils.isFastDoubleClick(mRltType,1000)){
                showBuyPopwindow();
            }
        }else if(view.getId() == R.id.tv_save|| view.getId() == R.id.tv_server){
            ToastUtils.showToast(this,"正在开发中");
        }
    }


    List<ImageInfo> getImgList() {
        List<String> images = mProductInfo.getSlideshow();
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
