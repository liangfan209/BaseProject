package com.bq.app.register;

import com.bq.app.base.BaseModel;
import com.bq.app.base.BaseProcess;
import com.bq.app.base.BaseView;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/9
 * 版权：
 */
public class Component {
    public BaseProcess process;
    public BaseView view;
    public BaseModel model;
    public String routePath;


    public void initailizeConfig(){
        process.initailizeConfig();
    }

    public String getRoutePath(){
        return this.routePath;
    }

    public void print(){
        System.out.println("print ---- >>>>>> " + this.routePath);
    }

}
