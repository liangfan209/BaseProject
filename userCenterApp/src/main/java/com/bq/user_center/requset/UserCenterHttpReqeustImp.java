package com.bq.user_center.requset;

import com.bq.comm_config_lib.request.RequestCallBackInter;
import com.bq.comm_config_lib.request.SignJsonCallBack;
import com.bq.comm_config_lib.utils.CommSpUtils;
import com.bq.netlibrary.NetManager;
import com.bq.netlibrary.http.BaseResponse;
import com.bq.user_center.requset.bean.BankCard;
import com.bq.user_center.requset.bean.UserInfo;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/16
 * 版权：
 */
public class UserCenterHttpReqeustImp implements UserCenterHttpReqestInter {

    /**
     * 获取银行卡列表
     * @param callBack
     */
    public void getBankList(RequestCallBackInter callBack) {
        String token = CommSpUtils.getToken();
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("api", ApiUserCenter.API_BANK_LIST);
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<List<BankCard>>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<List<BankCard>>> response) {
                super.onSuccess(response);
                callBack.onSuccess(response.body().result);
            }
        });
    }

    /**
     * 获取用户资料
     * @param callBack
     */
    public void showUserInfo(RequestCallBackInter callBack){
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiUserCenter.API_USER_INFO);
        map.put("token", CommSpUtils.getToken());
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<UserInfo>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<UserInfo>> response) {
                super.onSuccess(response);
                callBack.onSuccess(response.body().result);
            }
        });
    }

    public void addBank(RequestCallBackInter callBack) {
        String token = CommSpUtils.getToken();
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("api", ApiUserCenter.API_BANK_ADD);
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<String>>(callBack));
    }

}
