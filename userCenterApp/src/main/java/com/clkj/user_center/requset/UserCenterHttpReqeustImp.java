package com.clkj.user_center.requset;

import com.bq.comm_config_lib.request.RequestCallBackInter;
import com.bq.comm_config_lib.request.SignJsonCallBack;
import com.bq.comm_config_lib.utils.CommSpUtils;
import com.bq.netlibrary.NetManager;
import com.bq.netlibrary.http.BaseResponse;
import com.clkj.user_center.requset.bean.AddressInfoBean;
import com.clkj.user_center.requset.bean.AddressListBean;
import com.clkj.user_center.requset.bean.BankListBean;
import com.clkj.user_center.requset.bean.CertificationBean;
import com.clkj.user_center.requset.bean.CertificationInfo;
import com.clkj.user_center.requset.bean.UserInfo;
import com.google.gson.Gson;
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
public class UserCenterHttpReqeustImp implements UserCenterHttpReqestInter {

    /**
     * 获取银行卡列表
     * @param callBack
     */
    public void getBankList(RequestCallBackInter callBack) {
        String token = CommSpUtils.getToken();
        Map<String, String> map = new HashMap<>();
        map.put("auth", token);
        map.put("api", ApiUserCenter.API_BANK_LIST);
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<BankListBean>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<BankListBean>> response) {
                super.onSuccess(response);
            }
        });
    }


    @Override
    public void addBank(String bankinfo, RequestCallBackInter callBack) {
        String token = CommSpUtils.getToken();
        Map<String, String> map = new HashMap<>();
        map.put("bankcard_info", bankinfo);
        map.put("auth", token);
        map.put("api", ApiUserCenter.API_BANK_ADD);
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<String>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<String>> response) {
                super.onSuccess(response);
            }
        });
    }

    @Override
    public void deleteBank(String id, RequestCallBackInter callBack) {
        String token = CommSpUtils.getToken();
        Map<String, String> map = new HashMap<>();
        map.put("bankcard_id", id);
        map.put("auth", token);
        map.put("api", ApiUserCenter.API_BANK_DEL);
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<String>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<String>> response) {
                super.onSuccess(response);
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
        map.put("auth", CommSpUtils.getToken());
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<UserInfo>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<UserInfo>> response) {
                super.onSuccess(response);
            }
        });
    }


    /**
     * 更新用户资料
     * @param callBack
     */
    public void updateUserInfo(String userInfo,RequestCallBackInter callBack){
        Map<String,String> map = new HashMap<>();
        map.put("myself_info", userInfo);
        map.put("api", ApiUserCenter.API_UPDATE_USER_INFO);
        map.put("auth", CommSpUtils.getToken());
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<String>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<String>> response) {
                super.onSuccess(response);
            }
        });
    }

    /*
     *上传实名认证
     */
    @Override
    public void certification(String name, String id, String idFront, String idBack, String idHand,
                              RequestCallBackInter callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiUserCenter.API_AUTHENTICATGION);
        map.put("auth", CommSpUtils.getToken());
        String info = new Gson().toJson(new CertificationInfo(name,id,idFront,idBack,idHand));
        map.put("certification_info", info);
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<String>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<String>> response) {
                super.onSuccess(response);
            }
        });
    }


    /**
     * 获取实名认证信息
     * @param callBack
     */
    @Override
    public void getCertification(RequestCallBackInter callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiUserCenter.API_AUTHENTICATGION_GET);
        map.put("auth", CommSpUtils.getToken());
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<CertificationBean>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<CertificationBean>> response) {
                super.onSuccess(response);
            }
        });
    }


    /**
     * 获取地址列表
     * @param callBack
     */
    @Override
    public void getAddressList(RequestCallBackInter callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiUserCenter.API_ADDRESS_LIST);
        map.put("auth", CommSpUtils.getToken());
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<AddressListBean>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<AddressListBean>> response) {
                super.onSuccess(response);
            }
        });
    }

    /**
     * 添加地址
     * @param addressInfo
     */
    @Override
    public void addAddress(String addressInfo,int isDefault, RequestCallBackInter callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("address_info", addressInfo);
        map.put("is_default", isDefault+"");
        map.put("api", ApiUserCenter.API_ADDRESS_ADD);
        map.put("auth", CommSpUtils.getToken());
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<String>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<String>> response) {
                super.onSuccess(response);
            }
        });
    }

    /**
     * 删除地址
     * @param id
     * @param callBack
     */
    @Override
    public void deleteAddress(String id, RequestCallBackInter callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("address_id", id);
        map.put("api", ApiUserCenter.API_ADDRESS_DEL);
        map.put("auth", CommSpUtils.getToken());
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<String>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<String>> response) {
                super.onSuccess(response);
            }
        });
    }

    /**
     * 更新地址
     * @param addressInfo
     * @param callBack
     */
    @Override
    public void updateAddress(String id,String addressInfo,int isDefault, RequestCallBackInter callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("address_id", id);
        map.put("update_info", addressInfo);
        map.put("is_default", isDefault+"");
        map.put("api", ApiUserCenter.API_ADDRESS_UPDATE);
        map.put("auth", CommSpUtils.getToken());
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<Object>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<Object>> response) {
                super.onSuccess(response);
            }
        });
    }

    /**
     * 根据ID查询地址
     * @param id
     * @param callBack
     */
    @Override
    public void getAddressById(String id, RequestCallBackInter callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("address_id", id);
        map.put("api", ApiUserCenter.API_ADDRESS_BY_ID);
        map.put("auth", CommSpUtils.getToken());
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<AddressInfoBean>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<AddressInfoBean>> response) {
                super.onSuccess(response);
            }
        });
    }


}
