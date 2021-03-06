package com.clkj.order.requset;

import com.bq.comm_config_lib.request.AbstractReqeustCallback;
import com.bq.comm_config_lib.request.RequestCallBackInter;
import com.bq.comm_config_lib.request.SignJsonCallBack;
import com.bq.comm_config_lib.utils.CommSpUtils;
import com.bq.netlibrary.NetManager;
import com.bq.netlibrary.http.BaseResponse;
import com.clkj.order.requset.bean.AppVersionBean;
import com.clkj.order.requset.bean.BannerListBean;
import com.clkj.order.requset.bean.ContractListBean;
import com.clkj.order.requset.bean.ContractinfoBean;
import com.clkj.order.requset.bean.EvaluationListBean;
import com.clkj.order.requset.bean.OrderIdBean;
import com.clkj.order.requset.bean.OrderInfoDetailBean;
import com.clkj.order.requset.bean.OrderInfoListBean;
import com.clkj.order.requset.bean.ProductBean;
import com.clkj.order.requset.bean.ProductListBean;
import com.clkj.order.requset.bean.ProfessionListBean;
import com.clkj.order.requset.bean.SchoolBean;
import com.clkj.order.requset.bean.SchoolListBean;
import com.clkj.order.requset.bean.SchoolProfessionListBean;
import com.clkj.order.requset.bean.SelecterBean;
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
//        map.put("auth", CommSpUtils.getToken());
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
     * 获取热门专业列表
     * @param searchStr
     * @param callBack
     */
    public void getHostProfessionList(String searchStr, AbstractReqeustCallback<ProfessionListBean> callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiProduct.PRODUCT_HOT_PROFESSION);
        map.put("search_info", searchStr);
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<ProfessionListBean>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<ProfessionListBean>> response) {
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
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<SelecterBean>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<SelecterBean>> response) {
                super.onSuccess(response);
            }

            @Override
            public void onError(Response<BaseResponse<SelecterBean>> response) {
                super.onError(response);
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
//        map.put("auth", CommSpUtils.getToken());
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<ProductListBean>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<ProductListBean>> response) {
                super.onSuccess(response);
            }
        });
    }


    /**
     * 收藏列表
     * @param searchStr
     * @param callBack
     */
    public void collectProductList(int currentPage,String searchStr,RequestCallBackInter callBack){
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiProduct.PRODUCT_COLLECT_PRODUCT_LIST);
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
//        map.put("auth", CommSpUtils.getToken());
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
//        map.put("auth", CommSpUtils.getToken());
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
//        map.put("auth", CommSpUtils.getToken());
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<ProductBean>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<ProductBean>> response) {
                super.onSuccess(response);
            }
        });
    }

    /**
     * 获取扫码产品详情
     * @param id
     * @param callBack
     */
    public void getPosterDetail(String id, RequestCallBackInter callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiProduct.PRODUCT_POSTER_DETAIL);
        map.put("poster_id", id);
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
//        map.put("auth", CommSpUtils.getToken());
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

    public void orderAddByPoster(String info,String posterId,RequestCallBackInter callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiProduct.ORDER_POSTER_ADD);
        map.put("order_info", info);
        map.put("poster_id", posterId);
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

    /**
     * 添加合同
     * @param info
     * @param callBack
     */
    public void addContract(String info,String id, AbstractReqeustCallback<String> callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiProduct.ORDER_ADD_CONTRACT);
        map.put("contract_info", info);
        map.put("contract_id", id);
        map.put("auth", CommSpUtils.getToken());
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<String>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<String>> response) {
                super.onSuccess(response);
            }
        });
    }

    public void getContactImg(String productId, AbstractReqeustCallback<ContractinfoBean> callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiProduct.ORDER_GET_CONTRACT);
        map.put("contract_id", productId);
        map.put("auth", CommSpUtils.getToken());
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<ContractinfoBean>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<ContractinfoBean>> response) {
                super.onSuccess(response);
            }
        });
    }


    /**
     * 获取所有专业
     * @param page
     * @param searchStr
     * @param callBack
     */
    public void getProfessionList(int page, String searchStr, AbstractReqeustCallback<ProfessionListBean> callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiProduct.PRODUCT_PROFESSION_SEARCH_ALL);
        map.put("search_info", searchStr);
        map.put("current_page", page+"");
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<ProfessionListBean>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<ProfessionListBean>> response) {
                super.onSuccess(response);
            }
        });
    }

    /**
     * 获取学校专业列表
     * @param page
     * @param searchStr
     * @param callBack
     */
    public void getSchoolProfessionList(int page, int type,String searchStr, AbstractReqeustCallback<SchoolProfessionListBean> callBack) {
        Map<String,String> map = new HashMap<>();
        if(type == 1){
            //专业的学校列表
            map.put("api", ApiProduct.PRODUCT_PROFESSION_SCHOOL_LIST);
        }else{
            map.put("api", ApiProduct.PRODUCT_SCHOOL_PROFESSION_LIST);
        }
        map.put("search_info", searchStr);
        map.put("current_page", page+"");
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<SchoolProfessionListBean>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<SchoolProfessionListBean>> response) {
                super.onSuccess(response);
            }
        });
    }

    public void getContractList( AbstractReqeustCallback<ContractListBean> callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiProduct.PRODUCT_CONTRACT_SEARCH_ALL);
        map.put("auth", CommSpUtils.getToken());
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<ContractListBean>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<ContractListBean>> response) {
                super.onSuccess(response);
            }
        });
    }

    public void getBannerList(String info,AbstractReqeustCallback<BannerListBean> callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiProduct.PRODUCT_BANNEL_LIST);
        map.put("search_info", info);
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<BannerListBean>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<BannerListBean>> response) {
                super.onSuccess(response);
            }
        });
    }

    /**
     * 创建合同
     */
    public void createContract(String orderDetailId, AbstractReqeustCallback<ContractinfoBean> callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiProduct.ORDER_CREATE_CONTRACT);
        map.put("order_item_id", orderDetailId);
        map.put("auth", CommSpUtils.getToken());
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<ContractinfoBean>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<ContractinfoBean>> response) {
                super.onSuccess(response);
            }
        });
    }

    public void updateApp(AbstractReqeustCallback<AppVersionBean> callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiProduct.UPDATE_APP);
        map.put("type", "android");
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<AppVersionBean>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<AppVersionBean>> response) {
                super.onSuccess(response);
            }
        });
    }

    public void hasCollectProduct(String id,AbstractReqeustCallback<String> callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiProduct.ORDER_PRODUCT_COLLECT);
        map.put("auth", CommSpUtils.getToken());
        map.put("goods_id", id);
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<AppVersionBean>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<AppVersionBean>> response) {
                super.onSuccess(response);
            }
        });

    }

    public void feedBack(String info, AbstractReqeustCallback<String> callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiProduct.ORDER_FEED_BACK);
        map.put("auth", CommSpUtils.getToken());
        map.put("feedback_info", info);
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<AppVersionBean>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<AppVersionBean>> response) {
                super.onSuccess(response);
            }
        });
    }


    public void evaluationAdd(String info, String orderId, AbstractReqeustCallback<String> callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiProduct.ORDER_EVALUATION_ADD);
        map.put("auth", CommSpUtils.getToken());
        map.put("evaluation_info", info);
        map.put("order_item_id", orderId);
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<String>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<String>> response) {
                super.onSuccess(response);
            }
        });
    }

    public void getMyEvaluation(int page, AbstractReqeustCallback<EvaluationListBean> callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiProduct.ORDER_EVALUATION_LIST);
        map.put("auth", CommSpUtils.getToken());
        map.put("current_page", page+"");
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<EvaluationListBean>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<EvaluationListBean>> response) {
                super.onSuccess(response);
            }
        });
    }

    public void getAllEvaluation(int page, String good_id, AbstractReqeustCallback<EvaluationListBean> callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiProduct.ORDER_EVALUATION_ALL);
        map.put("auth", CommSpUtils.getToken());
        map.put("current_page", page+"");
        map.put("goods_id", good_id);
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<EvaluationListBean>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<EvaluationListBean>> response) {
                super.onSuccess(response);
            }
        });
    }

    public void searchAllEvaluation(int page, String good_id,String info, AbstractReqeustCallback<EvaluationListBean> callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiProduct.ORDER_EVALUATION_SEARCH_ALL);
        map.put("auth", CommSpUtils.getToken());
        map.put("current_page", page+"");
        map.put("goods_id", good_id);
        map.put("search_info", info);
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<EvaluationListBean>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<EvaluationListBean>> response) {
                super.onSuccess(response);
            }
        });
    }

}
