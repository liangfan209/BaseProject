package com.bq.app;

import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseFragment;
import com.bquan.app.R;

import butterknife.BindView;
import butterknife.OnClick;
import skin.support.SkinCompatManager;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/1/001
 * 版权：
 */
public class MainFragment extends BaseFragment {


    @BindView(R.id.bt_default)
    Button mBtDefault;
    @BindView(R.id.bt_red)
    Button mBtRed;
    @BindView(R.id.lottieAnimationView)
    LottieAnimationView mLottieAnimationView;

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected int getContentViewLayout() {
        return R.layout.fragment_main;
    }

    @Override
    protected void attach() {
        mLottieAnimationView.playAnimation();
        fun();
    }

    @OnClick({R.id.bt_default, R.id.bt_red})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_default:
                SkinCompatManager.getInstance().restoreDefaultTheme();
                break;
            case R.id.bt_red:
                SkinCompatManager.getInstance().loadSkin("appskin-debug.apk", null,
                        SkinCompatManager.SKIN_LOADER_STRATEGY_ASSETS);
                break;
        }
    }


    public void fun(){

//        String[] images = {
//                "http://img6.16fan.com/201510/11/005258wdngg6rv0tpn8z9z.jpg",
//                "http://img6.16fan.com/201510/11/013553aj3kp9u6iuz6k9uj.jpg",
//                "http://img6.16fan.com/201510/11/011753fnanichdca0wbhxc.jpg",
//                "http://img6.16fan.com/201510/11/011819zbzbciir9ctn295o.jpg",
//                "http://img6.16fan.com/201510/11/004847l7w568jc5n5wn385.jpg",
//                "http://img6.16fan.com/201510/11/004906z0a0a0e0hs56ce0t.jpg",
//                "http://img6.16fan.com/201510/11/004937pwttwjt0bgtoton7.jpg",
//                "http://img6.16fan.com/201510/11/004946t38ybzt8bq8c838y.jpg",
//                "http://img6.16fan.com/201510/11/004955d8ftz3t1sttt7ft7.jpg",
//                "http://img6.16fan.com/201510/11/005027qy2g55yyglb59zdu.jpg",
//                "http://img6.16fan.com/201510/11/005229bbtxkczcl0btmw8e.jpg",
//                // 下面这张是：5760 * 3840
//                "http://img6.16fan.com/attachments/wenzhang/201805/18/152660818127263ge.jpeg",
//                // 下面这张是：2280 * 22116
//                "http://img6.16fan.com/attachments/wenzhang/201805/18/152660818716180ge.jpeg"
//        };
//        ImageInfo imageInfo;
//        final List<ImageInfo> imageInfoList = new ArrayList<>();
//
//        for (String image : images) {
//            imageInfo = new ImageInfo();
//            // 原图地址
//            imageInfo.setOriginUrl(image);
//            // 缩略图；实际使用中，根据需求传入缩略图路径。如果没有缩略图url，可以将两项设置为一样。
//            imageInfo.setThumbnailUrl(image.concat("-400"));
//            imageInfoList.add(imageInfo);
//        }
//
//        // 测试超宽图
//        imageInfo = new ImageInfo();
//        imageInfo.setOriginUrl("http://cache.house.sina.com.cn/citylifehouse/citylife/de/26/20090508_7339__.jpg");
//        imageInfo.setThumbnailUrl("http://cache.house.sina.com.cn/citylifehouse/citylife/de/26/20090508_7339__.jpg");
//        imageInfoList.add(0, imageInfo);
//        // 测试gif图
//        imageInfo = new ImageInfo();
//        imageInfo.setOriginUrl("http://image.coolapk.com/feed/2019/0325/17/1105409_1553505598_4989@299x299.gif");
//        imageInfo.setThumbnailUrl("http://image.coolapk.com/feed/2019/0325/17/1105409_1553505598_4989@299x299.gif");
//        imageInfoList.add(0, imageInfo);
//        // 测试gif图
//        imageInfo = new ImageInfo();
//        imageInfo.setOriginUrl("http://image.coolapk.com/feed/2019/0716/15/1412643_6de29040_1600_6194@463x357.gif");
//        imageInfo.setThumbnailUrl("http://image.coolapk.com/feed/2019/0716/15/1412643_6de29040_1600_6194@463x357.gif");
//        imageInfoList.add(0, imageInfo);
//
//        ImagePreview.getInstance()
//                // 上下文，必须是activity，不需要担心内存泄漏，本框架已经处理好
//                .setContext(this.getActivity())
//                // 从第几张图片开始，索引从0开始哦~
//                .setIndex(0)
//
//                //=================================================================================================
//                // 有三种设置数据集合的方式，根据自己的需求进行三选一：
//                // 1：第一步生成的imageInfo List
//                .setImageInfoList(imageInfoList)
//
//                // 2：直接传url List
//                //.setImageList(List<String> imageList)
//
//                // 3：只有一张图片的情况，可以直接传入这张图片的url
//                //.setImage(String image)
//                //=================================================================================================
//
//                // 加载策略，默认为手动模式
//                .setLoadStrategy(ImagePreview.LoadStrategy.AlwaysThumb)
//
//                // 保存的文件夹名称，会在Picture目录进行文件夹的新建。比如："BigImageView"，会在Picture目录新建BigImageView文件夹)
//                .setFolderName("BigImageView")
//
//                // 缩放动画时长，单位ms
//                .setZoomTransitionDuration(300)
//
//                // 是否显示加载失败的Toast
//                .setShowErrorToast(false)
//
//                // 是否启用点击图片关闭。默认启用
//                .setEnableClickClose(false)
//                // 是否启用下拉关闭。默认不启用
//                .setEnableDragClose(false)
//                // 是否启用上拉关闭。默认不启用
//                .setEnableUpDragClose(false)
//                // 是否显示关闭页面按钮，在页面左下角。默认不显示
//                .setShowCloseButton(false)
//                // 设置关闭按钮图片资源，可不填，默认为库中自带：R.drawable.ic_action_close
//                .setCloseIconResId(R.drawable.ic_action_close)
//
//                // 是否显示下载按钮，在页面右下角。默认显示
//                .setShowDownButton(false)
//                // 设置下载按钮图片资源，可不填，默认为库中自带：R.drawable.icon_download_new
//                .setDownIconResId(R.drawable.icon_download_new)
//
//                // 设置是否显示顶部的指示器（1/9）默认显示
//                .setShowIndicator(true)
//                // 设置顶部指示器背景shape，默认自带灰色圆角shape
//                .setIndicatorShapeResId(R.drawable.shape_indicator_bg)
//
//                // 设置失败时的占位图，默认为库中自带R.drawable.load_failed，设置为 0 时不显示
//                .setErrorPlaceHolder(R.drawable.load_failed)
//
//                // 点击回调
//                .setBigImageClickListener(new OnBigImageClickListener() {
//                    @Override public void onClick(Activity activity, View view, int position) {
//                        // ...
//                    }
//                })
//                // 长按回调
//                .setBigImageLongClickListener(new OnBigImageLongClickListener() {
//                    @Override public boolean onLongClick(Activity activity, View view, int position) {
//                        // ...
//                        return false;
//                    }
//                })
//                // 页面切换回调
//                .setBigImagePageChangeListener(new OnBigImagePageChangeListener() {
//                    @Override
//                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                    }
//
//                    @Override public void onPageSelected(int position) {
//                    }
//
//                    @Override public void onPageScrollStateChanged(int state) {
//                    }
//                })
//                // 开启预览
//                .start();
    }
}
