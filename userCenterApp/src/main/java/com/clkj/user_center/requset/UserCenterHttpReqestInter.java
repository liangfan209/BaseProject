package com.clkj.user_center.requset;

import com.bq.comm_config_lib.request.RequestCallBackInter;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/17
 * 版权：
 */
public interface UserCenterHttpReqestInter {

    //银行卡
    void getBankList(RequestCallBackInter callBack);
    void addBank(String bankinfo,RequestCallBackInter callBack);
    void deleteBank(String id,RequestCallBackInter callBack);

    //用户
    void showUserInfo(RequestCallBackInter callBack);
    void updateUserInfo(String userInfo,RequestCallBackInter callBack);
    void certification(String name, String id,String idFront,String idBack,String idHand,RequestCallBackInter callBack);
    void getCertification(RequestCallBackInter callBack);


    //地址
    void getAddressList(RequestCallBackInter callBack);
    void addAddress(String addressInfo,int isDefault,RequestCallBackInter callBack);
    void deleteAddress(String id,RequestCallBackInter callBack);
    void updateAddress(String id,String addressInfo,int isDefault,RequestCallBackInter callBack);
    void getAddressById(String id,RequestCallBackInter callBack);

}
