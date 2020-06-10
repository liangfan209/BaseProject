package com.bq.comm_config_lib;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

import androidx.multidex.MultiDex;

public class BaseApplication extends Application {
//    public static BaseApplicationConfig baseApplicationConfig;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this);


        //反射manafest文件
//        ApplicationInfo applicationInfo = null;
//        try {
//            applicationInfo = getPackageManager().getApplicationInfo(getPackageName(),
//                    PackageManager.GET_META_DATA);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        Set<String> strings = applicationInfo.metaData.keySet();
//        for (String key: strings) {
//            if("test".equals(applicationInfo.metaData.get(key))){
//                baseApplicationConfig = parseClass(key);
//            }
//        }
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
