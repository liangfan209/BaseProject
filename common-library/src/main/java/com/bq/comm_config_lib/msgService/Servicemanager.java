package com.bq.comm_config_lib.msgService;

import android.content.Context;

import com.bq.comm_config_lib.annotation.Register;
import com.bq.utilslib.ScanClassUtils;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件名：
 * 描述：对注册消息的管理
 * 作者：梁帆
 * 时间：2020/6/16
 * 版权：
 */
public class Servicemanager {

    private static Servicemanager instance;
    List<Object> list = new ArrayList<>();
    private Servicemanager(){};

    public static Servicemanager getInstance(){
        if(instance == null){
            synchronized (Servicemanager.class){
                if(instance == null){
                    instance = new Servicemanager();
                }
            }
        }
        return  instance;
    }

    /**
     * 注册服务
     * @param context 上下文
     * @param packageStr 包名称
     */
    public void resiter(Context context,String packageStr){
        List<Class<?>> classes = ScanClassUtils.scanPackageClass(packageStr,context, Register.class);
        try {
            for (Class<?> aClass : classes) {
                Constructor<?> constructor =  aClass.getConstructor();
                Object obj = constructor.newInstance();
                EventBus.getDefault().register(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 取消服务
     */
    public void destroy(){
        for (Object o : list) {
            EventBus.getDefault().unregister(o);
        }
    }
}
