package com.bq.comm_config_lib.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

import androidx.annotation.ColorRes;

/**状态栏适配
 */

public class MyStatusBarUtil {
    /**
     * 设置状态栏颜色，默认fitWindow为true
     * @param activity
     * @param colorResId
     */
    public static void setStatusBarColor(Activity activity,int colorResId){
        setStatusBarTranslucent(activity,colorResId,true,true);
    }

    //设置透明状态栏，无需设置fitWindow 默认黑色
    public static void setStatusBarTransparent(Activity activity,boolean fitWindow){
        setStatusBarTranslucent(activity,0,fitWindow);
        if(isFlymeV4OrAbove()){
            setMeizuStatusBar(activity.getWindow(),true);
        }else  if(isMiUIV6OrAbove()){
            //小米魅族手机设置
            setXiaomiStatusBar(activity.getWindow(),true);
        }
    }

    /**
     * 设置透明状态栏
     * @param activity
     * @param fitWindow
     * @param isDark 字体颜色 黑/白
     */
    public static void setStatusBarTransparent(Activity activity,boolean fitWindow,boolean isDark){
        setStatusBarTranslucent(activity,0,fitWindow,isDark);
    }

    /**
     * 设置状态栏颜色，默认fitWindow为true,字体默认黑色
     * @param activity
     * @param colorResId
     */
    public static void setStatusBarTranslucent(Activity activity, @ColorRes int colorResId, boolean fitWindow) {
        setStatusBarTranslucent(activity,colorResId,fitWindow,true);
    }
    /**
     * 设置状态栏颜色，默认fitWindow为true,字体默认黑色
     * @param activity
     * @param colorResId 状态栏颜色
     * @param fitWindow
     * @param isDark 字体颜色 黑/白
     */
    public static void setStatusBarTranslucent(Activity activity, @ColorRes int colorResId, boolean fitWindow,boolean isDark) {
        if (activity == null) {
            return;
        }
        Window window = activity.getWindow();
        int color;
        if(colorResId == Color.TRANSPARENT){
            color = Color.TRANSPARENT;
        }else {
            color = activity.getResources().getColor(colorResId);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0以上
            //5.0-6.0设置半透明
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M && !isFlymeV4OrAbove() && !isMiUIV6OrAbove()){
//                color = Color.parseColor("#50000000");
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M && !isFlymeV4OrAbove() && !isMiUIV6OrAbove()){
//                color = Color.parseColor("#50000000");
//            }
                window.setStatusBarColor(color);
                ViewGroup contentView = ((ViewGroup) activity.findViewById(android.R.id.content));
                View childAt = contentView.getChildAt(0);
                if (childAt != null) {
                    childAt.setFitsSystemWindows(fitWindow);
                }
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {//4.4-5.0
//            if(color != -100) {
            //透明状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            //设置contentview为fitsSystemWindows
            ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
            View childAt = contentView.getChildAt(0);
            if (childAt != null) {
                childAt.setFitsSystemWindows(fitWindow);
            }
            //给statusbar着色
            View view = new View(activity);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity)));
            view.setBackgroundColor(color);
            contentView.addView(view);
        }

        if(isFlymeV4OrAbove()){
            setMeizuStatusBar(activity.getWindow(),isDark);
        }else  if(isMiUIV6OrAbove()){
            //小米魅族手机设置
            setXiaomiStatusBar(activity.getWindow(),isDark);
        }else {
            StatusBarUtil.setLightStatusBar(activity, isDark);
        }
    }

    // 是否是小米手机
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static boolean isMiUIV6OrAbove() {
        try {
            final Properties properties = new Properties();
            properties.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
            String uiCode = properties.getProperty(KEY_MIUI_VERSION_CODE, null);
            if (uiCode != null) {
                int code = Integer.parseInt(uiCode);
                return true;
            } else {
                return false;
            }

        } catch (final Exception e) {
            return false;
        }

    }

    // 是否是魅族手机
    private static boolean isFlymeV4OrAbove() {
        String displayId = Build.DISPLAY;
        if (!TextUtils.isEmpty(displayId) && displayId.contains("Flyme")) {
            String[] displayIdArray = displayId.split(" ");
            for (String temp : displayIdArray) {
                //版本号4以上，形如4.x.
                if (temp.matches("^[4-9]\\.(\\d+\\.)+\\S*")) {
                    return true;
                }
            }
        }
        return false;
    }

    // 设置小米状态栏
    private static boolean setXiaomiStatusBar(Window window, boolean isLightStatusBar) {
        Class<? extends Window> clazz = window.getClass();
        try {
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            //状态栏透明且黑色字体//清除黑色字体
            extraFlagField.invoke(window, isLightStatusBar ? darkModeFlag : 0, darkModeFlag);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                if (isLightStatusBar) {
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                } else {
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 设置魅族状态栏
    private static boolean setMeizuStatusBar(Window window, boolean isLightStatusBar) {
        WindowManager.LayoutParams params = window.getAttributes();
        try {
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(params);
            if (isLightStatusBar) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(params, value);
            window.setAttributes(params);
            darkFlag.setAccessible(false);
            meizuFlags.setAccessible(false);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    private static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    private static void setAndroidNativeLightStatusBar(Activity activity, boolean dark) {
        View decor = activity.getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }
}
