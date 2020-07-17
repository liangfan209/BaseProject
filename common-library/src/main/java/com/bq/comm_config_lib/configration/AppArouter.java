package com.bq.comm_config_lib.configration;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/2
 * 版权：
 */
public class AppArouter {



    //壳子
    public static final String MAIN = "/main";
    public static final String MAIN_ACTIVITY = "/main/MainActivity";

    //登录模块
    public static final String LOGIN = "/login";
    public static final String LOGIN_ACTVITY = LOGIN+"/LoginActivity";
    public static final String FORGET_PWD_ACTIVITY = LOGIN+"/RegisterActivity";
    public static final String LOGIN_SETTING_ACTIVITY = LOGIN+"/SettingPwdActivity";
    public static final String LOGIN_MODIFY_ACTIVITY = LOGIN+"/ModifyPwdActivity";

    public static final String LOGIN_SERVER = LOGIN+"/LoginService";
    public static final String LOGOUT_SERVER = LOGIN+"/logoutService";

    //用户中心
    public static final String USER_CENTER = "/userCenter";
    public static final String USER_CENTER_BANK_LIST = USER_CENTER+"/BankManagerActivity";
    public static final String USER_CENTER_BANK_ADD_ACTIVITY = USER_CENTER+"/AddBankActivity";
    public static final String USER_CENTER_USER_FRAGMENT = USER_CENTER+"/UserInfoFragment";
    public static final String USER_CENTER_ADDRESS_LIST = USER_CENTER+"/AddressManagerActivity";
    public static final String USER_CENTER_ADDRESS_SELECT = USER_CENTER+"/AddressSelectActivity";
    public static final String USER_CENTER_ADDRESS_OPTION = USER_CENTER+"/AddAddressActivity";
    public static final String USER_CENTER_USER_INFO_ACTIVITY = USER_CENTER+"/UserInfoActivity";
    public static final String USER_CENTER_ABOUT_US_ACTIVITY = USER_CENTER+"/AboutUsActivity";
    public static final String USER_CENTER_CETIFICATION_ACTIVITY = USER_CENTER+"/CertificationActivity";


    public static final String USER_CENTER_SERVICE = USER_CENTER+"/service";
    public static final String USER_CENTER_BANK_LIST_SERVICE = USER_CENTER+"/BankListService";

    //我的钱包
    public static final String WALLET = "/wallet";
    public static final String WALLET_MY_ACTIVITY = WALLET+"/MyWalletActivity";
    public static final String WALLET_BILL_DETAIL_ACTIVITY = WALLET+"/BillDetailActivity";
    public static final String WALLET_RECHARGE_ACTIVITY = WALLET+"/RechargeActivity";
    public static final String WALLET_TRANSACTION_DETAIL_ACTIVITY = WALLET+"/TransactionDetailActivity";
    public static final String WALLET_TACK_CASH_ACTIVITY = WALLET+"/TackCashActivity";


    //我的订单
    public static final String ORDER= "/order";
    public static final String ORDER_PRODUCT_DETAIL_ACTIVITY = ORDER+"/ProductDetailActivity";
    public static final String ORDER_LIST_ACTIVITY = ORDER+"/OrderListActivity";
    public static final String ORDER_ORDER_DETAIL_ACTIVITY = ORDER+"/OrderDetaiActivity";
    public static final String ORDER_ORDER_COMMIT_ACTIVITY = ORDER+"/OrderCommitActivity";

    //h5页面
    public static final String H5_ACTIVITY = "/h5/H5Activity";

}
