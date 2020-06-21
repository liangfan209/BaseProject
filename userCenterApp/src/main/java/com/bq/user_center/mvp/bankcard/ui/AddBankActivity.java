package com.bq.user_center.mvp.bankcard.ui;

import com.bq.comm_config_lib.mvp.ui.BaseAcitivty;
import com.bq.user_center.mvp.bankcard.presenter.BankCardPresenter;
import com.bq.user_center.requset.bean.BankCard;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/11
 * 版权：
 */
public class AddBankActivity extends BaseAcitivty implements BankCardBaseIView {

    BankCardPresenter mBankCardPersenter;

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
        mBankCardPersenter = new BankCardPresenter(this);
        mBankCardPersenter.addBank();
    }
}
