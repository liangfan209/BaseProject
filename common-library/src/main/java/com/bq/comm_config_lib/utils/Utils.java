package com.bq.comm_config_lib.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.SizeUtils;
import com.bq.comm_config_lib.R;
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
        return String.format("%.2f", value);
    }


    /**
     *  拆分时间
     * @param time 2020-07-06 12:46:36
     * @return
     */
    public static int getMonthByTime(String time){
        String splitStr = time.substring(5,7);
        Integer integer = Integer.valueOf(splitStr);
        return integer;
    }

    public static int getYearByTime(String time){
        String splitStr = time.substring(0,4);
        Integer integer = Integer.valueOf(splitStr);
        return integer;
    }
}
