package com.fan.baseuilibrary.utils;

import android.app.Activity;
import android.content.Context;

import com.fan.baseuilibrary.R;
import com.hjq.xtoast.XToast;


/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2019/8/1
 * 版权：
 */
public class ToastUtils {


    public static void showToast303(Activity activity, String txt) {
        new XToast(activity)
                .setDuration(1500)
                .setView(R.layout.toast_hint)
                .setAnimStyle(android.R.style.Animation_Activity)
                .setImageDrawable(android.R.id.icon, R.mipmap.icon_network_error_303)
                .setText(android.R.id.message, txt)
                .show();
    }

    public static void showToast(Activity activity, int res){
        showToast(activity,activity.getResources().getString(res));
    }

    public static void showToast(Activity activity, String txt) {
        new XToast(activity)
                .setDuration(1500)
                .setView(R.layout.toast_hint)
                .setAnimStyle(android.R.style.Animation_Activity)
                .setImageDrawable(android.R.id.icon, R.mipmap.ic_dialog_tip_warning)
                .setText(android.R.id.message, txt)
                .show();
    }

    public static void showLongToast(Activity activity, String txt) {
        new XToast(activity)
                .setDuration(3000)
                .setView(R.layout.toast_hint)
                .setAnimStyle(android.R.style.Animation_Activity)
                .setImageDrawable(android.R.id.icon, R.mipmap.ic_dialog_tip_warning)
                .setText(android.R.id.message, txt)
                .show();
    }

    public static void showToastOk(Activity activity, String txt) {
        new XToast(activity)
                .setDuration(1000)
                .setView(R.layout.toast_hint)
                .setAnimStyle(android.R.style.Animation_Activity)
                .setImageDrawable(android.R.id.icon, R.mipmap.ic_dialog_tip_finish)
                .setText(android.R.id.message, txt)
                .show();
    }


    public static int dp2px(Context context, final float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }



}
