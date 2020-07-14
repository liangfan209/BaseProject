package com.bq.app;

import android.os.Build;
import android.os.StrictMode;
import android.view.LayoutInflater;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.blankj.utilcode.util.LogUtils;
import com.bq.comm_config_lib.BaseApplication;
import com.bq.comm_config_lib.msgService.Servicemanager;
import com.fan.baseuilibrary.utils.provinces.CityUtils;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.wind.me.xskinloader.SkinInflaterFactory;
import com.wind.me.xskinloader.SkinManager;
import com.wind.me.xskinloader.util.AssetFileUtils;

import java.io.File;

import skin.support.SkinCompatManager;
import skin.support.app.SkinAppCompatViewInflater;
import skin.support.app.SkinCardViewInflater;
import skin.support.constraint.app.SkinConstraintViewInflater;
import skin.support.design.app.SkinMaterialViewInflater;

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

        baseUrl = "http://education.bq.com/interface/";

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

        SkinCompatManager.withoutActivity(this)
                .addInflater(new SkinAppCompatViewInflater())           // 基础控件换肤初始化
                .addInflater(new SkinMaterialViewInflater())            // material design 控件换肤初始化[可选]
                .addInflater(new SkinConstraintViewInflater())          // ConstraintLayout 控件换肤初始化[可选]
                .addInflater(new SkinCardViewInflater())                // CardView v7 控件换肤初始化[可选]
                .setSkinStatusBarColorEnable(false)                     // 关闭状态栏换肤，默认打开[可选]
                .setSkinWindowBackgroundEnable(false)                   // 关闭windowBackground换肤，默认打开[可选]
                .loadSkin();

        //初始化orc
        initAccessTokenWithAkSk();
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


    /**
     * 用明文ak，sk初始化
     */
    private void initAccessTokenWithAkSk() {
        OCR.getInstance(this).initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                String token = result.getAccessToken();
//                hasGotToken = true;
                LogUtils.e("token 获取成功");
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                LogUtils.e("token 获取失败");
//                ToastUtils.showShort("token 获取失败");
//                alertText("AK，SK方式获取token失败", error.getMessage());
            }
        }, getApplicationContext(),  "RXTHlx7rBgksHVk3sdC0iYpX", "1mG2sIZsIFX89EGjqpNWSXhs0or7EqCg");
    }

}
