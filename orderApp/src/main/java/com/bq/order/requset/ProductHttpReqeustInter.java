package com.bq.order.requset;

import com.bq.comm_config_lib.request.RequestCallBackInter;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/17
 * 版权：
 */
public interface ProductHttpReqeustInter {
    //获取热门学校
    public void getHostSchoolList(String searchStr,RequestCallBackInter callBack);
    //获取搜索学校列表
    public void getSearchSchoolList(int currentPage,String searchStr,RequestCallBackInter callBack);
    //所有学校列表
    public void getSchoolAll(String searchStr,RequestCallBackInter callBack);
    //获取热门商品
    public void getHostProductList(String searchStr,RequestCallBackInter callBack);
    //产品所搜列表
    public void getSearchProductList(int currentPage,String searchStr,RequestCallBackInter callBack);
    //专业列表
    public void getProfessionAll(String searchInfo,RequestCallBackInter callBack);
    //学年列表
    public void getDurationAll(String searchInfo,RequestCallBackInter callBack);
}
