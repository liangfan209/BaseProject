package com.bq.order.requset;

import com.bq.comm_config_lib.request.AbstractReqeustCallback;
import com.bq.comm_config_lib.request.RequestCallBackInter;
import com.bq.comm_config_lib.request.SignJsonCallBack;
import com.bq.comm_config_lib.utils.CommSpUtils;
import com.bq.netlibrary.NetManager;
import com.bq.netlibrary.http.BaseResponse;
import com.bq.order.requset.bean.OrderIdBean;
import com.bq.order.requset.bean.OrderInfoDetailBean;
import com.bq.order.requset.bean.OrderInfoListBean;
import com.bq.order.requset.bean.ProductBean;
import com.bq.order.requset.bean.ProductListBean;
import com.bq.order.requset.bean.SchoolBean;
import com.bq.order.requset.bean.SchoolListBean;
import com.bq.order.requset.bean.SelecterBean;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/16
 * 版权：
 */
public class ProductHttpReqeustImp implements ProductHttpReqeustInter {
    /**
     *     //获取热门学校
     * @param searchStr
     * @param callBack
     */
    @Override
    public void getHostSchoolList(String searchStr, RequestCallBackInter callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiProduct.PRODUCT_HOT_SCHOOL);
        map.put("search_info", searchStr);
        map.put("auth", CommSpUtils.getToken());
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<SchoolListBean>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<SchoolListBean>> response) {
                super.onSuccess(response);
            }
        });
    }

    /**
     * //获取搜索学校列表
     * @param currentPage
     * @param searchStr
     * @param callBack
     */
    @Override
    public void getSearchSchoolList(int currentPage,String searchStr, RequestCallBackInter callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiProduct.PRODUCT_SEARCH_SCHOOL_LIST);
        map.put("current_page", currentPage+"");
        map.put("search_info", searchStr);
        map.put("auth", CommSpUtils.getToken());
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<SchoolListBean>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<SchoolListBean>> response) {
                super.onSuccess(response);
            }
        });
    }

    /**
     * //获取热门商品
     * @param searchStr
     * @param callBack
     */
    @Override
    public void getHostProductList(String searchStr, RequestCallBackInter callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiProduct.PRODUCT_HOT_PRODUCT);
        map.put("search_info", searchStr);
        map.put("auth", CommSpUtils.getToken());
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<ProductListBean>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<ProductListBean>> response) {
                super.onSuccess(response);
            }
        });
    }

    /**
     * 所有学校
     * @param searchStr
     * @param callBack
     */
    @Override
    public void getSchoolAll(String searchStr,RequestCallBackInter callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiProduct.PRODUCT_SCHOOL_ALL);
        map.put("search_info", searchStr);
        map.put("auth", CommSpUtils.getToken());
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<SelecterBean>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<SelecterBean>> response) {
                super.onSuccess(response);
            }
        });
    }

    /**
     * 产品搜索列表
     * @param searchStr
     * @param callBack
     */
    @Override
    public void getSearchProductList(int currentPage,String searchStr,RequestCallBackInter callBack){
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiProduct.PRODUCT_PRODUCT_LIST);
        map.put("current_page", currentPage+"");
        map.put("search_info", searchStr);
        map.put("auth", CommSpUtils.getToken());
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<ProductListBean>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<ProductListBean>> response) {
                super.onSuccess(response);
            }
        });
    }

    /**
     * 所有专业
     * @param searchStr
     * @param callBack
     */
    @Override
    public void getProfessionAll(String searchStr,RequestCallBackInter callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiProduct.PRODUCT_PROFESSION_ALL);
        map.put("search_info", searchStr);
        map.put("auth", CommSpUtils.getToken());
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<SelecterBean>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<SelecterBean>> response) {
                super.onSuccess(response);
            }
        });
    }

    /**
     * 搜索所有学年
     * @param searchStr
     * @param callBack
     */
    @Override
    public void getDurationAll(String searchStr,RequestCallBackInter callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiProduct.PRODUCT_DURATION_ALL);
        map.put("search_info", searchStr);
        map.put("auth", CommSpUtils.getToken());
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<SelecterBean>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<SelecterBean>> response) {
                super.onSuccess(response);
            }
        });
    }

    /**
     * 获取商品详情
     * @param id
     * @param callBack
     */
    @Override
    public void getProductionDetail(String id, RequestCallBackInter callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiProduct.PRODUCT_DETAIL);
        map.put("goods_id", id);
        map.put("auth", CommSpUtils.getToken());
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<ProductBean>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<ProductBean>> response) {
                super.onSuccess(response);
            }
        });
    }


    /**
     * 获取学校详情
     * @param schoolId
     * @param callBack
     */
    public void getSchoolDetail(String schoolId, RequestCallBackInter callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiProduct.SCHOOL_DETAIL);
        map.put("school_id", schoolId);
        map.put("auth", CommSpUtils.getToken());
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<SchoolBean>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<SchoolBean>> response) {
                super.onSuccess(response);
            }
        });
    }

    /**
     * 添加订单
     * @param info
     * @param callBack
     */
    public void orderAdd(String info,RequestCallBackInter callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiProduct.ORDER_ADD);
        map.put("order_info", info);
        map.put("auth", CommSpUtils.getToken());
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<OrderIdBean>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<OrderIdBean>> response) {
                super.onSuccess(response);
            }
        });
    }

    public void getOrder(String orderId, RequestCallBackInter callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiProduct.ORDER_GET_DETAIL);
        map.put("order_id", orderId);
        map.put("auth", CommSpUtils.getToken());
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<OrderInfoDetailBean>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<OrderInfoDetailBean>> response) {
                super.onSuccess(response);
            }
        });
    }

    public void getOrderList(int currentPage,String searchStr,RequestCallBackInter callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiProduct.ORDER_LIST);
        map.put("search_info", searchStr);
        map.put("current_page", currentPage+"");
        map.put("auth", CommSpUtils.getToken());
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<OrderInfoListBean>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<OrderInfoListBean>> response) {
                super.onSuccess(response);
            }
        });
    }

    public void cancelOrder(int id, AbstractReqeustCallback<String> callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiProduct.ORDER_CANCEL_ORDER);
        map.put("order_id", id+"");
        map.put("auth", CommSpUtils.getToken());
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<String>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<String>> response) {
                super.onSuccess(response);
            }
        });
    }
}
