package com.bq.order.requset;

import com.bq.comm_config_lib.request.RequestCallBackInter;
import com.bq.comm_config_lib.request.SignJsonCallBack;
import com.bq.comm_config_lib.utils.CommSpUtils;
import com.bq.netlibrary.NetManager;
import com.bq.netlibrary.http.BaseResponse;
import com.bq.order.requset.bean.BalanceBean;
import com.bq.order.requset.bean.TransactionInfoBean;
import com.bq.order.requset.bean.TransactionListBean;
import com.bq.order.requset.bean.TransactionMonthListBean;
import com.bq.order.requset.bean.WxBean;
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
public class WalletHttpReqeustImp implements WalletHttpReqeustInter {
    @Override
    public void transactionMonthList(RequestCallBackInter callBackInter) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiWallet.API_TRANSATIONS_MONTH_LIST);
        map.put("auth", CommSpUtils.getToken());
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<TransactionMonthListBean>>(callBackInter){
            @Override
            public void onSuccess(Response<BaseResponse<TransactionMonthListBean>> response) {
                super.onSuccess(response);
            }
        });
    }

    @Override
    public void transactionList(int currentPage,String searchInfo,RequestCallBackInter callBackInter) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiWallet.API_TRANSATIONS_LIST);
        map.put("auth", CommSpUtils.getToken());
        map.put("current_page", currentPage+"");
        map.put("search_info",searchInfo);
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<TransactionListBean>>(callBackInter){
            @Override
            public void onSuccess(Response<BaseResponse<TransactionListBean>> response) {
                super.onSuccess(response);
            }
        });
    }

    @Override
    public void transactionDetail(String id, RequestCallBackInter callBackInter) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiWallet.API_TRANSATIONS_DETAIL);
        map.put("auth", CommSpUtils.getToken());
        map.put("transaction_id", id);
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<TransactionInfoBean>>(callBackInter){
            @Override
            public void onSuccess(Response<BaseResponse<TransactionInfoBean>> response) {
                super.onSuccess(response);
            }
        });
    }

    @Override
    public void recharge(String money,String remark,String payType, RequestCallBackInter callBackInter) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiWallet.API_RECHARGE);
        map.put("auth", CommSpUtils.getToken());
        map.put("amount", money);
        map.put("remark", remark);
        map.put("pay_type", payType);
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<WxBean>>(callBackInter){
            @Override
            public void onSuccess(Response<BaseResponse<WxBean>> response) {
                super.onSuccess(response);
            }
        });
    }

    @Override
    public void tackCash(String money, String id,String remark,String payType, RequestCallBackInter callBackInter) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiWallet.API_TACK_CASH);
        map.put("auth", CommSpUtils.getToken());
        map.put("bankcard_id", id);
        map.put("amount", money);
        map.put("remark", remark);
        map.put("pay_type", payType);
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<Object>>(callBackInter){
            @Override
            public void onSuccess(Response<BaseResponse<Object>> response) {
                super.onSuccess(response);
            }
        });
    }

    @Override
    public void getBalance(RequestCallBackInter callBackInter) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiWallet.API_GET_BANLANCE);
        map.put("auth", CommSpUtils.getToken());
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<BalanceBean>>(callBackInter){
            @Override
            public void onSuccess(Response<BaseResponse<BalanceBean>> response) {
                super.onSuccess(response);
            }
        });
    }

    public void pay(String orderid,String payType, RequestCallBackInter callBackInter) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiWallet.API_PAY);
        map.put("auth", CommSpUtils.getToken());
        map.put("order_id", orderid);
        map.put("pay_type", payType);
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<WxBean>>(callBackInter){
            @Override
            public void onSuccess(Response<BaseResponse<WxBean>> response) {
                super.onSuccess(response);
            }
        });
    }

    //登录请求
//    @Override
//    public void login(String name, String pwd, RequestCallBackInter callBack) {
//        Map<String,String> map = new HashMap<>();
//        map.put("api", ApiWallet.API_LOGIN);
//        map.put("username",name);
//        map.put("password",pwd);
//        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<LoginInfo>>(callBack){
//            @Override
//            public void onSuccess(Response<BaseResponse<LoginInfo>> response) {
//                super.onSuccess(response);
//                callBack.onSuccess(response.body().result);
//            }
//        });
//    }


}
