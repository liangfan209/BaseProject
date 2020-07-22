package com.bq.comm_config_lib.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.bq.comm_config_lib.R;

import java.util.List;

import cc.shinichi.library.ImagePreview;
import cc.shinichi.library.bean.ImageInfo;
import cc.shinichi.library.view.listener.OnBigImageClickListener;
import cc.shinichi.library.view.listener.OnBigImageLongClickListener;
import cc.shinichi.library.view.listener.OnBigImagePageChangeListener;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/22
 * 版权：
 */
public class BitImageUtils {

    public interface PageSelect{
        void onPageSelected(int postion);
    }

    public static void showBigImgPreview(Context context, int index, List<ImageInfo> list,final BitImageUtils.PageSelect inter){
        ImagePreview.getInstance()
                // 上下文，必须是activity，不需要担心内存泄漏，本框架已经处理好
                .setContext(context)
                // 从第几张图片开始，索引从0开始哦~
                .setIndex(index)

                //=================================================================================================
                // 有三种设置数据集合的方式，根据自己的需求进行三选一：
                // 1：第一步生成的imageInfo List
                .setImageInfoList(list)

                // 2：直接传url List
                //.setImageList(List<String> imageList)

                // 3：只有一张图片的情况，可以直接传入这张图片的url
                //.setImage(String image)
                //=================================================================================================

                // 加载策略，默认为手动模式
                .setLoadStrategy(ImagePreview.LoadStrategy.AlwaysThumb)

                // 保存的文件夹名称，会在Picture目录进行文件夹的新建。比如："BigImageView"，会在Picture目录新建BigImageView文件夹)
                .setFolderName("BigImageView")

                // 缩放动画时长，单位ms
                .setZoomTransitionDuration(300)

                // 是否显示加载失败的Toast
                .setShowErrorToast(false)

                // 是否启用点击图片关闭。默认启用
                .setEnableClickClose(true)
                // 是否启用下拉关闭。默认不启用
                .setEnableDragClose(false)
                // 是否启用上拉关闭。默认不启用
                .setEnableUpDragClose(false)
                // 是否显示关闭页面按钮，在页面左下角。默认不显示
                .setShowCloseButton(false)
                // 设置关闭按钮图片资源，可不填，默认为库中自带：R.drawable.ic_action_close
                .setCloseIconResId(R.drawable.ic_action_close)

                // 是否显示下载按钮，在页面右下角。默认显示
                .setShowDownButton(false)
                // 设置下载按钮图片资源，可不填，默认为库中自带：R.drawable.icon_download_new
                .setDownIconResId(R.drawable.icon_download_new)

                // 设置是否显示顶部的指示器（1/9）默认显示
                .setShowIndicator(true)
                // 设置顶部指示器背景shape，默认自带灰色圆角shape
                .setIndicatorShapeResId(R.drawable.shape_indicator_bg)

                // 设置失败时的占位图，默认为库中自带R.drawable.load_failed，设置为 0 时不显示
                .setErrorPlaceHolder(R.drawable.load_failed)

                // 点击回调
                .setBigImageClickListener(new OnBigImageClickListener() {
                    @Override public void onClick(Activity activity, View view, int position) {
                        // ...
                    }
                })
                // 长按回调
                .setBigImageLongClickListener(new OnBigImageLongClickListener() {
                    @Override public boolean onLongClick(Activity activity, View view, int position) {
                        // ...
                        return false;
                    }
                })
                // 页面切换回调
                .setBigImagePageChangeListener(new OnBigImagePageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    }

                    @Override public void onPageSelected(int position) {
                        inter.onPageSelected(position);
                    }

                    @Override public void onPageScrollStateChanged(int state) {
                    }
                })
                // 开启预览
                .start();
    }
}
