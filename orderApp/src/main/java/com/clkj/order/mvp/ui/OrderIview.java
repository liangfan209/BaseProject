package com.clkj.order.mvp.ui;

import com.bq.comm_config_lib.mvp.BaseIView;
import com.clkj.order.requset.bean.ContractInfo;
import com.clkj.order.requset.bean.OrderInfo;

import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/31
 * 版权：
 */
public interface OrderIview extends BaseIView {
    default void orderAddView(String orderId){};
    default void getOrderDetail(OrderInfo info){};
    default void getOrderListView(List<OrderInfo> list){};
    default void cancelOrderView(){};

    default void getContactInfo(ContractInfo info){}
    default void addContactView(){}

    default void feedbackView(){}
    default void feedbackCompleView(){}
//    default void createContactView(ContractInfo info){}
}
