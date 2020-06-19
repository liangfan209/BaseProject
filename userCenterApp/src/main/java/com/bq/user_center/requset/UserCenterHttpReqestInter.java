package com.bq.user_center.requset;

import com.bq.comm_config_lib.request.RequestCallBackInter;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/17
 * 版权：
 */
public interface UserCenterHttpReqestInter {

    void getBankList(RequestCallBackInter callBack);

    void showUserInfo(RequestCallBackInter callBack);

    void addBank(RequestCallBackInter callBack);
}
