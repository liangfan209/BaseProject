package com.bq.order.requset;


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




    //产品搜索列表
    public static String PRODUCT_PRODUCT_LIST = "production.goods.search";
    //所有专业
    public static String PRODUCT_PROFESSION_ALL = "university.major.all";
    //所有学年
    public static String PRODUCT_DURATION_ALL = "university.major.duration";
    //获取商品详情
    public static String PRODUCT_DETAIL = "production.goods.get";


    public static String SCHOOL_DETAIL = "university.school.get";


    //客户下单接口
    public static String ORDER_ADD = "customer.order.add";
    public static String ORDER_GET_DETAIL = "customer.order.get";
    //订单列表
    public static String ORDER_LIST = "customer.order.search";
    //取消订单
    public static String ORDER_CANCEL_ORDER = "customer.order.cancel";

    //添加合同
    public static String ORDER_ADD_CONTRACT = "customer.contract.add";
    public static String ORDER_GET_CONTRACT = "customer.contract.get";



}
