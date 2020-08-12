package com.bq.wallet.requset;

import com.bq.comm_config_lib.request.RequestCallBackInter;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/17
 * 版权：
 */
public interface WalletHttpReqeustInter {
    //交易月份列表
    void transactionMonthList(RequestCallBackInter callBackInter);
    //交易列表
    void transactionList(int currentPage,String searchInfo,RequestCallBackInter callBackInter);
    //交易详情
    void transactionDetail(String id,RequestCallBackInter callBackInter);
    //充值
    void recharge(String money,String remark,String payType,RequestCallBackInter callBackInter);


    //提现
    void tackCash(String money,String remark,String payType,String id,RequestCallBackInter callBackInter);
    //查询余额
    void getBalance(RequestCallBackInter callBackInter);

}
