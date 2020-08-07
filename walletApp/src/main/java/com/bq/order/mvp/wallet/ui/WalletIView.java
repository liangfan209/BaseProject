package com.bq.order.mvp.wallet.ui;

import com.bq.comm_config_lib.mvp.BaseIView;
import com.bq.order.api.bean.BankCard;
import com.bq.order.requset.bean.TransactionInfo;
import com.bq.order.requset.bean.TransactionMonthInfo;
import com.bq.order.requset.bean.WxBean;

import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/29/029
 * 版权：
 */
public interface WalletIView extends BaseIView {
    default void getBankList(List<BankCard> list){};
    //交易列表
    default void transactionListView(int page, List<TransactionInfo> list){};

    default void loadComplete(){};

    //交易月份列表
    default void transactionMonthListView(List<TransactionMonthInfo> list){};

     //交易详情
     default void transactionDetailView(TransactionInfo info){};
     //充值
     default void rechargeView(WxBean.PayInfo info){};
     //提现
     default void tackCashView(){};
     //查询余额
     default void getBalanceView(long balance){};

}