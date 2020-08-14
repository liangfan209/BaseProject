package com.bq.order.mvp.ui;

import com.bq.comm_config_lib.mvp.BaseIView;
import com.bq.order.requset.bean.ContractInfo;
import com.bq.order.requset.bean.ProductInfo;
import com.bq.order.requset.bean.ProfessionInfo;
import com.bq.order.requset.bean.SchoolInfo;
import com.bq.order.requset.bean.SchoolProfessionInfo;
import com.bq.order.requset.bean.SelecterBean;

import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/31
 * 版权：
 */
public interface ProductIview extends BaseIView {
    default void getSchooListlView(List<SchoolInfo> list){};
    //所有学校
    default void getSchoolAllSelcterView(List<SelecterBean.SelectInfo> list){};
    default void getSchoolAllSelcterErrorView(){}


    //所有专业
    default void getProfessionAllSelcterView(List<SelecterBean.SelectInfo> list){};
    //所有学年
    default void getDurationAllSelcterView(List<SelecterBean.SelectInfo> list){};

    //获取所有的产品
    default void getProductListView(List<ProductInfo> list){};
    default void getProductListErrorView(){};



    //获取专业列表
    default void getProfessionListView(List<ProfessionInfo> list){}
    default void getProfessionListErrorView(){}

    //所有学校专业列表
    default void getSchoolProfessionListView(List<SchoolProfessionInfo> list){}
    default void getSchoolProfessionListErrorView(){}

    //所有合同
    default void getContractListView(List<ContractInfo> list){}
    default void getContractListVErrorView(){}


    //获取商品详细信息
    default void getProductDetailView(ProductInfo info){}
    //获取学校详情
    default void getSchoolDetailView(SchoolInfo info){};
}
