package com.bq.order.mvp.presenter;

import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.request.AbstractReqeustCallback;
import com.bq.order.mvp.ui.OrderIview;
import com.bq.order.requset.ProductHttpReqeustImp;
import com.bq.order.requset.bean.OrderIdBean;
import com.bq.order.requset.bean.OrderInfoDetailBean;
import com.bq.order.requset.bean.OrderInfoListBean;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/31
 * 版权：
 */
public class OrderPresenter implements BasePresenter {
    private ProductHttpReqeustImp mProductHttpReqeustImp;
    private OrderIview mIView;

    public OrderPresenter(OrderIview IView) {
        mProductHttpReqeustImp = new ProductHttpReqeustImp();
        mIView = IView;
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
    }
    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
    }

    //添加订单
    public void getHotSchool(String strs){
        mProductHttpReqeustImp.getHostSchoolList(strs, new AbstractReqeustCallback<String>(mIView) {
            @Override
            public void onSuccess(String bean) {
//                mIView.getSchooListlView(bean.getData_list());
            }
        });
    }

    public void addOrder(String info) {
        mProductHttpReqeustImp.orderAdd(info, new AbstractReqeustCallback<OrderIdBean>(mIView) {
            @Override
            public void onStart() {
                mIView.showLoading();
            }

            @Override
            public void onSuccess(OrderIdBean bean) {
                mIView.orderAddView(bean.getOrder_id());
            }
        });
    }

    public void getOrderDetail(String orderId) {
        mProductHttpReqeustImp.getOrder(orderId, new AbstractReqeustCallback<OrderInfoDetailBean>(mIView) {
            @Override
            public void onStart() {
                mIView.showLoading();
            }

            @Override
            public void onSuccess(OrderInfoDetailBean bean) {
                mIView.getOrderDetail(bean.getOrder_info());
            }
        });
    }

    public void getOrderList(int currentPage,String searchInfo){
        mProductHttpReqeustImp.getOrderList(currentPage,searchInfo, new AbstractReqeustCallback<OrderInfoListBean>(mIView) {

            @Override
            public void onSuccess(OrderInfoListBean bean) {
//                mIView.getOrderDetail(bean.getOrder_info());
                mIView.getOrderListView(bean.getData_list());
            }
        });
    }
}
