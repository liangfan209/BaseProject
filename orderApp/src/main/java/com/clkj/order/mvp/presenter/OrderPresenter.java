package com.clkj.order.mvp.presenter;

import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.request.AbstractReqeustCallback;
import com.clkj.order.mvp.ui.OrderIview;
import com.clkj.order.requset.ProductHttpReqeustImp;
import com.clkj.order.requset.bean.ContractinfoBean;
import com.clkj.order.requset.bean.OrderIdBean;
import com.clkj.order.requset.bean.OrderInfoDetailBean;
import com.clkj.order.requset.bean.OrderInfoListBean;

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

    public void addOrderByPoster(String info,String postId) {
        mProductHttpReqeustImp.orderAddByPoster(info,postId, new AbstractReqeustCallback<OrderIdBean>(mIView) {
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

    public void cancelOrder(int id) {
        mProductHttpReqeustImp.cancelOrder(id,new AbstractReqeustCallback<String>(mIView) {

            @Override
            public void onStart() {
                mIView.showLoading();
            }
            @Override
            public void onSuccess(String bean) {
                mIView.cancelOrderView();
            }
        });
    }

    /**
     * 添加合同
     */
    public void addContract(String info,String id) {
        mProductHttpReqeustImp.addContract(info,id,new AbstractReqeustCallback<String>(mIView) {

            @Override
            public void onStart() {
                mIView.showLoading();
            }
            @Override
            public void onSuccess(String bean) {
                mIView.addContactView();
            }
        });
    }

//    /**
//     * 创建合同
//     */
//    public void createContract(String orderDetailId){
//        mProductHttpReqeustImp.createContract(orderDetailId,new AbstractReqeustCallback<ContractinfoBean>(mIView) {
//            @Override
//            public void onSuccess(ContractinfoBean contractinfoBean) {
//                mIView.createContactView(contractinfoBean.getContract_info());
//            }
//        });
//    }

    public void getContactImg(String productId) {
        mProductHttpReqeustImp.getContactImg(productId,new AbstractReqeustCallback<ContractinfoBean>(mIView) {
            @Override
            public void onStart() {
                mIView.showLoading();
            }
            @Override
            public void onSuccess(ContractinfoBean bean) {
                if(bean == null) return;
                mIView.getContactInfo(bean.getContract_info());
            }
        });
    }


    /**
     * 意见反馈
     */
    public void feedBack(String info) {
        mProductHttpReqeustImp.feedBack(info,new AbstractReqeustCallback<String>(mIView) {
            @Override
            public void onSuccess(String bean) {
                mIView.feedbackView();
            }

            @Override
            public void onComplete() {
                mIView.onComplete();
            }
        });
    }

    public void evaluationAdd(String info, String orderId) {
        mProductHttpReqeustImp.evaluationAdd(info,orderId,new AbstractReqeustCallback<String>(mIView) {
            @Override
            public void onSuccess(String bean) {
                mIView.feedbackView();
            }

            @Override
            public void onComplete() {
                mIView.onComplete();
            }
        });
    }
}
