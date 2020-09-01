package com.bquan.app.api;

import android.Manifest;

import com.blankj.utilcode.util.StringUtils;
import com.bq.comm_config_lib.annotation.Register;
import com.bq.comm_config_lib.msgService.MessageBody;
import com.bq.comm_config_lib.msgService.MessageEvent;
import com.bq.comm_config_lib.utils.CommSpUtils;
import com.bq.utilslib.AppUtils;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.jiantao.android.locate.LocationHelper;
import io.reactivex.functions.Consumer;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/22
 * 版权：
 */
@Register
public class LoginConfigProvider {

    String mCity = "";

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getLoginConfig(MessageEvent event) {
        if ("config/login".equals(event.key)) {
            String configStr = AppUtils.getAssetJson(AppUtils.getApp(), "login_login_config.json");
            event.eventInterface.callBack(new MessageBody(MessageBody.SUCCESS_CODE, configStr));
        } else if ("location".equals(event.key)) {
            if(StringUtils.isEmpty(mCity)){
                LocationHelper locateHelper = new LocationHelper.Builder(event.activity)
                        .setScanSpan(0)
                        .setIsNeedLocationDescribe(true).build();
                LocationHelper.LocationListener mLocationListener = new LocationHelper.LocationListener() {
                    @Override
                    public void onReceiveLocation(LocationHelper.LocationEntity location) {
                        System.out.println("====***===>" + location.getCity());
                        mCity = location.getCity();
                        if(StringUtils.isEmpty(mCity)){
                            mCity = "武汉市";
                        }
                        CommSpUtils.saveLocaltion(mCity);
                        event.eventInterface.callBack(new MessageBody(MessageBody.SUCCESS_CODE, mCity));
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println(" throwable " + e);
                    }
                };
                locateHelper.registerLocationListener(mLocationListener);
                new RxPermissions(event.activity)
                        .request(Manifest.permission.ACCESS_FINE_LOCATION)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    locateHelper.start();
                                } else {
                                    ToastUtils.showToast(event.activity, "请打开定位权限");
                                }
                            }
                        });
            }else{
                CommSpUtils.saveLocaltion(mCity);
                event.eventInterface.callBack(new MessageBody(MessageBody.SUCCESS_CODE, mCity));
            }
        }
    }
}
