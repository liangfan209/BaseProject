package com.clkj.order.requset;


/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/5/28
 * 版权：
 */
public class ApiProduct {
    //热门学校列表
    public static String PRODUCT_HOT_SCHOOL = "university.school.hotsearch";
    //搜索学校列表
    public static String PRODUCT_SEARCH_SCHOOL_LIST = "university.school.search";
    //所有学校
    public static String PRODUCT_SCHOOL_ALL = "university.school.all";
    //热门产品
    public static String PRODUCT_HOT_PRODUCT = "production.goods.hotsearch";

    //热门专业
    public static String PRODUCT_HOT_PROFESSION = "university.major.hotsearch";

    //所有专业
    public static String PRODUCT_PROFESSION_SEARCH_ALL = "university.major.search";

    //合同列表
    public static String PRODUCT_CONTRACT_SEARCH_ALL = "customer.contract.search";

    //banner
    public static String PRODUCT_BANNEL_LIST = "adsense.advertisement.search";

    //学校专业列表
    public static String PRODUCT_SCHOOL_PROFESSION_LIST = "university.relations.searchmajor";
    //专业学校列表
    public static String PRODUCT_PROFESSION_SCHOOL_LIST = "university.relations.searchschool";

    //产品搜索列表
    public static String PRODUCT_PRODUCT_LIST = "production.goods.search";
    //收藏列表
    public static String PRODUCT_COLLECT_PRODUCT_LIST = "customer.collection.search";
    //所有专业
    public static String PRODUCT_PROFESSION_ALL = "university.major.all";
    //所有学年
    public static String PRODUCT_DURATION_ALL = "university.major.duration";
    //获取商品详情
    public static String PRODUCT_DETAIL = "production.goods.get";

    public static String PRODUCT_POSTER_DETAIL = "production.poster.get";


    public static String SCHOOL_DETAIL = "university.school.get";


    //客户下单接口
    public static String ORDER_ADD = "customer.order.add";
    //二维码下单
    public static String ORDER_POSTER_ADD = "customer.order.posteradd";

    public static String ORDER_GET_DETAIL = "customer.order.get";
    //订单列表
    public static String ORDER_LIST = "customer.order.search";
    //取消订单
    public static String ORDER_CANCEL_ORDER = "customer.order.cancel";

    //签署合同
    public static String ORDER_ADD_CONTRACT = "customer.contract.autograph";

    public static String ORDER_GET_CONTRACT = "customer.contract.get";
    //创建合同
    public static String ORDER_CREATE_CONTRACT = "customer.contract.add";
    //在线更新
    public static final String UPDATE_APP = "edition.get";

    //收藏与取消
    public static final String ORDER_PRODUCT_COLLECT = "customer.collection.collection";



    public static final String ORDER_FEED_BACK = "customer.feedback.add";
    //添加评论
    public static final String ORDER_EVALUATION_ADD = "customer.order.addevaluation";

    //我的评价列表
    public static final String ORDER_EVALUATION_LIST = "customer.order.myevaluations";
    //所有评价
    public static final String ORDER_EVALUATION_ALL = "customer.order.allevaluations";
    //搜索评价
    public static final String ORDER_EVALUATION_SEARCH_ALL = "customer.order.searchevaluations";


}
