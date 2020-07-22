package com.bq.wallet.requset;


/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/5/28
 * 版权：
 */
public class ApiWallet {
    //客户交易搜索接口
    public static final String API_TRANSATIONS_LIST = "customer.finance.transaction.search";
    //客户交易按月统计接口
    public static final String API_TRANSATIONS_MONTH_LIST = "customer.finance.transaction.statistics.monthly";
    //客户交易详情获取接口
    public static final String API_TRANSATIONS_DETAIL = "customer.finance.transaction.get";
    //客户余额充值接口
    public static final String API_RECHARGE = "customer.finance.balance.topup";
    //客户余额提现接口
    public static final String API_TACK_CASH = "customer.finance.balance.withdraw";
    //客户余额获取接口
    public static final String API_GET_BANLANCE = "customer.finance.balance.get";
}
