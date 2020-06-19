package com.bq.user_center.mvp.bankcard.ui;

import com.bq.comm_config_lib.mvp.IView;
import com.bq.user_center.requset.bean.BankCard;

import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/11
 * 版权：
 */
public interface BankCardIView extends IView {
    //获取列表
    default void getBankListView(List<BankCard> list){};
    default void addBank(BankCard info){};
    default void deleteBank(String bankId){};
}
