package com.bq.baselibary;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.baselibary.config.BaseApplicationConfig;

import java.util.Set;

import androidx.multidex.MultiDex;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/5/28
 * 版权：
 */
public class BaseApplication extends Application {
    public static BaseApplication baseApplication;
    public static BaseApplicationConfig baseApplicationConfig;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        baseApplication = this;
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this);
        //反射manafest文件
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = getPackageManager().getApplicationInfo(getPackageName(),
                    PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Set<String> strings = applicationInfo.metaData.keySet();
        for (String key: strings) {
            if("test".equals(applicationInfo.metaData.get(key))){
                baseApplicationConfig = parseClass(key);
            }
        }
    }

    public <T> T parseClass(String name) {
        try {
            Class<?> aClass = Class.forName(name);
            Object o = aClass.newInstance();
            return (T) o;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

}
