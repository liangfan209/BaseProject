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
    //导航页面
//    public static final String ORDER_WELCOME_ACTIVITY = MAIN +"/WelcomeActivity";


    //登录模块
    public static final String LOGIN = "/login";
    public static final String LOGIN_ACTVITY = LOGIN+"/LoginActivity";
    public static final String FORGET_PWD_ACTIVITY = LOGIN+"/RegisterActivity";
    public static final String LOGIN_SETTING_ACTIVITY = LOGIN+"/SettingPwdActivity";
    public static final String LOGIN_MODIFY_ACTIVITY = LOGIN+"/ModifyPwdActivity";
    public static final String LOGIN_BIND_ACTIVITY = LOGIN+"/BindPhoneActivity";

    public static final String LOGIN_SERVER = LOGIN+"/LoginService";
    public static final String LOGOUT_SERVER = LOGIN+"/logoutService";
    public static final String SMS_SERVER = LOGIN+"/smsService";


    //用户中心
    public static final String USER_CENTER = "/userCenter";
    public static final String USER_CENTER_BANK_LIST = USER_CENTER+"/BankManagerActivity";
    public static final String USER_CENTER_BANK_ADD_ACTIVITY = USER_CENTER+"/AddBankActivity";
    public static final String USER_CENTER_USER_FRAGMENT = USER_CENTER+"/UserInfoFragment";
    public static final String USER_CENTER_MESSAGE_ACTIVITY = USER_CENTER+"/MessageActivity";
    public static final String USER_CENTER_MESSAGE_DETAIL_ACTIVITY = USER_CENTER+"/MessageDetailActivity";


    public static final String USER_CENTER_ADDRESS_LIST = USER_CENTER+"/AddressManagerActivity";
    public static final String USER_CENTER_ADDRESS_SELECT = USER_CENTER+"/AddressSelectActivity";
    public static final String USER_CENTER_ADDRESS_OPTION = USER_CENTER+"/AddAddressActivity";
    public static final String USER_CENTER_USER_INFO_ACTIVITY = USER_CENTER+"/UserInfoActivity";
    public static final String USER_CENTER_ABOUT_US_ACTIVITY = USER_CENTER+"/AboutUsActivity";
    public static final String USER_CENTER_CETIFICATION_ACTIVITY = USER_CENTER+"/CertificationActivity";


    public static final String USER_CENTER_SERVICE = USER_CENTER+"/service";
    public static final String USER_CENTER_BANK_LIST_SERVICE = USER_CENTER+"/BankListService";
    public static final String USER_CENTER_ADDRESS_DEFAULT_SERVICE = USER_CENTER+"/AddressDefaultService";

    //我的钱包
    public static final String WALLET = "/wallet";
    public static final String WALLET_MY_ACTIVITY = WALLET+"/MyWalletActivity";
    public static final String WALLET_BILL_DETAIL_ACTIVITY = WALLET+"/BillDetailActivity";
    public static final String WALLET_RECHARGE_ACTIVITY = WALLET+"/RechargeActivity";
    public static final String WALLET_TRANSACTION_DETAIL_ACTIVITY = WALLET+"/TransactionDetailActivity";
    public static final String WALLET_TACK_CASH_ACTIVITY = WALLET+"/TackCashActivity";
    public static final String WALLET_SECURITY_SETTIGN_PWD_ACTIVITY = WALLET+"/security/PayPwdSettingActivity";
    public static final String WALLET_BALANCE_SERVICE = WALLET+"/banlence/service";
    public static final String WALLET_PAY_SERVICE = WALLET+"/pay/service";
    public static final String WALLET_REFUND_ACTIVITY = WALLET+"/RefundActivity"; //申请退款
    public static final String WALLET_REFUND_PROCESS_ACTIVITY = WALLET+"/RefundProcessActivity"; //退款进度






    //我的订单
    public static final String ORDER= "/order";
    public static final String ORDER_PRODUCT_DETAIL_ACTIVITY = ORDER+"/ProductDetailActivity";
    public static final String ORDER_LIST_ACTIVITY = ORDER+"/OrderListActivity";
    public static final String ORDER_ORDER_DETAIL_ACTIVITY = ORDER+"/OrderDetaiActivity";
    public static final String ORDER_ORDER_COMMIT_ACTIVITY = ORDER+"/OrderCommitActivity";
    public static final String ORDER_PAY_SUCCESS_ACTIVITY = ORDER+"/PaySuccessActivity";
    public static final String ORDER_PAY_ORDER_DETAIL_ACTIVITY = ORDER+"/PayOrderDetailActivity";


    public static final String ORDER_HOME_FRAGMENT = ORDER+"/HomeFragment1";


    //商品列表
    public static final String ORDER_PRODUCT_LIST_ACTIVITY = ORDER +"/ProductListActivity";
    //学校列表
    public static final String ORDER_SCHOOL_LIST_ACTIVITY = ORDER +"/SchoolListActivity";

    //合同列表
    public static final String ORDER_CONTRACT_LIST_ACTIVITY = ORDER +"/ContractListActivity";

    //专业列表
    public static final String ORDER_PROFESSION_LIST_ACTIVITY = ORDER +"/ProfessionListActivity";

    //学校专业列表
    public static final String ORDER_SCHOOL_PROFESSION_LIST_ACTIVITY = ORDER +"/SchoolProfessionListActivity";

    //院校专业详情
    public static final String ORDER_SCHOOL_PROFRESSION_DETAIL_ACTIVITY = ORDER +"/SchoolProfessionDetailActivity";
    //签合同
    public static final String ORDER_SIGN_CONTRACT_ACTIVITY = ORDER+"/SignContract";

    //签署人信息
    public static final String ORDER_SIGN_USER_ACTIVITY = ORDER+"/SignUserActivity";

    //电子签名
    public static final String ORDER_AUTOGRAPH_ACTIVITY = ORDER+"/AutographActivity";

    public static final String ORDER_OPEN_FILE_ACTIVITY = ORDER+"/OpenFilesActivity";

    public static final String ORDER_SCAN_ACTIVITY = ORDER +"/ScanActivity";
    public static final String ORDER_COLLECT_ACTIVITY = ORDER+"/ProductCollectActivity";

    //意见反馈
    public static final String ORDER_FEED_BACK_ACTIVITY = ORDER+"/FeedBackActivity";
    public static final String ORDER_EVALUATION_ADD_ACTIVITY = ORDER+"/EvaluationAddActivity";

    //我的评价
    public static final String ORDER_MY_EVALUATION_ACTIVITY = ORDER+"/MyEvaluationActivity";
    public static final String ORDER_EVALUATION_ALL_ACTIVITY = ORDER+"/EvaluationAllActivity";
    public static final String ORDER_EVALUATION_SUCCESS_ACTIVITY = ORDER+"/EvaluationSuccessActivity";



    //h5页面
    public static final String H5_ACTIVITY = "/h5/H5Activity";


}
