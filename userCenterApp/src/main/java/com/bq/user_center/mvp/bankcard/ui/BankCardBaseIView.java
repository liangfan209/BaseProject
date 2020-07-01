package com.bq.user_center.mvp.bankcard.ui;

import com.bq.comm_config_lib.mvp.BaseIView;
import com.bq.user_center.requset.bean.BankCardInfo;

import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/11
 * 版权：
 */
public interface BankCardBaseIView extends BaseIView {
    //获取列表
    default void getBankListView(List<BankCardInfo> list){};
    default void addBankSuccess(){};
    default void removeSuccess(){};
}
