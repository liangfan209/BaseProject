package com.clkj.order.mvp.ui;

import com.bq.comm_config_lib.mvp.BaseIView;
import com.clkj.order.requset.bean.AppVersionBean;
import com.clkj.order.requset.bean.BannerInfo;
import com.clkj.order.requset.bean.ContractInfo;
import com.clkj.order.requset.bean.ProductInfo;
import com.clkj.order.requset.bean.ProfessionInfo;
import com.clkj.order.requset.bean.SchoolInfo;
import com.clkj.order.requset.bean.SchoolProfessionInfo;
import com.clkj.order.requset.bean.SelecterBean;

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


    //获取banner
    default void getBannerList(String type,List<BannerInfo> list){}


    //检查更新
    default void checkUpdateView(AppVersionBean.AppVersionInfo edition_info){};

    default void collectProductView(){}

}
