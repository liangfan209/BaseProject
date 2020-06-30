package com.bq.walletapp.mvp.ui;

import com.bq.comm_config_lib.mvp.BaseIView;
import com.bq.walletapp.api.bean.BankCard;

import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/29/029
 * 版权：
 */
public interface TackCashIView extends BaseIView {
    default void getBankList(List<BankCard> list){};
}
