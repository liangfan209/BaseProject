package com.bq.walletapp.mvp.ui;

import com.bq.comm_config_lib.mvp.BaseIView;
import com.bq.walletapp.api.bean.BankCard;
import com.bq.walletapp.api.bean.TransactionInfo;
import com.bq.walletapp.api.bean.TransactionMonthInfo;

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
    default void transactionListView(List<TransactionInfo> list){};

    default void loadComplete(){};

    //交易月份列表
    default void transactionMonthListView(List<TransactionMonthInfo> list){};

     //交易详情
     default void transactionDetailView(){};
     //充值
     default void rechargeView(){};
     //提现
     default void tackCashView(){};
     //查询余额
     default void getBalanceView(long balance){};

}
