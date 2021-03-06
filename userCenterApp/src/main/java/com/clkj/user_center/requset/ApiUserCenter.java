package com.clkj.user_center.requset;


/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/5/28
 * 版权： */
public class ApiUserCenter {

    //银行卡
    public static final String API_BANK_LIST = "customer.myself.bankcard.all";
    public static final String API_BANK_ADD = "customer.myself.bankcard.add";
    public static final String API_BANK_DEL = "customer.myself.bankcard.remove";

    //用户
    public static final String API_USER_INFO = "customer.myself.get";
    public static final String API_UPDATE_USER_INFO = "customer.myself.update";
    public static final String API_AUTHENTICATGION = "customer.myself.real.authenticate";
    public static final String API_AUTHENTICATGION_GET = "customer.myself.real.get";
    //获取消息
    public static final String API_MESSAGE_LIST = "message.search";
    //获取公告
    public static final String API_NOTICE_LIST = "notice.search";
    //未读消息
    public static final String API_UNREAD_COUNT = "message.unreadcount";
    //更改消息状态
    public static final String API_ChANGE_MSG = "message.changestatus";


    //地址列表
    public static final String API_ADDRESS_LIST = "customer.myself.address.all";
    public static final String API_ADDRESS_ADD = "customer.myself.address.add";
    public static final String API_ADDRESS_DEL = "customer.myself.address.remove";
    public static final String API_ADDRESS_UPDATE = "customer.myself.address.update";
    public static final String API_ADDRESS_BY_ID = "customer.myself.address.get";


}
