package com.clkj.app;

import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.bq.comm_config_lib.BaseApplication;
import com.bq.comm_config_lib.msgService.Servicemanager;
import com.bq.comm_config_lib.request.Api;
import com.fan.baseuilibrary.utils.provinces.CityUtils;
import com.fan.baseuilibrary.view.MyClassicsHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.wind.me.xskinloader.SkinInflaterFactory;

import androidx.annotation.NonNull;
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

    //static 代码段可以防止内存泄露
    static {
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                ClassicsHeader.REFRESH_HEADER_PULLDOWN = "下拉刷新";
                ClassicsHeader.REFRESH_HEADER_REFRESHING = "刷新中...";
                ClassicsHeader.REFRESH_HEADER_LOADING = "刷新中...";
                ClassicsHeader.REFRESH_HEADER_RELEASE = "释放即可刷新";
                ClassicsHeader.REFRESH_HEADER_FINISH = "刷新完成";
                ClassicsHeader.REFRESH_HEADER_FAILED = "刷新失败";

                layout.setPrimaryColorsId(R.color.white, R.color.color_999999);//全局设置主题颜色
                MyClassicsHeader header = new MyClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
//                header.setAccentColor(context.getResources().getColor(R.color.white));
                header.setBackgroundColor(context.getResources().getColor(R.color.ui_txt_hint_color));
                header.getTitleText().setTextSize(12);
                header.getLastUpdateText().setVisibility(View.GONE);
                header.setProgressBitmap(null);
                header.getProgressView().setVisibility(View.GONE);
                header.setArrowBitmap(null);
                header.setEnableLastTime(false);
                return header;
            }
        });

        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
//                return new ClassicsFooter(context).setDrawableSize(20);

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
        });
    }


        {
        PlatformConfig.setWeixin("wx8aa0090b4ffd643f", "06312ddcbfb17e74d213759db95dc34a"); //06312ddcbfb17e74d213759db95dc34a
        PlatformConfig.setQQZone("123", "123");
    }

    private Servicemanager mServicemanager;

    @Override
    public void onCreate() {
        //1.super是完成路由
        super.onCreate();
        //2.注册组件中暴露的服务
        Servicemanager.getInstance().resiter(this,"com.clkj");

        baseUrl = Api.BASE_API+ Api.INTER;

        // android 7.0系统拍照兼容
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }

        //友盟
        UMConfigure.setLogEnabled(true);
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE,"");
        UMConfigure.init(this, "5f87a90880455950e4aa4a0f","umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
//        应用名称：澄鹿科技
//        平台：Android
//        Appkey：5f87a90880455950e4aa4a0f
//        SDK下载地址：https://developer.umeng.com/sdk

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
//
        SkinCompatManager.getInstance().loadSkin("appskin-debug.apk", null,
                SkinCompatManager.SKIN_LOADER_STRATEGY_ASSETS);

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
        }, getApplicationContext(),  "IACnwkbfdoNZGW6YCQ22uEmn", "25guL3AnIw1kMfwfrwHSxgeGLTPsDSQ3");
        //"RXTHlx7rBgksHVk3sdC0iYpX", "1mG2sIZsIFX89EGjqpNWSXhs0or7EqCg"
    }

}
