package com.bq.order.mvp.ui;

import com.bq.comm_config_lib.mvp.BaseIView;
import com.bq.order.requset.bean.ProductInfo;
import com.bq.order.requset.bean.SchoolInfo;
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
    //所有专业
    default void getProfessionAllSelcterView(List<SelecterBean.SelectInfo> list){};
    //所有学年
    default void getDurationAllSelcterView(List<SelecterBean.SelectInfo> list){};
    default void getProductListView(List<ProductInfo> list){};
}
