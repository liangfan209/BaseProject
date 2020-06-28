package com.bq.app;

import android.os.Build;
import android.os.StrictMode;
import android.view.LayoutInflater;

import com.bq.comm_config_lib.BaseApplication;
import com.bq.comm_config_lib.msgService.Servicemanager;
import com.fan.baseuilibrary.utils.provinces.CityUtils;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.wind.me.xskinloader.SkinInflaterFactory;
import com.wind.me.xskinloader.SkinManager;
import com.wind.me.xskinloader.util.AssetFileUtils;

import java.io.File;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/16
 * 版权：
 */
public class AppApplication extends BaseApplication {

    {
        PlatformConfig.setWeixin("wx22a8fc65e8d220af", "f90d0b596034a8c92ed578f9c9a7773a");
        PlatformConfig.setQQZone("123", "123");
    }

    private Servicemanager mServicemanager;

    @Override
    public void onCreate() {
        //1.super是完成路由
        super.onCreate();
        //2.注册组件中暴露的服务
        Servicemanager.getInstance().resiter(this,"com.bq");

        // android 7.0系统拍照兼容
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }
        //替换皮肤
//        changeSkin();

        //友盟
        UMConfigure.setLogEnabled(true);
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE,"");

        SkinInflaterFactory.setFactory(LayoutInflater.from(this));
        //初始化城市json
        new Thread(()->{
            CityUtils.getInstance(this);
        }).start();




    }

    //销毁组件中的服务
    public void destroyService(){
        if(mServicemanager != null){
            mServicemanager.destroy();
        }
        mServicemanager = null;
    }


    /**
     * 替换资源文件
     */
    private void changeSkin() {
        String saveDir = getCacheDir().getAbsolutePath() + "/skins";
        String savefileName = "/cc.skin";
        String asset_dir = "skin-apk-debug.apk";
        File file = new File(saveDir + File.separator + savefileName);
        if (!file.exists()) {
            AssetFileUtils.copyAssetFile(this, asset_dir, saveDir, savefileName);
        }
        SkinManager.get().loadSkin(file.getAbsolutePath());
    }
}
