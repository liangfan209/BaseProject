package com.fan.baseuilibrary.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.bq.baselibary.BaseApplication;
import com.bumptech.glide.request.RequestOptions;
import com.fan.baseuilibrary.R;
import com.hjq.xtoast.XToast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import me.jessyan.autosize.utils.LogUtils;


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

    public static int getRemainData(String data) {
        long nowLong = convert2long(getStringDateYmd(System.currentTimeMillis()) + " 00:00:00");
        long chaLong = nowLong - convert2long(data);
        System.out.println(chaLong);
        System.out.println((1000 * 3600 * 24));
        int days = (int) (chaLong / (1000 * 3600 * 24));
        return days;
    }



    public static long convert2long(String date) {
        try {
            String format = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat sf = new SimpleDateFormat(format);
            return sf.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0l;
    }

    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd
     */
    public static String getStringDateYmd(long time) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(new Date(time));
        return dateString;
    }

    /**
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDateYmdHms(long time) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(new Date(time));
        return dateString;
    }

    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate(long time) {

        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
        String dateString = formatter.format(new Date(time));
        return dateString;
    }


    public static RequestOptions getRequestOptionRadus(Context context, int dp){
        RoundCornersTransformation transformation =
                new RoundCornersTransformation(context,
                        dp2px(context,dp),
                        RoundCornersTransformation.CornerType.ALL);
        return new RequestOptions().transform(transformation)
                .placeholder(R.mipmap.placeholder_small_pic)
                .fallback(R.mipmap.placeholder_small_pic);
    }


    public static RequestOptions getRequestOption() {



        return new RequestOptions()
                .placeholder(R.mipmap.placeholder_small_pic)
                .fallback(R.mipmap.placeholder_small_pic);

    }

    public static RequestOptions getRequestOption(int recourse) {
        return new RequestOptions()
                .placeholder(recourse)
                .fallback(recourse);

    }

    public static String getYmdTime(String time) {
        if (!TextUtils.isEmpty(time)) {
            if (time.length() >= 10) {
                return time.substring(5, 10);
            }
        }
        return "";
    }

    /**
     * 获取"2019-08-30 00:00:14" 年月日
     *
     * @param time
     * @return
     */
    public static String getYmd(String time) {
        if (!TextUtils.isEmpty(time)) {
            if (time.length() >= 10) {
                return time.substring(0, 10);
            }
        }
        return "";
    }

    /**
     * 判断是否是当年
     *
     * @param time "2019-09"
     * @return
     */
    public static boolean isCurrentYear(String time) {
        boolean b = false;
        if (!TextUtils.isEmpty(time)) {
            if (time.length() >= 4) {
                String year = time.substring(0, 4);
                Calendar date = Calendar.getInstance();
                String sysYear = String.valueOf(date.get(Calendar.YEAR));
                if (year.equals(sysYear)) {
                    b = true;
                }
            }
        }
        return b;
    }

    /**
     * 判断是否是当前月
     *
     * @param time "2019-09"
     * @return
     */
    public static boolean isCurrentMonth(String time) {
        boolean b = false;
        //判断是不是当年
        if (!isCurrentYear(time)) {
            return b;
        }

        if (!TextUtils.isEmpty(time)) {
            if (time.length() >= 5) {
                String month = time.substring(5);
                String substring = month.substring(0, 1);
                if ("0".equals(substring)) {
                    month = month.replaceAll("0", "");
                }
                Calendar date = Calendar.getInstance();
                String sysMonth = String.valueOf(date.get(Calendar.MONTH) + 1);

                if (month.equals(sysMonth)) {
                    b = true;
                }
            }
        }
        return b;
    }

    /**
     * 判断是否是当年
     *
     * @param time "2019-09"
     * @return
     */
    public static String getCurrentMonth(String time) {
        if (!TextUtils.isEmpty(time)) {
            if (time.length() >= 5) {
                String month = time.substring(5);
                return month;
            }
        }
        return "";
    }


    public static void main(String[] args) {
//        String str = "app://home/message?type=3&key=2/msgDetail?id=123";
        System.out.println(isCurrentMonth("2019-09"));
    }

    public static String getYmdAllTime(String time) {
        if (!TextUtils.isEmpty(time)) {
            if (time.length() >= 10) {
                return time.substring(0, 10);
            }
        }
        return "";
    }


    public static String getNotNumValue(String value) {
        if (!TextUtils.isEmpty(value)) {
            return value;
        } else {
            return "****";
        }
    }

    public static String getDefaultGang(String value) {
        if (!TextUtils.isEmpty(value)) {
            return value;
        } else {
            return "- - - -";
        }
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


    /**
     * 判断连接有几级，2判断连接有
     */
    public static String getLocalLink(int i, String str) {
        String content = str.substring(6);
        String[] split = content.split("/");
        if (split.length > i) {
            return split[i];
        } else {
            return "-1";
        }
    }

    public static int getOneParmar(String str, int defaultKey) {
        if (str != null && str.contains("=")) {
            String[] values = str.split("=");
            try {
                defaultKey = Integer.valueOf(values[1]);
            } catch (Exception e) {
                e.printStackTrace();
                LogUtils.e(values[0]);
            }
        }
        return defaultKey;
    }


    /**
     * 获取当前app version code
     */
    public static int getAppVersionCode(Context context) {
        int appVersionCode = 0;
        try {
            PackageInfo packageInfo =
                    context.getApplicationContext()
                            .getPackageManager()
                            .getPackageInfo(context.getPackageName(), 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                appVersionCode = (int) packageInfo.getLongVersionCode();
            } else {
                appVersionCode = packageInfo.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appVersionCode;
    }

    /**
     * 获取当前app version name
     */
    public static String getAppVersionName(Context context) {
        String appVersionName = "";
        try {
            PackageInfo packageInfo =
                    context.getApplicationContext()
                            .getPackageManager()
                            .getPackageInfo(context.getPackageName(), 0);
            appVersionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appVersionName;
    }






    public static int dp2px(Context context, final float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public static int dp2px(final float dpValue) {
        final float scale = BaseApplication.baseApplication.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 去掉默认动画
     *
     * @param mRvCustomer
     */
    public static void closeDefaultAnimator(RecyclerView mRvCustomer) {
        if (null == mRvCustomer) return;
        mRvCustomer.getItemAnimator().setAddDuration(0);
        mRvCustomer.getItemAnimator().setChangeDuration(0);
        mRvCustomer.getItemAnimator().setMoveDuration(0);
        mRvCustomer.getItemAnimator().setRemoveDuration(0);
        ((SimpleItemAnimator) mRvCustomer.getItemAnimator()).setSupportsChangeAnimations(false);
    }


    /**
     * 隐藏软键盘(只适用于Activity，不适用于Fragment)
     */
    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 隐藏软键盘(可用于Activity，Fragment)
     */
    public static void hideSoftKeyboard(Context context, List<View> viewList) {
        if (viewList == null) return;

        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);

        for (View v : viewList) {
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    //传入YYYY-MM　格式判断是否是当月
    public static String getCustomMonth(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        String StringDate1= format.format(date);
        Date date2 = new Date();
//        SimpleDateFormat format2 = new SimpleDateFormat("yyyy年MM月");
        String StringDate2= format.format(date2);
        if(StringDate1.equals(StringDate2)){
            return "本月";
        }else{
//           return format2.format(date);
           return StringDate1;
        }
    }

    //
    public static String[] getFilterTime(String selectDate){
        if(TextUtils.isEmpty(selectDate)){
            return null;
        }
        String[] strs = new String[2];
        if(selectDate.contains("至")){
            strs[0] = selectDate.split("至")[0].trim()+" 00:00:00";
            strs[1] = selectDate.split("至")[1].trim()+" 23:59:59";
        }else{
            Calendar calendar = Calendar.getInstance();
            int date1 =  calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            if(selectDate.length() == 7){
                strs[0] =  selectDate+"-01 00:00:00";
                strs[1] =  selectDate+"-"+date1+" 23:59:59";
            }else if(selectDate.length() == 10){
                strs[0] =  selectDate+" 00:00:00";
                strs[1] =  selectDate+" 23:59:59";
            }
        }
        return strs;
    }

    //传入一个月份，得到该月的第一天和最后一天
    public static String[] getCustomMonthStrs(Date date){
        if(date == null){
            date = new Date();
        }
        String[] strs = new String[2];
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
//        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        String StringDate1= format1.format(date);
//        String StringDate2= format2.format(date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int date1 =  calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println("==========================date = "+ date1);

        strs[0] =  StringDate1+"-01 00:00:00";
        strs[1] =  StringDate1+"-"+date1+" 23:59:59";
        return strs;
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
