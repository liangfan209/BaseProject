package com.bq.app.register;

import java.util.HashMap;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/9
 * 版权：
 */
public class God {
    private HashMap<String, Component> mapping;

    public void add(Component component){
        mapping.put(component.getRoutePath(), component);
    }

    public void remove(Component component){
        mapping.remove(component.getRoutePath());
    }

    public void refresh(Component component){

    }

    public Component getComponent(String key){
        return mapping.get(key);
    }

    public HashMap<String, Component> getRegister(){
        return this.mapping;
    }
}
