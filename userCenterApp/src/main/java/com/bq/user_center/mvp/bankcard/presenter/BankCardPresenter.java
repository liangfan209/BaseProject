package com.bq.user_center.mvp.bankcard.presenter;

import com.bq.comm_config_lib.mvp.BasePersenter;
import com.bq.comm_config_lib.request.AbstractReqeustCallback;
import com.bq.user_center.mvp.bankcard.ui.BankCardBaseIView;
import com.bq.user_center.requset.UserCenterHttpReqeustImp;
import com.bq.user_center.requset.bean.BankCard;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/11
 * 版权：
 */
public class BankCardPresenter implements BasePersenter {
    private BankCardBaseIView mBankCardView;
    public BankCardPresenter(BankCardBaseIView bankCardView) {
        mBankCardView = bankCardView;
    }



    public void getBankList(SmartRefreshLayout layout) {
        new UserCenterHttpReqeustImp().getBankList(new AbstractReqeustCallback<List<BankCard>>(mBankCardView) {
            @Override
            public void onSuccess(List<BankCard> list) {
                mBankCardView.getBankListView(list);
            }
        });
    }

    public void addBank(){
    }

}
