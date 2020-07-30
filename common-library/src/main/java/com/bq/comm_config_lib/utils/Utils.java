package com.bq.comm_config_lib.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.SizeUtils;
import com.bq.comm_config_lib.R;
import com.bumptech.glide.request.RequestOptions;
import com.fan.baseuilibrary.utils.RoundCornersTransformation;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;


/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/29
 * 版权：
 */
public class Utils {
    public static ClassicsHeader  createFreshHeader(Context context){
        ClassicsHeader header = new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        ClassicsHeader.REFRESH_HEADER_PULLDOWN = "下拉刷新";
        ClassicsHeader.REFRESH_HEADER_REFRESHING = "刷新中...";
        ClassicsHeader.REFRESH_HEADER_LOADING = "刷新中...";
        ClassicsHeader.REFRESH_HEADER_RELEASE = "释放即可刷新";
        ClassicsHeader.REFRESH_HEADER_FINISH = "刷新完成";
        ClassicsHeader.REFRESH_HEADER_FAILED = "刷新失败";
        header.setAccentColor(context.getResources().getColor(R.color.color_999999));
        header.getTitleText().setTextSize(12);
        header.getLastUpdateText().setVisibility(View.GONE);
        header.setProgressBitmap(null);
        header.setEnableLastTime(false);
        header.setArrowBitmap(null);
        return header;
    }

    public static ClassicsFooter createFreshFooter(Context context){
        ClassicsFooter.REFRESH_FOOTER_LOADING="加载中...";
        ClassicsFooter footer = new ClassicsFooter(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        footer.setAccentColor(context.getResources().getColor(R.color.color_999999));
        footer.getTitleText().setTextSize(12);
        ViewGroup.LayoutParams layoutParams = footer.getProgressView().getLayoutParams();
        layoutParams.height = SizeUtils.sp2px(15);
        layoutParams.width = SizeUtils.sp2px(15);
        footer.getProgressView().setLayoutParams(layoutParams);
//        footer.setProgressBitmap(null);
        footer.setArrowBitmap(null);
        return footer;
    }

    public static String getDouble2(double value){
        value = value/100;
        return String.format("%.2f", value);
    }

    /**
     *  拆分时间
     * @param time 2020-07-06 12:46:36
     * @return
     */
    public static String getYearMonthByTime(String time){
        String splitStr = time.substring(0,7);
        return splitStr;
    }

    public static int getYearByTime(String time){
        String splitStr = time.substring(0,4);
        Integer integer = Integer.valueOf(splitStr);
        return integer;
    }


    private static long mLastClickTime;
    private static int mLastClickViewId;

    /**
     * 是否是快速点击
     *
     * @param v  点击的控件
     * @param intervalMillis  时间间期（毫秒）
     * @return  true:是，false:不是
     */
    public static boolean isFastDoubleClick(View v, long intervalMillis) {
        int viewId = v.getId();
        long time = System.currentTimeMillis();

        long timeInterval = Math.abs(time - mLastClickTime);
        if (timeInterval < intervalMillis && viewId == mLastClickViewId) {
            return true;
        } else {
            mLastClickTime = time;
            mLastClickViewId = viewId;
            return false;
        }
    }


//    public static RequestOptions getRequestOptionRadus(Context context, int dp){
//        RoundCornersTransformation transformation =
//                new RoundCornersTransformation(context,
//                        SizeUtils.dp2px(dp),
//                        RoundCornersTransformation.CornerType.ALL);
//        return new RequestOptions().transform(transformation)
//                .placeholder(R.mipmap.placeholder_small_pic)
//                .fallback(R.mipmap.placeholder_small_pic);
//    }

    public static RequestOptions getRequestOptionRadus(Context context, int dp){
        RoundCornersTransformation transformation =
                new RoundCornersTransformation(context,
                        SizeUtils.dp2px(dp),
                        RoundCornersTransformation.CornerType.ALL);
        RequestOptions requestOptions = Utils.getRequestOption();
        requestOptions.transform(transformation);
        return requestOptions;
    }


    public static RequestOptions getRequestOption() {
        return new RequestOptions()
                .placeholder(R.mipmap.placeholder_small_pic)
                .fallback(R.mipmap.placeholder_small_pic);

    }



}
