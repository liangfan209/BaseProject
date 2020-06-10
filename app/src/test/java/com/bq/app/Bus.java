package com.bq.app;

import com.bq.app.login.LoginManage;
import com.bq.app.order.OrderManage;
import com.bq.app.register.Component;
import com.bq.app.register.God;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/9
 * 版权：
 */
public class Bus {
    private God god;

    public Bus() {
        this.god = new God();
    }

    private ArrayList<Component> getAppConfig(){
        ArrayList<Component> appList = new ArrayList<Component>();

        Component loginApp = new LoginManage();
        appList.add(loginApp);

        Component orderApp = new OrderManage();
        appList.add(orderApp);

        return appList;
    }

    private void loadApp(){
        ArrayList<Component> appList = this.getAppConfig();
        for (int i = 0; i < appList.size(); i++) {
            appList.get(i).initailizeConfig();
        }
        System.out.println("加载主线的app模块应用");
    }

    private void LoadThirdPackage(){
        System.out.println("加载主线的第三方包应用");
    }

    public void run(){
        this.LoadThirdPackage();
        this.loadApp();
    }

    public void printAppModuls(){
        HashMap<String, Component> mapping = this.god.getRegister();
        Set<Map.Entry<String, Component>> entries = mapping.entrySet();
        for (Map.Entry<String,Component> map: entries) {
            map.getValue().print();
        }
    }
}
