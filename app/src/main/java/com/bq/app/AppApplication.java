package com.bq.app;

import android.view.LayoutInflater;

import com.bq.comm_config_lib.BaseApplication;
import com.bq.comm_config_lib.msgService.Servicemanager;
import com.fan.baseuilibrary.utils.provinces.CityUtils;
import com.wind.me.xskinloader.SkinInflaterFactory;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/16
 * 版权：
 */
public class AppApplication extends BaseApplication {

    private Servicemanager mServicemanager;

    @Override
    public void onCreate() {
        //1.super是完成路由
        super.onCreate();
        //2.注册组件中暴露的服务
        Servicemanager.getInstance().resiter(this,"com.bq");

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
}
