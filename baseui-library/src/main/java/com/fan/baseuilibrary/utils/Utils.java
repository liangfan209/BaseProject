package com.fan.baseuilibrary.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

import com.fan.baseuilibrary.R;
import com.hjq.xtoast.XToast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import androidx.annotation.NonNull;


/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2019/8/1
 * 版权：
 */
public class Utils {


    @NonNull
    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


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




    /**
     * 将double转换为String
     */
    public static String getDouble2(double value){
        return String.format("%.2f", value);
    }


    /**
     * 粘贴板
     * @param activity
     * @param content
     */
    public static void clipBoard(Activity activity,String content){
        ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("simple text", content);
        clipboard.setPrimaryClip(clip);
    }

}
