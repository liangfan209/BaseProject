package com.bq.user_center.ui.bankcard;

import com.bq.comm_config_lib.ui.BaseAcitivty;
import com.bq.user_center.bean.BankCard;
import com.bq.user_center.mvp.bankcard.BankCardPersenter;
import com.bq.user_center.mvp.bankcard.BankCardIView;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/11
 * 版权：
 */
public class AddBankActivity extends BaseAcitivty implements BankCardIView {

    BankCardPersenter mBankCardPersenter;

    @Override
    public void addBank(BankCard info) {
        //弹出吐司
        finish();
    }

    @Override
    protected int getContentViewLayout() {
        return 0;
    }

    @Override
    protected void attach() {
        mBankCardPersenter = new BankCardPersenter(this);
        mBankCardPersenter.addBank();
    }
}
