package com.bq.order.requset;

import com.bq.comm_config_lib.request.RequestCallBackInter;
import com.bq.comm_config_lib.request.SignJsonCallBack;
import com.bq.comm_config_lib.utils.CommSpUtils;
import com.bq.netlibrary.NetManager;
import com.bq.netlibrary.http.BaseResponse;
import com.bq.order.requset.bean.ProductListBeanBean;
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
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<ProductListBeanBean>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<ProductListBeanBean>> response) {
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
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<ProductListBeanBean>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<ProductListBeanBean>> response) {
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
}
